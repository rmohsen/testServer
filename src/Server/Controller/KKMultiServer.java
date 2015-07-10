package Server.Controller;

import Server.Model.Processor;
import Logic.Info;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

public class KKMultiServer extends Processor {

    final static int PRIMARY_PORT_NUMBER = 8000;
    static ArrayList<Integer> portArray = new ArrayList<Integer>(PRIMARY_PORT_NUMBER);
    public ArrayList<Info> getDataArray() {
        return dataArray;
    }

    public void setDataArray(ArrayList<Info> dataArray) {
        this.dataArray = dataArray;
    }

    protected ArrayList<Info> dataArray = new ArrayList<>();

    private ArrayList<KKMultiServerThread> threads = new ArrayList<>();

    public HashMap<String, KKMultiServerThread> getThreadHashMap() {
        return threadHashMap;
    }

    public void setThreadHashMap(HashMap<String, KKMultiServerThread> threadHashMap) {
        this.threadHashMap = threadHashMap;
    }

    private HashMap<String,KKMultiServerThread> threadHashMap = new HashMap<>();

    public String getPassword() {
        return password;
    }

    String password = "1234";

    @Override
    public void run() {
        boolean listening = true;
        while (listening) {
            int portNumber = PRIMARY_PORT_NUMBER;
            try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
                KKMultiServerThread thread = new KKMultiServerThread(serverSocket.accept(),this);
                thread.start();
                threadHashMap.put(thread.getGate().getInfo().getUserName(), thread);
            } catch (IOException e) {
                System.err.println("Could not listen on port " + portNumber);
                System.exit(-1);
            }
        }
    }
}