package entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Troll extends NPC{

	public Troll(Vector2f loc) throws SlickException {
		super("Troll", 75, 50, 20, 1, loc, new Image("res/troll.png"));
	}
	
}
