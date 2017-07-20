package inv;

public class Weapon {

	WeaponType type;
	
	public Weapon(WeaponType type) {
		this.type = type;
	}

	public WeaponType getType() {
		return type;
	}

	public void setType(WeaponType type) {
		this.type = type;
	}

}
