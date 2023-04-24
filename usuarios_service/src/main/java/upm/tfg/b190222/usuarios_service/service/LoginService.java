package upm.tfg.b190222.usuarios_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import upm.tfg.b190222.usuarios_service.entity.Usuario;
import upm.tfg.b190222.usuarios_service.utils.Cifrado;

@Service
public class LoginService {

    @Autowired
    private EntityManager entityManager;

    public String login(String username, String password){
        try{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
            Root<Usuario> usuarios = cq.from(Usuario.class);

            Predicate p = cb.equal(usuarios.get("username"), username);

            cq.select(usuarios).where(p);

            Usuario u;
            try{
                u = entityManager.createQuery(cq).getSingleResult();

                if(Cifrado.decrypt(u.getPassword()).equals(password)){
                    if(!u.isActivado()) return "NOT_VALIDATED";
                    else return "OK";
                } 
                else return "BAD_PASS";
            } catch(NoResultException e){
                return "NO_USER_EXISTS";
            } catch(Exception e){
                return "ERROR";
            }
        } catch(Exception e){
            return "ERROR";
        }     
    }
}
