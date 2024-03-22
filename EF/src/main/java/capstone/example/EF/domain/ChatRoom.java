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
    private User sendUser;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_user_id")
    private User receiveUser;

    protected ChatRoom(){}

    public static ChatRoom craeteChatRoom(User sendUser, User receiveUser) {
        ChatRoom chatRoom = new ChatRoom();

        chatRoom.sendUser = sendUser;
        chatRoom.receiveUser = receiveUser;

        return chatRoom;
    }
}
