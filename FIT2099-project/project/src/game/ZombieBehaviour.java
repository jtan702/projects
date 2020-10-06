package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import java.util.Random;

/**
 * A class that generates a ZombieAction for the Zombie.
 * ZombieActions have a 0.1 chance of occuring.
 * @author Jiajun
 *
 */
public class ZombieBehaviour implements Behaviour{

	/**
	 * Method has a 10% of returning a Zombie Action or null, which means the Zombie will continue
	 * acting on its other behaviours.
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		if(Math.random() <= 0.1){
			return new ZombieAction();
		}
		
		return null;
	}
}
