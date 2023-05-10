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

@Service
public class TokenValidatorService {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public ResponseEntity<TokenResponse> validateToken(String token){
        if(token == null){
            return new ResponseEntity<TokenResponse>(new TokenResponse("MISSING_DATA", null), HttpStatus.BAD_REQUEST);
        }
        if(token.isBlank() || token.length() > 123){
            return new ResponseEntity<TokenResponse>(new TokenResponse("BAD_TOKEN_LENGTH", null), HttpStatus.BAD_REQUEST);
        }

        try{
            Token t = entityManager.find(Token.class, token, LockModeType.PESSIMISTIC_READ);

            if(t == null || LocalDateTime.now().compareTo(t.getFechaValidez()) > 0)  return new ResponseEntity<TokenResponse>(new TokenResponse("NOT_VALID", new Token()), HttpStatus.OK);

            if("admin".equals(token.split(":")[0])) return new ResponseEntity<TokenResponse>(new TokenResponse("VALID_ADMIN", new Token()), HttpStatus.OK);
            return new ResponseEntity<TokenResponse>(new TokenResponse("VALID", null), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<TokenResponse>(new TokenResponse("ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
}
