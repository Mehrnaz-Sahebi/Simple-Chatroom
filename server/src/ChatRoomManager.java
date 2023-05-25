import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;

public class ChatRoomManager extends Thread {
    private static HashSet<User> users = new HashSet<User>();
    private final Socket currentSocket;

    public ChatRoomManager(Socket client) {
        currentSocket = client;
    }

    @Override
    public void run() {
        try {
            User currentUser = new User(currentSocket);
            String username = (String) currentUser.getInputStream().readObject();
            currentUser.setUsername(username);
            users.add(currentUser);
            System.out.println("User " + username + " entered the chatroom");
            for (User loopUser : users) {
                if (!loopUser.getUsername().equals(username)) {
                    ObjectOutputStream outputStreamToOther = loopUser.getOutputStream();
                    outputStreamToOther.writeObject("newEnter");
                    outputStreamToOther.writeObject(username);
                    System.out.println("sent the notification of " + username + " enter to " + loopUser.getUsername());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
