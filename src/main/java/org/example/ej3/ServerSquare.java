package org.example.ej3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSquare {
    private static final int PORT = 57327;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening for incoming connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress());

                handleClient(clientSocket);
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
            double numberSq = 0;
            try{
                numberSq = Double.parseDouble(reader.readLine());
            }catch(Exception e){
                System.out.println("Invalid data type");
            }
            System.out.println("Received number from client: " + numberSq);

            double squaredNumber = numberSq*numberSq;
            System.out.println("Generated: " + squaredNumber);

            writer.println(squaredNumber);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
