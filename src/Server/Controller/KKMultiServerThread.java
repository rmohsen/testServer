package Server.Controller;

import Server.Model.Processor;

import java.io.*;
import java.net.Socket;

public class KKMultiServerThread extends Thread {

    private Socket socket = null;
    private int type;
    Processor processor;

    public Gate getGate() {
        return gate;
    }

    Gate gate = new Gate(KKMultiServer.PRIMARY_PORT_NUMBER,processor);

    public KKMultiServerThread(Socket socket,Processor processor) {
        super("Server.Controller.KKMultiServerThread");
        this.socket = socket;
        this.processor = processor;
    }

    public void run() {
        try {
            String inputLine;
            int[] outputLines;
            synchronized (socket) {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                KnockKnockProtocol kkp = new KnockKnockProtocol();
                inputLine = in.readLine();

                outputLines = kkp.processInput(inputLine, "PRIMARY", this);
                gate.initializePortsNumber(outputLines);
                gate.openAllGates();
                processor.getUserArray().add(gate.getInfo().getUserName());

                for (int i = 0; i < 7; i++) {
                    // TODO: appropriate out
                    out.println(outputLines[i]);
                }
            }

            String location = null;
            // TODO: declare location
            //write file info to disc
            FileWriter fw = new FileWriter(KKMultiServer.class.getResource(location).toString());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
