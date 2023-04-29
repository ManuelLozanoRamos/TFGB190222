package upm.tfg.b190222.tokens_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    TokenGeneratorService tokenGeneratorService;


    @GetMapping(value = "/token/generate")
    public TokenResponse generateToken(@RequestParam String username, @RequestParam String process){
        try{
            return tokenGeneratorService.generateToken(username, process);
        } catch(Exception e){
            return new TokenResponse(null, "ERROR");
        }
    }
  
}
