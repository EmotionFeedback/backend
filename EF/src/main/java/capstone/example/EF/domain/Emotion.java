package capstone.example.EF.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Emotion {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "male_user_id")
    private User male;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "female_user_id")
    private User female;

    private int maleEmotion;

    private int femaleEmotion;

    protected Emotion(){}

    public static Emotion createEmotion(User male, User female, int maleEmotion, int femaleEmotion) {
        Emotion emotion = new Emotion();

        emotion.male = male;
        emotion.female = female;
        emotion.maleEmotion = maleEmotion;
        emotion.femaleEmotion = femaleEmotion;

        return emotion;
    }
}
