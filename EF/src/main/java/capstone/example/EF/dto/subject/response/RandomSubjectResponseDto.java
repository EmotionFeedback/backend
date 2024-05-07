package capstone.example.EF.dto.subject.response;

import capstone.example.EF.domain.member.Member;
import lombok.Getter;

@Getter
public class RandomSubjectResponseDto {
    private Long memberId;
    private String randomSubject;

    public RandomSubjectResponseDto(Member member, String subject){
        this.memberId = member.getId();
        this.randomSubject = subject;
    }
}
