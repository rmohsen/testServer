package Server.Model;

import Logic.Fan;
import Logic.GameObjectID;
import Logic.Player;

import java.util.ArrayList;

/**
 * Created by Lenovo on 7/9/2015.
 */
public class Info implements java.io.Serializable {
    private boolean running = true ;

    Player player;
    Fan fan;


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Fan getFan() {
        return fan;
    }

    public void setFan(Fan fan) {
        this.fan = fan;
    }

    public boolean isAdmin() {
        return admin;
    }

//    private GameObjectID objectID = new GameObjectID();

    public ArrayList<GameObjectID> getObjectIDs() {
        return objectIDs;
    }

    public void setObjectIDs(ArrayList<GameObjectID> objectIDs) {
        this.objectIDs = objectIDs;
    }

    private ArrayList<GameObjectID> objectIDs ;

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    // TODO : care about admin access
    private boolean admin = true ;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
