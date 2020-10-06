package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * This class represents the action of shooting the shotgun.
 * @author Tan Jiajun
 *
 */
public class ShootShotgunAction extends Action{
	int[][] direction;
	String dirName;
	
	/**
	 * Constructor.
	 * @param direction: a 2d arraylist of directions
	 * @param dirName: the name of the direction
	 */
	public ShootShotgunAction(int[][] direction, String dirName) {
		this.direction = direction;
		this.dirName = dirName;
	}
	
	/**
	 * This method executes the action of shooting the shotgun.
	 * @param actor: an actor
	 * @param map: a gamemap
	 * @return a string
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		String result = "";
		Location location = map.locationOf(actor);
		int ammoLoc = 0;
		int quantity = 0;
		boolean empty = false;
		
		for (Item item : actor.getInventory()) {
			if (item instanceof ShotgunAmmo) {
				ShotgunAmmo ammo = (ShotgunAmmo) item;
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
		
		for(int i = 0; i <direction.length; i++) {
			int x = location.x() + direction[i][0];
			int y = location .y() + direction[i][1];
			
			if(x >= map.getXRange().min() && x <= map.getXRange().max() && y >= map.getYRange().min() && y <= map.getYRange().max()) {
 				if(map.at(x, y).containsAnActor()) {
					Actor target = map.getActorAt(map.at(x, y));
					if(result != "")
						result += System.lineSeparator();
					
					if(Math.random() < 0.25) {
						result += actor.toString() + " misses " + target.toString();
						
					} else {
						result += actor.toString() + " blasts " + target.toString();
					
						target.hurt(25);
					
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
					}
				}
			}
		}
		
		result += System.lineSeparator() + "Remaining shotgun ammo: " + quantity;
		
		return result;
	}
	
	/**
	 *This method returns a string description of the action that would be seen in the menu.
	 */
	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return "Shoot " + dirName;
	}

}
