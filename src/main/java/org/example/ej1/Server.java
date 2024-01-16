package org.example.ej1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final int PORT = 57327;

    private static final Map<Character, String> polybiusTable = new HashMap<>();

    static {
        polybiusTable.put('a', "11"); polybiusTable.put('b', "12"); polybiusTable.put('c', "13");
        polybiusTable.put('d', "14"); polybiusTable.put('e', "15"); polybiusTable.put('f', "21");
        polybiusTable.put('g', "22"); polybiusTable.put('h', "23"); polybiusTable.put('i', "24");
        polybiusTable.put('j', "25"); polybiusTable.put('k', "31"); polybiusTable.put('l', "32");
        polybiusTable.put('m', "33"); polybiusTable.put('n', "34"); polybiusTable.put('o', "35");
        polybiusTable.put('p', "41"); polybiusTable.put('q', "42"); polybiusTable.put('r', "43");
        polybiusTable.put('s', "44"); polybiusTable.put('t', "45"); polybiusTable.put('u', "51");
        polybiusTable.put('v', "52"); polybiusTable.put('w', "53"); polybiusTable.put('x', "54");
        polybiusTable.put('y', "55"); polybiusTable.put('z', "61"); polybiusTable.put(' ', "62");
    }

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
            String mode = reader.readLine();
            if (mode.equalsIgnoreCase("E")) {
                System.out.println("Client chose encryption mode.");
            } else if (mode.equalsIgnoreCase("D")) {
                System.out.println("Client chose decryption mode.");
            } else {
                System.out.println("Invalid mode.");
                return;
            }
            String message = reader.readLine();
            System.out.println("Received message from client: " + message);

            String encryptedMessage = mode.equals("E") ? polybiusEncrypt(message) : polybiusDecrypt(message);
            System.out.println("Generated message: " + encryptedMessage);

            writer.println(encryptedMessage);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String polybiusEncrypt(String message) {
        StringBuilder encryptedMessage = new StringBuilder();

        message = message.toLowerCase();

        for (char ch : message.toCharArray()) {
            if (polybiusTable.containsKey(ch)) {
                encryptedMessage.append(polybiusTable.get(ch)).append(" ");
            }
        }

        return encryptedMessage.toString().trim();
    }

    private static String polybiusDecrypt(String message) {
        StringBuilder decryptedMessage = new StringBuilder();

        String[] messageParts = message.split(" ");

        for (String part : messageParts) {
            for (Map.Entry<Character, String> entry : polybiusTable.entrySet()) {
                if (entry.getValue().equals(part)) {
                    decryptedMessage.append(entry.getKey());
                }
            }
        }

        return decryptedMessage.toString();
    }
}
