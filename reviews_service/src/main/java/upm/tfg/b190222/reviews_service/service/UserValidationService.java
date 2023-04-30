package upm.tfg.b190222.reviews_service.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {

    public String validate(String token){
        try{
            String validationUrl = "http://localhost:8084/api/token/validate?token=param&protectionToken=sonvelbOVnVGDxeXu3XOZZecXQ3gz7iVsWae8DYuOlOhchLrqqld11auFB34SAT2Tl_qh8ntQFJEtLTCyjClqg";
            validationUrl = validationUrl.replaceFirst("param", token);

            URL url = new URL(validationUrl);
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

            return json.get("response").toString();
        } catch(Exception e){
            return "ERROR";
        }
    }
}
