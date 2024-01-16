package org.example.ej2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMultiThread {
    private static final int PORT = 57327;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening for incoming connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread thread = new Thread(() -> handleClient(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void handleClient(Socket clientSocket) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            System.out.println("Accepted connection from " + clientSocket.getInetAddress());
            String ch = reader.readLine();
            writer.println(ch.toUpperCase());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
