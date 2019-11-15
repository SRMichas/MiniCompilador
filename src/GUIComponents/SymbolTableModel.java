package GUIComponents;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import entities.Identifier;

public class SymbolTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private String[] titulo ={"Name","Type","Value","Position","Scope"};
	private ArrayList<Identifier> ide;

	public SymbolTableModel(ArrayList<Identifier> id){
		ide = id;
	}
	
	@Override
	public int getColumnCount() {
		return titulo.length;
	}

	@Override
	public int getRowCount() {
		if( ide != null)
			return ide.size();
		else
			return 0;
	}

	@Override
	public Object getValueAt(int r, int c) {
		Object val = null;
		Identifier id = null;
		if (ide != null)
			id = ide.get(r);
		
		switch (c) {
		case 0: val = id.getName(); break;
		case 1: val = id.getType();  break;
		case 2: val = id.getValue(); break;
		case 3: val = id.getLine(); break;
		case 4: val = id.getScope(); break;
		/*case 5: 
			try {
				val = id.getExp().size();
			} catch (Exception e) {
				// TODO: handle exception
				val = "-";
			}
			 break;*/
		}
		return val;
	}
	public String getColumnName(int colu) {
		return titulo[colu];
	}
}
