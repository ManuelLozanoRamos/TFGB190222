package upm.tfg.b190222.reviews_service.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteResponse {
    
    private String response;

    public DeleteResponse(String response){
        this.response = response;
    }
}
