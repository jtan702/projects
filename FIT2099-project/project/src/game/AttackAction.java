package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target) {
		this.target = target;
	}

	/**
	 * Executes the AttackAction. A Zombie will heal if it lands a successful bite attack.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();

		if (rand.nextBoolean()) {
			return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		
		if(actor instanceof Zombie && weapon.verb() == "bites") {
			actor.heal(5);
			result += System.lineSeparator() + actor + " heals 10 HP.";
		}
		
		
		target.hurt(damage);
		if (!target.isConscious()) {
			
			if (target instanceof MamboMarie) {
				map.removeActor(target);
				result += System.lineSeparator() + "Voodoo Priestess " + target + " is killed.";
//				return "Voodoo Priestess " + target.toString() + "has been killed.";
			}
			else {
			
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

		return result;
	}

	/**
	 *This method returns a string description of the action that would be seen in the menu.
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target;
	}
}
