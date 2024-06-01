package capstone.example.EF.websocket;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import capstone.example.EF.websocket.WebSocketMessage;  // 패키지 경로에 따라 수정 필요


@Getter
@Setter
@Builder
@NoArgsConstructor  // 기본 생성자 추가
public class IceCandidate {
    private String candidate;
    private String sdpMid;
    private int sdpMLineIndex;
    private String usernameFragment;

    public IceCandidate(String candidate, String sdpMid, int sdpMLineIndex, String usernameFragment) {
        this.candidate = candidate;
        this.sdpMid = sdpMid;
        this.sdpMLineIndex = sdpMLineIndex;
        this.usernameFragment = usernameFragment;
    }
}
