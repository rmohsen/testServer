package Logic;

import java.util.ArrayList;

/**
 * Created by Lenovo on 7/9/2015.
 */
public class Info {
    private boolean running = true ;

    public boolean isAdmin() {
        return admin;
    }

    private ArrayList<GameObjectID> gameObjectIDs ;

    public ArrayList<GameObjectID> getGameObjectIDs() {
        return gameObjectIDs;
    }

    public void setGameObjectIDs(ArrayList<GameObjectID> gameObjectIDs) {
        this.gameObjectIDs = gameObjectIDs;
    }

    public GameObjectID getObjectID() {
        return objectID;
    }

    public void setObjectID(GameObjectID objectID) {
        this.objectID = objectID;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    private Map map;

    private GameObjectID objectID ;

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
