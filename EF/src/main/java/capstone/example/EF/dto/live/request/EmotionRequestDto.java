package capstone.example.EF.dto.live.request;

import capstone.example.EF.domain.live.Content;
import capstone.example.EF.domain.live.LiveEmotion;
import capstone.example.EF.domain.live.LiveRoom;
import lombok.Getter;

@Getter
public class EmotionRequestDto {

    private Integer image;
    private Integer voice;


    public static LiveEmotion toEntity(LiveRoom liveRoom, EmotionRequestDto emotionRequestDto, Long memberId) {
        return LiveEmotion.createLiveEmotion(liveRoom,emotionRequestDto.getImage(), emotionRequestDto.getVoice(),memberId);
    }
}
