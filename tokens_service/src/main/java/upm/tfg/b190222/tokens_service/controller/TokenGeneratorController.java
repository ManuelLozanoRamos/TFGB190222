package upm.tfg.b190222.tokens_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.tokens_service.response.TokenResponse;
import upm.tfg.b190222.tokens_service.service.TokenGeneratorService;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class TokenGeneratorController {

    private String secret = "sonvelbOVnVGDxeXu3XOZZecXQ3gz7iVsWae8DYuOlOhchLrqqld11auFB34SAT2Tl_qh8ntQFJEtLTCyjClqg";

    @Autowired
    TokenGeneratorService tokenGeneratorService;


    @GetMapping(value = "/token/generate")
    public ResponseEntity<TokenResponse> generateToken(@RequestParam String username, @RequestParam String process, @RequestParam String protectionToken){
        if(!secret.equals(protectionToken)){
            return new ResponseEntity<TokenResponse>(new TokenResponse("ACCESS_DENIED",  null), HttpStatus.FORBIDDEN);
        }

        try{
            return tokenGeneratorService.generateToken(username, process);
        } catch(Exception e){
            return new ResponseEntity<TokenResponse>(new TokenResponse("ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
  
}
