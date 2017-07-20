package inv;

import java.security.SecureRandom;

public enum WeaponType {
	IRON_SWORD("Iron Sword", 10),
	STEEL_SWORD("Steel Sword", 20),
	MORNING_STAR("Morning Star", 30),
	GREATSWORD("Greatsword", 40),
	DRAGON_SLAYER("Dragon Slayer", 75);
	
	private static SecureRandom rand = new SecureRandom();
	private String name;
	private int damage;
	
	WeaponType(String name, int damage){
		this.name = name;
		this.damage = damage;
	}

	public String getName() {
		return name;
	}

	public int getDamage() {
		return damage;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	public static WeaponType getRandomWeapon(){
		switch(rand.nextInt(5)){
		case 0: return IRON_SWORD;
		case 1: return STEEL_SWORD;
		case 2: return MORNING_STAR;
		case 3: return GREATSWORD;
		case 4: return DRAGON_SLAYER;
		}
		return null;
	}
}
