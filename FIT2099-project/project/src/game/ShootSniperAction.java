package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * This class represents the action of shooting a sniper
 * @author Wong Wai Chuin
 *
 */
public class ShootSniperAction extends Action{
	
	private Actor target;
	private Integer concentration;
	
	/**
	 * Constructor.
	 * @param target: actor to perform the action on
	 * @param concentration: an int for aiming
	 */
	public ShootSniperAction(Actor target, Integer concentration) {
		this.target = target;
		this.concentration = concentration;
	}
	
	/**
	 * This method executes the action of shooting the sniper.
	 * @param actor: an actor
	 * @param map: a gamemap
	 * @return a string
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		
		String result =  actor + " shot the sniper.";
		int ammoLoc = 0;
		int quantity = 0;
		boolean empty = false;
		
		for (Item item : actor.getInventory()) {
			if (item instanceof SniperAmmo) {
				SniperAmmo ammo = (SniperAmmo) item;
				ammo.use();
				ammoLoc = actor.getInventory().indexOf(item);
				empty = ammo.empty();
				quantity = ammo.getQuantity();
			}
		}
		
		if (empty) {
			Item item = actor.getInventory().get(ammoLoc);
			actor.removeItemFromInventory(item);
		}
		
		if (concentration == 0) {
			if (Math.random() <= 0.75) {
				target.hurt(30);	
				result += System.lineSeparator() + actor + " snipes " + target;
			} else {
				result += System.lineSeparator() + actor + " misses " + target;
			} 
			
		}
		else if (concentration == 1) {
			if (Math.random() <= 0.90) {
				target.hurt(60);
				result += System.lineSeparator() + actor + " snipes " + target;
			} else {
				result += System.lineSeparator() + actor + " misses " + target;
			} 
		}
		else {
			target.hurt(actor.getMaxHealth() + 1);
			result += System.lineSeparator() + actor + " snipes " + target;
		}
		
		if (!target.isConscious()) {
			Item corpse = new Corpse("dead " + target, '%', true, map);
			map.locationOf(target).addItem(corpse);
		
			Actions dropActions = new Actions();
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)		
				drop.execute(target, map);
			map.removeActor(target);
			result += System.lineSeparator() + target + " is killed.";
		}
		
		result += System.lineSeparator() + "Remaining sniper ammo: " + quantity;

		return result;

	}
	
	/**
	 *This method returns a string description of the action that would be seen in the menu.
	 */
	@Override
	public String menuDescription(Actor actor) {
		return  "Shoot at " + target;
	}

}
