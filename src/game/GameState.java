package game;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import data.Map;
import data.Room;
import data.Save;
import entity.Glemlock;
import entity.Goblin;
import entity.NPC;
import entity.Player;
import entity.Troll;
import entity.Undead;
import inv.Invent;
import inv.Weapon;
import inv.WeaponType;

public class GameState extends BasicGameState{
	public static Map map;
	public SpriteSheet tiles;
	public Image chest, dirt, door, pillar, pit, player, wall;
	public static Vector2f mapLoc, roomLoc, bossLoc, intendedMove;
	public static Room[][] rooms;
	public Player hero;
	public static Audio dungeonMusic, bossMusic, chestOpen, hit;
	
	public String dialogue = " ";
	
	public TreasureChest treasureChest;
	
	public static ArrayList<NPC> enemies;
	
	private int tileX, tileY, orientation, delay =0;
	
	public GameState() {
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		treasureChest = new TreasureChest(new Vector2f(0,0), null, 0, 0, 0);
		map = new Map();
		mapLoc = map.getStartLoc();
		roomLoc = new Vector2f(1,1);
		bossLoc = map.getEndLoc();
		rooms = map.getMap();
		tileX = 1;
		tileY = 0;
		orientation = 1;
		try {
			new Save(this);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		enemies = new ArrayList<NPC>();
		spawnEnemies();
		try {
			dungeonMusic = loadAudio("dungeonMusic");
			bossMusic = loadAudio("bossMusic");
			chestOpen = loadAudio("chestOpen");
			hit = loadAudio("hit");
		} catch (IOException e) {
			e.printStackTrace();
		}
		tiles = new SpriteSheet("res/player.png", 50, 50);
		hero = new Player("Hero", 100, 20, 10, 5, roomLoc, player);
		chest = new Image("res/chest.png");
		dirt = new Image("res/dirt.png");
		door = new Image("res/door.png");
		pillar = new Image("res/pillar.png");
		pit = new Image("res/pit.png");
		player = new Image("res/player.png");
		wall = new Image("res/wall.png");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		int playerX = (int) mapLoc.getX();
		int playerY = (int) mapLoc.getY();
		int[][] roomLayout = getRoomLayout();
		for(int y = 0; y < 13; y++){
			for(int x = 0; x < 13; x++){
				//0=empty, 1=entity, 2=pillar, 3=pit, 4=wall, 5=door, 6 = chest
				if(roomLayout[y][x] == 0)
					g.drawImage(dirt,x*DungeonStart.mapSize, y*DungeonStart.mapSize);
				if(roomLayout[y][x] == 1)
					g.drawImage(dirt,x*DungeonStart.mapSize, y*DungeonStart.mapSize);
				if(roomLayout[y][x] == 2)
					g.drawImage(pillar,x*DungeonStart.mapSize, y*DungeonStart.mapSize);
				if(roomLayout[y][x] == 3)
					g.drawImage(pit,x*DungeonStart.mapSize, y*DungeonStart.mapSize);
				if(roomLayout[y][x] == 4)
					g.drawImage(wall,x*DungeonStart.mapSize, y*DungeonStart.mapSize);
				if(roomLayout[y][x] == 7)
					g.drawImage(dirt,x*DungeonStart.mapSize, y*DungeonStart.mapSize);
				
				if(playerX-1>-1&&rooms[playerY][playerX-1].getOccupied()==1){
					door.setRotation(360);
					door.draw(0, container.getHeight()/2-DungeonStart.mapSize);
				}
				if(rooms[playerY][playerX+1].getOccupied()==1){
					door.setRotation(180f);
					door.draw((float)(container.getWidth()-DungeonStart.mapSize), (float)(container.getHeight()/2-DungeonStart.mapSize));
				}
				if(playerY-1>-1&&rooms[playerY-1][playerX].getOccupied()==1){
					door.setRotation(90f);
					door.draw((float)(container.getWidth()-(7*DungeonStart.mapSize)-1), 0);
				}
				if(rooms[playerY+1][playerX].getOccupied()==1){
					door.setRotation(270f);
					door.draw((float)(container.getWidth()-(7*DungeonStart.mapSize)-1), container.getHeight()-DungeonStart.mapSize*2);
				}
			}
		}
		for(NPC enemy: enemies){
			g.drawImage(enemy.getSpritesheet(), enemy.getLoc().x*DungeonStart.mapSize, enemy.getLoc().y*DungeonStart.mapSize);
		}
		if(!(treasureChest.getLoc().x==0&&treasureChest.getLoc().y==0)){
			g.drawImage(chest, treasureChest.getLoc().x*DungeonStart.mapSize, treasureChest.getLoc().y*DungeonStart.mapSize);
		}
		tiles.startUse();
		tiles.getSubImage(tileX, tileY).drawEmbedded(roomLoc.x*DungeonStart.mapSize, roomLoc.y*DungeonStart.mapSize, DungeonStart.mapSize, DungeonStart.mapSize);
		tiles.endUse();
		Minimap map = new Minimap(40, g, rooms, new Vector2f((int)Map.startLoc.x,(int)Map.startLoc.y), new Vector2f((int)Map.endLoc.x,(int)Map.endLoc.y));
		Lifebar bar = new Lifebar(100, g, hero.getHealth());
		Potion potion = new Potion(g, hero.getInv().getPotions());
		g.drawString(hero.getInv().getWeapon().getType().name(), 280, container.getHeight()-40);
		g.drawString("ATK: "+hero.getAttack(), 280, container.getHeight()-20);
		g.drawString("DEF: "+hero.getDefense(), 400, container.getHeight()-20);
	}
	public TreasureChest generateChests(Vector2f playerLoc, GameContainer c) throws SlickException{
		int[][] roomLayout = getRoomLayout();
		Vector2f chestLoc;
		for(int y = 1; y < 11; y++){
			for(int x = 1; x <  11; x++){
				if(roomLayout[y][x] == 0){
					if(Math.random()*100<8&&roomLayout[y][x]==0){
						chestLoc = new Vector2f(y,x);
						return new TreasureChest(chestLoc, new Weapon(WeaponType.getRandomWeapon()), 5, 
								3, 3);
					}
				}
			}
		}
		return null;
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int d) throws SlickException {
		hero.setLoc(roomLoc);
		int playerX = (int) mapLoc.getX();
		int playerY = (int) mapLoc.getY();
		int[][] roomLayout = getRoomLayout();
		if(hero.getHealth() <= 0){
			for(NPC enemy: enemies){
				roomLayout[(int) enemy.getLoc().y][(int) enemy.getLoc().x] = 0;
			}
			roomLayout[(int) roomLoc.getY()][(int) (roomLoc.getX())] = 0;
			init(container, game);
			game.enterState(StateBasedRunner.gameOverID);
			
		}
		for(NPC enemy: enemies){
			roomLayout[(int) enemy.getLoc().y][(int) (enemy.getLoc().x)] = 7;
		}
		if(enemies.size()==0&&(int)treasureChest.getLoc().getY()==(int)roomLoc.getY()&&treasureChest.getLoc().getX()==(int)roomLoc.getX()){
			Invent i = hero.getInv();
			if(hero.getInv().getWeapon().getType().getDamage()+hero.getAttack()< treasureChest.weapon.getType().getDamage()+hero.getAttack())
				i.setWeapon(treasureChest.weapon);
			i.setPotions(treasureChest.numPotions+i.getPotions());
			hero.setAttack(hero.getAttack()+treasureChest.attackBonus);
			hero.setDefense(hero.getDefense()+treasureChest.defBonus);
			hero.setMaxHealth(hero.getMaxHealth()+treasureChest.healthBonus);
			treasureChest.setLoc(new Vector2f(0,0));
			chestOpen.playAsSoundEffect(1.0f, 1.0f, false);
		}
		
		if (container.getInput().isKeyPressed(Input.KEY_ENTER)) {
			container.getInput().clearKeyPressedRecord();
			game.enterState(StateBasedRunner.pauseID);
		}
		if(container.getInput().isKeyPressed(Input.KEY_E)){
			container.getInput().clearKeyPressedRecord();
			if(hero.getInv().getPotions()>0&&hero.getHealth()<hero.getMaxHealth()){
				hero.getInv().setPotions(hero.getInv().getPotions()-1);
				if(hero.getHealth() + (hero.getMaxHealth()*.25) <= hero.getMaxHealth()){
					hero.setHealth((int)(hero.getHealth()+(hero.getMaxHealth()*.25)));
				}else{
					hero.setHealth(hero.getMaxHealth());
				}
			}
		}
		if(container.getInput().isKeyPressed(Input.KEY_D)){
			move(1, new Vector2f(1,0));
			if(rooms[playerY][playerX+1].getOccupied()==1&&roomLoc.y==6&&roomLoc.x>10){
				int y = (int) roomLoc.getY();
				int x = (int) (roomLoc.getX());
				roomLayout[(int) roomLoc.getY()][(int) (roomLoc.getX())] = 0;
				mapLoc.add(new Vector2f(1,0));
				roomLayout[y][x] = 0;
				roomLoc = new Vector2f(12-roomLoc.getX(), 6);
				roomLayout[y][x] = 0;
				if(!rooms[playerY][playerX+1].getIsBossRoom()){
					treasureChest = generateChests(roomLoc, container);
					spawnEnemies();
					if(bossMusic.isPlaying()){
						bossMusic.stop();
						dungeonMusic.playAsMusic(1.0f, 1.0f, true);
					}
				}
				else{
					spawnBoss();
				}
			}
		}		
		if(container.getInput().isKeyPressed(Input.KEY_W)){
			move(0, new Vector2f(0,-1));
			if(rooms[playerY-1][playerX].getOccupied()==1&&roomLoc.y<1&&roomLoc.x==6){
				int y = (int) roomLoc.getY();
				int x = (int) (roomLoc.getX());
				roomLayout[(int) roomLoc.getY()][(int) (roomLoc.getX())] = 0;
				mapLoc.add(new Vector2f(0,-1));
				roomLayout[y][x] = 0;
				roomLoc = new Vector2f(roomLoc.getX(), 11);
				roomLayout[y][x] = 0;
				if(!rooms[playerY-1][playerX].getIsBossRoom()){
					treasureChest = generateChests(roomLoc, container);
					spawnEnemies();
					if(bossMusic.isPlaying()){
						bossMusic.stop();
						dungeonMusic.playAsMusic(1.0f, 1.0f, true);
					}
				}
				else{
					spawnBoss();
				}
			}
		}
		if(container.getInput().isKeyPressed(Input.KEY_A)){
			move(3, new Vector2f(-1,0));
			if(playerX-1>0&&rooms[playerY][playerX-1].getOccupied()==1&&roomLoc.y==6&&roomLoc.x==1){
				int y = (int) roomLoc.getY();
				int x = (int) (roomLoc.getX());
				roomLayout[(int) roomLoc.getY()][(int) (roomLoc.getX())] = 0;
				mapLoc.add(new Vector2f(-1,0));
				roomLayout[y][x] = 0;
				roomLoc = new Vector2f(12-roomLoc.getX(), roomLoc.getY());
				roomLayout[y][x] = 0;
				if(!rooms[playerY][playerX-1].getIsBossRoom()){
					treasureChest = generateChests(roomLoc, container);
					spawnEnemies();
					if(bossMusic.isPlaying()){
						bossMusic.stop();
						dungeonMusic.playAsMusic(1.0f, 1.0f, true);
					}
				}
				else{
					spawnBoss();
				}
			}
		}
		if(container.getInput().isKeyPressed(Input.KEY_S)){
			move(2, new Vector2f(0,1));
			if(rooms[playerY+1][playerX].getOccupied()==1&&roomLoc.y>9&&roomLoc.x==6){
				int y = (int) roomLoc.getY();
				int x = (int) (roomLoc.getX());
				roomLayout[(int) roomLoc.getY()][(int) (roomLoc.getX())] = 0;
				mapLoc.add(new Vector2f(0,1));
				roomLayout[y][x] = 0;
				roomLoc = new Vector2f(roomLoc.getX(), 1);
				roomLayout[y][x] = 0;
				if(!rooms[playerY+1][playerX].getIsBossRoom()){
					treasureChest = generateChests(roomLoc, container);
					spawnEnemies();
					if(bossMusic.isPlaying()){
						bossMusic.stop();
						dungeonMusic.playAsMusic(1.0f, 1.0f, true);
					}
				}
				else{
					spawnBoss();
				}
			}
		}
		if(container.getInput().isKeyPressed(Input.KEY_SPACE)){
			tileY=1;
			for(NPC enemy: enemies){
				if(orientation == 0 && enemy.getLoc().y == hero.getLoc().y - 1){
					hero.attack(hero, enemy);
					hit.playAsSoundEffect(1.0f, 1.0f, false);
				}
				if(orientation == 1 && enemy.getLoc().x == hero.getLoc().x + 1){
					hero.attack(hero, enemy);
					hit.playAsSoundEffect(1.0f, 1.0f, false);
				}
				if(orientation == 2 && enemy.getLoc().y == hero.getLoc().y + 1){
					hero.attack(hero, enemy);
					hit.playAsSoundEffect(1.0f, 1.0f, false);
				}
				if(orientation == 3 && enemy.getLoc().x == hero.getLoc().x - 1){
					hero.attack(hero, enemy);
					hit.playAsSoundEffect(1.0f, 1.0f, false);
				}
			}
			
		}else{
			tileY =0;
		}
		if(delay>300){
			for(NPC enemy: enemies){
				if(!((enemy.getLoc().y + 1 == hero.getLoc().y && enemy.getLoc().x == hero.getLoc().x)
					|| (enemy.getLoc().y == hero.getLoc().y && enemy.getLoc().x + 1 == hero.getLoc().x)
					|| (enemy.getLoc().y - 1== hero.getLoc().y && enemy.getLoc().x == hero.getLoc().x)
					|| (enemy.getLoc().y == hero.getLoc().y && enemy.getLoc().x - 1 == hero.getLoc().x))){
					moveEnemy( enemy, getMoveNums(enemy));
				}
				else{
					hit.playAsSoundEffect(1.0f, 1.0f, false);
					enemy.attack(enemy, hero);
				}
			}
			delay = 0;
			}
			delay+=d;
			
			takeOutYourDead(game, container);
	}
	public void movePlayer(int[][] roomLayout, int orientation, Vector2f move, int occupied, boolean xLoc, boolean yLoc, Vector2f roomLoc, boolean boss, GameContainer container) throws SlickException{
		move(orientation, move);
		if(occupied==1&&yLoc&&xLoc){
			int y = (int) roomLoc.getY();
			int x = (int) (roomLoc.getX());
			roomLayout[(int) roomLoc.getY()][(int) (roomLoc.getX())] = 0;
			mapLoc.add(new Vector2f(0,1));
			roomLayout[y][x] = 0;
			roomLoc = new Vector2f(roomLoc.getX(), 1);
			roomLayout[y][x] = 0;
			if(!boss){
				treasureChest = generateChests(roomLoc, container);
				spawnEnemies();
				if(bossMusic.isPlaying()){
					bossMusic.stop();
					dungeonMusic.playAsMusic(1.0f, 1.0f, true);
				}
			}
			else{
				spawnBoss();
			}
		}
	}
	public void spawnBoss() throws SlickException{
		int[][] roomLayout = getRoomLayout();
		for(int row = 0; row < 12; row++){
			for(int col = 0; col < 12; col++){
				if(roomLayout[row][col] == 7){
					roomLayout[row][col] = 0;
				}
			}
		}
		
		enemies = new ArrayList<NPC>();
		enemies.add(new Glemlock(new Vector2f(6,6)));
		bossMusic.playAsMusic(1.0f, 1.0f, true);
	}
public void takeOutYourDead(StateBasedGame game, GameContainer container) throws SlickException{
		
		int[][] roomLayout = getRoomLayout();
		
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).getHealth()<=0){
				if(enemies.get(i).getName().equals("Glemlock")){
					roomLayout[(int) enemies.get(i).getLoc().y][(int) enemies.get(i).getLoc().x] = 0;
					enemies.remove(i);
					roomLayout[(int) hero.getLoc().y][(int) hero.getLoc().x] = 0;
					init(container, game);
					game.enterState(StateBasedRunner.victoryID);
				}
				roomLayout[(int) enemies.get(i).getLoc().y][(int) enemies.get(i).getLoc().x] = 0;
				enemies.remove(i);
			}
		}
	}
	
	public void move(int o, Vector2f move){
		
		if(orientation != o){
			tileX = o;
			orientation = o;
		}else{
			tileX = o;
			int[][] roomLayout = getRoomLayout();
			if(roomLayout[(int) roomLoc.getY() + (int) move.y][(int) roomLoc.getX() + (int) move.x] == 0){
				roomLayout[(int) roomLoc.y][(int) roomLoc.x] = 0;
				roomLayout[(int) (roomLoc.y + move.y)][(int) (roomLoc.x + move.x)] = 1;
				roomLoc.add(move);
			}
			orientation = o;
		}
		
		
	}
	
	public int[][] getMoveNums(NPC enemy){
		int[][] roomLayout = getRoomLayout();
		int val = 1;
		boolean done = false;
		int[][] moveNums = new int[12][12];
		int heroX = (int) hero.getLoc().x;
		int heroY = (int) hero.getLoc().y;
		
			if(heroY + 1 < 12 && (roomLayout[heroY + 1][heroX] == 0 || roomLayout[heroY + 1][heroX] == 7)){
				moveNums[heroY+1][heroX] = 1;
			}
			if(heroY - 1 > -1 && (roomLayout[heroY - 1][heroX] == 0 || roomLayout[heroY - 1][heroX] == 7)){
				moveNums[heroY-1][heroX] = 1;
			}
			if(heroX + 1 < 12 && (roomLayout[heroY][heroX + 1] == 0 || roomLayout[heroY][heroX + 1] == 7)){
				moveNums[heroY][heroX+1] = 1;
			}
			if(heroX - 1 > -1 && (roomLayout[heroY][heroX -1 ] == 0 || roomLayout[heroY][heroX + 1] == 7)){
				moveNums[heroY][heroX-1] = 1;
			}
			while(!done){
				for(int row = 0; row < 12; row++){
					for(int col = 0; col < 12; col++){
						if(moveNums[row][col] == val){
							if(row + 1 < 12 && moveNums[row+1][col] == 0 && (roomLayout[row + 1][col] == 0 || roomLayout[row + 1][col] == 7)){
								moveNums[row+1][col] = val + 1;
							}
							if(row - 1 > -1 && moveNums[row-1][col] == 0 && (roomLayout[row - 1][col] == 0 || roomLayout[row - 1][col] == 7)){
								moveNums[row-1][col] = val + 1;
							}
							if(col + 1 < 12 && moveNums[row][col+1] == 0 && (roomLayout[row][col + 1] == 0 || roomLayout[row][col + 1] == 7)){
								moveNums[row][col+1] = val + 1;
							}
							if(col - 1 > -1 && moveNums[row][col-1] == 0 && (roomLayout[row][col -1 ] == 0 || roomLayout[row][col - 1] == 7)){
								moveNums[row][col-1] = val + 1;
							}
						}
					}
				}
				if(moveNums[(int) enemy.getLoc().y][(int) enemy.getLoc().x] != 0){
					done = true;
				}
				val++;
			}
			return moveNums;
	}
	
	public void moveEnemy(NPC enemy, int[][] moveNums){
		int[][] roomLayout = getRoomLayout();
		int minY = 0;
		int minX = 0;
		int min = 50;
		if(enemy.getLoc().y+1<12&&moveNums[(int)enemy.getLoc().y + 1][(int)enemy.getLoc().x] != 0 && moveNums[(int)enemy.getLoc().y + 1][(int)enemy.getLoc().x] < min){
			minY = (int)enemy.getLoc().y + 1;
			minX = (int)enemy.getLoc().x;
			min = moveNums[minY][minX];
		}
		if(enemy.getLoc().y-1>-1&&moveNums[(int)enemy.getLoc().y - 1][(int)enemy.getLoc().x] != 0 && moveNums[(int)enemy.getLoc().y - 1][(int)enemy.getLoc().x] < min){
			minY = (int)enemy.getLoc().y - 1;
			minX = (int)enemy.getLoc().x;
			min = moveNums[minY][minX];
		}
		if(enemy.getLoc().x+1<12&&moveNums[(int)enemy.getLoc().y][(int)enemy.getLoc().x + 1] != 0 && moveNums[(int)enemy.getLoc().y][(int)enemy.getLoc().x + 1] < min){
			minY = (int)enemy.getLoc().y;
			minX = (int)enemy.getLoc().x + 1;
			min = moveNums[minY][minX];
		}		
		if(enemy.getLoc().x-1>-1&&moveNums[(int)enemy.getLoc().y][(int)enemy.getLoc().x - 1] != 0 && moveNums[(int)enemy.getLoc().y][(int)enemy.getLoc().x - 1] < min){
			minY = (int)enemy.getLoc().y;
			minX = (int)enemy.getLoc().x - 1;
			min = moveNums[minY][minX];
		}
		roomLayout[(int)enemy.getLoc().y][(int)enemy.getLoc().x] = 0;
		enemy.getLoc().set(minX, minY);
		roomLayout[(int)enemy.getLoc().y][(int)enemy.getLoc().x] = 7;
		
	}
	
public int[][] getRoomLayout(){
	int playerX = (int) mapLoc.getX();
	int playerY = (int) mapLoc.getY();
	int[][] roomLayout = rooms[playerY][playerX].getLayout();
	return roomLayout;
}
	
public void spawnEnemies() throws SlickException{
		
		int[][] roomLayout = getRoomLayout();
		for(int row = 0; row < 12; row++){
			for(int col = 0; col < 12; col++){
				if(roomLayout[row][col] == 7){
					roomLayout[row][col] = 0;
				}
			}
		}
		enemies = new ArrayList<NPC>();
		for(int i = 0; i < (int) (Math.random() * 4 +1); i++){
			boolean spawned = false;
			while(spawned == false){
			int x = (int)(Math.random() * 10 + 1);
			int y = (int)(Math.random() * 10 + 1);
			if(roomLayout[y][x] == 0){
				int type = (int) (Math.random() * 3);
				switch(type){
				case 0:
					enemies.add(i, new Goblin(new Vector2f(x,y)));
					break;
				case 1:
					enemies.add(i, new Undead(new Vector2f(x,y)));
					break;
				case 2:
					enemies.add(i, new Troll(new Vector2f(x,y)));
					break;
				}
				
				spawned = true;
			}
		}
		}
	}
	@Override
	public int getID() {
		return StateBasedRunner.gameID;
	}
	public static Audio loadAudio(String key) throws IOException{
		return AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/"+key+".wav"));
	}

	public Map getMap() {
		return map;
	}

	public SpriteSheet getTiles() {
		return tiles;
	}

	public Image getChest() {
		return chest;
	}

	public Image getDirt() {
		return dirt;
	}

	public Image getDoor() {
		return door;
	}

	public Image getPillar() {
		return pillar;
	}

	public Image getPit() {
		return pit;
	}

	public Image getPlayer() {
		return player;
	}

	public Image getWall() {
		return wall;
	}

	public Vector2f getMapLoc() {
		return mapLoc;
	}

	public Vector2f getRoomLoc() {
		return roomLoc;
	}

	public Vector2f getBossLoc() {
		return bossLoc;
	}

	public Room[][] getRooms() {
		return rooms;
	}

	public Player getHero() {
		return hero;
	}

	public static Audio getDungeonMusic() {
		return dungeonMusic;
	}

	public static Audio getBossMusic() {
		return bossMusic;
	}

	public static Audio getChestOpen() {
		return chestOpen;
	}

	public static Audio getHit() {
		return hit;
	}

	public String getDialogue() {
		return dialogue;
	}

	public TreasureChest getTreasureChest() {
		return treasureChest;
	}

	public ArrayList<NPC> getEnemies() {
		return enemies;
	}

	public int getTileX() {
		return tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public int getOrientation() {
		return orientation;
	}

	public int getDelay() {
		return delay;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void setTiles(SpriteSheet tiles) {
		this.tiles = tiles;
	}

	public void setChest(Image chest) {
		this.chest = chest;
	}

	public void setDirt(Image dirt) {
		this.dirt = dirt;
	}

	public void setDoor(Image door) {
		this.door = door;
	}

	public void setPillar(Image pillar) {
		this.pillar = pillar;
	}

	public void setPit(Image pit) {
		this.pit = pit;
	}

	public void setPlayer(Image player) {
		this.player = player;
	}

	public void setWall(Image wall) {
		this.wall = wall;
	}

	public void setMapLoc(Vector2f mapLoc) {
		this.mapLoc = mapLoc;
	}

	public void setRoomLoc(Vector2f roomLoc) {
		this.roomLoc = roomLoc;
	}

	public void setBossLoc(Vector2f bossLoc) {
		this.bossLoc = bossLoc;
	}

	public void setRooms(Room[][] rooms) {
		this.rooms = rooms;
	}

	public void setHero(Player hero) {
		this.hero = hero;
	}

	public static void setDungeonMusic(Audio dungeonMusic) {
		GameState.dungeonMusic = dungeonMusic;
	}

	public static void setBossMusic(Audio bossMusic) {
		GameState.bossMusic = bossMusic;
	}

	public static void setChestOpen(Audio chestOpen) {
		GameState.chestOpen = chestOpen;
	}

	public static void setHit(Audio hit) {
		GameState.hit = hit;
	}

	public void setDialogue(String dialogue) {
		this.dialogue = dialogue;
	}

	public void setTreasureChest(TreasureChest treasureChest) {
		this.treasureChest = treasureChest;
	}

	public void setEnemies(ArrayList<NPC> enemies) {
		this.enemies = enemies;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
}