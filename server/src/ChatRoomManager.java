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
            while (true){
                String inCommand =(String) currentUser.getInputStream().readObject();
                if(inCommand.equals("Exit")){
                    System.out.println("User "+ username+" exited the chatroom");
                    for (User loopUser: users) {
                        if(!loopUser.getUsername().equals(username)){
                            ObjectOutputStream outputStreamToOther = loopUser.getOutputStream();
                            outputStreamToOther.writeObject("newExit");
                            outputStreamToOther.writeObject(username);
                            System.out.println("sent the notification of "+ username+ " exit to "+loopUser.getUsername());
                        }
                    }
                    users.remove(currentUser);
                    break;
                }
                if(inCommand.equals("sendMessage")){
                    Message message = (Message) currentUser.getInputStream().readObject();
                    System.out.println(currentUser+" wrote the message: "+message.getText());
                    for (User loopUser: users){
                        if(!loopUser.getUsername().equals(username)){
                            ObjectOutputStream outputStreamToOther = loopUser.getOutputStream();
                            outputStreamToOther.writeObject("newMessage");
                            outputStreamToOther.writeObject(message);
                            System.out.println("sent "+ message.getUsername()+"'s message to "+loopUser.getUsername());
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
