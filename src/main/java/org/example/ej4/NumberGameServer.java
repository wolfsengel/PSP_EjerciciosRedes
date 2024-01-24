package org.example.ej4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class NumberGameServer {
    private static final int PORT = 50695;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Number game server is running. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Start a new thread for each client
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (DataInputStream reader = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream writer = new DataOutputStream(clientSocket.getOutputStream());) {

            int secretNumber = -33;
            int remainingTries = -33;

            writer.writeUTF("10 Number game server ready.");

            while (true) {
                String clientInput = reader.readUTF();
                if (clientInput == null) {
                    break;
                }

                String[] parts = clientInput.split(" ");
                String command = parts[0].toUpperCase();

                switch (command) {
                    case "NEW":
                        remainingTries = Integer.parseInt(parts[1]);
                        secretNumber = generateRandomNumber();
                        writer.writeUTF("20 PLAY " + remainingTries);
                        break;
                    case "NUM":
                        if (secretNumber == -33 || remainingTries == -33){
                            writer.writeUTF("80 ERR");
                            break;
                        }
                        int clientGuess = Integer.parseInt(parts[1]);
                        remainingTries--;
                        if (clientGuess == secretNumber) {
                            writer.writeUTF("50 WIN");
                            secretNumber = -33;
                            remainingTries = -33;
                        } else if (clientGuess < secretNumber) {
                            if (remainingTries == 0) {
                                writer.writeUTF("70 LOSE NUM " + secretNumber);
                                secretNumber = -33;
                                remainingTries = -33;
                                break;
                            }else{
                                writer.writeUTF("25 LOW "+remainingTries);

                            }
                        } else {
                            if (remainingTries == 0) {
                                writer.writeUTF("70 LOSE NUM " + secretNumber);
                                secretNumber = -33;
                                remainingTries = -33;
                                break;
                            }else{
                                writer.writeUTF("35 HIGH "+remainingTries);
                            }
                        }
                        break;
                    case "ERROR":
                        secretNumber = -33;
                        remainingTries = -33;
                        writer.writeUTF("15 Number game server ready.");
                        break;
                    case "HELP":
                        writer.writeUTF("""
                                NEW. This command indicates that the client wants to start a new game. As an argument it accept the number of tries the user want to have to guess the number. Example: NEW 8
                                NUM. The client sends its guess to the server. A new game has to be created before using this command. Example: NUM 42.
                                HELP. The client asks the server for information about the game and the commands to use.
                                ERROR. There were some error during communication.
                                QUIT. The client sends the request to terminate the communication with the server.
                                ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                10 Number game server ready. Indicates that the server is ready to receive request from the client.
                                11 BYE. Indicates that the communication with the client is going to be closed.
                                15 Number game server ready. Indicates that the server is ready to begin a new game after a communication error has been detected.
                                20 PLAY <numOp>. Indicates that the game starts and the client has numOp attemps.
                                25 LOW. Indicates that the client's guess is lower than the number to be guessed.
                                35 HIGH. Indicates that the client's guess is higher than the number to be guessed.
                                40 INFO. Informs the client about the game.
                                50 WIN. Indicates that the client has guessed the number exactly.
                                70 LOSE NUM <numGuess>. Indicates that the client has no more attempts to guess the number and informs the client of the number to guess.
                                80 ERR. The command used by the client cannot be used at this time.
                                90 UNKNOWN. The command used by the client is incorrect.
                                """);
                        break;
                    case "QUIT":
                        writer.writeUTF("11 BYE.");
                        clientSocket.close();
                        return;
                    default:
                        writer.writeUTF("90 UNKNOWN.");
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Client disconnected: " + clientSocket);
        }
    }

    private static int generateRandomNumber() {
        return new Random().nextInt(100) + 1;
    }
}
