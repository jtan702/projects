package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import javafx.beans.property.IntegerProperty;
import java.util.Random;

/**
 * ZombieAction makes Zombie do something 'zombie-like'
 * @author Jiajun
 *
 */
public class ZombieAction extends Action{
	
	/**
	 * Executes the actions of a Zombie-like action
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		String result = actor + " "; 
		if(Math.random()  < 0.5) {
			result += "says 'BRAAIIINNNNNSSSSS'.";
		} else {
			result += "trips on itself.";
		}
		return result;
	}

	/**
	 * This method returns a string description of the action that would be seen in the menu.
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + "is doing a Zombie.";
	}

}
