import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Edit_Grouping extends JMenuItem implements ActionListener {
	
	public Edit_Grouping()
	{
		super("Grouping");
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Grouping") {
			JOptionPane.showMessageDialog(null, "Click the objects you want to group!");
			MainFrame.drawer.setWhichShape(106);
		}
	}
}
