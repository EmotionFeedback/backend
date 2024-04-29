package capstone.example.EF.domain.subject;

import capstone.example.EF.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class SubjectCheck {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long checker;

    protected SubjectCheck(){}

    public static SubjectCheck createSubjectCheck(Member member, Long checker){
        SubjectCheck sjCheck = new SubjectCheck();
        sjCheck.member = member;
        sjCheck.checker = checker;

        member.getCheckers().add(sjCheck);
        return sjCheck;
    }
}
