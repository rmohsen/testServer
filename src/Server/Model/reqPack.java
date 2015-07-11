package Server.Model;

import Logic.Common.GameObjectID;

import java.util.ArrayList;

/**
 * Created by Lenovo on 7/11/2015.
 */
public class reqPack implements java.io.Serializable {
    private boolean forward = false;
    private boolean down = false;
    private boolean right = false;
    private boolean left = false;
    private ArrayList<GameObjectID> attackList = new ArrayList<>();
    private String capturedCell;

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public ArrayList<GameObjectID> getAttackList() {
        return attackList;
    }

    public void setAttackList(ArrayList<GameObjectID> attackList) {
        this.attackList = attackList;
    }

    public String getCapturedCell() {
        return capturedCell;
    }

    public void setCapturedCell(String capturedCell) {
        this.capturedCell = capturedCell;
    }
}
