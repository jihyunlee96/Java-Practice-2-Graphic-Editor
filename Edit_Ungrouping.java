import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Edit_Ungrouping extends JMenuItem implements ActionListener {
	
	public Edit_Ungrouping()
	{
		super("Ungrouping");
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Ungrouping") {
			JOptionPane.showMessageDialog(null, "Click an object you want to ungroup!");
			MainFrame.drawer.setWhichShape(107);
		}
	}
}
