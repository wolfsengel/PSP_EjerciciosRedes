package org.example.ej6.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ContactClient {

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 50695;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter command: ");
            String command = userInput.readLine();

            switch (command) {
                case "ADD" -> {
                    String name = userInput.readLine() == null ? "" : userInput.readLine();
                    String phone = userInput.readLine() == null ? "" : userInput.readLine();
                    output.println(command + " " + name + " " + phone);
                }
                case "REMOVE" -> {
                    String name = userInput.readLine() == null ? "" : userInput.readLine();
                    output.println(command + " " + name);
                }
                case "GET" -> {
                    String name = userInput.readLine() == null ? "" : userInput.readLine();
                    output.println(command + " " + name);
                    //read
                    String response = input.readLine();
                }
                case "LIST" -> output.println(command);
                default -> System.out.println("Invalid command");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
