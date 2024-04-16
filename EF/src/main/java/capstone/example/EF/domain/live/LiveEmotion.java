package capstone.example.EF.domain.live;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class LiveEmotion {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "live_room_id")
    private LiveRoom liveRoom;

    private int liveEmotion;

    protected LiveEmotion(){}

    public static LiveEmotion createLiveEmotion(int liveEmotion){
        LiveEmotion le = new LiveEmotion();
        le.liveEmotion = liveEmotion;
        return le;
    }

}
