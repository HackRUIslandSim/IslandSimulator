import java.awt.Color;


public class Tile
{
	enum Type{
		STONE(Color.gray), WATER(Color.blue);
		
		Color color;
		Type(Color c)
		{
			color = c;
		}
	}
}
