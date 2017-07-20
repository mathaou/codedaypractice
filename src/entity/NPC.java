package entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class NPC extends Entity{

	public NPC(String name, int health, int attack, int defence, int speed, Vector2f loc, Image spritesheet) {
		super(name, health, attack, defence, speed, loc, spritesheet);
	}
	@Override
	public void attack(Entity attacker, Entity defender) {
		if(attacker.getAttack() > defender.getDefense()){
			defender.setHealth(defender.getHealth() - (attacker.getAttack() - defender.getDefense()));
		}
		else
			defender.setHealth(defender.getHealth() - 1);
	}
}
