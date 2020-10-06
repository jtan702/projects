package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Menu;
import edu.monash.fit2099.engine.NumberRange;

/**
 * This class represents using a sniper.
 * @author Wong Wai Chuin
 *
 */
public class UseSniperAction extends Action{
	
	private Menu menu = new Menu();
	private Integer concentration = 0;
	private AimSniperAction doAction;
	private boolean hasAmmo = false;
	
	/**
	 * Constructor
	 */
	public UseSniperAction() {
	}
	
	/**
	 * This method executes the action of using a sniper.
	 * @param actor: an actor
	 * @param map: a gamemap
	 * @return a string
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		Actions actions = new Actions();		
		NumberRange x = map.getXRange();
		NumberRange y = map.getYRange();
		actor.addCapability(RangedCapability.FOCUS);
		
		for (Item item : actor.getInventory()) {
			if (item instanceof SniperAmmo) {
				hasAmmo = true;
			}
		}
		
		if(!hasAmmo) {
			return actor + " has no sniper ammo!";
		}
		
		for (int i = 0 ; i < x.max(); i++) {
			for (int j = 0; j < y.max(); j++) {
				if (map.at(i, j).containsAnActor() == true && (map.at(i, j).getActor().hasCapability(ZombieCapability.UNDEAD) == true 
						|| map.at(i, j).getActor().hasCapability(ZombieCapability.UNDEADBOSS) == true)) {
					actions.add(new AimSniperAction(map.at(i, j).getActor(), 0));

				}
			}
		}
		
		doAction = (AimSniperAction) menu.showMenu(actor, actions, new Display());
		return doAction.execute(actor, map);
	}
		
	/**
	 * This method gets the next action from the last action
	 * @return an action
	 */
	public Action getNextAction() {
		if (doAction != null) {
			return doAction.getNextAction();
		} else {
			return null;
		}
	}
	
	/**
	 *This method returns a string description of the action that would be seen in the menu.
	 */
	@Override
	public String menuDescription(Actor actor) {
		return "Use a sniper and choose a target";
	}

}
