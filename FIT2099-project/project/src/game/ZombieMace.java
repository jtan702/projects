package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A weapon that can be crafted from ZombieLeg using CraftAction
 * @author Jiajun
 *
 */
public class ZombieMace extends WeaponItem{

	/**
	 * Constructor for a Zombie Mace
	 */
	public ZombieMace() {
		super("Zombie Mace", 'I', 25, "smashes");
	}

}
