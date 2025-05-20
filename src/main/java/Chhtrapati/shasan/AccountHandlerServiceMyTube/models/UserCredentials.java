package Chhtrapati.shasan.AccountHandlerServiceMyTube.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document(collection = "Accounts")
public class UserCredentials {
    @Id
    private String id;
    private String username;
    private String emailID;
    private String password;
    public UserCredentials(String username, String emailID, String password){
        setUsername(username);
        setPassword(password);
        setEmailID(emailID);
    }
}
