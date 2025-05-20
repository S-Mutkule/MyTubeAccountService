package Chhtrapati.shasan.AccountHandlerServiceMyTube.controllers;

import Chhtrapati.shasan.AccountHandlerServiceMyTube.authencators.PasswordResetLinkSender;
import Chhtrapati.shasan.AccountHandlerServiceMyTube.handler.AccountsHandler;
import Chhtrapati.shasan.AccountHandlerServiceMyTube.models.PasswordResetLinkSenderObj;
import Chhtrapati.shasan.AccountHandlerServiceMyTube.models.PasswordResetObject;
import Chhtrapati.shasan.AccountHandlerServiceMyTube.models.UserCredentials;
import Chhtrapati.shasan.AccountHandlerServiceMyTube.repository.PasswordResetTokenMongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
public class PasswordReset {
    @Autowired
    private PasswordResetLinkSender passwordResetLinkSender;
    @Autowired
    private AccountsHandler accountsHandler;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PasswordResetTokenMongo passwordResetTokenMongo;
    @GetMapping("/resetPassword/")
    public ResponseEntity<String> resetPassword(
            @RequestParam("token") String token,
            @RequestParam("username") String username){
        log.debug("Endpoint hit");
        PasswordResetObject passwordResetObject = getPasswordResetObj(username,token);
        if(!passwordResetLinkSender.checkTokenValidity(passwordResetObject)){
            return new ResponseEntity<>("Invalid/Expired token", HttpStatusCode.valueOf(200));
        } else{
            return new ResponseEntity<>("Password Reset token Valid! Give new Password", HttpStatusCode.valueOf(200));
        }
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordResetObject passwordResetObject){
        String newPassword = passwordResetObject.getToken();
        String username = passwordResetObject.getUsername();
        UserCredentials useropt = accountsHandler.getUser(new UserCredentials(username, "", ""));
        if(useropt == null){
            return new ResponseEntity<>("User Not found!", HttpStatusCode.valueOf(401));
        }
        useropt.setPassword(newPassword);
        if(accountsHandler.addUserToDB(useropt))
            return new ResponseEntity<>("Password Updated Successfully!", HttpStatusCode.valueOf(200));
        return new ResponseEntity<>("Error in Updating the Password, Please Try Again.", HttpStatusCode.valueOf(200));
    }
    @PostMapping("/sendResetLink")
    public ResponseEntity<String> sendResetLink(@RequestBody PasswordResetLinkSenderObj linkResetObject){
        UserCredentials userCredentials = accountsHandler.getUser(new UserCredentials("", linkResetObject.getEmailID(), ""));
        String token = UUID.randomUUID().toString();
        if(passwordResetTokenMongo.existsByUsername(userCredentials.getUsername())){
            passwordResetTokenMongo.deleteByUsername(userCredentials.getUsername());
        }
        passwordResetTokenMongo.save(new PasswordResetObject(userCredentials.getUsername(), token));
        passwordResetLinkSender.sendResetEmail(userCredentials.getEmailID(), userCredentials.getUsername(), token);
        return new ResponseEntity<>("Password Reset Link Sent Successfully!", HttpStatusCode.valueOf(200));
    }
    private PasswordResetObject getPasswordResetObj(String token, String emailID){
        return new PasswordResetObject(token, emailID);
    }
}
