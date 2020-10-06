package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Menu;

/**
 * This class represents the action of aiming a sniper
 * @author Wong Wai Chuin
 *
 */
public class AimSniperAction extends Action{
	
	private Actor target;
	private Integer concentration;
	private boolean aiming = false;
	private Menu menu = new Menu();
	
	/**
	 * Constructor.
	 * @param target : an actor to perform an action on
	 * @param val: an integer value
	 */
	public AimSniperAction(Actor target, Integer val) {
		this.target = target;
		this.concentration = val;
	}
	
	/**
	 * This method executes the action of aiming the sniper, it also provides a submenu for aiming the sniper again or shooting it.
	 * @param actor: actor to perform action on
	 * @param map: a gamemap
	 * @return a string that states that it has been executed
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		
		if(!actor.hasCapability(RangedCapability.FOCUS)) {
			return actor + " was attacked and has lost concentration!";
		}
		
		String info = "concentration: " + concentration;
		Actions actions = new Actions();		
		if (concentration >= 2) {
			info += " ," + actor + " has full concentration";
		}
		System.out.println(info);
				
		actions.add(new ShootSniperAction(target, concentration));
		actions.add(new AimSniperAction(target, concentration + 1));
		
		
		Action doAction = menu.showMenu(actor, actions, new Display());
		if (doAction instanceof ShootSniperAction) {
			return doAction.execute(actor, map);
			
		} else if (doAction instanceof AimSniperAction){
			aiming = true;
			return "Player is Aiming";
		}
		else {
			return null;
		}
		
	}
	
	/**
	 * This method gets the next action.
	 * @return an action 
	 */
	public Action getNextAction() {

		if (aiming == true) {
			return new AimSniperAction(target, concentration + 1);
		}
		else {
			return null;
		} 
	}
	
	/**
	 *This method returns a string description of the action that would be seen in the menu.
	 *@param actor: an actor
	 *@return a string 
	 */
	
	@Override
	public String menuDescription(Actor actor) {
		return "Aim " + target;
	}

}
