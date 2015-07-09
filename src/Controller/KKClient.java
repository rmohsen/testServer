package Controller;

/**
 * Created by Lenovo on 7/5/2015.
 */
public class KKClient extends Processor {

    @Override
    public void run() {
        int PRIMARY_PORT_NUMBER = 8000;
        //TODO: set appropriate hostname
        String hostname = null;

        //TODO: initial the gate
        Gate gate = new Gate(PRIMARY_PORT_NUMBER,this);
        gate.makeConnection(hostname);
        gate.openAllGates();
    }
}
