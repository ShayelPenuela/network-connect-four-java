import java.net.*;
import java.io.*;

public class ThreadedServer {
    public static void main(String[] args) {

        int portNumber = 1024;
        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
        ) {
            while (true) {
                Socket client1=serverSocket.accept();
                Socket client2=serverSocket.accept();
                new ConnectionThread(client1,client2).start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }

    }
}
