package Chhtrapati.shasan.AccountHandlerServiceMyTube.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "PasswordResetTokensMytube")
@Getter@Setter
public class PasswordResetObject {
    String username;
    @Id
    String id;
    String token;
    public PasswordResetObject(String username, String token){
        setUsername(username);
        setToken(token);
    }
}
