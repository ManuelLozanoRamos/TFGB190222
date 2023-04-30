package upm.tfg.b190222.tokens_service.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {

    private static final int TOKEN_LENGTH = 64;    // Longitud del token en bytes

    
    public static String generateToken(String username, String process) {
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(tokenBytes);

        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
        token = username + ":" + process + ":" + token.replaceAll(":", String.valueOf(random.nextInt(10)));
        
        return token;
    }
}
