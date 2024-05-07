package capstone.example.EF.dto.subject.request;

import capstone.example.EF.domain.member.Hobby;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProfileSubjectRequestDto {

    private String job;
    private String hobby;

    public ProfileSubjectRequestDto(String job, List<Hobby> hobby){
        String re = "";
        for (Hobby hobby1 : hobby) {
            re += hobby1.getHobby();
            re += " ";
        }
        this.job = job;
        this.hobby = re;
    }
}
