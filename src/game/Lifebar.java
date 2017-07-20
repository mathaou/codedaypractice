package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import entity.Player;

public class Lifebar {
	public Rectangle canvas;
	public Lifebar(int opacity, Graphics g, int health) {
		g.setColor(Color.white);
		g.drawString("LIFE", 50, 12*50+50);
		canvas = new Rectangle(50, 12*50+70, Player.maxHealth*health/Player.maxHealth, 30);
		g.setColor(Color.red);
		g.fill(canvas);
	}

}
