package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * This class represents the action of picking up ammo.
 * @author Tan Jiajun
 *
 */
public class CollectAction extends PickUpItemAction{
	private Item ammo;
	private boolean collected = false;
	
	/**
	 * Constructor.
	 * @param ammo : an ammo item
	 */
	public CollectAction(Item ammo) {
		super(ammo);
		this.ammo = ammo;
	}
		
	/**
	 * This method executes the action of collecting up ammo.
	 * @param actor: an actor
	 * @param map: a gamemap
	 * @return a string that states that the action has been executed.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		map.locationOf(actor).removeItem(ammo);
		
		for (Item item : actor.getInventory()) {
			if (item.hasCapability(RangedCapability.SHOTGUNAMMO) && ammo.hasCapability(RangedCapability.SHOTGUNAMMO)) {
				actor.removeItemFromInventory(item);
				ShotgunAmmo existingAmmo = (ShotgunAmmo) item;
				ShotgunAmmo newAmmo = (ShotgunAmmo) ammo;
				existingAmmo.add(newAmmo.getQuantity());
				actor.addItemToInventory(existingAmmo);
				collected = true;
				
			} else if (item.hasCapability(RangedCapability.SNIPERAMMO) && ammo.hasCapability(RangedCapability.SNIPERAMMO)) {
				actor.removeItemFromInventory(item);
				SniperAmmo existingAmmo = (SniperAmmo) item;
				SniperAmmo newAmmo = (SniperAmmo) ammo;
				existingAmmo.add(newAmmo.getQuantity());
				actor.addItemToInventory(existingAmmo);
				collected = true;
			}
		}
		
		if(!collected) {
			actor.addItemToInventory(ammo);
		}
		
		return menuDescription(actor);
	}
	
	/**
	 *This method returns a string description of the action that would be seen in the menu.
	 */

	@Override
	public String menuDescription(Actor actor) {
		return actor + " collects " + ammo;
	}
	

}
