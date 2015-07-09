package Controller;

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


}
