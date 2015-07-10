package Server.Controller;

import Server.Model.Gate;
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
        if (gate.getMAIN_PORT_NUMBER() == 0){
            gate.makeConnection(hostname);
            gate.openAllGates();
        }
        if (lastInfo == null ||lastInfo.isRunning()) {

            //TODO: initial the gate
        }
        gate.openSendGate(hostname);
    }
}
