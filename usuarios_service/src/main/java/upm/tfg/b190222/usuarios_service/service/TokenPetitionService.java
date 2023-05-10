package upm.tfg.b190222.usuarios_service.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import upm.tfg.b190222.usuarios_service.info.TokenInfo;

@Service
public class TokenPetitionService {

    private String tokenPetitionUrl = "http://localhost:8084/api/token/generate";
    private String protectionToken = "sonvelbOVnVGDxeXu3XOZZecXQ3gz7iVsWae8DYuOlOhchLrqqld11auFB34SAT2Tl_qh8ntQFJEtLTCyjClqg";
    
    public String tokenPetition(String username, String process) throws Exception{
        TokenInfo tokenInfo = new TokenInfo(username, process, null, protectionToken);

        URL url = new URL(tokenPetitionUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(new JSONObject(tokenInfo).toString().getBytes());
        outputStream.flush();
        outputStream.close();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuffer response = new StringBuffer();
        while((line=bufferedReader.readLine()) != null){
            response.append(line);
        }
        bufferedReader.close();

        JSONObject json = new JSONObject(response.toString());

        return json.getJSONObject("token").get("token").toString();
    }
}
