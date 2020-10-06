package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * @author Jiajun
 * Class that represents a Zombie's arm
 * Actors can wield it as a simple club
 *
 */
public class ZombieArm extends WeaponItem {

	/**
	 * Constructor for a Zombie Arm
	 */
	public ZombieArm() {
		super("Zombie Arm",'1', 15, "bashes");
		this.addCapability(CraftableCapability.CRAFTABLE);
	}

}
