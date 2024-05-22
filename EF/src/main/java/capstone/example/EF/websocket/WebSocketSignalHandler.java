package capstone.example.EF.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class WebSocketSignalHandler extends TextWebSocketHandler {

    private final SessionRepository sessionRepositoryRepo = SessionRepository.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String MSG_TYPE_JOIN_ROOM = "join_room";
    private static final String MSG_TYPE_OFFER = "offer";
    private static final String MSG_TYPE_ANSWER = "answer";
    private static final String MSG_TYPE_CANDIDATE = "candidate";

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) {
        log.info("웹소켓 연결됨: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(final WebSocketSession session, final TextMessage textMessage) {
        try {
            WebSocketMessage message = objectMapper.readValue(textMessage.getPayload(), WebSocketMessage.class);
            String userName = message.getSender();
            Long roomId = message.getRoomId();

            log.info("======================================== origin message INFO");
            log.info("==========session.Id : {}, getType : {},  getRoomId :  {}", session.getId(), message.getType(), roomId.toString());

            switch (message.getType()) {
                case MSG_TYPE_JOIN_ROOM:
                    handleJoinRoom(session, message, roomId);
                    break;

                case MSG_TYPE_OFFER:
                case MSG_TYPE_ANSWER:
                case MSG_TYPE_CANDIDATE:
                    handleWebRTCMessage(session, message, roomId);
                    break;

                default:
                    log.info("======================================== DEFAULT");
                    log.info("============== 들어온 타입 : " + message.getType());
            }
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException: {}", e.getMessage());
            sendMessage(session, WebSocketMessage.builder()
                    .type("error")
                    .data("Invalid message format")
                    .build());
        }
    }

    private void handleJoinRoom(WebSocketSession session, WebSocketMessage message, Long roomId) {
        if (sessionRepositoryRepo.hasRoom(roomId)) {
            if (sessionRepositoryRepo.getClientList(roomId).size() >= 2) {
                sendMessage(session, WebSocketMessage.builder()
                        .type("error")
                        .data("Room is full")
                        .build());
                return;
            }
            sessionRepositoryRepo.addClient(roomId, session);
        } else {
            sessionRepositoryRepo.addClientInNewRoom(roomId, session);
        }

        sessionRepositoryRepo.saveRoomIdToSession(session, roomId);
        notifyOtherUserInRoom(session, roomId, message.getSender(), "all_users");
    }

    private void handleWebRTCMessage(WebSocketSession session, WebSocketMessage message, Long roomId) {
        if (sessionRepositoryRepo.hasRoom(roomId)) {
            Map<String, WebSocketSession> clientList = sessionRepositoryRepo.getClientList(roomId);

            if (clientList.size() == 2) {
                for (Map.Entry<String, WebSocketSession> entry : clientList.entrySet()) {
                    if (!entry.getKey().equals(session.getId())) {
                        sendMessage(entry.getValue(), message);
                    }
                }
            }
        }
    }

    private void notifyOtherUserInRoom(WebSocketSession session, Long roomId, String userName, String messageType) {
        Map<String, WebSocketSession> clientList = sessionRepositoryRepo.getClientList(roomId);
        List<String> users = new ArrayList<>();
        for (Map.Entry<String, WebSocketSession> entry : clientList.entrySet()) {
            if (!entry.getValue().equals(session)) {
                users.add(entry.getKey());
            }
        }

        sendMessage(session,
                WebSocketMessage.builder()
                        .type(messageType)
                        .sender(userName)
                        .allUsers(users)
                        .build());
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
        log.info("웹소켓 연결 해제 : {}", session.getId());
        Long roomId = sessionRepositoryRepo.getRoomId(session);

        if (roomId != null) {
            sessionRepositoryRepo.deleteClient(roomId, session);
            sessionRepositoryRepo.deleteRoomIdToSession(session);

            if (sessionRepositoryRepo.getClientList(roomId).isEmpty()) {
                sessionRepositoryRepo.removeRoom(roomId);
            } else {
                notifyOtherUserInRoom(session, roomId, session.getId(), "leave");
            }
        }
    }

    private void sendMessage(WebSocketSession session, WebSocketMessage message) {
        try {
            String json = objectMapper.writeValueAsString(message);
            log.info("========== 발송 to : " + session.getId());
            log.info("========== 발송 내용 : " + json);
            session.sendMessage(new TextMessage(json));
        } catch (IOException e) {
            log.error("============== 발생한 에러 메세지: " + e.getMessage());
        }
    }
}


