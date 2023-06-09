package upm.tfg.b190222.usuarios_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.usuarios_service.info.UserInfo;
import upm.tfg.b190222.usuarios_service.response.UserResponse;
import upm.tfg.b190222.usuarios_service.service.LoginService;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/usuarios/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserInfo userInfo){        
        try{
            return loginService.login(userInfo.getUsername(), userInfo.getPassword());
        } catch(Exception e){
            return new ResponseEntity<UserResponse>(new UserResponse("ERROR", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
