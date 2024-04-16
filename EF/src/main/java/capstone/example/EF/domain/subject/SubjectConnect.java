package capstone.example.EF.domain.subject;

import capstone.example.EF.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class SubjectConnect {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "sjConnect")
    private List<SubjectCheck> checkers = new ArrayList<>();

    @OneToMany(mappedBy = "sjConnect")
    private List<Subject> subjects = new ArrayList<>();


    protected SubjectConnect(){}

    public static SubjectConnect createSubjectConnect(Member member){
        SubjectConnect sjConnect = new SubjectConnect();
        sjConnect.member = member;

        return sjConnect;
    }

}
