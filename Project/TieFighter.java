package Project;

import javax.swing.ImageIcon;

public class TieFighter extends MovingObject {
	
	private Bomb bomb;
	
	public TieFighter(int x, int y) {
		initializeTieFighter(x, y);
	}
	
	private void initializeTieFighter(int x, int y) {
		this.x = x;
		this.y = y;
		bomb = new Bomb(x, y);
		String TieFighterImg = "src\\Images\\Tie fighter.png";
		ImageIcon TieFighterImgIcon = new ImageIcon(TieFighterImg);
		setImage(TieFighterImgIcon.getImage());
	}
	
	public void act(int direction) {
		this.x += direction;
	}
	
	public Bomb getBomb() {
		return bomb;
	}
	
	public class Bomb extends MovingObject {
		private boolean destroyed;
		public Bomb(int x, int y) {
			initializeBomb(x, y);
		}
		
		private void initializeBomb(int x, int y) {
			setDestroyed(true);
			this.x = x;
			this.y = y;
			String bombImg = "src\\Images\\bomb.png";
			ImageIcon bombImgIcon = new ImageIcon(bombImg);
			setImage(bombImgIcon.getImage());
		}
		
		public void setDestroyed(boolean destroyed) {
			this.destroyed = destroyed;
		}
		
		public boolean isDestroyed() {
			return destroyed;
		}
	}
}
