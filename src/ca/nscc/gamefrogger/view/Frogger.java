package ca.nscc.gamefrogger.view;

public class Frogger extends Sprite {

	
	private int life;
	
	public int getLife() { return life;}
	public void setLife(int life) {this.life = life;}
	
	public Frogger(int x, int y) {
		super(x, y, "/froggerUp.png");
		this.life = 3;
	}

}
