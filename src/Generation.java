
public class Generation
{

	public static void initialGen(Tile[][] tiles)
	{

	}

	public static void smooth(Tile[][] tiles)
	{
		// Checks to see if the surronding blocks are water
		for (int y = 1; y < (tiles[y].length - 1); y++)
		{
			for (int x = 1; x < (tiles[y].length - 1); x++)
			{
				// The next segment checks the tiles around the stone tile
				// and then randomly changes it based on amount of tiles around
				// it
				if (tiles[x][y].type == Tile.Type.STONE)
				{
					int numstone = 0;
					int numwater = 0;
					for (int suby = -1; suby < 2; suby++)
					{
						for (int subx = -1; subx < 2; subx++)
						{
							if (tiles[x + subx][y + suby].type != Tile.Type.WATER)
							{
								numstone++;
							} else
							{
								numwater++;
							}

						}
					}
					// randomly decides stone or water
					double judge = numstone / numwater;
					double random = Math.random() * 8;
					if (random > judge)
					{
						tiles[x][y].type = Tile.Type.WATER;
					}
				}
			}
		}
	}
}
