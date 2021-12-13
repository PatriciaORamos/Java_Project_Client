package ca.nscc.gamefrogger.view;


import javax.swing.JLabel;

public class Vehicle extends Sprite {
	
	private Boolean moving, increase;
	private JLabel vehicleLabel;
	private Frogger myFrogger;
	private int velocity;
	
	public int getVelocity() {return velocity;}
	public Boolean getMoving() { return moving; }
	public Boolean getIncrease() { return increase; }
	public JLabel getVehicleLabel() { return vehicleLabel; }
	public Frogger getMyFrogger() {return myFrogger;}
	
	public void setMoving(Boolean moving) { this.moving = moving;}
	public void setVelocity(int velocity) { this.velocity = velocity;}
	public void setIncrease(Boolean increase) { this.increase = increase;}
	public void setVehicleLabel(JLabel temp) { this.vehicleLabel = temp; }
	public void setMyFrogger(Frogger temp) { this.myFrogger = temp; }

	public Vehicle() {		
		this.moving = false;
	}
	
	public Vehicle(String image, int velocity, Boolean increase) {
		super(300, 50, image);
		this.moving = false;
		this.velocity = velocity;
		this.increase = increase;
 	}
	
}
