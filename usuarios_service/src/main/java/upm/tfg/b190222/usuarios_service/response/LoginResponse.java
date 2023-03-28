package upm.tfg.b190222.usuarios_service.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String response;

    public LoginResponse(String response){
        this.response = response;
    }
    
}
