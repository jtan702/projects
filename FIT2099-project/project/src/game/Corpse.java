/**
 * 
 */
package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

import java.util.List;
//import java.lang.Math;

/**
 * A class the represents a Corpse that extends from Item class.
 * @author Wong Wai Chuin
 *
 */
public class Corpse extends Item{
	
	private int age = 0;
	private GameMap mapObj;
	/**
	 * This constructor takes in parameters name, displayChar, portable and map.
	 * @param name: Name of corpse
	 * @param  displayChar: the display character of Corpse
	 * @param portable: boolean of whether corpse is portable or not
	 * @param map: GameMap
	 * 
	 */
	public Corpse(String name, char displayChar, boolean portable, GameMap map) {
		super(name, displayChar, portable);
		this.mapObj = map;
	}

	/**
	 * This function overrides the tick(Location currLocation) in Item class for items on the ground,
	 * it assumes that corpse would be on the ground
	 * 
	 * @param currentLocation: the location of the item on the ground
	 */
	@Override
	public void tick(Location currentLocation) {
		super.tick(currentLocation);
		List<Item> items = currentLocation.getItems();
//		System.out.println(items.contains(this));
		
		if (age >= 5 && age <= 10) {
			double randNum = Math.random();
			if (randNum > 0.5) {

				//what if there is more than one item on the location and its not a corpse
				currentLocation.removeItem(currentLocation.getItems().get(0));
				Zombie zombieExtra = new Zombie("Undead", mapObj);
				placeZombie(currentLocation);
			}
		} 
		
		age ++;
	}
	
	/**
	 * This function overrides tick(Location currLocation, Actor actor) in Item class for the corpse that the player
	 * is holding, where between 5-10 turns, there would be 50% chance of the corpse turning into a zombie. If that
	 * happens, it loops through the actor's inventory and check if there is an item which is an instance of Corpse class.
	 * If there is, It would remove the corpse from inventory and adds a zombie at the nearest adjacent location to the 
	 * player.
	 * 
	 * @param currentLocation: the current location of the actor carrying the item
	 * @param actor: an actor object
	 */
	@Override
	public void tick(Location currentLocation, Actor actor) {
		super.tick(currentLocation, actor);
		List<Item> items = currentLocation.getItems();
//		System.out.println(items.contains(this));
		if (age >= 5 && age <= 10) {
			double randNum = Math.random();
			if (randNum > 0.5) {
				List<Item> itemList = actor.getInventory();
				for (int i= 0; i < itemList.size(); i++) {
					if (itemList.get(i) instanceof Corpse) {
//						itemObj.getDropAction().execute(actor, mapObj);
						actor.removeItemFromInventory(itemList.get(i));
//						itemList.remove(i);
							
						placeZombie(currentLocation);
					}
				}
			}
			else {
				age ++;
			}
		}
		else {
			age ++ ;
		}		
	}
	
	private void placeZombie(Location location) {
		Zombie zombieExtra = new Zombie("Undead", mapObj);
		
		if (!location.containsAnActor()) {
			location.addActor(zombieExtra);
		} else {
			List<Exit> exitList = location.getExits();
			for (Exit exit : exitList) {
				if (!exit.getDestination().containsAnActor()) {
					exit.getDestination().addActor(zombieExtra);
					System.out.println("Corpse has risen from the dead");
					break;
				}
			}
		}
	}
	
}
