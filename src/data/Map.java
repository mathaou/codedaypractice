package data;
import java.io.File;
import java.security.SecureRandom;

import org.newdawn.slick.geom.Vector2f;

public class Map {

	public Room[][] map;
	public static Vector2f startLoc, endLoc;
	SecureRandom rand = new SecureRandom();
	Vector2f minerLocation;
	File file = new File("res/save.txt");
	
	public Map() {
		
//		if(file.exists()){
//			new LoadSave(file);
//		}else{
//			map = new Room[11][11];
//			resetMap();
//			mine(0,0);
//			setMap();
//		}
		map = new Room[11][11];
		resetMap();
		mine(0,0);
		setMap();
		startLoc = getStartOccupiedLocation();
		endLoc = getEndOccupiedLocation();
//		for(int y = 0; y < map.length; y++){
//			for(int x = 0; x < map.length; x++){
//				System.out.print(map[y][x].getOccupied());
//			}
//			System.out.println();
//		}
//		System.out.println();
	}
	public void resetMap(){
		for(int y = 0; y < map.length; y++){
			for(int x = 0; x < map.length; x++){
				map[y][x] = new Room();
			}
		}
	}
	public Vector2f getStartOccupiedLocation(){
		for(int y = 0; y < map.length; y++){
			for(int x = 0; x < map.length; x++){
				if(map[y][x].getOccupied()==1){
					return new Vector2f(x,y);
				}
			}
		}
		return null;
	}
	public Vector2f getEndOccupiedLocation(){
		for(int y = 10; y >= 0; y--){
			for(int x = 10; x >= 0; x--){
				if(map[y][x].getOccupied()==1){
					map[y][x].setIsBossRoom(true);
					return new Vector2f(x,y);
				}
			}
		}
		return null;
	}
	public void setMap(){
		for(Room[] room: map){
			for(int i = 0; i < room.length; i++){
				room[i].setLayout(Room.selectRoomType());
			}
		}
		startLoc = minerLocation.copy();
		map[(int)startLoc.x][(int)startLoc.y].setOccupied(1);
		endLoc = minerLocation.copy();
		endLoc.add(getDir());
		map[(int)endLoc.x][(int)endLoc.y].setOccupied(1);
	}
	public void mine(int iX, int iY){
		if(iX==0&iY==0){
			minerLocation = new Vector2f(rand.nextInt(10),rand.nextInt(10));
		}else{
			minerLocation = new Vector2f(iX,iY);
		}
		
		Vector2f locToCheck;
		for(int i = 0; i < 60; i++){
				locToCheck = getDir();
				int x = (int) (minerLocation.x+locToCheck.x);
				int y = (int) (minerLocation.y+locToCheck.y);
				if(x>0&&x<10&&y>0&&y<10){
					if(map[x][y].getOccupied()==0){
						map[x][y].setOccupied(1);
						minerLocation.add(locToCheck);
//						if(Math.random()*100 < 9){
//							mine((int)(minerLocation.x),(int)(minerLocation.y));
//						}
					}
				}
		}
	}
	public Vector2f getDir(){
		int num = rand.nextInt(4);
		switch(num){
		case 0 : return new Vector2f(-1,0);
		case 1: return new Vector2f(0,-1);
		case 2: return new Vector2f(1,0);
		case 3: return new Vector2f(0,1);
		}
		return null;
	}
	public Room[][] getMap() {
		return map;
	}
	public SecureRandom getRand() {
		return rand;
	}
	public void setMap(Room[][] map) {
		this.map = map;
	}
	public void setRand(SecureRandom rand) {
		this.rand = rand;
	}
	public Vector2f getStartLoc() {
		return startLoc;
	}
	public Vector2f getEndLoc() {
		return endLoc;
	}
	public void setStartLoc(Vector2f startLoc) {
		this.startLoc = startLoc;
	}
	public void setEndLoc(Vector2f endLoc) {
		this.endLoc = endLoc;
	}
	public Vector2f getMinerLocation() {
		return minerLocation;
	}
	public void setMinerLocation(Vector2f minerLocation) {
		this.minerLocation = minerLocation;
	}
}
