package capstone.example.EF.domain.subject;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Subject {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_connect_id")
    private SubjectConnect sjConnect;

    @Column(length = 255)
    private String subject;

    protected Subject(){}

    public static Subject createSubject(SubjectConnect sjConnect, String subject){

        Subject sj = new Subject();
        sj.sjConnect = sjConnect;
        sj.subject = subject;

        sjConnect.getSubjects().add(sj);

        return sj;

    }
}
