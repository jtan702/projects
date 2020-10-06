package game;

import java.util.List;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * This class represents food that extends from portableItem class
 * @author Wong Wai Chuin
 *
 */
public class Food extends PortableItem{
	
	private int hpRecovered = 0;
	
	/**
	 * Constructor.
	 * @param healthPoints: the health points that a food can recover.
	 */
	public Food(int healthPoints) {
		super("Food", 'e');
		this.hpRecovered = healthPoints;
		this.addCapability(EatCapability.EATABLE);
	}
	
	/**
	 * This method gets the integer of health points that a food can recover.
	 * @return the integer value of hpRecovered variable
	 */
	public int getHealthPointsRecovered() {
		return hpRecovered;
	}
	
	

}
