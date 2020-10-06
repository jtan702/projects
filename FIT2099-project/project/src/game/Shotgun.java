package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * This class represents the shotgun class.
 * @author Tan Jiajun
 *
 */
public class Shotgun extends WeaponItem{
	
	/**
	 * Constructor.
	 */
	public Shotgun() {
		super("Shotgun", 'r', 10, "blasts");
		this.addCapability(RangedCapability.SHOTGUN);
	}

}
