package Logic;

/**
 * Created by Lenovo on 7/10/2015.
 */
public class Fan{
    public GameObjectID id;
    public int x,y;
    public Player owner;
    public boolean isAlive=true;
    public Fan(int x, int y, Player player) {
        this.x=x;
        this.y=y;
        this.owner=player;
    }
}