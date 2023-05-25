import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            String username;
            Socket socket = new Socket("127.0.0.1",2254);
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
            ClientInputManager clientInputManager = new ClientInputManager(inputStream);
            clientInputManager.start();
            while (true){
                Message message = new Message(username,scanner.nextLine());
                if(message.getText().equals("exit")){
                    outputStream.writeObject("Exit");
                    outputStream.writeObject(username);
                    System.out.println("Bye!");
                    System.exit(0);
                }
                else {
                    outputStream.writeObject("sendMessage");
                    outputStream.writeObject(message);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
