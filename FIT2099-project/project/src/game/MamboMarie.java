package game;

import java.util.ArrayList;
import java.util.Arrays;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.WeaponItem;

/**
 * this class represents the MamboMarie boss that appears in the game at random locations.
 * @author Wong Wai Chuin
 *
 */
public class MamboMarie extends ZombieActor{
	
	private ArrayList <Behaviour> behaviours = new ArrayList<>(Arrays.asList(
			new ChantBehaviour(),
			new AttackBehaviour(ZombieCapability.ALIVE),
			new WanderBehaviour()
			));
	
	/**
	 * Constructor
	 * @param name: name of the boss
	 */
	public MamboMarie(String name) {
		super(name, 'M', 5, ZombieCapability.UNDEADBOSS);

	}
	/**
	 * This method gets the hit points for MamboMarie
	 * @return int of current hitpoints
	 */
	@Override
	public int getHitPoints() {
		// TODO Auto-generated method stub
		return this.hitPoints;
	}
	
	/**
	 * This method gets the max health for MamboMarie
	 * @return int of max hitpoints
	 */
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return this.maxHitPoints;
	}
	
	/**
	 * This method overrides the playturn method in ZombieActor that lets a player/npc do actions.
	 * @param actions : a set of actions
	 * @param lastAction: the last action being run
	 * @param map: the GameMap
	 * @param display: displaying the menu options
	 * @return an action to be executed
	 */

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null)
				return action;
		}
		return new DoNothingAction();	
	}

}
