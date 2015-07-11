package Server.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * Created by Lenovo on 7/7/2015.
 */
public class Data extends Transferable implements Serializable {

    // TODO: Match storedData with map info

    DataBase dataBase;

    public DataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Data(Socket socket, Processor processor) {
        super(socket, processor);
    }

    @Override
    public void receiver() {
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(socket.getInputStream());
            this.dataBase = (DataBase) input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sender() {
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(dataBase);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
