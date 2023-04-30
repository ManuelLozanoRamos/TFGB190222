package upm.tfg.b190222.usuarios_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import upm.tfg.b190222.usuarios_service.response.UserResponse;
import upm.tfg.b190222.usuarios_service.service.ChangePasswordService;
import upm.tfg.b190222.usuarios_service.service.UserValidationService;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ChangePasswordController {

    @Autowired
    ChangePasswordService changePasswordService;

    @Autowired
    UserValidationService userValidationService;

    @PostMapping(value = "/usuarios/reset/password/send/mail")
    public ResponseEntity<UserResponse> sendResetPasswordMail(@RequestParam String mail){
        try{
            return changePasswordService.sendResetPasswordMail(mail);
        } catch(Exception e){
            return new ResponseEntity<UserResponse>(new UserResponse("ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping(value = "/usuarios/{user}/change/password")
    public ResponseEntity<UserResponse> changePassword(@PathVariable String user, @RequestParam String newPassword, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);

            if(!token.contains("CHANGE_PASS") || !userValidationService.validate(token).equals("VALID")){
                return new ResponseEntity<UserResponse>(new UserResponse("INVALID_TOKEN", null), HttpStatus.FORBIDDEN);
            }

        } else {
            return new ResponseEntity<UserResponse>(new UserResponse("INVALID_TOKEN", null), HttpStatus.FORBIDDEN);
        }

        try{
            return changePasswordService.changePassword(user, newPassword);
        } catch(Exception e){
            return new ResponseEntity<UserResponse>(new UserResponse("ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }     
    }
}
