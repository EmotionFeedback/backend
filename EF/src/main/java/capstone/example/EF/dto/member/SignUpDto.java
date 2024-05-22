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
    private byte[] image;
    private String city;
    private Mbti mbti;
    private String job;

    public Member toEntity(String encodedPassword) {

        return Member.createMember(Email.from(email), encodedPassword,nickname,sex,age,image,city,mbti,job);
    }
}
