import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartUp extends JPanel
{
	private static final long serialVersionUID = 3284090396408753671L;
	static JFrame frame;
	JTextField sizeField, tileField, timeField;
	int size = 64, tileSize = 8, time = 100;

	public static void main(String[] args)
	{
		frame = new JFrame("Island Simulator Launcher");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		StartUp panel = new StartUp();
		frame.getContentPane().add(panel);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public StartUp()
	{
		this.add(new JLabel("Enter map size:"));
		sizeField = new JTextField("64", 5);
		this.add(sizeField);
		this.add(new JLabel("Enter tile size:"));
		tileField = new JTextField("8", 5);
		this.add(tileField);
		this.add(new JLabel("Enter run time:"));
		timeField = new JTextField("100", 5);
		this.add(timeField);
		this.add(new JLabel("<html>The run time is how many times per<br>second the program updates. 1 is<br>incredibly slow, and 1000 is max speed.</html>"));
		
		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				frame.dispose();
				try{ size = Integer.parseInt(sizeField.getText());} catch(NumberFormatException ex){};
				try{ tileSize = Integer.parseInt(tileField.getText());} catch(NumberFormatException ex){};
				try{ time = Integer.parseInt(timeField.getText());} catch(NumberFormatException ex){};
				IslandSimulator.main(size, tileSize, time);
			}
		});
		this.add(start);
		
		this.setPreferredSize(new Dimension(235, 175));
	}
}
