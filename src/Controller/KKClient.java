package Controller;

import judge.Info;

/**
 * Created by Lenovo on 7/5/2015.
 */
public class KKClient extends Processor {

    int PRIMARY_PORT_NUMBER = 8000;
    Gate gate = new Gate(PRIMARY_PORT_NUMBER,this);
    String hostname = null;

    @Override
    public void run() {
        Info lastInfo = gate.getInfo() ;
        if (lastInfo.isRunning()) {
            //TODO: set appropriate hostname
            //TODO: initial the gate
            gate.makeConnection(hostname);
            gate.openAllGates();
        }
        gate.openSendGate(hostname);
    }
}
