package game;

import java.util.HashMap;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * A class that provides players with the ability to craft Items from Items.
 * @author Jiajun
 *
 */
public class CraftAction extends Action{
	
	/**
	 * ingredientName is the simple name of the Item.
	 * craftIngredient is the item needed to make the crafted item.
	 * craftItem is the crafted item made from a craftIngredient.
	 */
	private String ingredientName;
	private Item craftIngredient;
	private Item craftItem;

	/**
	 * Constructor to initialise the craftIngredient and ingredientName
	 * @param item item to be crafted from.
	 */
	public CraftAction(Item item) {
		this.craftIngredient = item;
		this.ingredientName = item.toString();
		
	}
	
	/**
	 * Method stores all crafting recipes and will return a new craftItem depending
	 * on the craftIngredient given.
	 * @return Crafted item
	 */
	private Item getCraftItem() {
		Item out = null;
		
		if (ingredientName == "Zombie Arm") {
			out = new ZombieClub();
		} else if (ingredientName == "Zombie Leg") {
			out = new ZombieMace();
		}
		
		return out;
	}


	/**
	 * Carries out the CraftAction by deleting the craftIngredient from player's inventory
	 * and adding the craftItem to it.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		System.out.println(actor.getInventory());
		actor.removeItemFromInventory(craftIngredient);
		craftItem = getCraftItem();
		actor.addItemToInventory(craftItem);
		return actor + " crafted a " + craftItem.toString();
		
	}

	/**
	 *This method returns a string description of the action that would be seen in the menu.
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " craft with " + ingredientName;
	}

}
