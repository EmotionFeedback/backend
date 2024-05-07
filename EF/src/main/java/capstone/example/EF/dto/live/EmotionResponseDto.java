package capstone.example.EF.dto.live;

import capstone.example.EF.domain.live.LiveRoom;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EmotionResponseDto {
    private Long liveRoomId;
    private Long mean;
    private List<Long> emotion = new ArrayList<>();

    public EmotionResponseDto(LiveRoom liveRoom, Long mean, List<Long> emotion){
        this.liveRoomId = liveRoom.getId();
        this.emotion = emotion;
        this.mean = mean;
    }
}
