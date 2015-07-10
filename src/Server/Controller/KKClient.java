package Server.Controller;

import Server.Model.Processor;
import Logic.Info;

/**
 * Created by Lenovo on 7/5/2015.
 */
public class KKClient extends Processor {

    int PRIMARY_PORT_NUMBER = 8000;
    Gate gate = new Gate(PRIMARY_PORT_NUMBER,this);
    String hostname = "127.1.1.1";

    @Override
    public void run() {
        Info lastInfo = gate.getInfo() ;
        if (lastInfo == null || lastInfo.isRunning()) {
            //TODO: set appropriate hostname
            //TODO: initial the gate
            gate.makeConnection(hostname);
            gate.openAllGates();
        }
        gate.openSendGate(hostname);
    }
}
