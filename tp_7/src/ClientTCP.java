import java.io.*;
import java.net.*;


public class ClientTCP {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println("Bonjour serveur !");
        System.out.println("Réponse du serveur : " + in.readLine());

        socket.close();
    }
}
