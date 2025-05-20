package Chhtrapati.shasan.AccountHandlerServiceMyTube.controllers;

import Chhtrapati.shasan.AccountHandlerServiceMyTube.handler.AccountsHandler;
import Chhtrapati.shasan.AccountHandlerServiceMyTube.models.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class Signup {
    @Autowired
    private AccountsHandler accountsHandler;
    @PostMapping("/signup")
    public ResponseEntity<String> signupUser(@RequestBody UserCredentials userCredentials){
        if(accountsHandler.userExists(userCredentials)){
            return new ResponseEntity<>("Username already Taken!", HttpStatusCode.valueOf(200));
        }
        if(accountsHandler.addUserToDB(userCredentials))
            return new ResponseEntity<>("Signup Successful! Please Login to get started", HttpStatusCode.valueOf(200));
        return new ResponseEntity<>("Error in signing up, please try again", HttpStatusCode.valueOf(200));
    }
}
