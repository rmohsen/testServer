package Logic.Model;

import Logic.Controller.Judge;

/**
 * Created by Lenovo on 7/10/2015.
 */
public class Map{
    public Cell[][] getMap() {
        return map;
    }

    public void setMap(Cell[][] map) {
        this.map = map;
    }

    Cell[][] map;
    int width,height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Map(int[][] cellsType,int[][] wallsType,Player[] players){
        this.width=cellsType.length;
        this.height=cellsType[0].length;
        map=new Cell[width][height];
        for(int i=0;i<this.width;i++)
            for(int j=0;j<this.height;j++)
                map[i][j]=new Cell(cellsType[i][j],wallsType[i][j]);
        int cnt=0;
        for(int i=0;i<this.width;i++)
            for(int j=0;j<this.height;j++)
                if(map[i][j].getType()== Judge.START_CELL){
                    players[cnt].x=i;
                    players[cnt].y=j;
                    cnt++;
                }
    }
}

