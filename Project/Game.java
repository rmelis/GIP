package Project;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Game extends JFrame {
	
	public Game() {
		startGame();
	}
	
	private void startGame() {
		add(new Board());
		
		setTitle("Game");
		setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			
			Game game = new Game();
			game.setVisible(true);
		});
	}
}
