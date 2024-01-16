package org.example.ej3;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ClienteSquareMultiThread {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 57327;

    private  static  final double min = 4;
    private  static  final double max = 100;
    private static final int CONSULTAS = 2;

    public static void main(String[] args) throws IOException {
//CON THREADS
        /*for (int i = 0; i < CONSULTAS; i++) {
            Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            Thread thread = new Thread(() -> handleServer(clientSocket));
            thread.start();
        }*/
//CON MULTIPLES INSTANCIAS
        Socket cliSocket = new Socket(SERVER_IP,SERVER_PORT);
        handleServer(cliSocket);
    }
    private static void handleServer(Socket clientSocket) {
        System.out.println("Connected to server \n" + SERVER_IP +":"+SERVER_PORT);
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            //MULTI THREAD
            //Random r = new Random();
            //double numero = min + r.nextDouble() * (max - min);

            //MULTIPLES INSTANCIAS
            System.out.println("Write the number to send: ");
            String numero = consoleReader.readLine();

            System.out.println("Sending: " + numero);
            writer.println(numero);
            String result = reader.readLine();
            System.out.println("Result received from the server: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
