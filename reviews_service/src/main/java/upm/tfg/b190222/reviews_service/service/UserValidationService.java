package upm.tfg.b190222.reviews_service.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import upm.tfg.b190222.reviews_service.info.TokenInfo;



@Service
public class UserValidationService {

    private String validationUrl = "http://localhost:8084/api/token/validate";
    private String protectionToken = "sonvelbOVnVGDxeXu3XOZZecXQ3gz7iVsWae8DYuOlOhchLrqqld11auFB34SAT2Tl_qh8ntQFJEtLTCyjClqg";

    public String validate(String token){
        try{
            TokenInfo tokenInfo = new TokenInfo(null, null, token, protectionToken);

            URL url = new URL(validationUrl);
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

            return json.get("response").toString();
        } catch(Exception e){
            return "ERROR";
        }
    }
}
