package org.example.ej1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 57327;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Connected to the server.");

            System.out.println("(E)Encrypt or (D)Decrypt?: ");
            String mode = consoleReader.readLine();
            if (mode.equalsIgnoreCase("E")) {
                writer.println("E");
            } else if (mode.equalsIgnoreCase("D")) {
                writer.println("D");
            } else {
                System.out.println("Invalid mode.");
                return;
            }
            System.out.print("Enter the message: ");
            String message = consoleReader.readLine();

            writer.println(message);

            String encryptedMessage = reader.readLine();
            System.out.println("Encrypted message received from the server: " + encryptedMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
