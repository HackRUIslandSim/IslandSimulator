import java.awt.Graphics;
import java.awt.Graphics2D;
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
		JFrame frame = new JFrame("Orb Game");
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
	
	public static final int SIZE = 64;
	
	public IslandSimulator()
	{
		
	}
	
	public void tick()
	{
		
	}
	
	public boolean isOpen()
	{
		return true;
	}
	
	public void paintComponent(Graphics gr)
	{
//		Graphics2D g = (Graphics2D) gr;
	}
}
