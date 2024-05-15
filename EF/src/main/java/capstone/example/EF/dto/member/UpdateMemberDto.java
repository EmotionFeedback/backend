package capstone.example.EF.dto.member;

import capstone.example.EF.domain.member.Mbti;
import capstone.example.EF.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMemberDto {
    private int age;
    private byte[] imgs;
    private String city;
    private Mbti mbti;
    private String job;

}
