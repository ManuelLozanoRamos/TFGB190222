package upm.tfg.b190222.usuarios_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.usuarios_service.entity.Usuario;

@Service
public class ActivationService {

    @Autowired
    EntityManager entityManager;
    
    @Transactional
    public String activate(String user){
        try{
            Usuario usuario = entityManager.find(Usuario.class, user, LockModeType.PESSIMISTIC_WRITE);

            if(usuario == null) return "NOT_FOUND";
    
            usuario.setActivado(true);
    
            entityManager.merge(usuario);
    
            return "OK";
        } catch (Exception e){
            return "ERROR";
        }
    }
}