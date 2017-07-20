package game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PauseState extends BasicGameState{

	public Image pauseState;
	
	public PauseState() {
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		pauseState = new Image("res/pauseState.png");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		pauseState.draw();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int d) throws SlickException {
		if (container.getInput().isKeyPressed(Input.KEY_ENTER)) {
			container.getInput().clearKeyPressedRecord();
			game.enterState(StateBasedRunner.gameID);
		}
	}

	@Override
	public int getID() {
		return StateBasedRunner.pauseID;
	}

}
