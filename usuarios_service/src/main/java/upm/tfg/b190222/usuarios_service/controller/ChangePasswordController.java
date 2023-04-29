package upm.tfg.b190222.usuarios_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.usuarios_service.response.UserResponse;
import upm.tfg.b190222.usuarios_service.service.ChangePasswordService;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ChangePasswordController {

    @Autowired
    ChangePasswordService changePasswordService;

    @PostMapping(value = "/usuarios/reset/password/send/mail")
    public UserResponse sendResetPasswordMail(@RequestParam String mail){
        try{
            return new UserResponse(changePasswordService.sendResetPasswordMail(mail));
        } catch(Exception e){
            return new UserResponse("ERROR");
        }
    }
    
    @PutMapping(value = "/usuarios/{user}/change/password")
    public UserResponse changePassword(@PathVariable String user, @RequestParam String newPassword){
        try{
            return new UserResponse(changePasswordService.changePassword(user, newPassword));
        } catch(Exception e){
            return new UserResponse("ERROR");
        }     
    }
}
