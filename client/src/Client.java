import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            String username;
            Socket socket = new Socket("127.0.0.1",2244);
            System.out.println();
            System.out.println("--------------------------------HELLO--------------------------------");
            System.out.println("-----------------------WELCOME TO THE CHATROOM-----------------------");
            System.out.println();
            System.out.println("Please enter your username.");
            Scanner scanner = new Scanner(System.in);
            username = scanner.nextLine();
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeObject(username);
            System.out.println("Start chatting!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
