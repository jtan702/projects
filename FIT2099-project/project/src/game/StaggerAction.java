package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;


/**
 * @author Jiajun
 * An action used for when a Zombie staggers.
 */
public class StaggerAction extends Action {

	/**
	 * returns a String making the Zombie look like its staggering
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " staggers.";
	}

}
