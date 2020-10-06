package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A weapon that can be crafted from ZombieArm using CraftAction
 * @author Jiajun
 *
 */
public class ZombieClub extends WeaponItem{

	/**
	 * Constructor for a Zombie Club
	 */
	public ZombieClub() {
		super("Zombie Club", '!', 20, "bashes");
	}

}
