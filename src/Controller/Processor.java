package Controller;

import Model.Data;

import java.util.ArrayList;

/**
 * Created by Lenovo on 7/9/2015.
 */
public abstract class Processor implements Runnable {
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
