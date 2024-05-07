package capstone.example.EF.dto.live;

import capstone.example.EF.domain.live.LiveRoom;
import capstone.example.EF.domain.member.Member;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LiveRoomResponseDto {
    private Long liveRoomId;
    private Long maleId;
    private Long femaleId;

    public LiveRoomResponseDto(LiveRoom liveRoom, Member male, Member female){
        this.liveRoomId = liveRoom.getId();
        this.maleId = male.getId();
        this.femaleId = getFemaleId();
    }
}
