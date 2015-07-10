package Server.Controller;

import Logic.Info;
import Server.Model.Gate;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lenovo on 7/5/2015.
 */
public class KnockKnockProtocol {

    private int socketNumber = KKMultiServer.PRIMARY_PORT_NUMBER + 1;

    //TODO: care about different sockets requests

    public int[] processInput(String inputLine,String type,KKMultiServerThread serverThread) {
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
                KKMultiServer server = (KKMultiServer)serverThread.getGate().getProcessor();
                String password = server.getPassword();
                if (inputLine.equals("PAUSE")){
                    ArrayList<Info> infos = server.getDataArray();
                    Info lastInfo = infos.get(infos.size()-1);
                    lastInfo.setRunning(false);
                    gate.setInfo(lastInfo);
                }
                if (inputLine.contains(password+" KICK")){
                    inputLine = inputLine.substring(password.length()+5);
                    HashMap hashMap = server.getThreadHashMap();
                    if (hashMap.containsKey(inputLine) && gate.getInfo().isAdmin()){
                        hashMap.remove(inputLine);
                    }
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