package Chhtrapati.shasan.AccountHandlerServiceMyTube.authencators;

import Chhtrapati.shasan.AccountHandlerServiceMyTube.handler.AccountsHandler;
import Chhtrapati.shasan.AccountHandlerServiceMyTube.models.CustomAuthenticationToken;
import Chhtrapati.shasan.AccountHandlerServiceMyTube.models.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private AccountsHandler accountsHandler;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        String emailID = authentication.getPrincipal().toString();
        UserCredentials user = accountsHandler.getUser(getUserCredsObject(username, password, emailID));
        if(user == null){
            throw new BadCredentialsException("No user found with the username!");
        }
        if(passwordEncoder.matches(password, user.getPassword()))
            return new CustomAuthenticationToken(username, password, emailID);
        throw new BadCredentialsException("Incorrect Password! Please try again.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    private UserCredentials getUserCredsObject(String username, String password, String emailID){
        return new UserCredentials(username, emailID, password);
    }
}

