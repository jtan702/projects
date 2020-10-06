package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * This class represents the action of using an item.
 * @author Wong Wai Chuin
 *
 */
public class EatAction extends Action{
	
	private Food food;
	
	/**
	 * Constructor.
	 * @param foodObj: a food object
	 */
	public EatAction(Food foodObj) {
		this.food = foodObj;
	}
	
	/**
	 * This method removes the food item from the actor's inventory and uses it to heal themselves. Then, it would return a
	 * string description of the action being done.
	 * 
	 * @param actor: actor object
	 * @param map: GameMap
	 * @return a string description of the action
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		if (actor instanceof Player) {
			actor.removeItemFromInventory(food);
		}	
		else {
			map.locationOf(actor).removeItem(food);
		}
		
		actor.heal(food.getHealthPointsRecovered());
		
		if (actor.getHitPoints() == actor.getMaxHealth()) {
			return actor + " has regained to full HP!";
		}
		else {
		return actor + " has regained to " + actor.getHitPoints() + " out of " + actor.getMaxHealth() + " HP";
		}
	}
	
	/**
	 * This method returns a string containing description of the action in the menu.
	 * @param actor: actor object
	 * @return a string
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " eats food";
	}

}
