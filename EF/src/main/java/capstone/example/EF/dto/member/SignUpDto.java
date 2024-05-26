package capstone.example.EF.dto.member;

import capstone.example.EF.domain.member.Email;
import capstone.example.EF.domain.member.Hobby;
import capstone.example.EF.domain.member.Mbti;
import capstone.example.EF.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDto {

    private String email;
    private String password;
    private String nickname;
    private String sex;
    private String hobby;
    private int age;
    private String image;
    private String city;
    private Mbti mbti;
    private String job;

    public Member toEntity(String encodedPassword) {

        byte[] imageBytes = null;
        String base64Image = image.split(",")[1]; // 데이터 URI 스키마 제거
        imageBytes = Base64.getDecoder().decode(base64Image);


            return Member.createMember(Email.from(email), encodedPassword,nickname,sex,age,imageBytes,city,mbti,job);
    }
}
