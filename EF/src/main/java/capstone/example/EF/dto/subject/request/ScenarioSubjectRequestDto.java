package capstone.example.EF.dto.subject.request;

import capstone.example.EF.domain.live.Content;
import capstone.example.EF.domain.live.LiveEmotion;
import capstone.example.EF.domain.member.Hobby;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ScenarioSubjectRequestDto {
    private List<String> scenarios = new ArrayList<>();
    private List<Integer> likeabilities = new ArrayList<>();


    public ScenarioSubjectRequestDto(List<String> content, List<Integer> image, List<Integer> voice,int point){
        for(int i=point-1; i<=point+1 && i<image.size();i++){
            this.scenarios.add(content.get(i));
            this.likeabilities.add((image.get(i) + voice.get(i))/2);
        }
    }
}
