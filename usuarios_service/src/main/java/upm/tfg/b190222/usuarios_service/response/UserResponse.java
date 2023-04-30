package upm.tfg.b190222.usuarios_service.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String response;
    private String token;

    public UserResponse(String response, String token){
        this.response = response;
        this.token = token;
    }
}
