package upm.tfg.b190222.games_service.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {
    private String username;
    private String process;
    private String token;
    private String protectionToken;
}
