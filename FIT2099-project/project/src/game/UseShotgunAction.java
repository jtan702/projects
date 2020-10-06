package game;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Menu;

/**
 * This class represents using the shotgun
 * @author Tan Jiajun
 *
 */
public class UseShotgunAction extends Action{
	private int[][][] directions = {{{-2,-3},{-1,-3},{0,-3},{1,-3},{2,-3},{-1,-2},{0,-2},{1,-2},{0,-1}},		//up
									{{0,1},{-1,2},{0,2},{1,2},{-2,3},{-1,3},{0,3},{1,3},{2,3}},					//down
									{{3,-2},{2,-1},{3,-1},{1,0},{2,0},{3,0},{2,1},{3,1},{3,2}},					//right
									{{-3,-2},{-3,-1},{-2,-1},{-3,0},{-2,0},{-1,0},{-3,1},{-2,1},{-3,2}},		//left
									{{1,-3},{2,-3},{3,-3},{1,-2},{2,-2},{3,-2},{1,-1},{2,-1},{3,-1}},			//topR
									{{-1,-3},{-2,-3},{-3,-3},{-1,-2},{-2,-2},{-3,-2},{-1,-1},{-2,-1},{-3,-1}}, 	//topL 
									{{1,1},{2,1},{3,1},{1,2},{2,2},{3,2},{1,3},{2,3},{3,3}},					//botR
									{{-1,1},{-2,1},{-3,1},{-1,2},{-2,2},{-3,2},{-1,3},{-2,3},{-3,3}}};			//botL
	
	private String[] dirNames = {"up", "down", "right", "left", "up-right", "up-left", "down-right", "down-left"};
	private boolean hasAmmo = false;
	private Menu menu = new Menu();
	
	/**
	 * This method executes the action of using a shotgun
	 * @param actor: an actor
	 * @param map: a gamemap
	 * @return a string
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		
		for (Item item : actor.getInventory()) {
			if (item instanceof ShotgunAmmo) {
				hasAmmo = true;
			}
		}
		
		if(!hasAmmo) {
			return actor + " has no shotgun ammo!";
		}
		
		Actions actions = new Actions();
		
		for(int i = 0; i < directions.length; i++) {
			actions.add(new ShootShotgunAction(directions[i], dirNames[i]));
		}
		
		return menu.showMenu(actor, actions, new Display()).execute(actor, map);
	}		
	
	/**
	 *This method returns a string description of the action that would be seen in the menu.
	 */
	@Override
	public String menuDescription(Actor actor) {
		return "Choose a direction to shoot shotgun";
	}

}