package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * This class represents the behaviour of fertilising the crop
 * @author Wong Wai Chuin
 *
 */
public class FertiliseBehaviour implements Behaviour{
	
	/**
	 * Constructor.
	 * 
	 */
	public FertiliseBehaviour() {}
	
	
	/**
	 * This method overrides the getAction method in Behaviour class. this method would check if the ground is of instance Crop
	 * and whether the ground where the actor is currently at does not have a Crop capability of ripe. If both are true.
	 * Then, the method would call an action to fertilise the crop.
	 * @param actor: actor object
	 * @param map: gameMap
	 */

	@Override
	public Action getAction(Actor actor, GameMap map) {
		if (map.locationOf(actor).getGround() instanceof Crop && map.locationOf(actor).getGround().hasCapability(CropCapability.RIPE) != true) {
			Crop crop = (Crop) map.locationOf(actor).getGround();
			return new FertiliseAction(crop);
		}
		else {
			return null;
		}
	}

}
