package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * This class represents Crop that extends from ground.
 * @author Wong Wai Chuin
 *
 */
public class Crop extends Ground{
	
	private int age = 0;
	
	/**
	 * Constructor.
	 * @param crop: a crop's capability being ripe or not
	 */
	public Crop() {
		super('-');
	}

	/**
	 * This method tracks the time passed for a crop.
	 * @param location: location of the crop
	 */
	public void tick(Location location) {
		super.tick(location);
		
		if (age == 20) {
			displayChar = '=';
			addCapability(CropCapability.RIPE);
		}
		else {
			age ++;	
		}	
	}

	
	/**
	 * This method gets the age of a crop.
	 * @return the age of the crop
	 */
	public int getAgeOfCrop() {
		return age;
	}
//	
	/**
	 * This method sets/changes the display character of the crop, it overrides the method created in GroundInterface.
	 * @param displayChar: display character of crop
	 */
	public void setDisplayChar(char displayChar) {
		this.displayChar = displayChar;
	}

}
