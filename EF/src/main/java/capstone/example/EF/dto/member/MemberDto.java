package capstone.example.EF.dto.member;

import capstone.example.EF.domain.member.Email;
import capstone.example.EF.domain.member.Mbti;
import capstone.example.EF.domain.member.Member;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private Long id;
    private String email;
    private String nickname;
    private int age;
    private byte[] image;
    private String city;
    private Mbti mbti;
    private String job;

    static public MemberDto toDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail().getValue())
                .nickname(member.getNickname())
                .age(member.getAge())
                .image(member.getImgs())
                .city(member.getCity())
                .mbti(member.getMbti())
                .job(member.getJob())
                .build();
    }

    public Member toEntity() {
        return Member.createNonePasswordMember(Email.from(email),nickname, age, image, city,mbti, job);
    }
}
