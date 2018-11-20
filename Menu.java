import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Menu extends JMenuBar implements ActionListener {
	JMenu file;
	JMenu edit;
	JMenu tools;
	
	File_Save save;
	
	Edit_Reset reset;
	Edit_Undo undo;
	Edit_Redo redo;
	Edit_Move move;
	Edit_Resize resize;
	Edit_Remove remove;
	Edit_Rotate rotate;
	Edit_Copy copy;
	Edit_Grouping grouping;
	Edit_Ungrouping ungrouping;
	
	Menu_Color color;
	Menu_Thickness thickness;
	Menu_Fill fill;
	
	public Menu()
	{
		super();
		
		file = new JMenu("File");
		this.add(file);
		
		save = new File_Save();
		file.add(save);
		
		edit = new JMenu("Edit");
		this.add(edit);
		
		reset = new Edit_Reset();
		edit.add(reset);
		
		edit.addSeparator();
		
		undo = new Edit_Undo();
		edit.add(undo);
		
		redo = new Edit_Redo();
		edit.add(redo);
		
		rotate = new Edit_Rotate();
		edit.add(rotate);
		
		edit.addSeparator();
		
		move = new Edit_Move();
		edit.add(move);
		
		resize = new Edit_Resize();
		edit.add(resize);
		
		remove = new Edit_Remove();
		edit.add(remove);
		
		edit.addSeparator();
		
		copy = new Edit_Copy();
		edit.add(copy);
		
		edit.addSeparator();
		
		grouping = new Edit_Grouping();
		edit.add(grouping);
		
		ungrouping = new Edit_Ungrouping();
		edit.add(ungrouping);

		tools = new JMenu("Tools");
		this.add(tools);
		
		color = new Menu_Color();
		tools.add(color);
		
		thickness = new Menu_Thickness();
		tools.add(thickness);	
		
		fill = new Menu_Fill();
		tools.add(fill);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Reset") {
			JOptionPane.showMessageDialog(null, "Reset!");
			MainFrame.drawer.reset();
		}

	}
}

