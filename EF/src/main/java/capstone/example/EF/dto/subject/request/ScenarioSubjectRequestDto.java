package capstone.example.EF.dto.subject.request;

import capstone.example.EF.domain.live.Content;
import capstone.example.EF.domain.live.LiveEmotion;
import capstone.example.EF.domain.member.Hobby;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ScenarioSubjectRequestDto {
    private List<String> content = new ArrayList<>();
    private List<Integer> imageL = new ArrayList<>();
    private List<Integer> voiceL = new ArrayList<>();


    public ScenarioSubjectRequestDto(List<String> content, List<Integer> image, List<Integer> voice,int point){
        for(int i=point-1; i<=point+1 && i<image.size();i++){
            this.content.add(content.get(i));
            this.imageL.add(image.get(i));
            this.voiceL.add(voice.get(i));
        }
    }
}
