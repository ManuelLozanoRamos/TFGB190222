package upm.tfg.b190222.usuarios_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.usuarios_service.entity.Usuario;
import upm.tfg.b190222.usuarios_service.response.UserResponse;

@Service
public class ActivationService {

    @Autowired
    EntityManager entityManager;
    
    @Transactional
    public ResponseEntity<UserResponse> activate(String user, String tokenUser){
        if(user == null){
            return new ResponseEntity<UserResponse>(new UserResponse("MISSING_DATA", null), HttpStatus.BAD_REQUEST);
        }
        if(user.isBlank() || user.length() > 20){
            return new ResponseEntity<UserResponse>(new UserResponse("BAD_USER_LENGTH", null), HttpStatus.BAD_REQUEST);
        }

        try{
            Usuario usuario = entityManager.find(Usuario.class, user, LockModeType.PESSIMISTIC_WRITE);

            if(usuario == null) return new ResponseEntity<UserResponse>(new UserResponse("NOT_FOUND", null), HttpStatus.OK);
    
            if(!tokenUser.equals(usuario.getUsername()))  return new ResponseEntity<UserResponse>(new UserResponse("WRONG_USER", null), HttpStatus.FORBIDDEN);

            usuario.setActivado(true);
    
            entityManager.merge(usuario);
    
            return new ResponseEntity<UserResponse>(new UserResponse("OK", null), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<UserResponse>(new UserResponse("ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}