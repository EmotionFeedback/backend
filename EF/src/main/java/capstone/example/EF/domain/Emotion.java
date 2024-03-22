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
    private Long maleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "female_user_id")
    private Long femaleId;

    private int maleEmotion;

    private int femaleEmotion;

    protected Emotion(){}

    public static Emotion createEmotion(Long maleId, Long femaleId, int maleEmotion, int femaleEmotion) {
        Emotion emotion = new Emotion();

        emotion.maleId = maleId;
        emotion.femaleId = femaleId;
        emotion.maleEmotion = maleEmotion;
        emotion.femaleEmotion = femaleEmotion;

        return emotion;
    }
}
