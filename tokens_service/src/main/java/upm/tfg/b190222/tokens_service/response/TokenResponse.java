package upm.tfg.b190222.tokens_service.response;

import lombok.Getter;
import lombok.Setter;
import upm.tfg.b190222.tokens_service.entity.Token;

@Getter
@Setter
public class TokenResponse {

    private Token token;
    private String response;

    public TokenResponse(Token token, String response){
        this.token = token;
        this.response = response;
    }
}
