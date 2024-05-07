package capstone.example.EF.dto.subject.response;

import capstone.example.EF.domain.member.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileSubjectResponseDto {
    private Long memberId;
    private List<String> profileSubject;

    public ProfileSubjectResponseDto(Member member, List<String> subject){
        this.memberId = member.getId();
        this.profileSubject = subject;
    }
}
