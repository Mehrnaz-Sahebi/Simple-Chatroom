import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(2244);
            while (true) {
                Socket client = serverSocket.accept();
                Thread chatRoomManager = new ChatRoomManager(client);
                chatRoomManager.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
