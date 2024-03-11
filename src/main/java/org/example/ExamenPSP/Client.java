package org.example.ExamenPSP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String response = input.readLine();
            System.out.println(response);
            while (true) {
                String command = userInput.readLine();
                output.println(command);
                String response2 = input.readLine();
                System.out.println(response2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
