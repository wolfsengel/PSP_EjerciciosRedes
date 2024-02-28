package org.example.ej6.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ContactServer {
    private static final int SERVER_PORT = 50695;
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            System.out.println("Server started on port " + SERVER_PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);
                ContactServerWorker worker = new ContactServerWorker(clientSocket);
                worker.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
