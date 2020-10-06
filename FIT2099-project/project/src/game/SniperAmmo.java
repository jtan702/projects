package game;

import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * This class represents Sniper ammo
 * @author Tan Jiajun
 *
 */
public class SniperAmmo extends PortableItem{
	private int quantity;
	
	/**
	 * Constructor.
	 * @param quantity: int quantity
	 */
	public SniperAmmo(Integer quantity) {
		super("sniper ammo", '^');
		this.quantity = quantity;
		addCapability(RangedCapability.SNIPERAMMO);
	}
	
	/**
	 * This method gets quantity of sniper ammo
	 * @return int quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * This method adds sniper ammo to current quantity
	 * @param amount: an int amount
	 */
	public void add(int amount) {
		this.quantity += amount;
	}
	
	/**
	 * This method uses up sniper ammo when the sniper shoots a shot.
	 */
	public void use() {
		quantity -= 1;
	}
	
	/**
	 * This method checks if the sniper has ammo or not
	 * @return a boolean
	 */
	public boolean empty() {
		return quantity <= 0;
	}
	
	/**
	 * This method enables us to pick up sniper ammo 
	 * @return a PickUpItemAction
	 */
	public PickUpItemAction getPickUpAction() {
		return new CollectAction(this);
	}

}
