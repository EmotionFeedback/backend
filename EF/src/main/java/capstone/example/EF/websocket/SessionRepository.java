package capstone.example.EF.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.util.*;

public class SessionRepository {

    private static SessionRepository instance;
    private final Map<Long, Map<String, WebSocketSession>> rooms = new HashMap<>();
    private final Map<WebSocketSession, Long> sessionRoomMap = new HashMap<>();

    private SessionRepository() {}

    public static synchronized SessionRepository getInstance() {
        if (instance == null) {
            instance = new SessionRepository();
        }
        return instance;
    }

    public boolean hasRoom(Long roomId) {
        return rooms.containsKey(roomId);
    }

    public Map<String, WebSocketSession> getClientList(Long roomId) {
        return rooms.get(roomId);
    }

    public void addClient(Long roomId, WebSocketSession session) {
        rooms.get(roomId).put(session.getId(), session);
        sessionRoomMap.put(session, roomId);
    }

    public void addClientInNewRoom(Long roomId, WebSocketSession session) {
        Map<String, WebSocketSession> clients = new HashMap<>();
        clients.put(session.getId(), session);
        rooms.put(roomId, clients);
        sessionRoomMap.put(session, roomId);
    }

    public void saveRoomIdToSession(WebSocketSession session, Long roomId) {
        sessionRoomMap.put(session, roomId);
    }

    public Long getRoomId(WebSocketSession session) {
        return sessionRoomMap.get(session);
    }

    public void deleteClient(Long roomId, WebSocketSession session) {
        Map<String, WebSocketSession> clients = rooms.get(roomId);
        if (clients != null) {
            clients.remove(session.getId());
            if (clients.isEmpty()) {
                rooms.remove(roomId);
            }
        }
        sessionRoomMap.remove(session);
    }

    public void deleteRoomIdToSession(WebSocketSession session) {
        sessionRoomMap.remove(session);
    }

    public void removeRoom(Long roomId) {
        rooms.remove(roomId);
    }
}
