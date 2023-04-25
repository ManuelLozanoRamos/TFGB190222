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
import upm.tfg.b190222.usuarios_service.entity.Usuario;
import upm.tfg.b190222.usuarios_service.utils.Cifrado;

@Service
public class LoginService {

    @Autowired
    private EntityManager entityManager;

    public String login(String username, String password){
        try{
            Usuario usuario = entityManager.find(Usuario.class, username, LockModeType.PESSIMISTIC_READ);

            if(usuario == null) return "NO_USER_EXISTS";

            if(Cifrado.decrypt(usuario.getPassword()).equals(password)){
                if(!usuario.isActivado()) return "NOT_VALIDATED";
                else return "OK";
            } 
            else return "BAD_PASS";
        } catch(Exception e){
            return "ERROR";
        }     
    }
}
