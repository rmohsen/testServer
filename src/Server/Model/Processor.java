package Server.Model;


import java.util.ArrayList;

/**
 * Created by Lenovo on 7/9/2015.
 */
public abstract class Processor implements Runnable {
    private DataBase dataBase = new DataBase();

    public DataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public ArrayList<String> getChatArray() {
        return chatArray;
    }

    public void setChatArray(ArrayList<String> chatArray) {
        this.chatArray = chatArray;
    }

    protected ArrayList<String> chatArray = new ArrayList<>();

    public ArrayList<String> getUserArray() {
        return userArray;
    }

    public void setUserArray(ArrayList<String> userArray) {
        this.userArray = userArray;
    }

    private ArrayList<String> userArray = new ArrayList<>();

}
