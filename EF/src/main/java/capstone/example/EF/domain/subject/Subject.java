package capstone.example.EF.domain.subject;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Subject {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 255)
    private String subject;

    protected Subject(){}

    public static Subject createSubject(String subject){

        Subject sj = new Subject();
        sj.subject = subject;

        return sj;

    }
}
