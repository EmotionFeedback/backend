package capstone.example.EF.dto.member;

import capstone.example.EF.domain.member.Hobby;
import capstone.example.EF.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SaveHobbyDto {

    private List<String> hobby;

}
