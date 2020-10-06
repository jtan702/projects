package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class representing a subtype of Human called Farmers.
 * @author Wong Wai Chuin
 *
 */
public class Farmer extends Human{

	private Behaviour[] behaviours = { 
			new EatBehaviour(),
			new SowBehaviour(),
			new FertiliseBehaviour(),
			new HarvestBehaviour(),
			new WanderBehaviour()
			};
	
	/**
	 * Constructor.
	 * @param name:  Name of a farmer
	 * @param displayChar: the display character of a farmer
	 * @param hitPoints: the health points of a farmer
	 */
	public Farmer(String name, char displayChar, int hitPoints) {
		super(name,displayChar, hitPoints);
	}
	
	/**
	 * This method overrides playturn method in Human class to loop through behaviours of Farmer class.
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null) {
				return action;

				}
			}
		return new DoNothingAction();
		}

}
