package Server.Model;

import Server.Controller.KKMultiServer;
import Server.Controller.KKMultiServerThread;

import java.util.HashMap;

/**
 * Created by Lenovo on 7/11/2015.
 */
public class KnockKnockProtocol {

    private static int socketNumber = KKMultiServer.PRIMARY_PORT_NUMBER + 1;

    //TODO: care about different sockets requests

    public int[] processInput(String inputLine,String type, KKMultiServerThread serverThread) {
        switch (type) {
            case "PRIMARY":
                int[] portNumber = new int[7];
                int counter = socketNumber;
                socketNumber += 7;
                if (inputLine.equals("Knock Knock")){
                    for (int i = 0; i < 7; i++) {
                        portNumber[i] = counter;
                        counter++;
                    }
                    return portNumber;
                }
                break;
            case "MAIN":
                Gate gate = serverThread.getGate();
                Processor processor = serverThread.getGate().getProcessor();
                KKMultiServer server = (KKMultiServer)processor;
                String password = server.getPassword();
                if (inputLine.equals("PAUSE")){
                    processor.getDataBase().setRunning(false);
                }
                if (inputLine.contains(password+" KICK")){
                    inputLine = inputLine.substring(password.length()+5);
                    HashMap hashMap = server.getThreadHashMap();
                    if (hashMap.containsKey(inputLine) && gate.getSelfData().isAdmin()){
                        hashMap.remove(inputLine);
                    }
                }
                if (inputLine.contains("GET ")){
                    inputLine = inputLine.substring(4);
                }
                gate.setSendingDestination(inputLine);
                gate.SendMultimedia(gate.getHostName(), gate.getMultimedia_SEND_PORT_NUMBER());
                break;
            default:
                break;
        }
        return null;
    }
}

