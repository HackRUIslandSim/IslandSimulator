import java.awt.Color;
import java.util.ArrayList;

public class Tile
{
	public static enum Type
	{
		STONE(Color.gray), WATER(Color.blue), MOSS(Color.green.darker());

		public Color color;

		Type(Color c)
		{
			color = c;
		}
	}

	public Type type;
	public double hydration;
	public double height;

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
	}

	public void tick(int x, int y)
	{
		ArrayList<Tile> nearby = new ArrayList<Tile>();
		Tile[][] tiles = IslandSimulator.tiles;
		if (x != 0 && y != 0 && x != (tiles[x].length - 1) && y != (tiles[y].length - 1))
		{
			for (int subx = -1; subx < 2; subx++)
			{
				for (int suby = -1; suby < 2; suby++)
				{
					if (suby != 0 && subx != 0)
					{
						nearby.add(tiles[x + subx][y + suby]);
						tiles[x + subx][y + suby].hydration = tiles[x + subx][y + suby].hydration
								+ (tiles[x][y].hydration / 16);
					}
				}
			}
			tiles[x][y].hydration = tiles[x][y].hydration / 1.28;
			System.out.println(tiles[x][y].hydration);
			if (tiles[x][y].type == Tile.Type.STONE && tiles[x][y].hydration >= (0.33) && Math.random() * 10 >= 9.99 && tiles[x][y].height < 
					(IslandSimulator.MOUNTAIN_HEIGHT-0.5) )
			{
				tiles[x][y].type = Tile.Type.MOSS; 
			}
		}
	}

	public static void fertilize(Tile[][] tiles)
	{

	}

	public Tile clone()
	{
		Tile t = new Tile(type, height);
		t.hydration = hydration;
		return t;
	}
}
