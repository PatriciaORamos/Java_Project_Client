package ca.nscc.gamefrogger.controller;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import ca.nscc.gamefrogger.entity.Player;
import ca.nscc.gamefrogger.service.GameServiceClient;
import ca.nscc.gamefrogger.view.Frogger;
import ca.nscc.gamefrogger.view.GameFrogger;
import ca.nscc.gamefrogger.view.Vehicle;

public class GameController {

	final static int CLIENT_PORT = 5656;
	final static int SERVER_PORT = 5556;

	private Socket s;

	// Initialize data stream to send data out
	private OutputStream outstream;
	private PrintWriter out;
	private GameServiceClient myService;


	public GameController(GameFrogger game) {
		try {
			ServerSocket client = new ServerSocket(CLIENT_PORT);
			s = new Socket("localhost", SERVER_PORT);

			Thread t1 = new Thread(new Runnable() {
				public void run() {
					synchronized (this) {
						System.out.println("Waiting for server responses...");
						while (true) {
							Socket s2;
							try {
								s2 = client.accept();
								myService = new GameServiceClient(s2, game);
								Thread t = new Thread(myService);
								t.start();

							} catch (IOException e) {
								e.printStackTrace();
							}
							System.out.println("client connected");
						}
					}
				}
			});
			t1.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void addPlayer(Player p1, Player p2) throws UnknownHostException, IOException {
		outstream = s.getOutputStream();
		out = new PrintWriter(outstream);

		String command = "ADD_PLAYER " + p1.getName() + " " + p2.getName() + "\n";
		System.out.println("Sending: " + command);
		out.println(command);
		out.flush();

		s.close();		
	}

	public void moveFrog(Frogger frogT, KeyEvent e) throws UnknownHostException, IOException {
		if (frogT.getLife() < 0)
			return;

		String direction = null;

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			direction = "UP";
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			direction = "DOWN";
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			direction = "LEFT";
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			direction = "RIGHT";
		}

		outstream = s.getOutputStream();
		out = new PrintWriter(outstream);

		String command = "MOVE_FROG " + frogT.getX() + " " + frogT.getY() + " " + frogT.getHeight() + " " + direction + "\n";
		System.out.println("Sending: " + command);
		out.println(command);
		out.flush();

	}

	public void moveVehicle(Vehicle vehicleT, Frogger frogT) throws UnknownHostException, IOException {		
		s = new Socket("localhost", SERVER_PORT);
		outstream = s.getOutputStream();
		out = new PrintWriter(outstream);

		String command = "MOVE_VEHICLE " + vehicleT.getFilename() + " " + vehicleT.getIncrease() + " " + vehicleT.getVehicleLabel().getWidth() + " "
				+ vehicleT.getX() + " " + vehicleT.getY() + "\n";
		System.out.println("Sending: " + command);
		out.println(command);
		out.flush();

	}

	public void stopCars() throws UnknownHostException, IOException {
		outstream = s.getOutputStream();
		out = new PrintWriter(outstream);
		String command = "STOP_CARS \n";
		
		System.out.println("Sending: " + command);
		out.println(command);
		out.flush();
		
		for (Thread t : Thread.getAllStackTraces().keySet()) {
			if(t.getName().equals("myService")) {
				t.stop();
				s.close();
			}
		}		
	}
	
	public void updateScore(int count, int id) throws UnknownHostException, IOException {		
		
		outstream = s.getOutputStream();
		out = new PrintWriter(outstream);

		String command = "UPDATE_SCORE " + id + " " + count  +"\n";
		System.out.println("Sending: " + command);
		out.println(command);
		out.flush();

	}
	

}
