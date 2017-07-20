package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class VictoryState extends BasicGameState{

	public Image victoryState;
	
	@Override
	public void init(GameContainer c, StateBasedGame game) throws SlickException {
		victoryState = new Image("res/victoryState.png");
	}

	@Override
	public void render(GameContainer c, StateBasedGame game, Graphics g) throws SlickException {
		victoryState.draw();
	}

	@Override
	public void update(GameContainer c, StateBasedGame game, int d) throws SlickException {
		if (c.getInput().isKeyPressed(Input.KEY_ENTER)) {
			GameState.bossMusic.stop();
			GameState.dungeonMusic.stop();
			c.getInput().clearKeyPressedRecord();
			game.enterState(StateBasedRunner.menuID);
		}
	}

	@Override
	public int getID() {
		return StateBasedRunner.victoryID;
	}


}
