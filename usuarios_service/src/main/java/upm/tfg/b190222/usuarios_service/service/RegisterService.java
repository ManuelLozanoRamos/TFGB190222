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
public class RegisterService {

    private static final String SUBJECT_VALIDATION = "Confirma tu dirección de correo electrónico en GameRatings";
    private static final String BODY_VALIDATION = "Acabas de crear una nueva cuenta en GameRatings.\nConfirma tu dirección de correo electrónico pulsando en el siguiente enlace para comenzar a disfrutar de tu nueva cuenta: http://localhost:8080/users/userTo/activate";
    
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public String register(Usuario usuario) {
        try{
            Usuario user = entityManager.find(Usuario.class, usuario.getUsername(), LockModeType.PESSIMISTIC_READ);
        
            if(user != null) return "EXISTS";

            //Envío del correo de confirmación de mail
            try{
                Mail.sendMail(usuario.getUsername(), usuario.getMail(), SUBJECT_VALIDATION, BODY_VALIDATION);
            } catch(Exception e){
                return "MAIL_ERROR";
            }    

            usuario.setPassword(Cifrado.encript(usuario.getPassword()));
                        
            entityManager.persist(usuario);

            return "OK";
        } catch(Exception e){
            return "ERROR";
        }     
    }
    
}
