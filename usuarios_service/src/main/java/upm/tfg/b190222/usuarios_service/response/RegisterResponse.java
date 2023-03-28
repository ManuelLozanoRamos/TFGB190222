package upm.tfg.b190222.usuarios_service.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {

    private String response;

    public RegisterResponse(String response){
        this.response = response;
    }
    
}
