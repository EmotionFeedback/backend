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

    private int image;
    private int voice;
    private Long memberId;

    protected LiveEmotion(){}

    public static LiveEmotion createLiveEmotion(LiveRoom liveRoom, int imageL,int voiceL, Long memberId){
        LiveEmotion le = new LiveEmotion();
        le.image = imageL;
        le.voice = voiceL;
        le.liveRoom = liveRoom;
        le.memberId = memberId;
        liveRoom.getLiveEmotions().add(le);
        return le;
    }

}
