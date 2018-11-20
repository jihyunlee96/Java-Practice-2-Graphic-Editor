import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Edit_Resize extends JMenuItem implements ActionListener {
	
	public Edit_Resize()
	{
		super("Resize");
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Resize") {
			JOptionPane.showMessageDialog(null, "Resize Mode On!");
			MainFrame.drawer.whichShape = 101;			
		}
	}

}
