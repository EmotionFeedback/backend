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
    private Long maleUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "female_user_id")
    private Long femaleUserId;

    protected LiveRoom(){}

    public static LiveRoom createLiveRoom(Long id, Long maleUserId, Long femaleUserId) {
        LiveRoom liveRoom = new LiveRoom();
        liveRoom.id = id;
        liveRoom.maleUserId = maleUserId;
        liveRoom.femaleUserId = femaleUserId;

        return liveRoom;
    }
}
