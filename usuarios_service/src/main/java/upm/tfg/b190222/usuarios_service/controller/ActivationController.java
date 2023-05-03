package upm.tfg.b190222.usuarios_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import upm.tfg.b190222.usuarios_service.response.UserResponse;
import upm.tfg.b190222.usuarios_service.service.ActivationService;
import upm.tfg.b190222.usuarios_service.service.UserValidationService;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ActivationController  {
    
    @Autowired
    ActivationService activationService;

     @Autowired
     UserValidationService userValidationService;

    @PostMapping(value = "/usuarios/{user}/activate")
    public ResponseEntity<UserResponse> activate(@PathVariable String user, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println(authorizationHeader);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);

            if(!token.contains("USER_ACTIVATION") || (!userValidationService.validate(token).equals("VALID") && !userValidationService.validate(token).equals("VALID_ADMIN"))){
                return new ResponseEntity<UserResponse>(new UserResponse("INVALID_TOKEN", null), HttpStatus.FORBIDDEN);
            }

        } else {
            return new ResponseEntity<UserResponse>(new UserResponse("INVALID_TOKEN", null), HttpStatus.FORBIDDEN);
        }

        try{
            return activationService.activate(user);
        } catch(Exception e){
            return new ResponseEntity<UserResponse>(new UserResponse("ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }
}
