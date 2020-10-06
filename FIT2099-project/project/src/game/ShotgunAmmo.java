package game;

import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * This class represents shotgun ammo.
 * @author Tan Jiajun
 *
 */
public class ShotgunAmmo extends PortableItem{
	private int quantity;
	
	/**
	 * Constructor.
	 * @param quantity: amount of ammo
	 */
	public ShotgunAmmo(int quantity) {
		super("shotgun ammo", '~');
		this.quantity = quantity;
		addCapability(RangedCapability.SHOTGUNAMMO);
	}
	
	/**
	 * This method gets the quantity of the shotgun ammo
	 * @return an int quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * This method adds ammo into the shotgun.
	 * @param amount: an int amount
	 */
	public void add(int amount) {
		this.quantity += amount;
	}
	
	/**
	 * This method uses shotgun ammo to shoot
	 */
	public void use() {
		quantity -= 1;
	}
	
	/**
	 * This method checks if the shotgun ammo is empty or not.
	 * @return a boolean
	 */
	
	public boolean empty() {
		return quantity <= 0;
	}
	
	/**
	 * This method picks up shotgun ammo from the ground
	 * @return a PickUpItemAction
	 */
	public PickUpItemAction getPickUpAction() {
		return new CollectAction(this);
	}
}
