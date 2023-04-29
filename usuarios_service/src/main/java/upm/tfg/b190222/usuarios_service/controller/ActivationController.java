package upm.tfg.b190222.usuarios_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.usuarios_service.response.UserResponse;
import upm.tfg.b190222.usuarios_service.service.ActivationService;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ActivationController  {
    
    @Autowired
    ActivationService activationService;

    @PostMapping(value = "/usuarios/{user}/activate")
    public UserResponse activate(@PathVariable String user, @RequestParam String newPassword){
        try{
            return new UserResponse(activationService.activate(user));
        } catch(Exception e){
            return new UserResponse("ERROR");
        } 
    }
}
