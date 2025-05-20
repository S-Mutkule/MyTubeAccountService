package Chhtrapati.shasan.AccountHandlerServiceMyTube.controllers;

import Chhtrapati.shasan.AccountHandlerServiceMyTube.authencators.CustomAuthenticationProvider;
import Chhtrapati.shasan.AccountHandlerServiceMyTube.models.CustomAuthenticationToken;
import Chhtrapati.shasan.AccountHandlerServiceMyTube.models.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class Login {
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserCredentials userCredentials){
        try {
            customAuthenticationProvider.authenticate(new CustomAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword(), userCredentials.getEmailID()));
                return new ResponseEntity<>("Login SuccessFul", HttpStatusCode.valueOf(200));
        } catch (Exception ex) {
            return new ResponseEntity<>("Login Failed, Please try again. Error : " + ex.getMessage(), HttpStatusCode.valueOf(401));
        }
    }
}
