package upm.tfg.b190222.usuarios_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
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

    private static final String SUBJECT_VALIDATION = "Confirme su dirección de correo electrónico en GameRatings";
    private static final String BODY_VALIDATION="Acaba de crear una nueva cuenta en GameRatings.\nConfirme su dirección de correo para comenzar a disfrutar de su nueva cuenta pulsando en el siguiente enlace: http://localhost:8080/users/userToValidate/activate";
    
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public String register(Usuario usuario) {
        try{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
            Root<Usuario> usuarios = cq.from(Usuario.class);

            Predicate p = cb.equal(usuarios.get("username"), usuario.getUsername());

            cq.select(usuarios).where(p);

            Usuario u;
            try{
                u = entityManager.createQuery(cq).getSingleResult();
            } catch(NoResultException e){
                u = null;
            }
        
            if(u == null){
                //Envío del correo de confirmación de mail
                try{
                    Mail.sendMail(usuario.getUsername(), usuario.getMail(), SUBJECT_VALIDATION, BODY_VALIDATION);
                } catch(Exception e){
                    e.printStackTrace();
                    return "MAIL_ERROR";
                }    

                usuario.setPassword(Cifrado.encript(usuario.getPassword()));
                        
                entityManager.persist(usuario);

                return "OK";
            } else {
                return "EXISTS";
            }  
        } catch(Exception e){
            return "ERROR";
        }     
    }
    
}
