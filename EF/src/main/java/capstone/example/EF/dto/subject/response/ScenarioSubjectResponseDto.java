package capstone.example.EF.dto.subject.response;

import capstone.example.EF.domain.live.LiveRoom;
import capstone.example.EF.domain.member.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class ScenarioSubjectResponseDto {
    private Long roomId;
    private List<String> profileSubject;

    public ScenarioSubjectResponseDto(LiveRoom liveRoom, List<String> subject){
        this.roomId = liveRoom.getId();
        this.profileSubject = subject;
    }
}
