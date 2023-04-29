package upm.tfg.b190222.games_service.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {

    private String validationUrl = "localhost:8084/api/token/validate?username=param1&token=param2&process=USER_SESSION";

    public String validate(String username, String token){
        try{
            validationUrl = validationUrl.replaceFirst("param1", username);
            validationUrl = validationUrl.replaceFirst("param2", token);

            URL url = new URL(validationUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();
            while((line=br.readLine()) != null){
                response.append(line);
            }
            br.close();

            JSONObject json = new JSONObject(response.toString());

            return json.get("response").toString();
        } catch(Exception e){
            return "ERROR";
        }
    }
}
