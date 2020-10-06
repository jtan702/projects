package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * This class represents a behaviour of hasrvesting a crop.
 * @author Wong Wai Chuin
 *
 */
public class HarvestBehaviour implements Behaviour{
	
	/**
	 * Constructor.
	 */
	public HarvestBehaviour() {}
	
	/**
	 * This method goes through all adjacent location near to the actor and the current location the actor is at, if any location
	 * has the crop capability of ripe. then, it would return a new harvestAction method.
	 * 
	 * @param actor: actor object
	 * @param map: GameMap
	 * @return a harvestAction
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		List<Exit> exitList = map.locationOf(actor).getExits();
		Location currLocation = map.locationOf(actor);
		
		if (currLocation.getGround().hasCapability(CropCapability.RIPE) == true) {
			return new HarvestAction(map.locationOf(actor));
		}
		else {
			for (Exit e : exitList) {
				Location adjacentLocation = e.getDestination();
				if (adjacentLocation.getGround().hasCapability(CropCapability.RIPE) == true) {
					return new HarvestAction(adjacentLocation);
				}
			}
		}
		
		return null;
	}

}
