package capstone.example.EF.dto.live;


import capstone.example.EF.domain.live.LiveRoom;
import capstone.example.EF.domain.member.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class ContentResponseDto {

    private Long roomId;
    private List<String> content;

    public ContentResponseDto(LiveRoom liveRoom, List<String> content){
        this.roomId = liveRoom.getId();
        this.content = content;
    }
}
