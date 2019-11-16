package codeGeneration;

import java.awt.FontMetrics;
import java.util.ArrayList;

import javax.swing.JTextPane;

import entities.Identifier;
import entities.Token;

public class CuadrupleGenerator{

	private ArrayList<Identifier> tSymb;
	private ExpressionTree tree;
	private String output = "";
	private JTextPane txtpane;

	public CuadrupleGenerator(){ }
	public CuadrupleGenerator(ArrayList<Identifier> t,JTextPane view){
		tSymb = t;
		txtpane = view;
		//arbol = new ArbolExpresion();
	}

	public ArrayList<Identifier> retTable(){
		return tSymb;
	}
	public String retOutput(){
		return output;
	}
	public String delimiliter(){
		FontMetrics fm = txtpane.getFontMetrics(txtpane.getFont());
		int charwidth = fm.charWidth('w');
		int total_width = txtpane.getWidth();
		int num = total_width/charwidth;
		String doubleline = "";
		for (int i = 0; i < num-3; i++) {
			doubleline += "=";
		}
		return doubleline += "\n";
	}
	public void genarateCuadruples(){
		output = "";
		int i = 1;
		String men="";
		int space = 11;
		String delimiter = delimiliter();
		for( Identifier ide: tSymb){
			if( !ide.getType().equals("-") && ide.getExp() != null){
				output += delimiter;
				output += String.format("%25s %n","Cadruple #"+i);
				tree = new ExpressionTree(ide,tSymb);
				ArrayList<Token> expression = ide.getExp();
				for(Token tok: expression){
					tree.add(tok);
					men += " "+tok.getToken();
				}
				output += String.format("%10s %s %s %n", ide.getName(),"=",men);
				output += String.format("%"+space+"s "+"%"+space+"s "+"%"+space+"s "+
						"%"+space+"s %n", "Operator","Operand1","Operand2","Result");
				String val = tree.solve();
				ide.setValue(val);
				output += tree.result;
				i++;
				men = "";
			}
		}
		output += delimiter;
	}
}