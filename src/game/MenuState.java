package game;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState{
	public static Audio titleScreen;
	public MenuState() {
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		try {
			titleScreen = GameState.loadAudio("titleScreen");
		} catch (IOException e) {
			e.printStackTrace();
		}
		titleScreen.playAsMusic(1.0f, 1.0f, true);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawString("Glemlock is evil. Go kill him.", container.getWidth()/2, container.getHeight()/2);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int d) throws SlickException {
		if (container.getInput().isKeyPressed(Input.KEY_ENTER)) {
			titleScreen.stop();
			GameState.dungeonMusic.playAsMusic(1.0f, 1.0f, true);
			container.getInput().clearKeyPressedRecord();
			game.enterState(StateBasedRunner.gameID);
		}
	}

	@Override
	public int getID() {
		return StateBasedRunner.menuID;
	}

}
