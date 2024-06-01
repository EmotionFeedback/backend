package capstone.example.EF.websocket;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import capstone.example.EF.websocket.IceCandidate;  // 패키지 경로에 따라 수정 필요

@Getter
@Setter
@Builder
@NoArgsConstructor  // 기본 생성자 추가
public class WebSocketMessage {
    private String type;
    private String sender;
    private Long roomId;
    private String data;
    private List<String> allUsers;
    private IceCandidate candidate;

    public WebSocketMessage(String type, String sender, Long roomId, String data, List<String> allUsers, IceCandidate candidate) {
        this.type = type;
        this.sender = sender;
        this.roomId = roomId;
        this.data = data;
        this.allUsers = allUsers;
        this.candidate = candidate;
    }
}
