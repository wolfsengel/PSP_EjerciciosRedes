package org.example.ej5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.http.*;
import java.util.Random;

public class RandomWordServerWorker extends Thread {

    private Socket clientSocket;

    public RandomWordServerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            String command = input.readLine();

            if (command != null && command.startsWith("WORD")) {
                int length = 5; // default length
                String[] parts = command.split(" ");
                if (parts.length == 2) {
                    try {
                        length = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException e) {
                        output.println("Invalid command format. Using default length 5.");
                    }
                }

                String randomWord = getRandomWord(length);
                output.println(randomWord);
            } else {
                output.println("Hello! Please use the command format: WORD <number_letters>");
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRandomWord(int length) {
        String apiUrl = "https://random-word-api.herokuapp.com/word";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "?length=" + length))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                String responseBody = response.body();
                String[] words = responseBody.replaceAll("[\"\\[\\]]", "").split(",");
                if (words.length > 0) {
                    Random random = new Random();
                    return words[random.nextInt(words.length)].trim();
                }
            } else {
                System.out.println("API Error: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
