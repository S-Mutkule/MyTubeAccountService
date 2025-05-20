package Chhtrapati.shasan.AccountHandlerServiceMyTube.models;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal; // Can be username or a user object
    private final Object credentials;
    @Getter
    private final String emailID;

    public CustomAuthenticationToken(String username, String password, String email) {
        super(null);
        this.principal = username;
        this.credentials = password;
        this.emailID = email;
        setAuthenticated(false);
    }

    // getters for username, password, email
    public String getUsername() { return (String) principal; }
    public String getPassword() { return (String) credentials; }

    @Override
    public Object getCredentials() { return credentials; }
    @Override
    public Object getPrincipal() { return principal; }
}

