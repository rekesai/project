package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private int id;
    private String username;
    private String password;
    private int age;
    private String gender;
    private String email;
}
