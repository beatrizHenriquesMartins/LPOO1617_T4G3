package dkeep.logic;

import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class Character.
 */
public abstract class Character extends Element{

	/////////////////////////////////////////MOVEMENTS//////////////////////////////////////
	/**
	 * Moves Character up by reducing the attribute x.
	 */	
	public void moveUp() {
		x--;
	}
	/**
	 * Moves Character down by adding to the attribute x.
	 */	
	public void moveDown() {
		x++;
	}
	/**
	 * Moves Character left by reducing the attribute y.
	 */	
	public void moveLeft() {
		y--;
	}
	/**
	 * Moves Character up by adding to the attribute y.
	 */	
	public void moveRight() {
		y++;
	}
	/**
	 * Checks if Character can move in a certain direction.
	 * @param map Map in which Character is moving.
	 * @param direction Direction in which Character is trying to move.
	 * @return True for permission to move.
	 */	
	public boolean wall(Map map, char direction) {
		switch (direction)
		{
			case 'w':
				return wallAux(map,-1,0);
			case 's':
				return wallAux(map,1,0);
			case 'a':
				return wallAux(map,0,-1);
			case 'd':
				return wallAux(map,0,1);
		}
		return true;
	}	
	/**
	 * Checks if Character can move.
	 * @param map Map in which Character is moving.
	 * @param i parameter added to coordinate x.
	 * @param j parameter added to coordinate y.
	 * @return True for permission to move.
	 */	
	public boolean wallAux(Map map, int i, int j)
	{
		int a=x+i, b=y+j;
		if (map.getMap()[a][b] == 'X' || map.getMap()[a][b] == 'k' || map.getMap()[a][b] == 'I'
				|| map.getMap()[a][b] == 'H' || map.getMap()[a][b] == 'A' || map.getMap()[a][b] == 'K' 
				|| a < 0 || (b) < 0 || a > map.getMap().length
				|| b > map.getMap()[a].length)
			return false;
		else return true;
	}
	/**
	 * Moves Character in all directions as long as it's possible.
	 * @param map Map in which Character is moving.
	 * @param direction Direction in which Character is trying to move.
	 */	
	public void movement(Map map, char direction) {

		if (wall(map, direction)) {
			switch (direction) {
			case 'w':
				moveUp();
				break;
			case 's':
				moveDown();
				break;
			case 'a':
				moveLeft();
				break;
			case 'd':
				moveRight();
				break;
			case ' ':
				break;
			}
		}
	}
	/**
	 * Generates random direction.
	 * @return char referring to a direction (a for left, w for up, s for down and d for right).
	 */	
	public char randomTrajectory() {
		char direction = ' ';
		Random n = new Random();
		int value = n.nextInt(4);
		if (value == 0) direction = 'a';
		else if (value == 1) direction = 'w';
		else if (value == 2) direction = 's';
		else if (value == 3) direction = 'd';

		return direction;
	}
}
