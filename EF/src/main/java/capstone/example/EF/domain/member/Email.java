package capstone.example.EF.domain.member;

import capstone.example.EF.exception.UserException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
@Embeddable
public class Email {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    private static final Pattern EMAIL_MATCHER = Pattern.compile(EMAIL_PATTERN);

    @Column(name = "email", nullable = false, unique = true,updatable = false)
    private String value;

    private Email(String value){
        this.value = value;
    }

    public static Email from(String value) {
        validateEmailPattern(value);
        return new Email(value);
    }

    private static void validateEmailPattern(String value) {
        if (isNotValidPattern(value)) {
            throw new UserException("이메일 형식에 맞지 않습니다!");
        }
    }

    private static boolean isNotValidPattern(String email) {
        return !EMAIL_MATCHER.matcher(email).matches();
    }

}
