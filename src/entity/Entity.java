package entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import inv.Invent;

public abstract class Entity {
	private int health, attack, defense, speed;
	private Vector2f loc;
	private String name;
	private Image spritesheet;
	private Invent inv;
	public Entity(String name, int health, int attack, int defense, int speed, Vector2f loc, Image spritesheet) {
		this.name = name;
		this.health = health;
		this.attack = attack; 
		this.defense = defense;
		this.speed = speed;
		this.loc = loc;
		this.spritesheet = spritesheet;
		inv = new Invent();
	}
	public abstract void attack(Entity attacker, Entity defender);
	public void move(Vector2f m){
		this.getLoc().set(this.getLoc().x+m.x, this.getLoc().y+m.y);
	}
	public int getHealth() {
		return health;
	}
	public int getAttack() {
		return attack;
	}
	public int getDefense() {
		return defense;
	}
	public int getSpeed() {
		return speed;
	}
	public Vector2f getLoc() {
		return loc;
	}
	public String getName() {
		return name;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public void setLoc(Vector2f loc) {
		this.loc = loc;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Image getSpritesheet() {
		return spritesheet;
	}
	public void setSpritesheet(Image spritesheet) {
		this.spritesheet = spritesheet;
	}
	public Invent getInv() {
		return inv;
	}
	public void setInv(Invent inv) {
		this.inv = inv;
	}
}
