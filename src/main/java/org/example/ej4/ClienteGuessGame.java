package org.example.ej4;

import java.io.*;
import java.net.Socket;

public class ClienteGuessGame {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 57327;

    public static void main(String[] args) throws IOException {
        Socket cliSocket = new Socket(SERVER_IP,SERVER_PORT);
        handleServer(cliSocket);
    }
    private static void handleServer(Socket clientSocket) {
        System.out.println("Connected to server \n" + SERVER_IP + ":" + SERVER_PORT);
        try (
                DataInputStream reader = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream writer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Enter your next order:");
            while (true) {
                String[] lineaDeMando = consoleReader.readLine().split(" ");
                String orden = lineaDeMando[0].toUpperCase();
                String numero = lineaDeMando[1];
                int respuesta = 80;

                if (orden.equals("QUIT") || orden.equals("HELP") || orden.equals("ERROR")) {
                    writer.writeUTF(orden);
                    respuesta = reader.readInt();
                } else {
                    writer.writeUTF(orden);
                    writer.writeInt(Integer.parseInt(numero));
                    respuesta = reader.readInt();
                }
                System.out.println(respuesta);

                if (respuesta == 50) {
                    System.out.println("You won!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
