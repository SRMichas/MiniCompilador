package GUIComponents;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import entities.Token;

public class TokenTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private String[] titulo ={"Type","Token","Column","Row"};
	private ArrayList<Token> ComLex;
	private boolean small;

	public TokenTableModel(ArrayList<Token> cl,boolean c){
		ComLex = cl;		small = c;
	}
	
	@Override
	public int getColumnCount() {
		if( small )
			return 2;
		else
			return 4;
	}

	@Override
	public int getRowCount() {
		if (ComLex == null)
			return 0;
		else
			return ComLex.size();
	}

	@Override
	public Object getValueAt(int r, int c) {
		Object val = null;
		Token cl = null;
		if (ComLex != null)
			cl = ComLex.get(r);
		
		switch (c) {
		case 0: 
			if( cl != null)
				val = cl.getDesc();
			else
				val = "";
			break;
		case 1: 
			if( cl != null)
				if( cl.getType() == Token.EOF)
					val = "";
				else
					val = cl.getToken();
			else
				val = "";
			break;
		case 2: val = cl.getColumn();
			break;
		case 3: val = cl.getLine();
			break;
		}
		return val;
	}
	public String getColumnName(int colu) {
		return titulo[colu];
	}
}
