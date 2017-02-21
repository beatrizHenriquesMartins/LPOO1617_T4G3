package dkeep.logic;
import java.util.Scanner;
import java.util.Vector;

public class Game {
	private Hero hero = new Hero();
	private Guard guard = new Guard();
	private Ogre ogre = new Ogre();
	private Club club = new Club();
	private Map map;
	private Lever lever;
	private Vector<Exit> exits;
	private Key key;

	// Levels
	public boolean levelOne() {
		boolean nextLevel = true; // permission to play level 2

		loadElementsLevel1(); // generates all elements for level1

		System.out.println("-----LEVEL 1-----");
		map.printMap();

		Scanner s = new Scanner(System.in);
		boolean end = false;

		int i = -1;

		while (end != true) {
			System.out.println("Play with the keys a,w,s,d.");
			char direction = s.next().charAt(0);

			eraseTrailC(hero); // deletes trail when hero moves
			hero.movement(map, direction);
			updateCharacterPosition(hero); // updates hero's position on the map

			// generates guard's fixed trajectory
			i++;
			if (i > 23)
				i = 0;
			direction = guard.fixedTrajectory(i);

			eraseTrailC(guard); // erases guard's trail as it changes position
			guard.movement(map, direction);
			updateCharacterPosition(guard); // updates guard's position on map

			openLever(); // checks to see if lever can be opened and updates

			map.printMap();

			if (catchHero(guard)) { // checks if guard has caught hero
				end = true;
				nextLevel = false; // if hero is caught, you can't move on to
									// level2
			}

			if ((exits.get(0).getX() == hero.getX() && exits.get(0).getY() == hero.getY())
					|| (exits.get(1).getX() == hero.getX() && exits.get(1).getY() == hero.getY()))
			// if hero is on the exit, you win
			{
				System.out.println("YOU WIN");
				end = true;
			}
		}
		return nextLevel;
	}

	public void levelTwo() {

		loadElementsLevel2(); //// generates all elements for level2

		System.out.println("-----LEVEL 2-----");
		map.printMap();

		boolean end = false;

		while (end != true) {
			System.out.println("Play with the keys a,w,s,d.");
			Scanner s = new Scanner(System.in);
			char direction = s.next().charAt(0);

			eraseTrailC(hero);
			hero.movement(map, direction);
			updateCharacterPosition(hero);

			// generates ogre's trajectory randomly
			direction = ogre.randomTrajectory();

			eraseTrailC(ogre); // erases ogre's trail as it changes position
			ogre.movement(map, direction);
			updateCharacterPosition(ogre); // updates ogre's position on map

			// generates club's position randomly
			eraseTrailC(club);
			club.movement(map, ogre.getX(), ogre.getY());
			updateCharacterPosition(club);

			pickKey(); // checks if hero has picked up key and updates
			nearKey(); // checks if ogre is near key and updates

			if (exits.get(0).getX() == hero.getX() && exits.get(0).getY() + 1 == hero.getY() && key.getPickedUp()) {
				// if hero is close to exit and has the key
				// the exit turns to stairs
				exits.get(0).open();
				updateObjectPosition(exits.get(0));
			}

			map.printMap();
			end = catchHero(ogre); // checks if ogre has caught hero

			if (exits.get(0).getX() == hero.getX() && exits.get(0).getY() == hero.getY())
			// if hero is on top of stairs, you win
			{
				System.out.println("YOU WIN");
				end = true;
			}
		}
	}

	// useful functions
	public void openLever() {
		if ((lever.getX() - 1 == hero.getX() && lever.getY() == hero.getY())
				|| (lever.getX() + 1 == hero.getX() && lever.getY() == hero.getY())
				|| (lever.getX() == hero.getX() && lever.getY() - 1 == hero.getY())
				|| (lever.getX() == hero.getX() && lever.getY() + 1 == hero.getY()))
		// if hero is near lever (up,down,right,left)
		{
			// lever opens, exits open and both are updated
			lever.open();
			exits.get(0).open();
			updateObjectPosition(exits.get(0));
			exits.get(1).open();
			updateObjectPosition(exits.get(1));
		}
	}

	public void pickKey() {
		if ((key.getX() - 1 == hero.getX() && key.getY() == hero.getY())
				|| (key.getX() + 1 == hero.getX() && key.getY() == hero.getY())
				|| (key.getX() == hero.getX() && key.getY() - 1 == hero.getY())
				|| (key.getX() == hero.getX() && key.getY() + 1 == hero.getY()))
		// if hero is near key (up,down,right,left)
		{
			// key gets picked up, disappears, hero turns to K and all is
			// updated
			key.pickedUp();
			updateObjectPosition(key);
			hero.hasKey();
			updateCharacterPosition(hero);
		}
	}

	public void nearKey() {
		if (((key.getX() - 1 == ogre.getX() && key.getY() == ogre.getY())
				|| (key.getX() + 1 == ogre.getX() && key.getY() == ogre.getY())
				|| (key.getX() == ogre.getX() && key.getY() - 1 == ogre.getY())
				|| (key.getX() == ogre.getX() && key.getY() + 1 == ogre.getY())) && !key.getPickedUp())
		// if ogre is near key (up,down,left,right)
		{
			// ogre turns to $ and updates
			ogre.setSymbol('$');
			updateCharacterPosition(ogre);
		} else {
			// otherwise, ogre goes back to O and updates
			ogre.setSymbol('O');
			updateCharacterPosition(ogre);
		}

		if (((key.getX() - 1 == club.getX() && key.getY() == club.getY())
				|| (key.getX() + 1 == club.getX() && key.getY() == club.getY())
				|| (key.getX() == club.getX() && key.getY() - 1 == club.getY())
				|| (key.getX() == club.getX() && key.getY() + 1 == club.getY())) && !key.getPickedUp())
		// if club is near key (up,down,left,right)
		{
			// club turns to $ and updates
			club.setSymbol('$');
			updateCharacterPosition(club);
		} else {
			// otherwise, club goes back to * and updates
			club.setSymbol('*');
			updateCharacterPosition(club);
		}
	}

	public boolean catchHero(Character c) {
		if ((c.getX() - 1 == hero.getX() && c.getY() == hero.getY())
				|| (c.getX() + 1 == hero.getX() && c.getY() == hero.getY())
				|| (c.getX() == hero.getX() && c.getY() - 1 == hero.getY())
				|| (c.getX() == hero.getX() && c.getY() + 1 == hero.getY())) {
			System.out.println("***********");
			System.out.println("*GAME OVER*");
			System.out.println("***********");
			System.out.println("You just got caught!");
			return true;
		}

		if (c == ogre) {
			if ((club.getX() - 1 == hero.getX() && club.getY() == hero.getY())
					|| (club.getX() + 1 == hero.getX() && club.getY() == hero.getY())
					|| (club.getX() == hero.getX() && club.getY() - 1 == hero.getY())
					|| (club.getX() == hero.getX() && club.getY() + 1 == hero.getY())) {
				System.out.println("***********");
				System.out.println("*GAME OVER*");
				System.out.println("***********");
				System.out.println("You just got caught!");
				return true;
			}

		}
		return false;
	}

	// update map
	public void eraseTrailC(Character c) {
		map.getMap()[c.getX()][c.getY()] = ' ';
	}

	public void eraseTrailO(Object o) {
		map.getMap()[o.getX()][o.getY()] = ' ';
	}

	public void updateCharacterPosition(Character c) {
		map.insertCharacter(c);
	}

	public void updateObjectPosition(Object o) {
		map.insertObject(o);
	}

	// load elements
	public void loadElementsLevel1() {
		// map
		map = new Map(1);

		// lever
		lever = new Lever(8, 7);
		map.insertObject(lever);

		// exits
		exits = new Vector<Exit>();
		exits.add(new Exit(5, 0));
		exits.add(new Exit(6, 0));
		exits.add(new Exit(1, 4));
		exits.add(new Exit(3, 4));
		exits.add(new Exit(3, 2));
		exits.add(new Exit(8, 2));
		exits.add(new Exit(8, 4));

		for (int i = 0; i < exits.size(); i++) {
			map.insertObject(exits.get(i));
		}

		// guard
		guard.setX(1);
		guard.setY(8);
		map.insertCharacter(guard);

		// hero
		hero.setX(1);
		hero.setY(1);
		map.insertCharacter(hero);
	}

	public void loadElementsLevel2() {
		// map
		map = new Map(2);

		// key
		key = new Key(1, 7);
		map.insertObject(key);

		// exits
		exits = new Vector<Exit>();
		exits.add(new Exit(1, 0));

		for (int i = 0; i < exits.size(); i++) {
			map.insertObject(exits.get(i));
		}

		// ogre
		ogre.setX(1);
		ogre.setY(4);
		map.insertCharacter(ogre);

		// ogre's club
		club.setX(0);
		club.setY(0);
		club.movement(map, ogre.getX(), ogre.getY());
		map.insertCharacter(club);

		// hero
		hero.setX(7);
		hero.setY(1);
		map.insertCharacter(hero);
	}
}