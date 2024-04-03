package elibrary.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
public class Admin  {
    @Id
    private String adminId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isLoggedIn = false;


}
