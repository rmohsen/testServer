package Logic.Model;

import Logic.Common.GameObjectID;
import Logic.Controller.Judge;
import Logic.Controller.JudgeAbstract;

/**
 * Created by Lenovo on 7/10/2015.
 */
public class Player {

	int fans;
	int speed;
	int vision;
	int power;
	int name;
	int moveDir = 0;
	int moveTime = 0;
	int attackDir = 0;
	int attackTime = 0;

	public int getFans() {
		return fans;
	}

	public void setFans(int fans) {
		this.fans = fans;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getVision() {
		return vision;
	}

	public void setVision(int vision) {
		this.vision = vision;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getMoveDir() {
		return moveDir;
	}

	public void setMoveDir(int moveDir) {
		this.moveDir = moveDir;
	}

	public int getMoveTime() {
		return moveTime;
	}

	public void setMoveTime(int moveTime) {
		this.moveTime = moveTime;
	}

	public int getAttackDir() {
		return attackDir;
	}

	public void setAttackDir(int attackDir) {
		this.attackDir = attackDir;
	}

	public int getAttackTime() {
		return attackTime;
	}

	public void setAttackTime(int attackTime) {
		this.attackTime = attackTime;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getDoubleSpeed() {
		return doubleSpeed;
	}

	public void setDoubleSpeed(int doubleSpeed) {
		this.doubleSpeed = doubleSpeed;
	}

	public int getGlobalVision() {
		return globalVision;
	}

	public void setGlobalVision(int globalVision) {
		this.globalVision = globalVision;
	}

	public int getStun() {
		return stun;
	}

	public void setStun(int stun) {
		this.stun = stun;
	}

	public int getJump() {
		return jump;
	}

	public void setJump(int jump) {
		this.jump = jump;
	}

	public int getDead() {
		return dead;
	}

	public void setDead(int dead) {
		this.dead = dead;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public GameObjectID getId() {
		return id;
	}

	public void setId(GameObjectID id) {
		this.id = id;
	}

	int hp = 100;
	int doubleSpeed = 0;
	int globalVision = 0;
	int stun = 0;
	int jump = 0;
	int dead = 0;
	public int x;
	public int y;
	public GameObjectID id;

	public Player(int i) {
		this.name = i;
		if (i == Judge.HASIN) {
			fans = 10;
			speed = 500;
			vision = 3;
			power = 5;
		}
		if (i == Judge.JAFAR) {
			fans = 5;
			speed = 200;
			vision = 3;
			power = 1;
		}
		if (i == Judge.SAMAN) {
			fans = 100;
			speed = 500;
			vision = 3;
			power = 5;
		}
		if (i == Judge.REZA) {
			fans = 0;
			speed = 500;
			vision = 6;
			power = 4;
		}
	}

	public void next50milis() {
		if (dead > 0) {
			dead -= 50;
			return;
		}
		int t = 50;
		if (doubleSpeed > 0) {
			t *= 2;
			doubleSpeed -= 50;
		}
		if (stun > 0) {
			stun -= 50;
			return;
		}
		if (globalVision > 0)
			globalVision -= 50;
		if (jump > 0)
			jump -= 50;
		if (attackTime > 0) {
			attackTime -= t;
			if (attackTime == 0) {
				int x = this.x, y = this.y;
				if (attackDir == JudgeAbstract.UP) {
					x = this.x - 1;
					y = this.y;
				}
				if (attackDir == JudgeAbstract.DOWN) {
					x = this.x + 1;
					y = this.y;
				}
				if (attackDir == JudgeAbstract.RIGHT) {
					x = this.x;
					y = this.y + 1;
				}
				if (attackDir == JudgeAbstract.LEFT) {
					x = this.x;
					y = this.y - 1;
				}
				for (Player p : Judge.players) {
					if (this.equals(p))
						continue;
					if (p.x == x && p.y == y)
						p.hp = Math.max(p.hp - this.power, 0);
					if (p.hp == 0) {
						p.dead = 30000;
						p.attackTime = 0;
						p.moveTime = 0;
						p.doubleSpeed = 0;
						p.globalVision = 0;
						p.jump = 0;
						p.stun = 0;
						p.hp = 100;
					}
				}
			}
		}
		if (moveTime >= 0) {
			moveTime -= t;
			if (moveTime == 0) {
				if (moveDir == JudgeAbstract.UP) {
					this.x--;
				}
				if (moveDir == JudgeAbstract.DOWN) {
					this.x++;
				}
				if (moveDir == JudgeAbstract.RIGHT) {
					this.y++;
				}
				if (moveDir == JudgeAbstract.LEFT) {
					this.y--;
				}
			}
		}
	}
}
