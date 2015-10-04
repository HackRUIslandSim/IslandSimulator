import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class IslandSimulator extends JPanel
{
	private static final long serialVersionUID = 3858735718453756678L;
	static Timer loop = new Timer();
	static IslandSimulator panel;
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Island Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new IslandSimulator();
		frame.getContentPane().add(panel);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		loop.scheduleAtFixedRate(new TimerTask(){
			public void run(){
				panel.tick();
				panel.repaint();
				
				if(!panel.isOpen())
					loop.cancel();
			}
		}, 0, 1000/10);
	}
	
	public static final int SIZE = 64, TILE_SIZE = 8;
	public static final double MOUNTAIN_HEIGHT = 5;
	public static int mouseX = 0, mouseY = 0;
	public static Tile[][] tiles;
	public static ArrayList<Creature> creatures;
	
	public IslandSimulator()
	{
		tiles = new Tile[SIZE][SIZE];
		creatures = new ArrayList<Creature>();

		Generation.initialGen(tiles);
		Generation.cleanUp(tiles, 30);
		Generation.genMountain(tiles);
		
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_1)
					Generation.noise(tiles);
				else if(e.getKeyCode() == KeyEvent.VK_2)
					Generation.smooth(tiles);
				else if(e.getKeyCode() == KeyEvent.VK_3)
					Generation.genMountain(tiles);
				else if(e.getKeyCode() == KeyEvent.VK_SPACE)
					if(mouseX >= 0 && mouseY >= 0 && mouseX < SIZE * TILE_SIZE && mouseY < SIZE * TILE_SIZE)
						System.out.println(tiles[mouseX/TILE_SIZE][mouseY/TILE_SIZE].hydration + " " +
					mouseX/TILE_SIZE + " " + mouseY/TILE_SIZE); 
			}
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent e)
			{
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		
		this.setPreferredSize(new Dimension(TILE_SIZE*SIZE, TILE_SIZE*SIZE));
	}
	
	public void tick()
	{
		for(int x = 0; x < tiles.length; x++)
			for(int y = 0; y < tiles[x].length; y++)
				tiles[x][y].tick(x, y);

		for(int i = 0; i < creatures.size(); i++)
		{
			creatures.get(i).tick();
			if(creatures.get(i).alive == false)
				creatures.remove(i);
		}
	}
	
	public boolean isOpen()
	{
		return true;
	}
	
	public void paintComponent(Graphics gr)
	{
		Graphics2D g = (Graphics2D) gr;
		
		for(int x = 0; x < tiles.length; x++)
			for(int y = 0; y < tiles[x].length; y++)
			{
				g.drawImage(tiles[x][y].type.img, x*TILE_SIZE, y*TILE_SIZE, null);
				g.setColor(new Color(0, 0, 0, (int)(170 - tiles[x][y].height*24)));
				g.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
			}
	}
	
	public static void debugHang()
	{
		panel.repaint();
		try
		{
			Thread.sleep(2000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
