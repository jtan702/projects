package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * @author Jiajun
 * A class that generates a pickUpAction for the actor. 
 * The actor will only pick the item up if its a target item.
 *
 */
public class PickUpBehaviour implements Behaviour{
	private Class<?> targetClass;
	
	/**
	 * Constructor that lets the class know what item to provide the pick
	 * up action for
	 * @param cls the class of the item to be picked up.
	 */
	public PickUpBehaviour(Class<?> cls) {
		this.targetClass = cls;
	}

	/**
	 * Method returns an action to pick up an item on the map if the item
	 * is a target item that the Zombie wants to pick up.
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		Location actorLocation = map.locationOf(actor);
		
		if (actor.getWeapon() instanceof IntrinsicWeapon) {
			for (Item item : actorLocation.getItems()) {
				if (targetClass.isInstance(item)) {
					return item.getPickUpAction();
				}
			}
		}
		return null;
	}

}
