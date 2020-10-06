package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.WeaponItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A Zombie.
 * 
 * This Zombie is pretty boring.  It needs to be made more interesting.
 * 
 * @author ram
 *
 */
public class Zombie extends ZombieActor {
	private GameMap map; 
	private int age = 0; 
	private int stagger;
	private ArrayList <Behaviour> behaviours = new ArrayList<>(Arrays.asList(
			new ZombieBehaviour(),
			new PickUpBehaviour(WeaponItem.class),
			new AttackBehaviour(ZombieCapability.ALIVE),
			new HuntBehaviour(Human.class, 10),
			new WanderBehaviour()
			));

	
	/**
	 * Default constructor for a Zombie
	 * @param name the Zombie's display name
	 * @param map the GameMap of the current game
	 */
	public Zombie(String name, GameMap map) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
		this.addCapability(LimbCapability.BOTHARM);
		this.addCapability(LimbCapability.BOTHLEG);
		this.map = map;
	}
	
	/**
	 * Override method to give Zombie a probability of returning a bite attack instead of a punch
	 * Chances of the bite attack increases with the lesser number of arms it has
	 * 
	 */
	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		IntrinsicWeapon out;
		if (Math.random() < 0.5 && this.hasCapability(LimbCapability.BOTHARM)) {
			out = new IntrinsicWeapon(15, "bites");
			
		} else if (Math.random() < 0.75 && this.hasCapability(LimbCapability.ONEARM)) {
			out = new IntrinsicWeapon(15, "bites");
			
		} else if (this.hasCapability(LimbCapability.NOARM)) {
			out = new IntrinsicWeapon(15, "bites");
			
		} else {
			out = new IntrinsicWeapon(10, "punches");
		}
			
		return out;
	}
	
	/**
	 * Override method in Zombie to allow it to lose body parts when getting hurt.
	 */
	@Override
	public void hurt(int points) {
		double randNum1 = Math.random();
		double randNum2 = Math.random();
		super.hurt(points);


		if (randNum1 < 0.25 && !(this.hasCapability(LimbCapability.NOARM) && this.hasCapability(LimbCapability.NOLEG))) {
			
			if ((randNum2 < 0.5 && !this.hasCapability(LimbCapability.NOARM)) || this.hasCapability(LimbCapability.NOLEG)) {
				LoseArm();
			} else if ((randNum2 >= 0.5 && !this.hasCapability(LimbCapability.NOLEG)) || this.hasCapability(LimbCapability.NOARM)) {
				LoseLeg();
			} else {
				return;
			}
		}
	}
	
	/**
	 * A private method that when called, will change the Zombie's capabilities.
	 * and then drop a ZombieArm object somewhere on the map beside the Zombie's location.
	 * When a Zombie loses an arm, it has a 50% chance to drop the weapon its holding
	 * When a Zombie loses both its arms, it drops everything that it is carrying.
	 */
	private void LoseArm() {
		List<Item> items = this.getInventory();
		
		if (this.hasCapability(LimbCapability.BOTHARM)) {
			this.removeCapability(LimbCapability.BOTHARM);
			this.addCapability(LimbCapability.ONEARM);
			
			if (Math.random() < 0.5) {
			for (int i = 0; i < items.size(); i++) {
				Item toDrop = items.get(0);
				List<Exit> exits = new ArrayList<Exit>(map.locationOf(this).getExits());
				Collections.shuffle(exits);
				exits.get(0).getDestination().addItem(toDrop);
				}
			}
			
		} else if (this.hasCapability(LimbCapability.ONEARM)) {
			this.removeCapability(LimbCapability.ONEARM);
			this.addCapability(LimbCapability.NOARM);
			
			for (int i = 0; i < items.size(); i++) {
				Item toDrop = items.get(0);
				List<Exit> exits = new ArrayList<Exit>(map.locationOf(this).getExits());
				Collections.shuffle(exits);
				exits.get(0).getDestination().addItem(toDrop);
				}
			
		} else { 
			//zombie will have noarm
			return;
		}
		
		Item arm = new ZombieArm();
		List<Exit> exits = new ArrayList<Exit>(map.locationOf(this).getExits());
		Collections.shuffle(exits);
		exits.get(0).getDestination().addItem(arm);
	}
	
	/**
	 * A private method that when called, will change the Zombie's capabilities.
	 * and then drop a ZombieLeg object somewhere on the map beside the Zombie's location
	 * When a zombie loses a leg, it movements stagger.
	 * When a zombie loses both its legs, it will note be able to move.
	 */
	private void LoseLeg() {
		if (this.hasCapability(LimbCapability.BOTHLEG)) {
			this.removeCapability(LimbCapability.BOTHLEG);
			this.addCapability(LimbCapability.ONELEG);
			stagger = age;
			
		} else if (this.hasCapability(LimbCapability.ONELEG)) {
			this.removeCapability(LimbCapability.ONELEG);
			this.addCapability(LimbCapability.NOLEG);
			behaviours.remove(behaviours.size()-1);
			behaviours.remove(behaviours.size()-1);
		} else {
			//zombie will have noleg
			return;
		}
		
		Item leg = new ZombieLeg();
		List<Exit> exits = new ArrayList<Exit>(map.locationOf(this).getExits());
		Collections.shuffle(exits);
		exits.get(0).getDestination().addItem(leg);
		
	}

	/**
	 * If a Zombie can attack, it will.  If not, it will chase any human within 10 spaces.  
	 * If no humans are close enough it will wander randomly.
	 * 
	 * When the Zombie has one leg, it will stagger instead of move every 2nd turn.
	 * 
	 * @param actions list of possible Actions
	 * @param lastAction previous Action, if it was a multiturn action
	 * @param map the map where the current Zombie is
	 * @param display the Display where the Zombie's utterances will be displayed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		age += 1;
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null) {
				if ((behaviour instanceof HuntBehaviour || behaviour instanceof WanderBehaviour) && this.hasCapability(LimbCapability.ONELEG)) {
					if((age - stagger)%2 == 0) {
						return new StaggerAction();
					}else {
						return action;
					}
				}
				return action;
			}
		}
		return new DoNothingAction();	
	}

	@Override
	public int getHitPoints() {
		return this.hitPoints;
	}

	@Override
	public int getMaxHealth() {
		return this.maxHitPoints;
	}
}
