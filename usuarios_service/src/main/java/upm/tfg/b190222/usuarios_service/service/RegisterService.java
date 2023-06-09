package upm.tfg.b190222.usuarios_service.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.usuarios_service.entity.Usuario;
import upm.tfg.b190222.usuarios_service.response.UserResponse;
import upm.tfg.b190222.usuarios_service.utils.Cifrado;
import upm.tfg.b190222.usuarios_service.utils.Mail;

@Service
public class RegisterService {

    private static final String SUBJECT_VALIDATION = "Confirma tu dirección de correo electrónico en GameRatings";
    private static final String BODY_VALIDATION = "Acabas de crear una nueva cuenta en GameRatings.\nConfirma tu dirección de correo electrónico pulsando en el siguiente enlace para comenzar a disfrutar de tu nueva cuenta: http://localhost:8080/users/param1/activate/param2";
    
    @Autowired
    TokenPetitionService tokenPetitionService;
    
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public ResponseEntity<UserResponse> register(String username, String password, String mail) {
        if(username == null || password == null || mail == null){
            return new ResponseEntity<UserResponse>(new UserResponse("BAD_REQUEST", null), HttpStatus.BAD_REQUEST);
        }
        if(username.isBlank() || username.length() > 20){
            return new ResponseEntity<UserResponse>(new UserResponse("BAD_USERNAME_LENGTH", null), HttpStatus.BAD_REQUEST);
        }
        if(password.isBlank() || password.length() > 25){
            return new ResponseEntity<UserResponse>(new UserResponse("BAD_PASSWORD_LENGTH", null), HttpStatus.BAD_REQUEST);
        }
        if(mail.isBlank() || mail.length() > 100){
            return new ResponseEntity<UserResponse>(new UserResponse("BAD_MAIL_LENGTH", null), HttpStatus.BAD_REQUEST);
        }


        try{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
            Root<Usuario> users = cq.from(Usuario.class);

            Usuario user;

            cq.select(users).where(cb.equal(users.get("mail"), mail));

            try{
                user = entityManager.createQuery(cq).setLockMode(LockModeType.PESSIMISTIC_READ).getSingleResult();
                return new ResponseEntity<UserResponse>(new UserResponse("MAIL_EXISTS", null), HttpStatus.OK);
            } catch (NoResultException e){
            }

            user = entityManager.find(Usuario.class, username, LockModeType.PESSIMISTIC_READ);
        
            if(user != null) return new ResponseEntity<UserResponse>(new UserResponse("USER_EXISTS", null), HttpStatus.OK);

            //Envío del correo de confirmación de mail
            try{
                String token = tokenPetitionService.tokenPetition(username, "USER_ACTIVATION");
                Mail.sendMail(token, username, mail, SUBJECT_VALIDATION, BODY_VALIDATION);
            } catch(Exception e){
                return new ResponseEntity<UserResponse>(new UserResponse("MAIL_ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
            }    

            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setMail(mail);
            usuario.setPassword(Cifrado.encript(password));
            usuario.setActivado(false);
            usuario.setFechaRegistro(LocalDate.now());
                        
            entityManager.persist(usuario);

            return new ResponseEntity<UserResponse>(new UserResponse("OK", null), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<UserResponse>(new UserResponse("ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }     
    }
    
}
