package Server.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

/**
 * Created by Lenovo on 7/8/2015.
 */
public class Gate {
    private ArrayList<Info> infos;
    private String comment;
    private String sendingDestination;
    private String receivingDestination;
    private String command;


    final String hostName = null;

    Info selfData;
    Processor processor;
    int PRIMARY_PORT_NUMBER = 0;
    int MAIN_PORT_NUMBER = 0;
    int CHAT_SEND_PORT_NUMBER = 0;
    int Data_SEND_PORT_NUMBER = 0;
    int Multimedia_SEND_PORT_NUMBER = 0;
    int CHAT_RECEIVE_PORT_NUMBER = 0;
    int Data_RECEIVE_PORT_NUMBER = 0;
    int Multimedia_RECEIVE_PORT_NUMBER = 0;

    public Info getSelfData() {
        return selfData;
    }

    public void setSelfData(Info selfData) {
        this.selfData = selfData;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getHostName() {
        return hostName;
    }

    public ArrayList<Info> getInfos() {
        return infos;
    }

    public void setInfos(ArrayList<Info> infos) {
        this.infos = infos;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSendingDestination() {
        return sendingDestination;
    }

    public void setSendingDestination(String sendingDestination) {
        this.sendingDestination = sendingDestination;
    }

    public String getReceivingDestination() {
        return receivingDestination;
    }

    public void setReceivingDestination(String receivingDestination) {
        this.receivingDestination = receivingDestination;
    }

    private void sendingRequest(PrintWriter out) {
        Formatter formatter = new Formatter(out);
        formatter.format("Knock Knock");
        formatter.flush();
    }

    public Scanner firstConnect(String hostName, int primaryPortNumber) {
        try {
            Socket kkSocket = new Socket(hostName, primaryPortNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));

            //send request to server to get port
            sendingRequest(out);
            return new Scanner(in);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getPortNumber(Scanner scanner) {
        MAIN_PORT_NUMBER = scanner.nextInt();
        getReceivePortNumber(scanner);
        getSendPortNumber(scanner);
    }

    private void getSendPortNumber(Scanner scanner) {
        CHAT_SEND_PORT_NUMBER = scanner.nextInt();
        Data_SEND_PORT_NUMBER = scanner.nextInt();
        Multimedia_SEND_PORT_NUMBER = scanner.nextInt();
    }

    private void getReceivePortNumber(Scanner scanner) {
        CHAT_RECEIVE_PORT_NUMBER = scanner.nextInt();
        Data_RECEIVE_PORT_NUMBER = scanner.nextInt();
        Multimedia_RECEIVE_PORT_NUMBER = scanner.nextInt();
    }

    public void SendChat(String hostName, int chatSendPortNumber) {
        try {
            Chat sendingChat = new Chat(new Socket(hostName, chatSendPortNumber), processor);
            sendingChat.setString(comment);
            Port sendingChatPort = new Port(sendingChat);
            sendingChatPort.run();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SendData(String hostName, int dataSendPortNumber) {
        try {
            Data sendingData = new Data(new Socket(hostName, dataSendPortNumber), processor);
            sendingData.getDataBase().setSelfData(selfData);
            Port sendingDataPort = new Port(sendingData);
            sendingDataPort.run();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SendMain(String hostName, int mainSendPortNumber) {

        try {
            MMain sendingMain = new MMain(new Socket(hostName, mainSendPortNumber), processor);
            sendingMain.setCommand(command);
            Port sendingMainPort = new Port(sendingMain);
            sendingMainPort.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReceiveMain(String hostName, int mainSendPortNumber) {

        try {
            MMain receivingMain = new MMain(new Socket(hostName, mainSendPortNumber), processor);
            receivingMain.setCommand(command);
            Port sendingMainPort = new Port(receivingMain);
            sendingMainPort.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public int getPRIMARY_PORT_NUMBER() {
        return PRIMARY_PORT_NUMBER;
    }

    public void setPRIMARY_PORT_NUMBER(int PRIMARY_PORT_NUMBER) {
        this.PRIMARY_PORT_NUMBER = PRIMARY_PORT_NUMBER;
    }

    public int getMAIN_PORT_NUMBER() {
        return MAIN_PORT_NUMBER;
    }

    public void setMAIN_PORT_NUMBER(int MAIN_PORT_NUMBER) {
        this.MAIN_PORT_NUMBER = MAIN_PORT_NUMBER;
    }

    public int getCHAT_SEND_PORT_NUMBER() {
        return CHAT_SEND_PORT_NUMBER;
    }

    public void setCHAT_SEND_PORT_NUMBER(int CHAT_SEND_PORT_NUMBER) {
        this.CHAT_SEND_PORT_NUMBER = CHAT_SEND_PORT_NUMBER;
    }

    public int getData_SEND_PORT_NUMBER() {
        return Data_SEND_PORT_NUMBER;
    }

    public void setData_SEND_PORT_NUMBER(int data_SEND_PORT_NUMBER) {
        Data_SEND_PORT_NUMBER = data_SEND_PORT_NUMBER;
    }

    public int getMultimedia_SEND_PORT_NUMBER() {
        return Multimedia_SEND_PORT_NUMBER;
    }

    public void setMultimedia_SEND_PORT_NUMBER(int multimedia_SEND_PORT_NUMBER) {
        Multimedia_SEND_PORT_NUMBER = multimedia_SEND_PORT_NUMBER;
    }

    public int getCHAT_RECEIVE_PORT_NUMBER() {
        return CHAT_RECEIVE_PORT_NUMBER;
    }

    public void setCHAT_RECEIVE_PORT_NUMBER(int CHAT_RECEIVE_PORT_NUMBER) {
        this.CHAT_RECEIVE_PORT_NUMBER = CHAT_RECEIVE_PORT_NUMBER;
    }

    public int getData_RECEIVE_PORT_NUMBER() {
        return Data_RECEIVE_PORT_NUMBER;
    }

    public void setData_RECEIVE_PORT_NUMBER(int data_RECEIVE_PORT_NUMBER) {
        Data_RECEIVE_PORT_NUMBER = data_RECEIVE_PORT_NUMBER;
    }

    public int getMultimedia_RECEIVE_PORT_NUMBER() {
        return Multimedia_RECEIVE_PORT_NUMBER;
    }

    public void setMultimedia_RECEIVE_PORT_NUMBER(int multimedia_RECEIVE_PORT_NUMBER) {
        Multimedia_RECEIVE_PORT_NUMBER = multimedia_RECEIVE_PORT_NUMBER;
    }

    public void SendMultimedia(String hostName, int MultimediaSendPortNumber) {
        try {
            Multimedia sendingMultimedia = new Multimedia(new Socket(hostName, MultimediaSendPortNumber), processor);
            sendingMultimedia.setDestination(sendingDestination);
            Port sendingMultimediaPort = new Port(sendingMultimedia);
            sendingMultimediaPort.run();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReceiveChat(String hostName, int chatReceivePortNumber) {
        try {
            Chat receivingChat = new Chat(new Socket(hostName, chatReceivePortNumber), processor);
            Port receivingChatPort = new Port(receivingChat);
            receivingChatPort.setSending(false);
            receivingChatPort.run();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReceiveData(String hostName, int dataReceivePortNumber) {
        try {
            Data receivingData = new Data(new Socket(hostName, dataReceivePortNumber), processor);
            Port receivingDataPort = new Port(receivingData);
            receivingDataPort.setSending(false);
            receivingDataPort.run();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReceiveMultimedia(String hostName, int MultimediaReceivePortNumber) {
        try {
            Multimedia receivingMultimedia = new Multimedia(new Socket(hostName, MultimediaReceivePortNumber), processor);
            Port receivingMultimediaPort = new Port(receivingMultimedia);
            receivingMultimediaPort.setSending(false);
            receivingMultimedia.setDestination(receivingDestination);
            receivingMultimediaPort.run();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Gate(int primaryPortNumber, Processor processor) {
        this.processor = processor;
        PRIMARY_PORT_NUMBER = primaryPortNumber;
    }

    public void initializePortsNumber(int[] portsNumber) {
        MAIN_PORT_NUMBER = portsNumber[0];

        CHAT_SEND_PORT_NUMBER = portsNumber[4];
        Data_SEND_PORT_NUMBER = portsNumber[5];
        Multimedia_SEND_PORT_NUMBER = portsNumber[6];

        CHAT_RECEIVE_PORT_NUMBER = portsNumber[1];
        Data_RECEIVE_PORT_NUMBER = portsNumber[2];
        Multimedia_RECEIVE_PORT_NUMBER = portsNumber[3];
    }

    public void makeConnection(String hostName) {
        Scanner scanner = firstConnect(hostName, PRIMARY_PORT_NUMBER);
        getPortNumber(scanner);
    }

    public void openSendGate(String hostName) {
        SendChat(hostName, CHAT_SEND_PORT_NUMBER);
        SendData(hostName, Data_SEND_PORT_NUMBER);
        SendMultimedia(hostName, Multimedia_SEND_PORT_NUMBER);
    }

    public void openReceiveGate(String hostName) {
        ReceiveChat(hostName, CHAT_RECEIVE_PORT_NUMBER);
        ReceiveData(hostName, Data_RECEIVE_PORT_NUMBER);
        ReceiveMultimedia(hostName, Multimedia_RECEIVE_PORT_NUMBER);
    }

    public void openAllGates() {
        //TODO: choose appropriate destination for HOST_NAME & NUMBER
        String fromServer;
        // make a socket to primary port

        //receive ports number from server

        openReceiveGate(hostName);
        openSendGate(hostName);
        //make sending object

        //make receiving object

        //initialize sending objects

        //initialize receiving objects

        //make needed receiving ports

        //make needed sending ports

        //TODO : make loop

        //initialize receiving port

        // aim port on receiving:

    }
}
