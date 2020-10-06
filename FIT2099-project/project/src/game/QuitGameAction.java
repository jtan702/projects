package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * This class represents the action to quit the game
 * @author Wong Wai Chuin
 *
 */
public class QuitGameAction extends Action{
	/**
	 * Constructor.
	 */
	public QuitGameAction() {}
	
	/**
	 * the method which executes the action of quitting the game.
	 * @param actor: the actor performing the action
	 * @param map: the gamemap
	 * @return a string that says that the action is executed.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		map.removeActor(actor);
		return "You have quit the game.";
	}
	
	/**
	 * A description that appears in the menu that the player can choose from.
	 * @param actor: an actor
	 * @return a string that describes the action.
	 */
	@Override
	public String menuDescription(Actor actor) {
		return "Quit Game";
	}

}
