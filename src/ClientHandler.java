import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Set<ClientHandler> clients;
    private String clientUserName;

    public ClientHandler(Socket socket, Set<ClientHandler> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                out.println("Enter a unique username to continue : ");
                String potentialUserName = in.readLine();
                if (potentialUserName == null || potentialUserName.trim().isEmpty()) {
                    continue;
                }
                boolean isTaken = false;
                synchronized (clients) {
                    for (ClientHandler client : clients) {
                        if (potentialUserName.equals(client.clientUserName)) {
                            isTaken = true;
                            break;
                        }
                    }
                    if(!isTaken) {
                        this.clientUserName = potentialUserName;
                        broadcast(clientUserName + " joined the chat.");
                        break;

                    }else{
                        out.println("Username is already in use");
                    }


                }

            }

            String message;
            while ((message = in.readLine()) != null) {
                if(message.equalsIgnoreCase("/quit")){
                    out.println(clientUserName+" left the chat.");
                    broadcast(clientUserName + " left the chat.");
                    System.out.println("Client disconnected : "+ socket.getRemoteSocketAddress());
                    break;
                }
                if(message.startsWith("/msg")){
                    String[] msg =  message.split(" ",3);
                    if(msg.length == 3){
                        String recipient = msg[1];
                        String messageToSend = msg[2];
                        sendDirectMessage(recipient,messageToSend);
                    }else{
                        out.println("Invalid command. Use: /msg <username> <message>");
                    }
                }else {

                    System.out.println(clientUserName + ": " + message);
                    broadcast("[ " + clientUserName + " ] : " + message);
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clients.remove(this);
        }
    }

    private void sendDirectMessage(String recipient, String messageToSend) {
        ClientHandler rcp = null;
        boolean recipientFound = false;
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client.clientUserName.equals(recipient)) {
                    rcp = client;
                    recipientFound = true;
                }
            }
        }
        if(!recipientFound){
            out.println(recipient+" is not a valid username to chat.");
        }else{
            rcp.out.println("<Private> [ "+clientUserName+" ] : "+messageToSend);
        }

    }

    private void broadcast(String message) {
        for (ClientHandler client : clients) {
            if (client != this) {
                client.out.println(message);
            }
        }
    }
}
