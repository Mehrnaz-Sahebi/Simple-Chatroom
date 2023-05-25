import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientInputManager extends Thread{
    private ObjectInputStream inputStream;

    public ClientInputManager(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String inCommand = (String) inputStream.readObject();
                if (inCommand != null && inCommand.equals("newEnter")) {
                    System.out.println((String) inputStream.readObject() + " joined the chat!");
                }
                if (inCommand != null && inCommand.equals("newExit")) {
                    System.out.println((String) inputStream.readObject() + " exited the chat!");
                }
                if (inCommand != null && inCommand.equals("newMessage")) {
                    Message message = (Message) inputStream.readObject();
                    System.out.println(message.getUsername() + " : " + message.getText());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
