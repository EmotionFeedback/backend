package capstone.example.EF.dto.live.response;


import capstone.example.EF.domain.live.LiveRoom;
import capstone.example.EF.domain.member.Member;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ContentResponseDto {

    private Long roomId;
    private List<String> content = new ArrayList<>();
    private List<Integer> millsec = new ArrayList<>();

    public ContentResponseDto(LiveRoom liveRoom, List<String> content, List<Integer> millsec){
        this.roomId = liveRoom.getId();
        this.content = content;
        this.millsec = millsec;
    }
}
