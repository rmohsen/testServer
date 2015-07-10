package Server.Controller;

import Logic.Info;

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
                if (inputLine.contains("PASSWORD KICK")){
                    inputLine = inputLine.substring(14);
                    HashMap hashMap = ((KKMultiServer)(serverThread.getGate().getProcessor())).getThreadHashMap();
                    if (hashMap.containsKey(inputLine) && serverThread.getGate().getInfo().isAdmin()){
                        hashMap.remove(inputLine);
                    }
                }
                break;
            case "MAIN":
                if (inputLine.equals("PAUSE")){
                    ArrayList<Info> infos = ((KKMultiServer)serverThread.getGate().getProcessor()).getDataArray();
                    Info lastInfo = infos.get(infos.size()-1);
                    lastInfo.setRunning(false);
                    serverThread.getGate().setInfo(lastInfo);
                }
                Gate gate = serverThread.getGate();
                gate.setSendingDestination(inputLine);
                gate.SendMultimedia(gate.getHostName(), gate.getMultimedia_SEND_PORT_NUMBER());
                break;
            default:
                break;
        }

        return null;
    }
}