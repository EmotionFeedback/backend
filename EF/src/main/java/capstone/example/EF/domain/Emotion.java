package capstone.example.EF.domain;

import capstone.example.EF.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Emotion {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "male_id")
    private Member male;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "female_id")
    private Member female;

    private int maleEmotion;

    private int femaleEmotion;

    protected Emotion(){}

    public static Emotion createEmotion(Member male, Member female, int maleEmotion, int femaleEmotion) {
        Emotion emotion = new Emotion();

        emotion.male = male;
        emotion.female = female;
        emotion.maleEmotion = maleEmotion;
        emotion.femaleEmotion = femaleEmotion;

        return emotion;
    }
}
