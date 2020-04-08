package Project;

import Project.XWing;
import Project.TieFighter;
import Project.Shot;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Board extends JPanel {
	private Dimension dimension;
	private List<TieFighter> TieFighters;
	private XWing xwing;
	private Shot shot;
	
	private int direction = -1;
	private int deaths = 0;
	
	private boolean inGame = true;
	private String explosionImg = "C:\\Users\\Kristien\\Documents\\Ruben & Dietger\\Ruben\\Provil ION 6\\Programmeren\\afbeeldingen\\Explosion.jpg";
	private String message = "Game Over";
	
	private Timer timer;
	
	
	public Board() {
		initializeBoard();
		initializeGame();
	}
	
	private void initializeBoard() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		dimension = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
		setBackground(Color.black);
		
		timer = new Timer(Commons.DELAY, new GameRound());
		timer.start();
		
		initializeGame();
	}
	
	private void initializeGame() {
		TieFighters = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 6; j++) {
				/*var*/TieFighter tiefighter = new TieFighter(Commons.Tie_fighter_INITIALIZE_X + 18 * j,
						Commons.Tie_fighter_INITIALIZE_Y + 18 * i);
				TieFighters.add(tiefighter);
			}
		}
		
		xwing = new XWing();
		shot = new Shot();
	}
	
	private void drawTieFighters(Graphics g) {
		for (TieFighter tiefighter : TieFighters) {
			
			if (tiefighter.isVisible()) {
				g.drawImage(tiefighter.getImage(), tiefighter.getX(), tiefighter.getY(), this);
			}
			if (tiefighter.isDying()) {
				tiefighter.die();
			}
		}
	}
	
	private void drawXWing(Graphics g) {
		if (xwing.isVisible()) {
			g.drawImage(xwing.getImage(), xwing.getX(), xwing.getY(), this);
		}
		if (xwing.isDying()) {
			xwing.die();
			inGame = false;
		}
	}
	
	private void drawShot(Graphics g) {
		if (shot.isVisible()) {
			g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
		}
	}
	
	private void drawBombing(Graphics g) {
		for(TieFighter tf : TieFighters) {
			TieFighter.Bomb b = tf.getBomb();
			
			if (!b.isDestroyed()) {
				g.drawImage(b.getImage(), b.getX(), b.getY(), this);
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
	
	private void doDrawing(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, dimension.width, dimension.height);
		g.setColor(Color.green);
		
		if (inGame) {
			g.drawLine(0, Commons.GROUND,
					Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
			
			drawTieFighters(g);
			drawXWing(g);
			drawShot(g);
			drawBombing(g);
		}else {
			if (timer.isRunning()) {
				timer.stop();
			}
			GameOver(g);
		}
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void GameOver(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
		
		g.setColor(new Color(0, 32, 48));
		g.fillRect(50,  Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_HEIGHT - 100, 50);
		g.setColor(Color.white);
		g.drawRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);

        Font small = new Font("Aurebesh", Font.BOLD, 14);
        FontMetrics fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
                Commons.BOARD_WIDTH / 2);
	}
	
	private void update() {
		if (deaths == Commons.aantal_TIE_fighters) {
			inGame = false;
			timer.stop();
			message = "VICTORY!";
		}
		
//		xwing
		xwing.act();
		
//		shot
		if (shot.isVisible()) {
			int shotX = shot.getX();
			int shotY = shot.getY();
			for (TieFighter tiefighter : TieFighters) {
				int tiefighterX = tiefighter.getX();
				int tiefighterY = tiefighter.getY();
				if (tiefighter.isVisible() && shot.isVisible()) {
					if (shotX >= (tiefighterX)
							&& shotX <= (tiefighterX + Commons.Tie_fighter_WIDTH)
							&& shotY >= (tiefighterY)
							&& shotY <= (tiefighterY + Commons.Tie_fighter_HEIGHT)) {
						/*var*/ImageIcon ImgIcon = new ImageIcon(explosionImg);
						tiefighter.setImage(ImgIcon.getImage());
						tiefighter.setDying(true);
						deaths++;
						shot.die();	
					}
				}
			}
			
			int y = shot.getY();
			y -= 4;
			
			if (y < 0) {
				shot.die();
			}else {
				shot.setY(y);
			}
		}
		
//		tiefighter
		for (TieFighter tiefighter : TieFighters) {
			int x = tiefighter.getX();
			if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && direction != -1) {
				direction = -1;
				Iterator<TieFighter> i1 = TieFighters.iterator();
				while (i1.hasNext()) {
					TieFighter tf2 = i1.next();
					tf2.setY(tf2.getY() + Commons.GO_DOWN);
				}
			}
			
			if (x <= Commons.BORDER_LEFT && direction != 1) {
				direction = 1;
				Iterator<TieFighter> i2 = TieFighters.iterator();
				
				while (i2.hasNext()) {
					TieFighter tf = i2.next();
					tf.setY(tf.getY() + Commons.GO_DOWN);
				}
			}
		}
		
		Iterator<TieFighter> it = TieFighters.iterator();
		
		while (it.hasNext()) {
			TieFighter tiefighter = it.next();
			if (tiefighter.isVisible()) {
				int y = tiefighter.getY();
				if (y > Commons.GROUND - Commons.Tie_fighter_HEIGHT) {
					inGame = false;
					message = "DEFEAT!";
				}
				tiefighter.act(direction);
			}
		}
		
//		bombs
		Random generator = new Random();
		
		for (TieFighter tiefighter : TieFighters) {
			int shot = generator.nextInt(15);
			TieFighter.Bomb bomb = tiefighter.getBomb();
			
			if (shot == Commons.CHANCE && tiefighter.isVisible() && bomb.isDestroyed()) {
				bomb.setDestroyed(false);
				bomb.setX(tiefighter.getX());
				bomb.setY(tiefighter.getY());
			}
			
			int bombX = bomb.getX();
			int bombY = bomb.getY();
			int xwingX = xwing.getX();
			int xwingY = xwing.getY();
			
			if (xwing.isVisible() && !bomb.isDestroyed()) {
				if (bombX >= (xwingX)
						&& bombX <= (xwingX + Commons.PLAYER_WIDTH)
						&& bombY >= (xwingY)
						&& bombY <= (xwingY + Commons.PLAYER_HEIGHT)) {
					
					ImageIcon ImageIcon = new ImageIcon(explosionImg);
					xwing.setImage(ImageIcon.getImage());
					xwing.setDying(true);
					bomb.setDestroyed(true);
				}
			}
			
			if (!bomb.isDestroyed()) {
				bomb.setY(bomb.getY() + 1);
				if (bomb.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT) {
					bomb.setDestroyed(true);
				}
			}
		}
	}
	
	private void doGameRound() {
		update();
		repaint();
	}
	
	private class GameRound implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			doGameRound();
		}
	}
	
	private class TAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			xwing.keyReleased(e);
		}
	}
	
//	@Override
	public void keyPressed(KeyEvent e) {
		xwing.keyPressed(e);
		
		int x = xwing.getX();
		int y = xwing.getY();
		
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE) {
			if (inGame) {
				if (!shot.isVisible()) {
					shot = new Shot(x, y);
				}
			}
		}
	}
}