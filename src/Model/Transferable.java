package Model;

import Controller.Processor;

import java.net.Socket;

/**
 * Created by Lenovo on 7/7/2015.
 */
public abstract class Transferable {

    protected Processor processor;

    public Transferable(Socket socket, Processor processor){
        this.socket = socket ;
        this.processor = processor;
    }

    protected Socket socket ;

    public abstract void sender();

    public abstract void receiver();

    public Socket getSocket(){
        return socket;
    }

}
