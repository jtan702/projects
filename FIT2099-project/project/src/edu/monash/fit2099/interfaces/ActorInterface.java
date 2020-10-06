package edu.monash.fit2099.interfaces;

/**
 * This interface provides the ability to add methods to Actor, without modifying code in the engine,
 * or downcasting references in the game.   
 */

public interface ActorInterface {
	
	/**
	 * This method was made in order to get the hit points remaining on an actor.
	 * @return an integer value of an actor's health remaining
	 */
	public int getHitPoints();
	public int getMaxHealth();
}
