import java.util.Random;

public class Creature {
	public boolean alive = true;
	private int x, y, hunger, movSpeed = 1;
	private final int TicksPerMinute = 3600, maxHunger;	
	//MaxMins = maximum time (in minutes) the creature can stay alive without eating
	public Creature(int initX, int initY, int maxMins)
	{
		x = initX;
		y = initY;
		maxHunger = TicksPerMinute * maxMins;
		hunger = maxHunger;
	}
	
	public void tick()
	{
		hunger--; 
		Random generator = new Random();
		float chance = generator.nextFloat();
		
		//If chance is greater than the creatures hunger:
		//at 70% full, creature has a 30% chance not to move out of hunger
		//Just trust that it makes sense somehow
		if(chance >= hunger / maxHunger )
			move();
		
		if(hunger == 0)
			alive = false;
	}
	
	public void move()
	{
		for(int i = 3; i > -3; i--)
			for(int j = 3; j > -3; j--)
			{
				//if(IslandSimulator.tiles[x + i][y + j].type == Tile.Type.APPLEFOREST || FOREST)
				//	moveTowards(x + i, y + j);
				//else
				//  moveRand();
			}
	}
	public void moveTowards(int tileX, int tileY)
	{
		if(y < tileY)
			y++;
		if(y > tileY)
			y--;
		if(x < tileX)
			x++;
		if(x > tileX)
			x--;
	}
	public void moveRand()
	{
		Random generator = new Random();
		
		boolean xChange = false;
		if(generator.nextDouble() > 0.5)
			xChange = true;
		
		int change;
		if(generator.nextDouble() > 0.5)
			change = 1;
		else
			change = -1;
		
		if(xChange)
			x += change;
		else
			y += change;
	}	
}