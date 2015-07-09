package Model;

import java.lang.*;

/**
 * Created by Lenovo on 7/7/2015.
 */
public class Port implements Runnable {
    Transferable transferable ;
    boolean sending = true ;

    public boolean isSending() {
        return sending;
    }

    public void setSending(boolean sending) {
        this.sending = sending;
    }

    public Port(Chat chat){
        transferable = chat ;
    }

    public Port(Multimedia multimedia){
        transferable = multimedia;
    }

    public Port(Data data){
        transferable = data;
    }

    public Port(MMain main){transferable = main;}

    @Override
    public void run() {
        if (isSending())
            transferable.sender();
        transferable.receiver();
    }
}
