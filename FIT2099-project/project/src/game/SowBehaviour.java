package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import java.lang.Math;

/**
 * This class represents a behaviour of sowing a crop
 * @author Wong Wai Chuin
 *
 */
public class SowBehaviour implements Behaviour{
	
	/**
	 * Constructor
	 * @param crop: crop object
	 */
	public SowBehaviour() {
	}
	
	/**
	 * This method that overrides the same method in Behaviour would loop through all adjacent location relative to the actor's
	 * current location and if there is a patch of dirt, then the actor would have a 33% of return a sowAction.
	 * 
	 * @param actor: actor object
	 * @param map: GameMap
	 * @return a sowing action
	 */
	public Action getAction(Actor actor, GameMap map) {
		List<Exit> sowExits = map.locationOf(actor).getExits();
		
		for (Exit exit: sowExits) {
			Location sowLocation = exit.getDestination();
			if (sowLocation.canActorEnter(actor) == true && sowLocation.getGround() instanceof Crop == false) {
				double randNum = Math.random();
				if (randNum <= 0.33) {
					return new SowAction(sowLocation);
				}
				else {
					return null;
				}
			}
		}
		return null;
		
	}

}
