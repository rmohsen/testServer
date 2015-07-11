package Server.Model;

import Logic.Model.Fan;
import Logic.Common.GameObjectID;
import Logic.Model.Player;

import java.util.ArrayList;

/**
 * Created by Lenovo on 7/9/2015.
 */
public class Info implements java.io.Serializable {
    private boolean running = true ;

    Player player;
    Fan fan;
    private ArrayList<GameObjectID> objectIDs ;
    private boolean admin = true ;

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

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    // TODO : care about admin access

    public int getUserName() {
        return player.id.getNumber();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
