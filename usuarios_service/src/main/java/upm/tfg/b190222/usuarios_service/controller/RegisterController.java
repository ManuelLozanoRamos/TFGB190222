package upm.tfg.b190222.usuarios_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.usuarios_service.entity.Usuario;
import upm.tfg.b190222.usuarios_service.response.UserResponse;
import upm.tfg.b190222.usuarios_service.service.RegisterService;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class RegisterController {
    
    @Autowired
    private RegisterService registerService;

    @PostMapping(value = "/usuarios")
    public UserResponse register(@RequestBody Usuario usuario){
        try{
            return new UserResponse(registerService.register(usuario));
        } catch(Exception e){
            return new UserResponse("ERROR");
        }
    }
}
