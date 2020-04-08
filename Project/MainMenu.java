package Project;

import Project.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainMenu {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Main menu");
		frame.setSize(1000, 1000);
		
		JPanel panel = new JPanel();
		
		JLabel GameTitleLabel = new JLabel("THE GAME");
		JLabel PressStartLabel = new JLabel("Press Start");
		
		JButton StartButton = new JButton("Start");
		
		frame.add(panel);
		panel.add(GameTitleLabel);
		panel.add(PressStartLabel);
		panel.add(StartButton);
		
		StartButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Game game = new Game();
				game.setVisible(true);
			}
		});
		frame.setVisible(true);
	}
}
