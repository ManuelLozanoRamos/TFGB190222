package upm.tfg.b190222.usuarios_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import upm.tfg.b190222.usuarios_service.info.UserInfo;
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
    public ResponseEntity<UserResponse> sendResetPasswordMail(@RequestBody UserInfo userInfo){
        try{
            return changePasswordService.sendResetPasswordMail(userInfo.getMail());
        } catch(Exception e){
            return new ResponseEntity<UserResponse>(new UserResponse("ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping(value = "/usuarios/change/password")
    public ResponseEntity<UserResponse> changePassword(@RequestBody UserInfo userInfo, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        String [] tokenParts;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            tokenParts = token.split(":");

            if(!"CHANGE_PASS".equals(tokenParts[1]) || (!userValidationService.validate(token).equals("VALID") && !userValidationService.validate(token).equals("VALID_ADMIN"))){
                return new ResponseEntity<UserResponse>(new UserResponse("INVALID_TOKEN", null), HttpStatus.UNAUTHORIZED);
            }

        } else {
            return new ResponseEntity<UserResponse>(new UserResponse("INVALID_TOKEN", null), HttpStatus.UNAUTHORIZED);
        }

        try{
            return changePasswordService.changePassword(userInfo.getUsername(), userInfo.getPassword(), tokenParts[0]);
        } catch(Exception e){
            return new ResponseEntity<UserResponse>(new UserResponse("ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }     
    }
}
