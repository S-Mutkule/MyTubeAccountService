package Chhtrapati.shasan.AccountHandlerServiceMyTube.handler;

import Chhtrapati.shasan.AccountHandlerServiceMyTube.models.PasswordResetObject;
import Chhtrapati.shasan.AccountHandlerServiceMyTube.models.UserCredentials;
import Chhtrapati.shasan.AccountHandlerServiceMyTube.repository.AccountsMongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class AccountsHandler {
    @Autowired
    private AccountsMongo accountsMongo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public boolean userExists(UserCredentials userCredentials){
        return accountsMongo.existsByUsername(userCredentials.getUsername());
    }
    public UserCredentials getUser(UserCredentials userCredentials){
        if(!userCredentials.getUsername().isEmpty())
            return accountsMongo.findByUsername(userCredentials.getUsername());
        return accountsMongo.findByEmailID(userCredentials.getEmailID());
    }
    public boolean addUserToDB(UserCredentials userCredentials){
        try {
            userCredentials.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
            UserCredentials user = accountsMongo.save(userCredentials);
            return true;
        } catch (Exception e) {
            log.error("exception encountered while adding user to the DB. Error Message : ", e.getMessage());
            return false;
        }
    }
}
