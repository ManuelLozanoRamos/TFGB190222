package upm.tfg.b190222.usuarios_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.usuarios_service.response.UserResponse;
import upm.tfg.b190222.usuarios_service.service.LoginService;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping(value = "/usuarios")
    public UserResponse login(@RequestParam String username, @RequestParam String password){
        try{
            return new UserResponse(loginService.login(username, password));
        } catch(Exception e){
            return new UserResponse("ERROR");
        }
    }
}
