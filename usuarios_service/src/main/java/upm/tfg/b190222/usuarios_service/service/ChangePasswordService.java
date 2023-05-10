package upm.tfg.b190222.usuarios_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.usuarios_service.entity.Usuario;
import upm.tfg.b190222.usuarios_service.response.UserResponse;
import upm.tfg.b190222.usuarios_service.utils.Cifrado;
import upm.tfg.b190222.usuarios_service.utils.Mail;

@Service
public class ChangePasswordService {

    private static final String SUBJECT_VALIDATION = "Solicitud de reseteo de contraseña en GameRatings";
    private static final String BODY_VALIDATION = "La cuenta de GameRatings asociada a este correo electrónico ha solicitado restablecer la contraseña.\nPulsa en el siguiente enlace para completar la operación: http://localhost:8080/users/param1/change/password/param2";

    @Autowired
    TokenPetitionService tokenPetitionService;
    
    @Autowired
    EntityManager entityManager;

    @Transactional
    public ResponseEntity<UserResponse> sendResetPasswordMail(String mail){
        if(mail == null) {
            return new ResponseEntity<UserResponse>(new UserResponse("MISSING_DATA", null), HttpStatus.BAD_REQUEST);
        }
        if(mail.isBlank() || mail.length() > 100){
            return new ResponseEntity<UserResponse>(new UserResponse("BAD_MAIL_LENGTH", null), HttpStatus.BAD_REQUEST);
        }

        try{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
            Root<Usuario> usuarios = cq.from(Usuario.class);

            Predicate p = cb.equal(usuarios.get("mail"), mail);

            cq.select(usuarios).where(p);

            Usuario usuario;
            try{
                usuario = entityManager.createQuery(cq).setLockMode(LockModeType.PESSIMISTIC_READ).getSingleResult();
            } catch(NoResultException e){
                usuario = null;
            }
        
            if(usuario != null){
                if(!usuario.isActivado())  return new ResponseEntity<UserResponse>(new UserResponse("NOT_VALIDATED", null), HttpStatus.OK);;

                //Envío del correo para resetear la contraseña
                try{
                    String token = tokenPetitionService.tokenPetition(usuario.getUsername(), "CHANGE_PASS");
                    Mail.sendMail(token, usuario.getUsername(), usuario.getMail(), SUBJECT_VALIDATION, BODY_VALIDATION);
                } catch(Exception e){
                    e.printStackTrace();
                    return new ResponseEntity<UserResponse>(new UserResponse("MAIL_ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
                }    

                return new ResponseEntity<UserResponse>(new UserResponse("OK", null), HttpStatus.OK);
            } else {
                return new ResponseEntity<UserResponse>(new UserResponse("NOT_EXISTS", null), HttpStatus.OK);
            }  
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<UserResponse>(new UserResponse("ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<UserResponse> changePassword(String user, String newPassword, String tokenUser){
        if(user == null || newPassword == null){
            return new ResponseEntity<UserResponse>(new UserResponse("MISSING_DATA", null), HttpStatus.BAD_REQUEST);
        }
        if(user.isBlank() || user.length() > 20){
            return new ResponseEntity<UserResponse>(new UserResponse("BAD_USERNAME_LENGTH", null), HttpStatus.BAD_REQUEST);
        }
        if(newPassword.isBlank() || newPassword.length() > 25){
            return new ResponseEntity<UserResponse>(new UserResponse("BAD_PASSWORD_LENGTH", null), HttpStatus.BAD_REQUEST);
        }

        try{
            Usuario usuario = entityManager.find(Usuario.class, user, LockModeType.PESSIMISTIC_WRITE);

            if(usuario == null) return new ResponseEntity<UserResponse>(new UserResponse("NOT_FOUND", null), HttpStatus.OK);
    
            if(!tokenUser.equals(usuario.getUsername()))  return new ResponseEntity<UserResponse>(new UserResponse("WRONG_USER", null), HttpStatus.FORBIDDEN);

            usuario.setPassword(Cifrado.encript(newPassword));
    
            entityManager.merge(usuario);
    
            return new ResponseEntity<UserResponse>(new UserResponse("OK", null), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<UserResponse>(new UserResponse("ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
