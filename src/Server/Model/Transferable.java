package Server.Model;

import java.net.Socket;

/**
 * Created by Lenovo on 7/7/2015.
 */
public abstract class Transferable {

    public Processor processor;

    public Transferable(Socket socket, Processor processor){
        this.socket = socket ;
        this.processor = processor;
    }

    public Socket socket ;

    public abstract void sender();

    public abstract void receiver();

    public Socket getSocket(){
        return socket;
    }

}
