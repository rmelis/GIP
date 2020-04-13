package Project;

import javax.swing.ImageIcon;

public class Shot extends MovingObject {
	public Shot() {
	}
	
	public Shot(int x, int y) {
		initializeShot(x, y);
	}
	
	private void initializeShot(int x, int y) {
		String shotImg = "src\\Images\\bomb.png";
		ImageIcon shotImgIcon = new ImageIcon(shotImg);
		setImage(shotImgIcon.getImage());
		
		int H_SPACE = 6;
		setX(x + H_SPACE);
		
		int V_SPACE = 1;
		setY(y + V_SPACE);
	}
}
