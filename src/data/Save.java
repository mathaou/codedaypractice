package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import game.GameState;

public class Save {
	GameState game;
	BufferedWriter writer = null;
	String mapLayout = "";
	int[][] map = new int[10][10];
	
	public Save(GameState game) throws IOException {
		this.game = game;
		Room[][] arr = this.game.getRooms();
		for(int y = 0; y < 10; y++){
			for(int x = 0; x < 10; x++){
				map[y][x] = arr[y][x].getOccupied();
			}
		}
		mapLayout += "ML: ";
		for(int y = 0; y < 10; y++){
			for(int x = 0; x < 10; x++){
				mapLayout +=map[y][x];
				if(!(x == 9))
					mapLayout += ", ";
				else{
					mapLayout +="-";
				}
			}
		}
		writeToFile(mapLayout);
	}
	public void writeToFile(String str) throws IOException{
		try {
			File file = new File("res/save.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(str);
			
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeMapLayout(BufferedWriter write){
		
	}
}
