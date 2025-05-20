package Chhtrapati.shasan.AccountHandlerServiceMyTube.authencators;

import Chhtrapati.shasan.AccountHandlerServiceMyTube.models.PasswordResetObject;
import Chhtrapati.shasan.AccountHandlerServiceMyTube.repository.PasswordResetTokenMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class PasswordResetLinkSender {
    @Autowired
    private PasswordResetTokenMongo passwordResetTokenMongo;

    public boolean checkTokenValidity(PasswordResetObject passwordResetObject){
        if(!passwordResetTokenMongo.existsByToken(passwordResetObject.getToken())){
            return false;
        }
        PasswordResetObject passwordResetObject_fromDB = passwordResetTokenMongo.findAllByUsername(passwordResetObject.getUsername());
        return passwordResetObject_fromDB.getToken().equals(passwordResetObject.getToken());
    }

    public void sendResetEmail(String emailID, String username, String resetLink) {
        String tokenURL = "http://localhost:8080/resetPassword/?"+"token="+resetLink+"&username="+username;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailID);
        message.setSubject("Password Reset Request MyTube");
        message.setText("To reset your password, click the link below:\n" + tokenURL);
        getJavaMailSender().send(message);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("sarwadnyacoding@gmail.com");
        mailSender.setPassword("pgifdpgcrbtnrvic");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }


}
