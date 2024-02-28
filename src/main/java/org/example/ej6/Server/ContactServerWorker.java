package org.example.ej6.Server;

import java.net.Socket;

public class ContactServerWorker extends Thread {
    private Socket clientSocket;
    public ContactServerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    public void run() {

    }
}
