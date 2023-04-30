package upm.tfg.b190222.tokens_service.response;

import lombok.Getter;
import lombok.Setter;
import upm.tfg.b190222.tokens_service.entity.Token;

@Getter
@Setter
public class TokenResponse {

    private String response;
    private Token token;

    public TokenResponse(String response, Token token){
        this.response = response;
        this.token = token;
    }
}
