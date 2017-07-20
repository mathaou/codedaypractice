package entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Glemlock extends NPC{

	public Glemlock(Vector2f loc) throws SlickException {
		super("Glemlock", 100, 100, 50, 1, loc, new Image("res/glemlock.png"));
	}
}
