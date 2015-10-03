
public class Generation
{
	public static void initialGen(Tile[][] tiles)
	{
		for(int x = 0; x < tiles.length; x++)
			for(int y = 0; y < tiles.length; y++)
				tiles[x][y] = new Tile(Tile.Type.WATER);
		
		for(int x = 1; x < tiles.length-1; x++)
			for(int y = 1; y < tiles.length-1; y++)
				if(Math.sqrt(Math.pow(IslandSimulator.SIZE/2-x, 2) + Math.pow(IslandSimulator.SIZE/2-y, 2)) / 
						Math.sqrt(Math.pow(IslandSimulator.SIZE/2-1, 2) + Math.pow(IslandSimulator.SIZE/2-1, 2)) <= Math.random())
					tiles[x][y].type = Tile.Type.STONE;
	}
	
	public static void smooth (Tile[][] tiles)
	{
		//Checks to see if the surronding blocks are water
//		for(int y = 0;y<tiles.length;y++)
//		{
//			for(int x  = 0;)
//		}
	}
}
