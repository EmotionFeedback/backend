package capstone.example.EF.domain.subject;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class SubjectCheck {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectConnect")
    private SubjectConnect sjConnect;

    private Long check;

    protected SubjectCheck(){}

    public static SubjectCheck createSubjectCheck(SubjectConnect sbjConnect, Long check){
        SubjectCheck sjCheck = new SubjectCheck();
        sjCheck.sjConnect = sbjConnect;
        sjCheck.check = check;

        sbjConnect.getCheckers().add(sjCheck);
        return sjCheck;
    }
}
