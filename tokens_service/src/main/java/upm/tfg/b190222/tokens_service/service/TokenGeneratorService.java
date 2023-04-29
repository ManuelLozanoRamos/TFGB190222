package upm.tfg.b190222.tokens_service.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.tokens_service.entity.Token;
import upm.tfg.b190222.tokens_service.response.TokenResponse;
import upm.tfg.b190222.tokens_service.utils.TokenGenerator;

@Service
public class TokenGeneratorService {

    @Autowired
    EntityManager entityManager;
    

    @Transactional
    public TokenResponse generateToken(String username, String process){
        try{
            String token = "";
            boolean tokenExists = true;
            do{
                token = TokenGenerator.generateToken();

                Token t = entityManager.find(Token.class, token, LockModeType.PESSIMISTIC_READ);

                if(t == null) tokenExists = false;
            } while(tokenExists);

            LocalDateTime fechaCreacion = LocalDateTime.now();
            LocalDateTime fechaValidez;
            if(process.equals("USER_ACTIVATION") || process.equals("RESET_PASS")) fechaValidez = fechaCreacion.plusDays(1);
            else fechaValidez = fechaCreacion.plusHours(12);

            Token newToken = new Token(token, process, username, fechaCreacion, fechaValidez);

            entityManager.persist(newToken);

            return new TokenResponse(newToken, "OK");
        } catch(Exception e){
            return new TokenResponse(null, "ERROR");
        }
    }
}
