package game;

import java.util.ArrayList;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.World;

/**
 * This class represents the same World map that is the same as its parent class, however with added functionality of having bosses
 * in the game and options to end the game.
 * @author Wong Wai Chuin
 *
 */
public class NewWorld extends World{
	
	/**
	 * Constructor
	 * @param display: a display 
	 */
	public NewWorld(Display display) {
		super(display);
	}


	/**
	 * Run the game.
	 *
	 * On each iteration the gameloop does the following: - displays the player's
	 * map - processes the actions of every Actor in the game, regardless of map
	 *
	 * We could either only process the actors on the current map, which would make
	 * time stop on the other maps, or we could process all the actors. We chose to
	 * process all the actors.
	 *
	 * @throws IllegalStateException if the player doesn't exist
	 */
	public void run() {
		int marieTurn = 0;
		boolean bossKilled = false;
		
		if (player == null)
			throw new IllegalStateException();

		// initialize the last action map to nothing actions;
		for (Actor actor : actorLocations) {
			lastActionMap.put(actor, new DoNothingAction());
		}

		// This loop is basically the whole game
		while (stillRunningLoseEnding() && stillRunningWinEnding()) {	
			int trackActorCount = 0;
			int trackActorCount2 = 0;
			
			GameMap playersMap = actorLocations.locationOf(player).map();
			playersMap.draw(display);
			
			// Process all the actors.
			for (Actor actor : actorLocations) {
				if (stillRunning()) {
					processActorTurn(actor);
					
				}
			}	
			
			// This is to get the size of actorLocations so that we can use it later. 
			for (Actor actor : actorLocations) {
				trackActorCount ++;

			}
			
			
			// This loop then checks through actorLocations again, If MamboMarie is not on the map, then she would have a 5% chance of appearing at the edges of the map.
			// This for loop still runs until the boss is killed.
			for (Actor actor : actorLocations) {
				trackActorCount2 ++;
				
				// if boss is on the map already, we break out of this loop
				if (bossKilled == false && actor.hasCapability(ZombieCapability.UNDEADBOSS) == true) {
					if (bossKilled == false) {
						
						if (marieTurn % 30 == 0 && marieTurn != 0) {
							playersMap.removeActor(actor);
							int resetVal = 0;
							marieTurn = resetVal;
						}
						
						else if (actor.getHitPoints() <= 0) {
							bossKilled = true;
						}
						else {
						marieTurn ++;}
					}
					
					break;
				}

				if (actor.hasCapability(ZombieCapability.UNDEADBOSS) == false && trackActorCount2 == trackActorCount) {
					
					MamboMarie newBoss = new MamboMarie("Marie");
					double randNum = Math.random();
					
					if (randNum <= 0.05) {
						int[][] coordinates = new int[][] {{playersMap.getXRange().max(), playersMap.getYRange().max()}, {playersMap.getXRange().max(), 0}, {0, playersMap.getYRange().max()}, {0,0}};
						ArrayList<Location> locArray = new ArrayList<Location>();
						
						for (int[] j : coordinates) {
							Location newLoc = new Location(playersMap, j[0], j[1]);
							locArray.add(newLoc);
						}
						
						Random rnd = new Random();
						Location chosenLocation = locArray.get(rnd.nextInt((locArray.size())));
						playersMap.at(chosenLocation.x(), chosenLocation.y()).addActor(newBoss);
						marieTurn ++;
						
						System.out.println("Voodoo Priestess " + actor.toString() + " has appeared!!");

					}
					if (bossKilled == false) {
						
						if (marieTurn % 30 == 0 && marieTurn != 0) {
							playersMap.removeActor(actor);
							int resetVal = 0;
							marieTurn = resetVal;
							System.out.println("Voodoo Priestess " + actor.toString() + " has vanished!!");
						}
						
						else if (newBoss.getHitPoints() <= 0) {bossKilled = true;}		
					}
					break;
				}		
			} 
			
						
			// Tick over all the maps. For the map stuff.
			for (GameMap gameMap : gameMaps) {
				gameMap.tick();
			}
			
		}
		
		display.println(endGameMessage());

	}
	
	/**
	 * Gives an Actor its turn.
	 *
	 * The Actions an Actor can take include:
	 * <ul>
	 * <li>those conferred by items it is carrying</li>
	 * <li>movement actions for the current location and terrain</li>
	 * <li>actions that can be done to Actors in adjacent squares</li>
	 * <li>actions that can be done using items in the current location</li>
	 * <li>skipping a turn</li>
	 * </ul>
	 *
	 * @param actor the Actor whose turn it is.
	 */
	protected void processActorTurn(Actor actor) {
		Location here = actorLocations.locationOf(actor);
		GameMap map = here.map();

		Actions actions = new Actions();
		for (Item item : actor.getInventory()) {
			actions.add(item.getAllowableActions());
			// Game rule. If you're carrying it, you can drop it.
			actions.add(item.getDropAction());
		}

		for (Exit exit : here.getExits()) {
			Location destination = exit.getDestination();

			// Game rule. You don't get to interact with the ground if someone is standing
			// on it.
			if (actorLocations.isAnActorAt(destination)) {
				actions.add(actorLocations.getActorAt(destination).getAllowableActions(actor, exit.getName(), map));
				
				// **ADDED LINES** inserted these 2 lines of code so that the player is able to attack the boss
				if (actorLocations.getActorAt(destination) instanceof MamboMarie) {
					actions.add(new AttackAction(actorLocations.getActorAt(destination)));
				}

			} else {
				actions.add(destination.getGround().allowableActions(actor, destination, exit.getName()));
			}
			actions.add(destination.getMoveAction(actor, exit.getName(), exit.getHotKey()));
		}

		for (Item item : here.getItems()) {
			actions.add(item.getAllowableActions());
			// Game rule. If it's on the ground you can pick it up.
			actions.add(item.getPickUpAction());
		}
		actions.add(new DoNothingAction());

		Action action = actor.playTurn(actions, lastActionMap.get(actor), map, display);
		lastActionMap.put(actor, action);
		
		String result = action.execute(actor, map);
//		System.out.println("result: " + result);
		display.println(result);

	}
	
	
	/**
	 * Returns true if the player and the humans are still on the map.
	 * If either all the humans or the player dies, then the player loses.
	 * 
	 * @return true if the player is still on the map.
	 */
	protected boolean stillRunningLoseEnding() {
		
		for (Actor actor : actorLocations) {
			if (actorLocations.contains(player) == true && actor.hasCapability(ZombieCapability.ALIVE) == true ) {
				if (actor != player) {
					return true;
				}
			}

		}
		
		return false;
		
			
	}
	
	/**
	 * Returns true if the game is still running where zombies and the boss is still on the map.
	 * If both are not on the map, it means that the player wins.
	 *
	 * @return true if the player is still on the map.
	 */
	protected boolean stillRunningWinEnding() {
		for (Actor actor : actorLocations) {
			if (actor.hasCapability(ZombieCapability.UNDEADBOSS) == true || (actor.hasCapability(ZombieCapability.UNDEAD) == true)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Return a string that can be displayed when the game ends, depending on whether the player wins or loses.
	 *
	 * @return the string of whether the player wins or not depending on the situation
	 */
	protected String endGameMessage() {
		
		if (stillRunningWinEnding() == false) {
			return "You got the 'Player Wins' Ending ! This is because you managed to defeat the boss and clear the compound of Zombies. Congrats!! ";
		}
		else if (stillRunningLoseEnding() == false) {
			return "You got the 'Player Loses' Ending. This is because either you died or all the humans in the compound were killed. Game Over. ";
		}
		else {
			return "";

		}
	}
}
