import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER = "localhost"; // Change to server IP if remote
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER, PORT)) {
            System.out.println("Connected to chat server");

            new Thread(new ReadHandler(socket)).start();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            String input;
            while ((input = console.readLine()) != null) {
                out.println(input);
            }
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
}

class ReadHandler implements Runnable {
    private BufferedReader in;

    public ReadHandler(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        String response;
        try {
            while ((response = in.readLine()) != null) {
                System.out.println(response);
            }
        } catch (IOException e) {
            System.out.println("Connection closed.");
        }
    }
}
