package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import inv.Weapon;

public class TreasureChest {
	public Vector2f loc;
	public Weapon weapon;
	public int healthBonus, defBonus, attackBonus, numPotions;
	public TreasureChest(Vector2f loc, Weapon weapon, int defBonus, int attackBonus, int numPotions) throws SlickException {
		this.loc = loc;
		this.weapon = weapon;
		this.defBonus = defBonus;
		this.attackBonus = attackBonus;
		this.numPotions = numPotions;
	}
	public String printContents(){
		return "Health Bonus: "+healthBonus+"; Defense Bonus: "+defBonus+
				"; Attack Bonus: "+attackBonus+"; Num Potions: "+numPotions;
	}
	public boolean weaponConfirm(GameContainer c){
		if(c.getInput().isKeyPressed(Input.KEY_Y))
			return true;
		if(c.getInput().isKeyPressed(Input.KEY_N))
			return false;
		return false;
	}
	public Vector2f getLoc() {
		return loc;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	public int getDefBonus() {
		return defBonus;
	}
	public int getAttackBonus() {
		return attackBonus;
	}
	public int getNumPotions() {
		return numPotions;
	}
	public void setLoc(Vector2f loc) {
		this.loc = loc;
	}
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	public void setDefBonus(int defBonus) {
		this.defBonus = defBonus;
	}
	public void setAttackBonus(int attackBonus) {
		this.attackBonus = attackBonus;
	}
	public void setNumPotions(int numPotions) {
		this.numPotions = numPotions;
	}

}
