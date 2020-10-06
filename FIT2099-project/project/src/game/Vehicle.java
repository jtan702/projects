package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Item;

public class Vehicle extends Item{

	public Vehicle(String name, char displayChar, boolean portable) {
		super(name, displayChar, portable);
	}
	
	public void addAction(Action action) {
		this.allowableActions.add(action);
	}

}