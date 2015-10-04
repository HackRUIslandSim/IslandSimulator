import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import javax.imageio.ImageIO;

public class Creature {
	public boolean alive = true;
	public int x, y, hunger, movSpeed = 1, size = 1;
	public final int TicksPerMinute = 600, maxHunger;	
	public BufferedImage img = null;
	//MaxMins = maximum time (in minutes) the creature can stay alive without eating
	public Creature(int initX, int initY, int maxMins)
	{
		x = initX;
		y = initY;
		maxHunger = TicksPerMinute * maxMins;
		hunger = maxHunger;
		switch (size)
		{
			case 1:
				try{
					img = ImageIO.read(new File("res/SmallAnimal.png"));
				} catch (IOException e) {}
			case 2:
				try{
					img = ImageIO.read(new File("resMedAnimal.png"));
				} catch (IOException e) {}
			case 3:
				try{
					img = ImageIO.read(new File("res/LargeAnimal.png"));
				} catch (IOException e) {}
		}
		
		
		
		
	}
	
	public void tick()
	{
		hunger--; 
		Random generator = new Random();
		float chance = generator.nextFloat();
		
		//If chance is greater than the creatures hunger:
		//at 70% full, creature has a 30% chance not to move out of hunger
		//Just trust that it makes sense somehow
		if(chance >= hunger / maxHunger)
			move();
		
		//if(IslandSimulator.tiles[x][y].type = Tile.Type.APPLEFOREST)
		//	hunger += TicksPerMinute;
		//If it can't feed its self every minute, its deaaad
		
		if(hunger == 0)
			alive = false;
		if(hunger > maxHunger * 0.80)
			if(generator.nextFloat() < 0.25 && size != 3)
				size++;
	}
	
	public void move()
	{
		boolean applesNear = false;
		int appleX = 0, appleY = 0;
		for(int i = 3; i > -4; i--)
			for(int j = 3; j > -4; j--)
			{
				if(IslandSimulator.tiles[x + i][y + j].type == Tile.Type.FOREST)
					applesNear = true;
				appleX = x + i;
				appleY = y + j;
			}
		if(applesNear)
			moveTowards(appleX, appleY);
		else
			moveRand();
			
	}
	public void moveTowards(int tileX, int tileY)
	{
		if(y < tileY)
			if(IslandSimulator.tiles[x][y + 1].type != Tile.Type.WATER)
				y++;
		if(y > tileY)
			if(IslandSimulator.tiles[x][y - 1].type != Tile.Type.WATER)
				y--;
		if(x < tileX)
			if(IslandSimulator.tiles[x + 1][y].type != Tile.Type.WATER)
				x++;
		if(x > tileX)
			if(IslandSimulator.tiles[x - 1][y].type != Tile.Type.WATER)
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
		
		if(xChange && IslandSimulator.tiles[x + change][y].type != Tile.Type.WATER)
			x += change;
		else if(IslandSimulator.tiles[x][y + change].type != Tile.Type.WATER)
			y += change;
	}	
}