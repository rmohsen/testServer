package Controller;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class KKMultiServer extends Processor {

    final static int PRIMARY_PORT_NUMBER = 8000;
    static ArrayList<Integer> portArray = new ArrayList<Integer>(PRIMARY_PORT_NUMBER);

    @Override
    public void run() {
        boolean listening = true;
        while (listening) {

            int portNumber = PRIMARY_PORT_NUMBER;
            try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
                new KKMultiServerThread(serverSocket.accept(),this).start();
            } catch (IOException e) {
                System.err.println("Could not listen on port " + portNumber);
                System.exit(-1);
            }
        }
    }
}