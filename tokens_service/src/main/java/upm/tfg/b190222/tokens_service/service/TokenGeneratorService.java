package upm.tfg.b190222.tokens_service.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TokenResponse> generateToken(String username, String process){
        if(username == null || process == null){
            return new ResponseEntity<TokenResponse>(new TokenResponse("MISSING_DATA", null), HttpStatus.BAD_REQUEST);
        }
        if(username.isBlank() || username.length() > 20){
            return new ResponseEntity<TokenResponse>(new TokenResponse("BAD_USERNAME_LENGTH", null), HttpStatus.BAD_REQUEST);
        }
        if(process.isBlank() || process.length() > 15){
            return new ResponseEntity<TokenResponse>(new TokenResponse("BAD_PROCESS_LENGTH", null), HttpStatus.BAD_REQUEST);
        }
        if(!"USER_SESSION".equals(process) && !"USER_ACTIVATION".equals(process) && !"CHANGE_PASS".equals(process)){
            return new ResponseEntity<TokenResponse>(new TokenResponse("WRONG_PROCESS", null), HttpStatus.BAD_REQUEST);
        }

        try{
            String token = "";
            boolean tokenExists = true;
            do{
                token = TokenGenerator.generateToken(username, process);

                Token t = entityManager.find(Token.class, token, LockModeType.PESSIMISTIC_READ);

                if(t == null) tokenExists = false;
            } while(tokenExists);

            LocalDateTime fechaCreacion = LocalDateTime.now();
            LocalDateTime fechaValidez;
            if(process.equals("USER_ACTIVATION") || process.equals("RESET_PASS")) fechaValidez = fechaCreacion.plusDays(1);
            else fechaValidez = fechaCreacion.plusHours(12);

            Token newToken = new Token(token, fechaCreacion, fechaValidez);

            entityManager.persist(newToken);

            return new ResponseEntity<TokenResponse>(new TokenResponse("OK", newToken), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<TokenResponse>(new TokenResponse("ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
