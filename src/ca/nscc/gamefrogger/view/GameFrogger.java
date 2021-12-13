package ca.nscc.gamefrogger.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import ca.nscc.gamefrogger.controller.GameController;
import ca.nscc.gamefrogger.entity.Player;

public class GameFrogger extends JFrame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 427610188913178394L;

	private static GameController controller;
	private Frogger myFrogger;
	private Vehicle myVehicle;
	private Player p1, p2;
	private Timer timer;

	private int count = 0;
	private int startTimer = 10;

	private ArrayList<JLabel> listLifeFrog;
	private String[] imageVehicle = { "/truckRight.png", "/carLeft.png" };
	private int[] yVehicle = { 270, 370, 560, 660 };
	private ArrayList<JLabel> listVehiclesLabel;
	private ArrayList<Vehicle> listVehicles;
	private JTextField txtName, txtName2;
	private JPanel panel_welcome, panel_game;
	private JButton btnOK;
	private JLabel playerLabel, timerLabel, lifeLabel, froggerLabel, vehicleLabel;

	public JTextField getTxtName() {
		return txtName;
	}

	public JTextField getTxtName2() {
		return txtName2;
	}

	public JPanel getPanel_welcome() {
		return panel_welcome;
	}

	public JLabel getPlayerLabel() {
		return playerLabel;
	}

	public JLabel getTimerLabel() {
		return timerLabel;
	}

	public JLabel getFroggerLabel() {
		return froggerLabel;
	}

	public Frogger getMyFrogger() {
		return myFrogger;
	}

	public Vehicle getMyVehicle() {
		return myVehicle;
	}

	public ArrayList<JLabel> getListLifeFrog() {
		return listLifeFrog;
	}

	public ArrayList<Vehicle> getListVehicles() {
		return listVehicles;
	}

	public ArrayList<JLabel> getListVehiclesLabel() {
		return listVehiclesLabel;
	}

	public Player getP1() {
		return p1;
	}

	public Player getP2() {
		return p2;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setP1(Player p1) {
		this.p1 = p1;
	}

	public void setP2(Player p2) {
		this.p2 = p2;
	}

	public void setMyFrogger(Frogger myFrogger) {
		this.myFrogger = myFrogger;
	}

	public void setMyVehicle(Vehicle myVehicle) {
		this.myVehicle = myVehicle;
	}

	public void setListLifeFrog(ArrayList<JLabel> listLifeFrog) {
		this.listLifeFrog = listLifeFrog;
	}

	public void setListVehiclesLabel(ArrayList<JLabel> vehiclesLabel) {
		this.listVehiclesLabel = vehiclesLabel;
	}

	public void setListVehicles(ArrayList<Vehicle> listVehicles) {
		this.listVehicles = listVehicles;
	}

	private Container content;

	public JPanel welcome() {
		panel_welcome = new JPanel(new BorderLayout());
		panel_welcome.setBackground(Color.WHITE);

		JPanel name = new JPanel(new FlowLayout());
		JLabel lblName = new JLabel("Player 1 name:");
		txtName = new JTextField(10);
		name.add(lblName);
		name.add(txtName);
		JLabel lblName2 = new JLabel("Player 2 name:");
		txtName2 = new JTextField(10);
		btnOK = new JButton("OK");
		btnOK.addActionListener(this);

		name.add(lblName);
		name.add(txtName);
		name.add(lblName2);
		name.add(txtName2);
		name.add(btnOK);

		JLabel lblImage = new JLabel();
		ImageIcon image = new ImageIcon(getClass().getResource("/welcome.png"));
		lblImage.setIcon(image);
		panel_welcome.add(lblImage, BorderLayout.PAGE_START);
		panel_welcome.add(name, BorderLayout.CENTER);
		panel_welcome.setVisible(true);
		return panel_welcome;
	}

	public JPanel playGame() {
		
		count = startTimer;
		panel_game = new JPanel();

		playerLabel = new JLabel();
		playerLabel.setSize(100, 50);
		playerLabel.setForeground(Color.BLUE);
		playerLabel.setFont(new Font("Arial", Font.BOLD, 20));
		playerLabel.setLocation(50, 0);
		add(playerLabel);

		timerLabel = new JLabel("TIMER");
		timerLabel.setSize(50, 50);
		timerLabel.setLocation(150, 0);
		add(timerLabel);

		listLifeFrog = new ArrayList<JLabel>();
		for (int i = 0; i < 3; i++) {
			lifeLabel = new JLabel();
			ImageIcon lifeImage = new ImageIcon(getClass().getResource("/life.png"));
			lifeLabel.setIcon(lifeImage);
			lifeLabel.setSize(50, 50);
			lifeLabel.setLocation(350 + (i * 50), 0);
			listLifeFrog.add(lifeLabel);
			add(lifeLabel);
		}

		setMyFrogger(new Frogger(50, 50));
		getMyFrogger().setX(270);
		getMyFrogger().setY(740);
		froggerLabel = new JLabel();
		ImageIcon froggerImage = new ImageIcon(getClass().getResource(getMyFrogger().getFilename()));
		froggerLabel.setIcon(froggerImage);
		froggerLabel.setSize(getMyFrogger().getWidth(), getMyFrogger().getHeight());
		froggerLabel.setLocation(getMyFrogger().getX(), getMyFrogger().getY());
		add(froggerLabel);

		listVehicles = new ArrayList<Vehicle>();
		listVehiclesLabel = new ArrayList<JLabel>();

		for (int i = 0; i < 1; i++) {
			vehicleLabel = new JLabel();
			if (i == 0 || i == 2) {
				setMyVehicle(new Vehicle(imageVehicle[0], 20, true));
			} else {
				setMyVehicle(new Vehicle(imageVehicle[1], 10, false));
			}
			getMyVehicle().setX(0);
			getMyVehicle().setY(yVehicle[i]);
			vehicleLabel.setSize(getMyVehicle().getWidth(), getMyVehicle().getHeight());
			getMyVehicle().setVehicleLabel(vehicleLabel);
			getMyVehicle().setMyFrogger(getMyFrogger());

			// GAME 2
			try {
				controller.moveVehicle(getMyVehicle(), getMyFrogger());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			listVehicles.add(getMyVehicle());
			listVehiclesLabel.add(vehicleLabel);
			add(vehicleLabel);
		}

		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setSize(800, 900);
		ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/back.png"));
		backgroundLabel.setIcon(backgroundImage);
		add(backgroundLabel);

		panel_game.setVisible(true);
		return panel_game;
	}

	public GameFrogger() {
		super("Game Frogger");
		controller = new GameController(this);
		setSize(GameProperties.SCREEN_WIDTH, GameProperties.SCREEN_HEIGHT);
		add(welcome());
		content = getContentPane();
		content.setFocusable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) throws IOException {
		GameFrogger myGame = new GameFrogger();
		myGame.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		// case of time finish - no move frog
		if (getCount() > 0 && getMyFrogger().getLife() > 0) {
			// GAME 2 - move frog by server
			try {
				controller.moveFrog(getMyFrogger(), e);
				froggerLabel.setLocation(getMyFrogger().getX(), getMyFrogger().getY());
				
				//update score
				if(getMyFrogger().getY() <= 180) {
					updateScore();
				}

			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOK) {
			executa();
		}
	}

	public void executa() {
		getPanel_welcome().setVisible(false);

		setP1(new Player(getTxtName().getText()));
		setP2(new Player(getTxtName2().getText()));
		getP1().setPlaying(true);

		try {
			//GAME 2 - add user by server
			controller.addPlayer(getP1(), getP2());
			add(playGame());
			getContentPane().addKeyListener(this);
			getPlayerLabel().setText(getP1().getName());
			timer = new Timer(60, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (getCount() <= 0) {
						getTimerLabel().setText("STOP");
						stopCars();
						messagePlayerGameOver();
						updatePlayers();												
					} else {
						getTimerLabel().setText(Integer.toString(getCount()));
						setCount(getCount() - 1);
					}
				}
			});
//			timer.start();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void stopCars() {		
		try {
			//GAME 2 - stop thread of vehicle by server
			controller.stopCars();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void messagePlayerGameOver() {
		String txt = "";
		if(count == 0) txt = "time"; else txt = "life";
		
		if(getP1().isPlaying()) {			
			JOptionPane.showMessageDialog(null, "*** " + getP1().getName() + " *** your "+ txt +" finished.\n" 
					+"Now *** " + getP2().getName() + " *** will play");			
		} else if( getP2().isPlaying()) {
			JOptionPane.showMessageDialog(this, "*** " + getP2().getName() + " *** your "+ txt +" finished!" );
		}
		
	}
	
	public void removeLife(Frogger frog) {
		getMyFrogger().setLife(frog.getLife() - 1);
		switch (getMyFrogger().getLife()) {
		case 2:
			getListLifeFrog().get(2).setVisible(false);
			break;
		case 1:
			getListLifeFrog().get(1).setVisible(false);
			break;
		case 0:
			getListLifeFrog().get(0).setVisible(false);
			stopCars();
			messagePlayerGameOver();
			updatePlayers();
			break;
		}
	}
	
	public void updatePlayers() {
		if(getP1().isPlaying()) {
			getP1().setPlaying(false);
			getP2().setPlaying(true);
			getPlayerLabel().setText(getP2().getName());
			resetGame();
		} else {
			getP2().setPlaying(false);
			stopCars();
		}
	}
	

	public void resetGame() {
		count = startTimer;
		getMyFrogger().setLife(3);
		getMyFrogger().setX(270);
		getMyFrogger().setY(740);
		froggerLabel.setLocation(getMyFrogger().getX(), getMyFrogger().getY());
		showFrogLife();
		
		//GAME 2 
		try {
			controller.moveVehicle(getMyVehicle(), getMyFrogger());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showFrogLife() {
		for (JLabel frogLife : listLifeFrog) {
			frogLife.setVisible(true);
		}
	}
	
	public void updateScore() {
		int id = 0;
		try {
			if(getP1().isPlaying()) {
				id = getP1().getId().intValue();
			} else if (getP2().isPlaying()) {
				id = getP2().getId().intValue();
			}
			controller.updateScore(count, id);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
