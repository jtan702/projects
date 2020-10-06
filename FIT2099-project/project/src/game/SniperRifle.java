package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * This class represents a sniper rifle
 * @author Tan Jiajun
 *
 */
public class SniperRifle extends WeaponItem{
	
	/**
	 * Constructor
	 */
	public SniperRifle() {
		super("Sniper", 's', 10, "pierces");
		this.addCapability(RangedCapability.SNIPER);
	}

}
