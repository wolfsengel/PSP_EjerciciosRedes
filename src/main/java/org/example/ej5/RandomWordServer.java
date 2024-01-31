package org.example.ej5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RandomWordServer {
    private static final int SERVER_PORT = 50695;
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);
                RandomWordServerWorker worker = new RandomWordServerWorker(clientSocket);
                worker.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
