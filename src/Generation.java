import java.util.ArrayList;
import java.util.HashMap;

public class Generation
{

	public static void initialGen(Tile[][] tiles)
	{
		for(int x = 0; x < tiles.length; x++)
			for(int y = 0; y < tiles.length; y++)
				tiles[x][y] = new Tile(Tile.Type.WATER, 0);
		
		for(int x = 1; x < tiles.length-1; x++)
			for(int y = 1; y < tiles.length-1; y++)
				if(Math.sqrt(Math.pow(IslandSimulator.SIZE/2-x, 2) + Math.pow(IslandSimulator.SIZE/2-y, 2)) / 
						Math.sqrt(Math.pow(IslandSimulator.SIZE/2-1, 2) + Math.pow(IslandSimulator.SIZE/2-1, 2)) <= Math.random() - 0.02)
					tiles[x][y] = new Tile(Tile.Type.STONE, 1);
	}

	
	static final double SCRUB_CHANCE = 0.1;
	static final int END_SCRUB = 4;
	public static void cleanUp(Tile[][] tiles, int iter)
	{
		for(int n = 0; n < iter; n++)
			if(Math.random() < SCRUB_CHANCE)
				smooth(tiles);
			else
				noise(tiles);
		
		for(int n = 0; n < END_SCRUB; n++)
			smooth(tiles);
		
		deAlias(tiles);
	}
	

	public static void noise(Tile[][] tiles)
	{
		// Checks to see if the surronding blocks are water
		for (int y = 1; y < (tiles[y].length - 1); y++)
		{
			for (int x = 1; x < (tiles[y].length - 1); x++)
			{
				//The next segment checks the tiles around the stone tile
				//and then randomly changes it based on amount of tiles around it
				ArrayList<Tile> nearby = new ArrayList<Tile>();
				for(int suby = -1;suby<2;suby++)
				{
					for(int subx = -1; subx < 2;subx++)
					{
						nearby.add(tiles[x+subx][y+suby]);
					}
				}
				
				if(Math.random() > 0.05)
					tiles[x][y] = nearby.get((int)(Math.random() * nearby.size()));
				else
					if(Math.sqrt(Math.pow(IslandSimulator.SIZE/2-x, 2) + Math.pow(IslandSimulator.SIZE/2-y, 2)) / 
							Math.sqrt(Math.pow(IslandSimulator.SIZE/2-1, 2) + Math.pow(IslandSimulator.SIZE/2-1, 2)) <= Math.random()-0.02)
						tiles[x][y] = new Tile(Tile.Type.STONE, 1);
			}
		}
	}
	
	public static void smooth(Tile[][] tiles)
	{
		Tile[][] oldSet = new Tile[tiles.length][tiles[0].length];
		for(int x = 0; x < tiles.length; x++)
			for(int y = 0; y < tiles[x].length; y++)
				oldSet[x][y] = tiles[x][y];
		
		for(int x = 1; x < tiles.length-1; x++)
			for(int y = 1; y < tiles[x].length-1; y++)
			{
				HashMap<Tile, Integer> map = new HashMap<Tile, Integer>();
				
				for(int rx = -1; rx < 2; rx++)
					for(int ry = -1; ry < 2; ry++)
						map.put(oldSet[x+rx][y+ry], map.get(oldSet[x+rx][y+ry]) == null ? 1 : map.get(oldSet[x+rx][y+ry]) + 1);
				
//				int rx = 1, ry = 0;
//				map.put(oldSet[x+rx][y+ry], map.get(oldSet[x+rx][y+ry]) == null ? 1 : map.get(oldSet[x+rx][y+ry]) + 1);
//				rx = -1; ry = 0;
//				map.put(oldSet[x+rx][y+ry], map.get(oldSet[x+rx][y+ry]) == null ? 1 : map.get(oldSet[x+rx][y+ry]) + 1);
//				rx = 0; ry = -1;
//				map.put(oldSet[x+rx][y+ry], map.get(oldSet[x+rx][y+ry]) == null ? 1 : map.get(oldSet[x+rx][y+ry]) + 1);
//				rx = 0; ry = 1;
//				map.put(oldSet[x+rx][y+ry], map.get(oldSet[x+rx][y+ry]) == null ? 1 : map.get(oldSet[x+rx][y+ry]) + 1);
//				rx = 0; ry = 0;
//				map.put(oldSet[x+rx][y+ry], map.get(oldSet[x+rx][y+ry]) == null ? 1 : map.get(oldSet[x+rx][y+ry]) + 1);
				
				Tile top = (Tile) map.keySet().toArray()[0];
				for(int n = 0; n < map.size(); n++)
					if(map.get(map.keySet().toArray()[n]) > map.get(top))
						top = (Tile) map.keySet().toArray()[n];
				
				tiles[x][y] = top;
			}
	}
	
	public static void genMountain(Tile[][] tiles)
	{
		for(int x = 1; x < tiles.length; x++)
			for(int y = 1; y < tiles[x].length; y++)
			{
				int waters = 0;
				for(int rx = -2; rx < 3; rx++)
					for(int ry = -2; ry < 3; ry++)
						if(x+rx>=0 && y+ry>=0 && x+rx<tiles.length && y+ry < tiles[x].length && tiles[x+rx][y+ry].type == Tile.Type.WATER)
							waters++;
				
				if(tiles[x][y].type == Tile.Type.STONE)
					tiles[x][y].height = IslandSimulator.MOUNTAIN_HEIGHT - (double)waters / 25.0 * IslandSimulator.MOUNTAIN_HEIGHT;
			}
	}
	
	public static void deAlias(Tile[][] tiles)
	{
		for(int x = 0; x < tiles.length; x++)
			for(int y = 0; y < tiles[x].length; y++)
				tiles[x][y] = tiles[x][y].clone();
	}
}
