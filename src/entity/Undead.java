package entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Undead extends NPC{

	public Undead(Vector2f loc) throws SlickException {
		super("Undead", 40, 30, 15, 1, loc, new Image("res/zombie.png"));
	}
	
}
