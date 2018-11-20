import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Edit_Undo extends JMenuItem implements ActionListener {
	
	public Edit_Undo()
	{
		super("Undo");
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Undo") {
			JOptionPane.showMessageDialog(null, "Undo!");
			MainFrame.drawer.undo();
		}
	}
	
}
