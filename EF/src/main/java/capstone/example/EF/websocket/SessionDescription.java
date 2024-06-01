package capstone.example.EF.websocket;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SessionDescription {
    private String type; // "offer" 또는 "answer"
    private String sdp;  // SDP 정보 문자열
}

