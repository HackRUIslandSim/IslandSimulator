import java.awt.Color;


public class Tile
{
	public static enum Type{
		STONE(Color.gray), WATER(Color.blue);
		
		public Color color;
		Type(Color c)
		{
			color = c;
		}
	}
	public Type type;
	
	public Tile(Type t)
	{
		type = t;
	}
}
