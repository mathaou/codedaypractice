package game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverState extends BasicGameState{
	
	public Image gameOver;
	
	public GameOverState() {
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		gameOver = new Image("res/loseState.png");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		gameOver.draw();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int d) throws SlickException {
		if (container.getInput().isKeyPressed(Input.KEY_ENTER)) {
			container.getInput().clearKeyPressedRecord();
			game.enterState(StateBasedRunner.menuID);
		}
	}

	@Override
	public int getID() {
		return StateBasedRunner.gameOverID;
	}

}
