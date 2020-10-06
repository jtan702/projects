package game;

import java.util.ArrayList;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.NumberRange;

public class ChantingAction extends Action{
	
	/**
	 * Constructor.
	 */
	public ChantingAction() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * This method executes the action.
	 * @param actor: an actor
	 * @param map: a gamemap
	 * @return a string
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		int zombieCount = 0;
		
		// This is to initialise x and y so that it will go through the while loop
		int x = (int) Math.floor(Math.random() * map.getXRange().max());
		int y = (int) Math.floor(Math.random() * map.getYRange().max());
		
		while (true) {
			if (map.at(x, y).containsAnActor() == false) {
				
			map.at(x,  y).addActor(new Zombie("Summoned Zombie", map));	
			x = (int) Math.floor(Math.random() * map.getXRange().max());
			y = (int) Math.floor(Math.random() * map.getYRange().max());
			zombieCount ++;

			}
			if (zombieCount == 5) {
				break;
			}
		}
		
		return "Voodoo Priestess " + actor.toString() + " has summoned 5 Zombies!";
	}
	
	/**
	 *This method returns a string description of the action that would be seen in the menu.
	 */
	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor.toString() + " has summoned 5 Zombies!!!!";
	}

}
