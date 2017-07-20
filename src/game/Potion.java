package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Potion {
	public Rectangle canvas;
	public Image potion;
	public Potion(Graphics g, int numPotions) throws SlickException {
		g.setColor(Color.white);
		g.drawString("X"+numPotions, 220, 12*50+50);
		potion = new Image("res/potion.png");
		potion.draw(200, 12*50+60);
	}

}
