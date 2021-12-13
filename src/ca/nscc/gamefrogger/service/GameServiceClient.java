package ca.nscc.gamefrogger.service;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.ImageIcon;

import ca.nscc.gamefrogger.view.Frogger;
import ca.nscc.gamefrogger.view.GameFrogger;

public class GameServiceClient implements Runnable {

	private Socket s;
	private Scanner in;
	private GameFrogger game;

	public GameServiceClient(Socket aSocket, GameFrogger game) {
		this.s = aSocket;
		this.game = game;
	}

	public void run() {
		try {
			in = new Scanner(s.getInputStream());
			processRequest();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void processRequest() throws IOException {
		while (true) {
			if (!in.hasNext()) {
				return;
			}
			String command = in.next();
			if (command.equals("Quit")) {
				return;
			} else {
				executeCommand(command);
			}
		}
	}

	public void executeCommand(String command) throws IOException {
		if (command.equals("PLAYER_ID")) {
			int player1Id = in.nextInt();
			int player2Id = in.nextInt();
			this.game.getP1().setId(Long.valueOf(player1Id));
			this.game.getP2().setId(Long.valueOf(player2Id));
			System.out.println("Player 1 id: " + player1Id + " Player 2 id:  " + player2Id);

		} else if (command.equals("FROG_POSITION")) {
			int dx = in.nextInt();
			int dy = in.nextInt();
			this.game.getMyFrogger().setX(dx);
			this.game.getMyFrogger().setY(dy);
			System.out.println(" Frog position dx: " + dx + " dy: 5 " + dy);


		} else if (command.equals("VEHICLE_POSITION")) {
			int dx = in.nextInt();
			int dy = in.nextInt();
			String fileName = in.next();
			this.game.getMyVehicle().setX(dx);
			this.game.getMyVehicle().setY(dy);
			this.game.getMyVehicle().setFilename(fileName);
			ImageIcon vehicleImage = new ImageIcon(getClass().getResource(this.game.getMyVehicle().getFilename()));
			this.game.getMyVehicle().getVehicleLabel().setIcon(vehicleImage);
			this.game.getMyVehicle().getVehicleLabel().setSize(this.game.getMyVehicle().getWidth(),
					this.game.getMyVehicle().getHeight());
			this.game.getMyVehicle().getVehicleLabel().setLocation(dx, dy);
			this.game.getMyVehicle().setVehicleLabel(this.game.getMyVehicle().getVehicleLabel());
			
			this.game.getListVehicles().add(this.game.getMyVehicle());
			this.game.getListVehiclesLabel().add(this.game.getMyVehicle().getVehicleLabel());

			System.out.println("Vehicle position dx: " + dx + " dy:  " + dy);
				
			//case collision - remove frog life
			if (this.game.getMyVehicle().getRectangle().intersects(this.game.getMyFrogger().getRectangle())) {
				System.out.println("Boom!");
				removeLife(this.game.getMyFrogger());
			}
			
			//case player complete crossing - 1: stop cars, 2: p1 stop, 3: p2 start
			if( this.game.getMyFrogger().getLife() > 0 && this.game.getMyFrogger().getY() <= 180) {
				if(this.game.getP1().isPlaying()) {
					this.game.getP1().setConcluded(true);
					this.game.getP1().setPlaying(false);
				} else if( this.game.getP2().isPlaying()) {
					this.game.getP2().setConcluded(true);
					this.game.getP2().setPlaying(false);
				} 			
			}

		}
	}

	private void removeLife(Frogger frog) {
		frog.setX(275);
		frog.setY(740);
		frog.getRectangle().setLocation(50, 50);
		this.game.getFroggerLabel().setLocation(frog.getX(), frog.getY());
		frog.setLife(frog.getLife() - 1);

		switch (frog.getLife()) {
		case 2:
			this.game.getListLifeFrog().get(2).setVisible(false);
			break;
		case 1:
			this.game.getListLifeFrog().get(1).setVisible(false);
			break;
		case 0:
			this.game.getListLifeFrog().get(0).setVisible(false);
			this.game.stop();
			break;
		}
	}

}
