package capstone.example.EF.domain.live;

import capstone.example.EF.domain.Emotion;
import capstone.example.EF.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class LiveRoom {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "male_user_id")
    private Member maleUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "female_user_id")
    private Member femaleUser;

    @OneToMany(mappedBy = "liveRoom",cascade = CascadeType.ALL)
    private List<LiveEmotion> liveEmotions = new ArrayList<>();

    @OneToMany(mappedBy = "liveRoom",cascade = CascadeType.ALL)
    private List<Content> contents = new ArrayList<>();

    protected LiveRoom(){}

    public static LiveRoom createLiveRoom(Member maleUser, Member femaleUser) {
        LiveRoom liveRoom = new LiveRoom();

        liveRoom.maleUser = maleUser;
        liveRoom.femaleUser = femaleUser;

        return liveRoom;
    }


}
