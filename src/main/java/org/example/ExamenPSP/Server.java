package org.example.ExamenPSP;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        int port = 8080;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);

            ExecutorService threadPool = Executors.newFixedThreadPool(10); // Adjust pool size as needed

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Spawn a new thread to handle the client
                threadPool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // Set up input and output streams for communication with the client
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            // Send a welcome message to the client
            writer.println("Available commands: 1. Mandar un Correo  2. Leer tu correo  3. Ver lo que hay en tu FTP  4. Ver lo que hay en FTP Anónimo");
            // Read client commands and perform corresponding actions
            while (true){
                int command = Integer.parseInt(reader.readLine());
                switch (command) {
                    case 1:
                        // Implement sending an email
                        MailSender mailSender = new MailSender(reader, writer);
                        mailSender.startEmailHandler();
                        writer.println("Email sent successfully!");
                        break;
                    case 2:
                        // Implement reading email
                        MailReader mailReader = new MailReader(reader, writer);
                        mailReader.readEmails();
                        writer.println("Here's your email content...");
                        break;
                    case 3:
                        // Implement FTP file listing
                        writer.println("Listing files in FTP...");
                        break;
                    case 4:
                        // Implement anonymous FTP file listing
                        writer.println("Listing files in anonymous FTP...");
                        break;
                    case 5:
                        writer.println("Goodbye!");
                        // Clean up resources
                        reader.close();
                        writer.close();
                        clientSocket.close();
                        return;
                    default:
                        writer.println("Invalid command. Please try again.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
