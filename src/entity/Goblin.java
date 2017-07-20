package entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Goblin extends NPC{

	public Goblin(Vector2f loc) throws SlickException {
		super("Goblin", 20, 15, 10, 1, loc, new Image("res/goblin.png"));
	}


}
