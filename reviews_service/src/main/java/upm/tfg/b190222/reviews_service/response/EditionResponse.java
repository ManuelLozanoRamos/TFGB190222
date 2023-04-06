package upm.tfg.b190222.reviews_service.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditionResponse {
    
    private String response;

    public EditionResponse(String response){
        this.response = response;
    }
}
