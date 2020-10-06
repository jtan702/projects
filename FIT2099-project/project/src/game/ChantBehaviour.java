package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;


/**
 * This class represents the behaviour of chanting to summon a spell.
 * @author Wong Wai Chuin
 *
 */
public class ChantBehaviour implements Behaviour{
	private int trackCount = 0;
	
	/**
	 * Constructor.
	 */
	public ChantBehaviour() {
	}
	
	/**
	 * This method gets an action if the conditions are true.
	 * @param actor: an actor
	 * @param map: a gamemap
	 * @return an action
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		
		if (trackCount % 10 == 0 && trackCount != 0) {
			trackCount += 1;
			return new ChantingAction();
		}
		else {
			trackCount += 1;
			return null;
		}

	}

}
