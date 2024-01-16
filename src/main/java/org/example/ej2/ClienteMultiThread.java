package org.example.ej2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class ClienteMultiThread {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 57327;

    private static final int CONSULTAS = 5;

    public static void main(String[] args) throws IOException {

        for (int i = 0; i < CONSULTAS; i++) {
            Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);
                String letter = String.valueOf((char) ('a' + i));
                Thread thread = new Thread(() -> handleServer(clientSocket, letter));
                thread.start();
        }
    }
    private static void handleServer(Socket clientSocket, String letter) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Connected to the server.\n Sending: " + letter);
            writer.println(letter);
            String result = reader.readLine();
            System.out.println("Result received from the server: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
