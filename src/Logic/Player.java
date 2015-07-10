package Logic;

/**
 * Created by Lenovo on 7/10/2015.
 */
public class Player{

    int fans;
    int speed;
    int vision;
    int power;
    int name;
    int moveDir=0;
    int moveTime=0;
    int attackDir=0;
    int attackTime=0;
    int hp=100;
    int doubleSpeed=0;
    int globalVision=0;
    int stun=0;
    int jump=0;
    int dead=0;
    public int x;
    public int y;
    public GameObjectID id;

    public Player(int i) {
        this.name=i;
        if(i==Judge.HASIN){
            fans=10;
            speed=500;
            vision=3;
            power=5;
        }
        if(i==Judge.JAFAR){
            fans=5;
            speed=200;
            vision=3;
            power=1;
        }
        if(i==Judge.SAMAN){
            fans=100;
            speed=500;
            vision=3;
            power=5;
        }
        if(i==Judge.REZA){
            fans=0;
            speed=500;
            vision=6;
            power=4;
        }
    }

    public void next50milis(){
        if(dead>0){
            dead-=50;
            return;
        }
        int t=50;
        if(doubleSpeed>0){
            t*=2;
            doubleSpeed-=50;
        }
        if(stun>0){
            stun-=50;
            return;
        }
        if(globalVision>0)
            globalVision-=50;
        if(jump>0)
            jump-=50;
        if(attackTime>0){
            attackTime-=t;
            if(attackTime==0){
                int x = this.x,y = this.y;
                if(attackDir==JudgeAbstract.UP){
                    x=this.x-1;
                    y=this.y;
                }
                if(attackDir==JudgeAbstract.DOWN){
                    x=this.x+1;
                    y=this.y;
                }
                if(attackDir==JudgeAbstract.RIGHT){
                    x=this.x;
                    y=this.y+1;
                }
                if(attackDir==JudgeAbstract.LEFT){
                    x=this.x;
                    y=this.y-1;
                }
                for(Player p:Judge.players){
                    if(this.equals(p))
                        continue;
                    if(p.x==x && p.y==y)
                        p.hp=Math.max(p.hp-this.power,0);
                    if(p.hp==0){
                        p.dead=30000;
                        p.attackTime=0;
                        p.moveTime=0;
                        p.doubleSpeed=0;
                        p.globalVision=0;
                        p.jump=0;
                        p.stun=0;
                        p.hp=100;
                    }
                }
            }
        }
        if(moveTime>0){
            moveTime-=t;
            if(moveTime==0){
                if(attackDir==JudgeAbstract.UP){
                    this.x--;
                }
                if(attackDir==JudgeAbstract.DOWN){
                    this.x++;
                }
                if(attackDir==JudgeAbstract.RIGHT){
                    this.y++;
                }
                if(attackDir==JudgeAbstract.LEFT){
                    this.y--;
                }
            }
        }
    }
}
