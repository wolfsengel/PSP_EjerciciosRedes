package org.example.ej4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServerGuessGame {
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
                /*BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)*/
                //DATA STREAMS
                DataInputStream reader = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream writer = new DataOutputStream(clientSocket.getOutputStream());
        ) {
            int solucion = new Random().nextInt(100);
            int intentos = 0;
            while (true) {
                String orden = reader.readUTF();
                int numero = reader.readInt();
                System.out.println("Received: "+orden + " " + numero);
                int respuesta = 80;
                switch (orden) {
                    case "HELP":
                        respuesta = 6699;
                        break;
                    case "QUIT":
                        respuesta = 11;
                        clientSocket.close();
                        break;
                    case "ERROR":
                        break;
                    case "NUM":
                        if (intentos == 0) {
                            respuesta = 40;
                        } else if (numero == solucion)  {
                            respuesta = 50;
                        } else if (numero > 50) {
                            respuesta = 25;
                        } else {
                            respuesta = 35;
                        }
                        intentos--;
                        break;
                    case "NEW":
                        solucion = new Random().nextInt(100);
                        intentos = numero;
                        break;
                    default:
                        break;
                }
                writer.writeInt(respuesta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
