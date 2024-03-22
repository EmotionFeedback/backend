package capstone.example.EF.domain;

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



}
