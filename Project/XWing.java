package Project;

import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;

public class XWing extends MovingObject {
	private int width;
	public XWing() {
		initializeXWing();
	}
	
	private void initializeXWing() {
		String XWingImg = "C:\\Users\\Kristien\\Documents\\Ruben & Dietger\\Ruben\\Provil ION 6\\Programmeren\\afbeeldingen\\X-Wing.png";
		ImageIcon XWingImgIcon = new ImageIcon(XWingImg);
		
		width = XWingImgIcon.getImage().getWidth(null);
		setImage(XWingImgIcon.getImage());
		
		int START_X = 400;
		setX(START_X);
		
		int START_Y = 700;
		setY(START_Y);
	}
	
	public void act() {
		x += dx;
		
		if (x <= 2) {
			x = 2;
		}
		
		if (x >= Commons.BOARD_WIDTH - 2 * width) {
			x = Commons.BOARD_WIDTH - 2 * width;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			dx = -2;
		}
		
		if (key == KeyEvent.VK_RIGHT) {
			dx = 2;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}
		
		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}
	}
}
