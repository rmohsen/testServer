package Server.Model;

import judge.Info;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * Created by Lenovo on 7/7/2015.
 */
public class Data extends Transferable implements Serializable  {

    // TODO: Match storedData with map info
    Info StoredData = null ;

    public Data(Socket socket, Processor processor) {
        super(socket,processor);
    }

    public Info getStoredData() {
        return StoredData;
    }

    public void setStoredData(Info storedData) {
        StoredData = storedData;
    }

    @Override
    public void receiver(){
        ObjectInputStream input = null ;
        try {
            input = new ObjectInputStream(socket.getInputStream());
            StoredData = (Info)input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sender(){
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(this);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
