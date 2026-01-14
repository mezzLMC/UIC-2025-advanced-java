import java.io.*;
import java.net.*;


public class ServeurTCP {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5000);
        System.out.println("Serveur en attente de connexion...");
        Socket socket = server.accept();
        System.out.println("Client connecté !");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        String message = in.readLine();
        System.out.println("Message reçu : " + message);
        out.println("Message bien reçu !");

        socket.close();
        server.close();
    }
}
