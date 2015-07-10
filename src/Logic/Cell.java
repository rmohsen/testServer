package Logic;

/**
 * Created by Lenovo on 7/10/2015.
 */
public class Cell{
    int type;
    int walls;
    public Cell(int type,int walls){
        this.type=type;
        this.walls=walls;
    }

    public int getType(){
        return this.type;
    }

    public void setType(int type){
        this.type=type;
    }

    public boolean up(){
        return (this.walls%2==0);
    }

    public boolean right(){
        return ((this.walls/2)%2==0);
    }

    public boolean down(){
        return ((this.walls/4)%2==0);
    }

    public boolean left(){
        return ((this.walls/8)%2==0);
    }
}