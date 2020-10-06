package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * This class represents a null action
 * @author Tan Jiajun
 *
 */
public class NullAction extends Action{
	
	/**
	 * This method executes the action of returning a null.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		return null;
	}
	
	/**
	 *This method returns a string description of the action that would be seen in the menu.
	 */
	@Override
	public String menuDescription(Actor actor) {
		return "Go Back to previous actions menu";
	}

}
