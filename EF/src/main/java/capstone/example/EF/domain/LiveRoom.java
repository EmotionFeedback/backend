package capstone.example.EF.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class LiveRoom {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "male_user_id")
    private User maleUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "female_user_id")
    private User femaleUser;

    protected LiveRoom(){}

    public static LiveRoom createLiveRoom(User maleUser, User femaleUser) {
        LiveRoom liveRoom = new LiveRoom();

        liveRoom.maleUser = maleUser;
        liveRoom.femaleUser = femaleUser;

        return liveRoom;
    }
}
