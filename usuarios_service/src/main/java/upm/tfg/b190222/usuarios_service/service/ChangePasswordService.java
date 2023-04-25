package upm.tfg.b190222.usuarios_service.service;

import org.springframework.beans.factory.annotation.Autowired;
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
import upm.tfg.b190222.usuarios_service.utils.Cifrado;
import upm.tfg.b190222.usuarios_service.utils.Mail;

@Service
public class ChangePasswordService {

    private static final String SUBJECT_VALIDATION = "Solicitud de reseteo de contraseña en GameRatings";
    private static final String BODY_VALIDATION = "La cuenta de GameRatings asociada a este correo electrónico ha solicitado un reseteo de contraseña.\nPulsa en el siguiente enlace para realizar dicho reseteo: http://localhost:8080/users/userTo/change/password";

    @Autowired
    EntityManager entityManager;

    @Transactional
    public String sendResetPasswordMail(String mail){
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
                if(!usuario.isActivado()) return "NOT_VALIDATED";

                //Envío del correo para resetear la contraseña
                try{
                    Mail.sendMail(usuario.getUsername(), usuario.getMail(), SUBJECT_VALIDATION, BODY_VALIDATION);
                } catch(Exception e){
                    e.printStackTrace();
                    return "MAIL_ERROR";
                }    

                return "OK";
            } else {
                return "NOT_EXISTS";
            }  
        } catch(Exception e){
            return "ERROR";
        }
    }

    @Transactional
    public String changePassword(String user, String newPassword){
        try{
            Usuario usuario = entityManager.find(Usuario.class, user, LockModeType.PESSIMISTIC_WRITE);

            if(usuario == null) return "NOT_FOUND";
    
            usuario.setPassword(Cifrado.encript(newPassword));
    
            entityManager.merge(usuario);
    
            return "OK";
        } catch (Exception e){
            return "ERROR";
        }
    }
}
