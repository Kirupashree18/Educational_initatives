// File: chatei.java
import java.util.*;

// ---------------- MAIN CLASS ----------------
public class chat {
    public static void main(String[] args) {
        ChatRoomManager manager = ChatRoomManager.getInstance();

        // Create or join a chat room
        ChatRoom room1 = manager.getRoom("Room123");

        // Users
        User alice = new User("Alice");
        User bob = new User("Bob");
        User charlie = new User("Charlie");

        room1.join(alice);
        room1.join(bob);
        room1.join(charlie);

        // Communication protocols
        CommunicationAdapter wsAdapter = new WebSocketAdapter();
        CommunicationAdapter httpAdapter = new HttpAdapter();

        // Sending messages
        wsAdapter.sendMessage(room1, alice, "Hello, everyone!");
        httpAdapter.sendMessage(room1, bob, "How's it going?");
        wsAdapter.sendMessage(room1, charlie, "Goodbye!");

        // Show active users
        System.out.println("Active Users in " + room1.getRoomId() + ": " + room1.getActiveUsers());

        // User leaves
        room1.leave(bob);
        System.out.println("Active Users now: " + room1.getActiveUsers());
    }
}

// ---------------- OBSERVER PATTERN ----------------
interface Observer {
    void update(String message);
}

class User implements Observer {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void update(String message) {
        System.out.println("[" + name + " received] " + message);
    }
}

// ---------------- SUBJECT ----------------
class ChatRoom {
    private String roomId;
    private List<Observer> users = new ArrayList<>();
    private List<String> messageHistory = new ArrayList<>();

    public ChatRoom(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void join(User user) {
        users.add(user);
        broadcast("[SYSTEM]: " + user.getName() + " joined the room.");
        sendHistory(user);
    }

    public void leave(User user) {
        users.remove(user);
        broadcast("[SYSTEM]: " + user.getName() + " left the room.");
    }

    public void broadcast(String message) {
        messageHistory.add(message);
        for (Observer u : users) {
            u.update(message);
        }
    }

    public List<String> getActiveUsers() {
        List<String> names = new ArrayList<>();
        for (Observer o : users) {
            if (o instanceof User) {
                names.add(((User) o).getName());
            }
        }
        return names;
    }

    private void sendHistory(User user) {
        System.out.println("-- Message History for " + user.getName() + " --");
        for (String msg : messageHistory) {
            System.out.println(msg);
        }
        System.out.println("-------------------------");
    }
}

// ---------------- SINGLETON PATTERN ----------------
class ChatRoomManager {
    private static ChatRoomManager instance;
    public static synchronized ChatRoomManager getInstance() {
        if (instance == null) {
            instance = new ChatRoomManager();
        }
        return instance;
    }

    private Map<String, ChatRoom> rooms = new HashMap<>();

    private ChatRoomManager() {}

    public ChatRoom getRoom(String roomId) {
        return rooms.computeIfAbsent(roomId, ChatRoom::new);
    }
}

// ---------------- ADAPTER PATTERN ----------------
interface CommunicationAdapter {
    void sendMessage(ChatRoom room, User user, String message);
}

class WebSocketAdapter implements CommunicationAdapter {
    @Override
    public void sendMessage(ChatRoom room, User user, String message) {
        room.broadcast("[WebSocket][" + user.getName() + "]: " + message);
    }
}

class HttpAdapter implements CommunicationAdapter {
    @Override
    public void sendMessage(ChatRoom room, User user, String message) {
        room.broadcast("[HTTP][" + user.getName() + "]: " + message);
    }
}
