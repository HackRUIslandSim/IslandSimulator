import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
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
		}, 0, 1000/60);
	}
	
	public static final int SIZE = 64, TILE_SIZE = 8;
	public static final double MOUNTAIN_HEIGHT = 5;
	public static Tile[][] tiles;
	public static ArrayList<Creature> creatures;
	
	public IslandSimulator()
	{
		tiles = new Tile[SIZE][SIZE];

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
			}
		});
		
		this.setPreferredSize(new Dimension(TILE_SIZE*SIZE, TILE_SIZE*SIZE));
	}
	
	public void tick()
	{
		for(int x = 0; x < tiles.length; x++)
			for(int y = 0; y < tiles[x].length; y++)
				tiles[x][y].tick(x, y);
//		Iterator<Creature> itr = creatures.iterator();
//		while(itr.hasNext())
//			itr.next().tick();
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
				g.setColor(tiles[x][y].type.color);
				g.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
				g.setColor(new Color(0, 0, 0, (int)(tiles[x][y].height*24)));
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
