package Chhtrapati.shasan.AccountHandlerServiceMyTube.repository;

import Chhtrapati.shasan.AccountHandlerServiceMyTube.models.PasswordResetObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PasswordResetTokenMongo extends MongoRepository<PasswordResetObject, String> {
    boolean existsByToken(String token);
    PasswordResetObject findAllByUsername(String username);
    void deleteByUsername(String username);
    boolean existsByUsername(String username);
}
