package upm.tfg.b190222.reviews_service.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreationResponse {
    
    private String response;

    public CreationResponse(String response){
        this.response = response;
    }
}
