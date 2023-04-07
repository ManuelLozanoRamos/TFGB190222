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

@Service
public class RegisterService {
    
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public String register(Usuario usuario) throws Exception{
        try{
            String username = usuario.getUsername();
            String password = Cifrado.encript(usuario.getPassword());

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
            Root<Usuario> usuarios = cq.from(Usuario.class);

            Predicate p = cb.equal(usuarios.get("username"), username);

            cq.select(usuarios).where(p);

            Usuario u;
            try{
                u = entityManager.createQuery(cq).getSingleResult();
            } catch(NoResultException e){
                u = null;
            }
        
            if(u == null){
                Usuario user = new Usuario();
                user.setUsername(username);
                user.setPassword(password);
                user.setFechaRegistro(usuario.getFechaRegistro());
                        
                entityManager.persist(user);

                return "OK";
            } else {
                return "EXISTS";
            }  
        } catch(Exception e){
            return "ERROR";
        }     
    }
    
}
