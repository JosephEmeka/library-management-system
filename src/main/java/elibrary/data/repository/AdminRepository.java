package elibrary.data.repository;

import elibrary.data.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository <Admin, String> {
    Optional<Admin> findByUsername(String adminName);
    Admin findByEmail(String email);
}
