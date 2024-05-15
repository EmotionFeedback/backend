package capstone.example.EF.dto.member;

import capstone.example.EF.domain.member.Hobby;
import capstone.example.EF.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveHobbyDto {

    private String hobby;

    public Hobby toEntity(Member member){
        return Hobby.createHobby(hobby,member);
    }
}
