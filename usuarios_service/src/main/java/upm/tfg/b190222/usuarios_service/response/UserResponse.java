package upm.tfg.b190222.usuarios_service.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String response;

    public UserResponse(String response){
        this.response = response;
    }
}
