package upm.tfg.b190222.usuarios_service.utils;

import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Mail {

    private static final String USER = "gameratingsweb@gmail.com";
    private static final String PASSWORD = "pledvdulsoeorcne";

    
    public static void sendMail(String username, String to, String subject, String body) throws Exception{
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  
        props.put("mail.smtp.user", USER);
        props.put("mail.smtp.clave", PASSWORD);  
        props.put("mail.smtp.auth", "true");    
        props.put("mail.smtp.starttls.enable", "true"); 
        props.put("mail.smtp.port", "587"); 
        Session session = Session.getDefaultInstance(props);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(to));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));   
        message.setSubject(subject);
        body = body.replaceFirst("userTo", username);
        message.setText(body);

        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", USER, PASSWORD);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

    }
}
