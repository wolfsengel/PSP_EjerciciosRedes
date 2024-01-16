package org.example.ej3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSquare {
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

            System.out.println("Write the number you want to square: \n");
            double number = 0;
            try {
                number = (double) Double.parseDouble(consoleReader.readLine());
            } catch (Exception e) {
                System.out.println("Invalid mode.");
                return;
            }

            writer.println(number);
            System.out.println("Sent: " + number);

            String numberSq = reader.readLine();
            System.out.println("Received: " + numberSq);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
