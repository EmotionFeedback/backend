package capstone.example.EF.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Fetch;

import java.time.LocalDateTime;

@Entity
@Getter
public class Message {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Long roomId;

    private String content;

    private LocalDateTime sendingTime;

    protected Message(){}

    public static Message createMessage(Long roomId, String content, LocalDateTime sendingTime) {
        Message message = new Message();

        message.roomId = roomId;
        message.content = content;
        message.sendingTime = sendingTime;

        return message;
    }
}
