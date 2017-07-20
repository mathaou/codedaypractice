package data;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import game.GameState;

public class LoadSave {

	public TXTRead read;
	private Map map;
	private Vector2f mapLoc, roomLoc, bossLoc;
	private Room[][] rooms;
	private String[] mapLayout;
	
	public LoadSave(File fileKey) {
		try {
			read = new TXTRead(fileKey);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		mapLayout = read.getArr();
		System.out.println(Arrays.toString(mapLayout));
	}
	public void setMapLayout(){
		String a = "";
		for(int i = 0; i < mapLayout.length; i++){
			while(a!=mapLayout[i]){
				
			}
		}
	}
	public void transferValues(){
		GameState.map = map;
		GameState.bossLoc = bossLoc;
		GameState.roomLoc = roomLoc;
		GameState.mapLoc = mapLoc;
		GameState.rooms = rooms;
	}

}
