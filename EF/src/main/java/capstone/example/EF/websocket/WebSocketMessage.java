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
    private IceCandidate candidate;  // IceCandidate 클래스 필요
    private SessionDescription offer;  // WebRTC 세션 설명 (SDP)의 제안
    private SessionDescription answer; // WebRTC 세션 설명 (SDP)의 응답

    public WebSocketMessage(){}

    public WebSocketMessage(String type, String sender, Long roomId, String data, List<String> allUsers, IceCandidate candidate, SessionDescription offer, SessionDescription answer) {
        this.type = type;
        this.sender = sender;
        this.roomId = roomId;
        this.data = data;
        this.allUsers = allUsers;
        this.candidate = candidate;
        this.offer = offer;
        this.answer = answer;
    }
}
