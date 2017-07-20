package game;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class DungeonStart {

	static int mapSize = 50;
	public static void main(String[]args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new StateBasedRunner("Game of Merriment"));
		
		app.setTargetFrameRate(60);
		app.setDisplayMode((13*mapSize), 13*(mapSize)+mapSize, false);
		app.setAlwaysRender(true);
		
		app.start();
	}
}
