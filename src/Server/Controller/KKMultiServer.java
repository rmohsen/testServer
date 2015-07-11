package Server.Controller;

import Server.Model.Processor;
import Server.Model.Info;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

public class KKMultiServer extends Processor {

    public final static int PRIMARY_PORT_NUMBER = 8000;

    static ArrayList<Integer> portArray = new ArrayList<Integer>(PRIMARY_PORT_NUMBER);
    protected ArrayList<Info> dataArray = new ArrayList<>();
    private ArrayList<KKMultiServerThread> threads = new ArrayList<>();
    private HashMap<Integer, KKMultiServerThread> threadHashMap = new HashMap<>();
    String password = "1234";

    public ArrayList<Info> getDataArray() {
        return dataArray;
    }

    public void setDataArray(ArrayList<Info> dataArray) {
        this.dataArray = dataArray;
    }

    public HashMap<Integer, KKMultiServerThread> getThreadHashMap() {
        return threadHashMap;
    }

    public void setThreadHashMap(HashMap<Integer, KKMultiServerThread> threadHashMap) {
        this.threadHashMap = threadHashMap;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void run() {
        int portNumber = PRIMARY_PORT_NUMBER;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            KKMultiServerThread thread = new KKMultiServerThread(serverSocket.accept(), this);
            thread.start();
            Info info = thread.getGate().getSelfData();
            threadHashMap.put(info.getUserName(), thread);
            processInfo();
        } catch (IOException e) {

        }
    }

    private void processInfo() {
        for (Info info : dataArray) {

        }
    }
}