import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class DrawingMenuPanel extends JPanel {
	public static JButton line;
	public static JButton rect;
	public static JButton circle;
	public static JButton droplet;
	public static JButton sketch;
	//public static JButton polyLine;
	public static JButton spray;
	public static JButton pattern;
	public static JButton eraser;
	
	int buttonSize = 30;
	
	public DrawingMenuPanel ()
	{
		setSize(100, 600);
		setLocation(0, 0);
				
		setLayout(new GridLayout(1, 6));
		
		line = new JButton ("line");
		line.setSize(buttonSize, buttonSize);
		line.setLocation(0, buttonSize);
		add(line);
		
		rect = new JButton ("rect");
		rect.setSize(buttonSize, buttonSize);
		rect.setLocation(0, buttonSize);
		add(rect);
		
		circle = new JButton ("circle");
		circle.setSize(buttonSize, buttonSize);
		circle.setLocation(0, buttonSize);
		add(circle);
		
		sketch = new JButton ("sketch");
		sketch.setSize(buttonSize, buttonSize);
		sketch.setLocation(0, buttonSize);
		add(sketch);
		
		/*polyLine = new JButton ("poly line");
		polyLine.setSize(buttonSize, buttonSize);
		polyLine.setLocation(0, buttonSize);
		add(polyLine);*/
		
		spray = new JButton ("spray");
		spray.setSize(buttonSize, buttonSize);
		spray.setLocation(0, buttonSize);
		add(spray);
		
		pattern = new JButton ("pattern");
		pattern.setSize(buttonSize, buttonSize);
		pattern.setLocation(0, buttonSize);
		add(pattern);
		
		eraser = new JButton ("eraser");
		eraser.setSize(buttonSize, buttonSize);
		eraser.setLocation(0, buttonSize);
		eraser.setForeground(Color.RED);
		add(eraser);
		
		setBackground(Color.WHITE);
				
		setVisible(true);
	}
	
	
}
