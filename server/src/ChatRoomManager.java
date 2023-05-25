import java.net.Socket;
import java.util.HashSet;

public class ChatRoomManager extends Thread{
    private static HashSet<User> users = new HashSet<User>();
    private final Socket currentSocket;
    public ChatRoomManager(Socket client){
        currentSocket = client;
    }
    @Override
    public void run(){}
}
