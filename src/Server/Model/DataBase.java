package Server.Model;

import Logic.Map;

import java.util.ArrayList;

/**
 * Created by Lenovo on 7/11/2015.
 */
public class DataBase {
    ArrayList<Info> StoredData ;
    Info selfData;
    Map map;
    boolean running = true;

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public ArrayList<Info> getStoredData() {
        return StoredData;
    }

    public void setStoredData(ArrayList<Info> storedData) {
        StoredData = storedData;
    }

    public Info getSelfData() {
        return selfData;
    }

    public void setSelfData(Info selfData) {
        this.selfData = selfData;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
