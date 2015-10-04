import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tile
{
	public static enum Type
	{
		STONE(Color.gray), WATER(Color.blue), MOSS(Color.green.darker()), SOIL(new Color(0x8b4513)), GRASS(Color.GREEN.brighter()),
		FOREST("res/Forest.png");

		public BufferedImage img;

		Type(Color c)
		{
			img = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
			Graphics g = img.getGraphics();
			g.setColor(c);
			g.fillRect(0, 0, 8, 8);
		}
		
		Type(String path)
		{
			try
			{
				img = ImageIO.read(new File(path));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public Type type;
	public double hydration, height, fertilization;

	public Tile(Type t, double h)
	{
		type = t;
		height = h;
		switch (type)
		{
			case STONE:
				hydration = 0;
				break;
			case WATER:
				hydration = 1;
				break;
			default:
				break;
		}
		fertilization = 0;
	}

	public void tick(int x, int y)
	{
		Tile[][] tiles = IslandSimulator.tiles;
			
			if(type == Type.WATER)
			{
				hydration = 1;
				if (x != 0 && y != 0 && x != (tiles[x].length - 1) && y != (tiles[y].length - 1))
				{
					for (int subx = -1; subx < 2; subx++)
					{
						for (int suby = -1; suby < 2; suby++)
						{
							if (suby != 0 && subx != 0)
							{
								tiles[x + subx][y + suby].hydration += (tiles[x][y].hydration / 8);
							}
						}
					}
				}
			}
			else
			{
				if (x != 0 && y != 0 && x != (tiles[x].length - 1) && y != (tiles[y].length - 1))
				{
					for (int subx = -1; subx < 2; subx++)
					{                                                                               
						for (int suby = -1; suby < 2; suby++)
						{
							if (suby != 0 && subx != 0)
							{
								tiles[x + subx][y + suby].hydration += (tiles[x][y].hydration / 16);
							}
						}
					}
				}
				
					
				tiles[x][y].hydration /= 2;
				tiles[x][y].hydration /= 1.05;
				if (tiles[x][y].type == Tile.Type.STONE && hydration >= (0.05) && Math.random() >= 0.9992 &&
						tiles[x][y].height < (IslandSimulator.MOUNTAIN_HEIGHT-0.5) )
				{
					tiles[x][y].type = Tile.Type.MOSS; 
				}
			}
		
		if(type == Type.MOSS && Math.random() > 0.9999)
		{
			type = Type.STONE;
			fertilization += Math.random() * 0.3 + 0.1;
		}
		
		if(type == Type.STONE && fertilization > 0.5 && Math.random() > 0.99)
		{
			type = Type.SOIL;
		}
		if(type == Type.SOIL && hydration > 0.05 && Math.random() < 0.00001)
		{
			tiles[x][y].type = Tile.Type.GRASS;
		}
	}

	public Tile clone()
	{
		Tile t = new Tile(type, height);
		t.hydration = hydration;
		return t;
	}
}
