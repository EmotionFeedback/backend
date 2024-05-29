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
        Map<String, WebSocketSession> clients = rooms.get(roomId);
        if (clients == null) {
            clients = new HashMap<>();
            rooms.put(roomId, clients);
        }
        clients.put(session.getId(), session);
        sessionRoomMap.put(session, roomId);
        System.out.println("Client added to room " + roomId + ": " + session.getId());
    }

    public void addClientInNewRoom(Long roomId, WebSocketSession session) {
        Map<String, WebSocketSession> clients = new HashMap<>();
        clients.put(session.getId(), session);
        rooms.put(roomId, clients);
        sessionRoomMap.put(session, roomId);
        System.out.println("New room created with ID " + roomId + " for client: " + session.getId());
    }

    public void saveRoomIdToSession(WebSocketSession session, Long roomId) {
        sessionRoomMap.put(session, roomId);
        System.out.println("Room ID " + roomId + " saved to session: " + session.getId());
    }

    public Long getRoomId(WebSocketSession session) {
        Long roomId = sessionRoomMap.get(session);
        System.out.println("Room ID retrieved for session " + session.getId() + ": " + roomId);
        return roomId;
    }

    public void deleteClient(Long roomId, WebSocketSession session) {
        Map<String, WebSocketSession> clients = rooms.get(roomId);
        if (clients != null) {
            clients.remove(session.getId());
            if (clients.isEmpty()) {
                rooms.remove(roomId);
                System.out.println("Room " + roomId + " removed as it is now empty.");
            }
        }
        sessionRoomMap.remove(session);
        System.out.println("Client removed from room " + roomId + ": " + session.getId());
    }

    public void deleteRoomIdToSession(WebSocketSession session) {
        sessionRoomMap.remove(session);
        System.out.println("Room ID removed from session: " + session.getId());
    }

    public void removeRoom(Long roomId) {
        rooms.remove(roomId);
        System.out.println("Room " + roomId + " removed.");
    }
}