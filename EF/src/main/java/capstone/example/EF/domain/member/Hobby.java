package capstone.example.EF.domain.member;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Hobby {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 10)
    private String hobby;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;

    protected Hobby(){}

    public static Hobby createHobby(String hobby, Member member){
        Hobby hobby1 = new Hobby();
        hobby1.hobby = hobby;
        hobby1.member = member;
        member.getHobbies().add(hobby1);

        return hobby1;
    }
}
