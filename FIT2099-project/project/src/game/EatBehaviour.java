package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * This class represents the behaviour of being hurt.
 * @author Wong Wai Chuin
 *
 */
public class EatBehaviour implements Behaviour{
	
	/**
	 * Constructor.
	 */
	public EatBehaviour() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * This method goes through an actor's inventory and if there is a item that is instance of food, we can sum the actor's 
	 * current hp with the hp that can be gained from the food. If the sum of both equals to 100, then it would return a new
	 * UseAction.
	 * 
	 * @param actor: actor object
	 * @param map: GameMap
	 * @return a new action
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		
		List<Exit> adjacentLocations = map.locationOf(actor).getExits();
		List<Item> items = map.locationOf(actor).getItems();
		
		if (actor.getHitPoints() >= actor.getMaxHealth()) {
			return null;
		}
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) instanceof Food) {
				Food foodObj = (Food) items.get(i);
				return new EatAction(foodObj);
			}
		}
		return null;
	}

}
