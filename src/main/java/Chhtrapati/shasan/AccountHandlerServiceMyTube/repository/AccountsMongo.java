package Chhtrapati.shasan.AccountHandlerServiceMyTube.repository;

import Chhtrapati.shasan.AccountHandlerServiceMyTube.models.UserCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountsMongo extends MongoRepository<UserCredentials, String> {
    UserCredentials findByUsername(String username);
    boolean existsByUsername(String username);
    UserCredentials findByEmailID(String emailID);
}
