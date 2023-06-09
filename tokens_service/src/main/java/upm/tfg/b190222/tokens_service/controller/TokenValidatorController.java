package upm.tfg.b190222.tokens_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.tokens_service.info.TokenInfo;
import upm.tfg.b190222.tokens_service.response.TokenResponse;
import upm.tfg.b190222.tokens_service.service.TokenValidatorService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class TokenValidatorController {

    private String secret = "sonvelbOVnVGDxeXu3XOZZecXQ3gz7iVsWae8DYuOlOhchLrqqld11auFB34SAT2Tl_qh8ntQFJEtLTCyjClqg";

    @Autowired
    TokenValidatorService tokenValidatorService;


    @PostMapping(value="/token/validate")
    public ResponseEntity<TokenResponse> validateToken(@RequestBody TokenInfo tokenInfo){
        if(!secret.equals(tokenInfo.getProtectionToken())){
            return new ResponseEntity<TokenResponse>(new TokenResponse("ACCESS_DENIED", null), HttpStatus.UNAUTHORIZED);
        }

        try{
            return tokenValidatorService.validateToken(tokenInfo.getToken());
        } catch(Exception e){
            return new ResponseEntity<TokenResponse>(new TokenResponse("ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
