package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * @author Jiajun
 * Class that represents a Zombie's arm
 * Actors can wield it as a simple club
 */
public class ZombieLeg extends WeaponItem {

	/**
	 * Constructor for a Zombie Leg
	 */
	public ZombieLeg() {
		super("Zombie Leg",'l', 15, "bashes");
		this.addCapability(CraftableCapability.CRAFTABLE);
	}
}
