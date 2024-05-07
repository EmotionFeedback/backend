package capstone.example.EF.dto.subject.request;

import capstone.example.EF.domain.live.Content;
import capstone.example.EF.domain.member.Hobby;
import lombok.Getter;

import java.util.List;

@Getter
public class ScenarioSubjectRequestDto {
    private String content;

    public ScenarioSubjectRequestDto(List<Content> content){
        String re = "";
        for (Content content1 : content) {
            re += content1.getContent();
            re += " ";
        }

        this.content = re;
    }
}
