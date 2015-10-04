import java.awt.Color;
import java.util.ArrayList;

public class Tile
{
	public static enum Type
	{
		STONE(Color.gray), WATER(Color.blue);

		public Color color;

		Type(Color c)
		{
			color = c;
		}
	}

	public Type type;
	public double height;

	public Tile(Type t, double h)
	{
		type = t;
		height = h;
	}

	public void tick(int x, int y)
	{
		ArrayList<Tile> nearby = new ArrayList<Tile>();
		Tile tiles[][] = IslandSimulator.tiles;
		if (tiles[x][y].type != Tile.Type.WATER)
		{
			for (int subx = -1; subx < 2; subx++)
			{
				for (int suby = -1; suby < 2; suby++)
				{
					nearby.add(tiles[x + subx][y + suby]);
				}
			}
			double hydration = 0;
			for (int counter = 0; counter < 9; counter++)
			{
				if(nearby.get(counter).type == Tile.Type.WATER) 
				{
					hydration = hydration + 1;
				}
			}
			hydration = hydration/9;
			
		}
	}

	public static void fertilize(Tile[][] tiles)
	{

	}
}
