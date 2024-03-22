package capstone.example.EF.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Embedded
    private String email;

    @Column(unique = true)
    private String password;
    private int age;
    private byte[] imgs;
    private String city;

    protected User(){}

    public static User createUser(String email, String password, int age, byte[] imgs, String city) {

        User user = new User();

        user.email = email;
        user.password = password;
        user.age = age;
        user.imgs = imgs;
        user.city = city;

        return user;
    }

    public Long updateUserInfo (User user, int age, byte[] imgs, String city){
        user.age = age;
        user.imgs = imgs;
        user.city = city;

        return user.id;
    }

    public User setEncodedPassword(User user, String password){
        user.password = password;

        return user;
    }
}
