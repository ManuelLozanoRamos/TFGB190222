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
import upm.tfg.b190222.usuarios_service.utils.Cifrado;

@Service
public class LoginService {

    @Autowired
    TokenPetitionService tokenPetitionService;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public ResponseEntity<UserResponse> login(String username, String password){
        try{
            Usuario usuario = entityManager.find(Usuario.class, username, LockModeType.PESSIMISTIC_READ);

            if(usuario == null) return new ResponseEntity<UserResponse>(new UserResponse("NO_USER_EXISTS", null), HttpStatus.OK);

            if(Cifrado.decrypt(usuario.getPassword()).equals(password)){
                if(!usuario.isActivado()) return new ResponseEntity<UserResponse>(new UserResponse("NOT_VALIDATED", null), null, HttpStatus.OK);
                else{
                    String token = tokenPetitionService.tokenPetition(username, "USER_SESSION");
                    return new ResponseEntity<UserResponse>(new UserResponse("OK", token), HttpStatus.OK);
                }
            } 
            else return new ResponseEntity<UserResponse>(new UserResponse("BAD_PASS", null), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<UserResponse>(new UserResponse("ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }     
    }
}
