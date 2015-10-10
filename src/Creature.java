import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import javax.imageio.ImageIO;

public class Creature {
	public boolean alive = true;
	public int x, y, hunger, movSpeed = 8, size = 1, time = 0;
	public final int TicksPerMinute = 600, maxHunger;	
	public BufferedImage img = null;
	
	public Creature(int initX, int initY)
	{
		x = initX;
		y = initY;
		maxHunger = TicksPerMinute * 10; //currently lives for 10 minutes without aid
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
		Random generator = new Random();
		float chance = generator.nextFloat();
		if(time == 0)
		{
			time = 8;
			if(chance >= hunger / maxHunger)
				move();
		}
		hunger--; 
		
		//If chance is greater than the creatures hunger:
		//at 70% full, creature has a 30% chance not to move out of hunger
		//Just trust that it makes sense somehow
		
		if(IslandSimulator.tiles[x / 8][y / 8].type == Tile.Type.LUSHFOREST)
			hunger += TicksPerMinute;
		//If it can't feed its self every minute, its dead
		
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
				if((x/8 + i) < 64 && (y/8 + j) < 64 && (x/8 + i) > 0 && (y/8 + j) > 0 && IslandSimulator.tiles[x/8 + i][y/8 + j].type == Tile.Type.LUSHFOREST)
					applesNear = true;
				if((x/8 + i) < 64 && (y/8 + j) < 64 && (x/8 + i) > 0 && (y/8 + j) > 0 && IslandSimulator.tiles[x/8+ i][y/8 + j].type == Tile.Type.FOREST);
					applesNear = true;
					
				appleX = x + i;
				appleY = y + j;
			}
		if(hunger <= maxHunger * 0.5 && applesNear)
			moveTowards(appleX * 8, appleY * 8);
		else
			moveRand();
			
	}
	public void moveTowards(int tileX, int tileY)
	{
		if(y < tileY)
			if(IslandSimulator.tiles[x/8][y/8 + 1].type != Tile.Type.WATER)
				y+=8;
		if(y > tileY)
			if(IslandSimulator.tiles[x/8][y/8 - 1].type != Tile.Type.WATER)
				y-=8;
		if(x < tileX)
			if(IslandSimulator.tiles[x/8 + 1][y/8].type != Tile.Type.WATER)
				x+=8;
		if(x > tileX)
			if(IslandSimulator.tiles[x/8 - 1][y/8].type != Tile.Type.WATER)
				x-=8;
	}
	public void moveRand()
	{
		System.out.println("rand");
		Random generator = new Random();
		
		boolean xChange = false;
		if(generator.nextDouble() > 0.5)
			xChange = true;
		
		int change;
		if(generator.nextDouble() > 0.5)
			change = 1;
		else
			change = -1;
		
		if(xChange && IslandSimulator.tiles[x/8 + change][y/8].type != Tile.Type.WATER)
			x += change*8;
		else if(IslandSimulator.tiles[x/8][y/8 + change].type != Tile.Type.WATER)
			y += change*8;
	}	
}