package Controller;

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
                serverThread.getGate().setSendingDestination(inputLine);
                serverThread.getGate().SendMultimedia(serverThread.getGate().getHostName(),serverThread.getGate().getMultimedia_SEND_PORT_NUMBER());
                break;
            default:
                break;
        }

        return null;
    }
}