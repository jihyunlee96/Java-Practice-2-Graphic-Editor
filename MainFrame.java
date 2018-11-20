import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame implements ActionListener {
	
	// 여러 Drawer 패널들 객체 생성
	public static Drawer drawer = new Drawer();
	DrawingMenuPanel dm = new DrawingMenuPanel();
	Menu menuBar = new Menu();
	
	public MainFrame()
	{
		setTitle("My Image Editor");
		setSize(900, 600);
		setLocation(100, 0);
		
		setJMenuBar(menuBar);
		
		this.setContentPane(drawer);
		this.getContentPane().setBackground(Color.WHITE);
		
		add(dm);
		
		DrawingMenuPanel.line.addActionListener(this);
		DrawingMenuPanel.rect.addActionListener(this);
		DrawingMenuPanel.circle.addActionListener(this);
		DrawingMenuPanel.sketch.addActionListener(this);
		//DrawingMenuPanel.polyLine.addActionListener(this);
		DrawingMenuPanel.spray.addActionListener(this);
		DrawingMenuPanel.pattern.addActionListener(this);
		DrawingMenuPanel.eraser.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setResizable(false);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand() == "line") {
			drawer.setWhichShape(0);		
		}
		
		else if(e.getActionCommand() == "rect") {
			drawer.setWhichShape(1);		
		}
		
		else if(e.getActionCommand() == "circle") {
			drawer.setWhichShape(2);				
		}
		
		/*else if(e.getActionCommand() == "droplet") {
			drawer.setWhichShape(3);				
		}*/
		
		else if(e.getActionCommand() == "sketch") {
			drawer.setWhichShape(4);				
		}
		
		else if(e.getActionCommand() == "poly line") {
			drawer.setWhichShape(5);				
		}
		
		else if(e.getActionCommand() == "eraser") {
			drawer.setWhichShape(6);				
		}
		
		else if(e.getActionCommand() == "spray") {
			drawer.setWhichShape(7);				
		}
		
		else if(e.getActionCommand() == "pattern") {
			drawer.setWhichShape(105);		
			Drawer.temp_copy.setSize(0);
			
			JOptionPane.showMessageDialog(null, "Click an object you want to patternize!");
		}
	}
	
}
