package org.example.ej4;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class NumberGameClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 50695;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            Scanner scanner = new Scanner(System.in)) {DataInputStream reader = new DataInputStream(socket.getInputStream());
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            while (true) {
                String serverResponse = reader.readUTF();
                System.out.println(serverResponse);
                if (serverResponse.equals("11 BYE.")) {
                    socket.close();
                    System.exit(0);
                }
                String userInput = scanner.nextLine();
                writer.writeUTF(userInput);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
