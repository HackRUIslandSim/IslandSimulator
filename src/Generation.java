
public class Generation
{
	public static void initialGen(Tile[][] tiles)
	{
		
	}
	public static void smooth (Tile[][] tiles)
	{
		//Checks to see if the surronding blocks are water
		for(int y = 1;y<tiles.length;y++)
		{
			for(int x  = 1;x<tiles.length;x++)
			{
				if(tiles[x][y].type==Tile.Type.STONE)
				{
					
				}
			}
		}
	}
}
