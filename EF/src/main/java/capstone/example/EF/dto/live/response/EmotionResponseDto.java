package capstone.example.EF.dto.live.response;

import capstone.example.EF.domain.live.LiveRoom;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EmotionResponseDto {
    private List<Integer> image = new ArrayList<>();
    private List<Integer> voice = new ArrayList<>();

    public EmotionResponseDto(List<Integer> image,List<Integer> voice){
        this.image = image;
        this.voice = voice;
    }
}
