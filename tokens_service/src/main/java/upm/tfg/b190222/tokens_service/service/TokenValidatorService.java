package upm.tfg.b190222.tokens_service.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.tokens_service.entity.Token;

@Service
public class TokenValidatorService {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public String validateToken(String token, String username, String process){
        try{
            Token t = entityManager.find(Token.class, token, LockModeType.PESSIMISTIC_READ);

            if(t == null || t.getUsername() != username || t.getProceso() != process || LocalDateTime.now().compareTo(t.getFechaValidez()) > 0)  return "NOT_VALID";

            return "VALID";
        } catch(Exception e){
            return "ERROR";
        }
        
    }
    
}
