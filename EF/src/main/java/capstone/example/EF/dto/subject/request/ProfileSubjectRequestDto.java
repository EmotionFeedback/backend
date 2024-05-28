package capstone.example.EF.dto.subject.request;

import capstone.example.EF.domain.member.Hobby;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProfileSubjectRequestDto {

    private List<String> interests;

    public ProfileSubjectRequestDto(String job, List<Hobby> hobby){

        List<String> re = new ArrayList<>();
        for (Hobby hobby1 : hobby) {
            re.add(hobby1.getHobby());
        }

        re.add(job);

        this.interests = re;
    }
}
