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
    private IceCandidate candidate;  // 이 라인 추가

    public WebSocketMessage() {
    }

    public WebSocketMessage(String type, String sender, Long roomId, String data, List<String> allUsers, IceCandidate candidate) {
        this.type = type;
        this.sender = sender;
        this.roomId = roomId;
        this.data = data;
        this.allUsers = allUsers;
        this.candidate = candidate;  // 이 라인 추가
    }
}

@Getter
@Setter
@Builder
class IceCandidate {
    private String candidate;
    private String sdpMid;
    private int sdpMLineIndex;
    private String usernameFragment;
}
