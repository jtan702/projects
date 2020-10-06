package game;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Menu;

/**
 * Class representing the Player.
 */
public class Player extends Human {

	private Menu menu = new Menu();
	private GameMap mapObj;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints, GameMap map) {
		super(name, displayChar, hitPoints);
		this.mapObj = map;
	}
	
	/**
	 * Method adds additional actions to the player's action, based on the items 
	 * the player has in his/her inventory.
	 * @param actions current list of actions that the player can make
	 */
	private void getAdditionalActions(Actions actions) {
		HarvestBehaviour harvest = new HarvestBehaviour();
		if (harvest.getAction(this, mapObj) != null) {
			actions.add(harvest.getAction(this, mapObj));
		}
		
		for (Item item : this.getInventory()) {
			if (item.hasCapability(CraftableCapability.CRAFTABLE)) {
				actions.add(new CraftAction(item));
			}
			if (item.hasCapability(EatCapability.EATABLE) && this.getHitPoints() < this.maxHitPoints) {
				actions.add(new EatAction((Food)item));
			}
			if (item.hasCapability(RangedCapability.SHOTGUN)) {
				actions.add(new UseShotgunAction());
			}
			
			if (item.hasCapability(RangedCapability.SNIPER)) {
				actions.add(new UseSniperAction());
			}
			
		}
	}
	
	public void hurt(int points) {
		removeCapability(RangedCapability.FOCUS);
		hitPoints -= points;
	}

	
	/**
	 * Override playturn to call getAdditionalActions so that players can craft and eat.
	 *
	 */
	@Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // Handle multi-turn Actions
        //action that takes more than one turn
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();
        
        //return menu of actions
        //can add craftAction options to actions here
        getAdditionalActions(actions);
        
        // add a new option to quit the game
        actions.add(new QuitGameAction());
        

        return menu.showMenu(this, actions, display);
    }
	
}
