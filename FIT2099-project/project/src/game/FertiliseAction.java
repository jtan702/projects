package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;

/**
 * This class represents a action of fertilising a crop.
 * @author Wong Wai Chuin
 *
 */
public class FertiliseAction extends Action{
	
	private Crop cropObj;
	
	/**
	 * Constructor.
	 * @param crop: a Crop object
	 */
	public FertiliseAction(Crop crop) {
		this.cropObj = crop;
	}
	
	/**
	 * This method executes the action of fertilising the crop, if the age of the crop is more than or equals to 20, 
	 * then crop is considered ripe.
	 * 
	 * @param actor: an actor object
	 * @param map: GameMap
	 * @return a string from method menuDescription
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		int cropAge = cropObj.getAgeOfCrop();
		cropAge = cropAge + 10;
		
		if (cropAge >= 20) {
			cropObj.addCapability(CropCapability.RIPE);
			cropObj.setDisplayChar('=');
			return actor + " has fertilised the crop and the crop has ripened!!";
		}
			
		return actor + " has fertilised the crop.";
	}

	
	/**
	 * This method returns a string description of the action that would be seen in the menu.
	 * @param actor: actor object
	 * @return a string
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " fertilise the crop";
	}

}
