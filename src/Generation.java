import java.util.ArrayList;
import java.util.HashMap;

public class Generation
{

	public static void initialGen(Tile[][] tiles)
	{


	}

	
	static final double SCRUB_CHANCE = 0.333;
	static final int END_SCRUB = 3;
	public static void cleanUp(Tile[][] tiles, int iter)
	{
		for(int n = 0; n < iter; n++)
			if(Math.random() < SCRUB_CHANCE)
				scrub(tiles);
			else
				smooth(tiles);
		
		for(int n = 0; n < END_SCRUB; n++)
			scrub(tiles);
	}
	
	public static void smooth (Tile[][] tiles)

	{
		// Checks to see if the surronding blocks are water
		for (int y = 1; y < (tiles[y].length - 1); y++)
		{
			for (int x = 1; x < (tiles[y].length - 1); x++)
			{
				//The next segment checks the tiles around the stone tile
				//and then randomly changes it based on amount of tiles around it
				ArrayList<Tile.Type> nearby = new ArrayList<Tile.Type>();
				for(int suby = -1;suby<2;suby++)
				{
					for(int subx = -1; subx < 2;subx++)
					{
						nearby.add(tiles[x+subx][y+suby].type);
					}
				}
				
				tiles[x][y].type = nearby.get((int)(Math.random() * nearby.size()));
			}
		}
	}
	
	public static void scrub(Tile[][] tiles)
	{
		for(int x = 1; x < tiles.length-1; x++)
			for(int y = 1; y < tiles[x].length-1; y++)
			{
				HashMap<Tile.Type, Integer> map = new HashMap<Tile.Type, Integer>();
				
				for(int rx = -1; rx < 2; rx++)
					for(int ry = -1; ry < 2; ry++)
						map.put(tiles[x+rx][y+ry].type, map.get(tiles[x+rx][y+ry].type) == null ? 1 : map.get(tiles[x+rx][y+ry].type) + 1);
				
				Tile.Type top = (Tile.Type) map.keySet().toArray()[0];
				for(int n = 0; n < map.size(); n++)
					if(map.get(map.keySet().toArray()[n]) > map.get(top))
						top = (Tile.Type) map.keySet().toArray()[n];
			}
	}
}
