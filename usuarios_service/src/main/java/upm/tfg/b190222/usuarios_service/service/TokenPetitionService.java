package upm.tfg.b190222.usuarios_service.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TokenPetitionService {
    
    public String tokenPetition(String username, String process) throws Exception{
        String tokenPetitionUrl = "http://localhost:8084/api/token/generate?username=param1&process=param2&protectionToken=sonvelbOVnVGDxeXu3XOZZecXQ3gz7iVsWae8DYuOlOhchLrqqld11auFB34SAT2Tl_qh8ntQFJEtLTCyjClqg";
        tokenPetitionUrl = tokenPetitionUrl.replaceFirst("param1", username);
        tokenPetitionUrl = tokenPetitionUrl.replaceFirst("param2", process);

        URL url = new URL(tokenPetitionUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

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
