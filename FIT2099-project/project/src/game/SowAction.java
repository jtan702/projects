package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * This class represents an action of sowing a crop on a patch of dirt. 
 * @author Wong Wai Chuin
 *
 */
public class SowAction extends Action{
	
	private Location sowLocation;
	
	/**
	 * Constructor.
	 * @param currLocation: current location 
	 * @param crop: crop object
	 */
	public SowAction(Location currLocation) {
		this.sowLocation = currLocation;
	}
	
	/**
	 * this method that overrides Action class's same method would replace the dirt object with a crop object and return
	 * a string description of the action.
	 * 
	 * @param actor: actor object
	 * @param map: GameMap
	 * @return a string containing a description
	 *
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		Crop cropObj = new Crop();
		sowLocation.setGround(cropObj);
		return actor + " sows crop on position " + sowLocation.x() +" , " + sowLocation.y();
	}

	/**
	 * this method returns a string description of the action in the menu.
	 * @param actor: actor object
	 * @return a string
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " sows crop";
	}

}
