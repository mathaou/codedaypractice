package entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class Player extends Entity{

	public static int maxHealth;
	
	public Player(String name, int health, int attack, int defence, int speed, Vector2f loc, Image spritesheet) {
		super(name, health, attack, defence, speed, loc, spritesheet);
		maxHealth = 100;
	}
	@Override
	public void attack(Entity attacker, Entity defender) {
		// TODO Auto-generated method stub
		
		if(attacker.getAttack() > defender.getDefense()){
			defender.setHealth(defender.getHealth() - (attacker.getAttack() - defender.getDefense()));
		}
		else
			defender.setHealth(defender.getHealth() - 1);
		
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

}
