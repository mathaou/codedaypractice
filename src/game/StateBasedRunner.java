package game;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

	
public class StateBasedRunner extends StateBasedGame {

	public static BasicGameState gameState, menuState, pauseState, gameOverState, victoryState;
	public static int menuID = 0, gameID = 1, pauseID = 2, gameOverID = 3, victoryID = 4;
	
	public StateBasedRunner(String name) throws SlickException {
		super(name);
		gameState = new GameState();
		menuState = new MenuState();
		pauseState = new PauseState();
		gameOverState = new GameOverState();
		victoryState = new VictoryState();
	}

	@Override
	public void initStatesList(GameContainer c) throws SlickException {
		addState(menuState);
		addState(gameState);
		addState(pauseState);
		addState(gameOverState);
		addState(victoryState);
	}

}
