package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * This class represents an action of harvesting a ripened crop.
 * @author Wong Wai Chuin
 *
 */
public class HarvestAction extends Action{
	
	private Location harvestLocation;
	
	/**
	 * Constructor.
	 * @param locationToHarvest: the location to harvest the crop
	 */
	public HarvestAction(Location locationToHarvest) {
		this.harvestLocation = locationToHarvest;
	}
	
	/**
	 * This method overrides the execute method in Action class. this method would remove the ripe crop capability from
	 * the ground which has a crop object and change it back to a dirt object. After that, a food item would be added to
	 * the same location.
	 * 
	 * @param actor: actor object
	 * @param map: GameMap
	 * @return a string of the action that has been done
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		harvestLocation.getGround().removeCapability(CropCapability.RIPE);
		Dirt dirt = new Dirt();
		harvestLocation.setGround(dirt);
		Food food = new Food(30);

		if (actor instanceof Player) {
			actor.addItemToInventory(food);
		}
		else {
			harvestLocation.addItem(food);
		}
		
		return actor + " has harvested the crop.";
	}
	
	/**
	 * This method returns a string containing description of the actor harvesting the crop in the menu
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " harvests the crop";
	}

}
