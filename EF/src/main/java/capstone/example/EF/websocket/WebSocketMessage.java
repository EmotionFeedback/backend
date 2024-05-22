package capstone.example.EF.websocket;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class WebSocketMessage {
    private String type;
    private String sender;
    private Long roomId;
    private String data;
    private List<String> allUsers;

    public WebSocketMessage() {
    }

    public WebSocketMessage(String type, String sender, Long roomId, String data, List<String> allUsers) {
        this.type = type;
        this.sender = sender;
        this.roomId = roomId;
        this.data = data;
        this.allUsers = allUsers;
    }
}

