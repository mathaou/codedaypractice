package inv;

public class Invent {
	int potions, gold;
	Weapon weapon;
	public Invent() {
		weapon = new Weapon(WeaponType.IRON_SWORD);
		potions = 0;
		gold = 0;
	}
	public int getPotions() {
		return potions;
	}
	public int getGold() {
		return gold;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	public void setPotions(int potions) {
		this.potions = potions;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

}
