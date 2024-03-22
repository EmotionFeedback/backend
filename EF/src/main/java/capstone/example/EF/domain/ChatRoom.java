package capstone.example.EF.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ChatRoom {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_user_id")
    private Long sendUserId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_user_id")
    private Long receiveUserId;

    protected ChatRoom(){}

    public static ChatRoom craeteChatRoom(Long sendUserId, Long receiveUserId) {
        ChatRoom chatRoom = new ChatRoom();

        chatRoom.sendUserId = sendUserId;
        chatRoom.receiveUserId = receiveUserId;

        return chatRoom;
    }
}
