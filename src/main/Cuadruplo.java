package main;

import java.awt.FontMetrics;
import java.util.ArrayList;

import javax.swing.JTextPane;

public class Cuadruplo{

	private ArrayList<Identificador> tSimb;
	private ArbolExpresion arbol;
	private String mensaje = "";
	private JTextPane txtpane;

	public Cuadruplo(){ }
	public Cuadruplo(ArrayList<Identificador> t,JTextPane view){
		tSimb = t;
		txtpane = view;
		//arbol = new ArbolExpresion();
	}

	public ArrayList<Identificador> retTabla(){
		return tSimb;
	}
	public String retMensaje(){
		return mensaje;
	}
	public String delimitador(){
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
	public void algo(){
		mensaje = "";
		int i = 1;
		String men="";
		int space = 11;
		String delimitador = delimitador();
		for( Identificador ide: tSimb){
			if( !ide.getTipo().equals("-") && ide.getExp() != null){
				mensaje += delimitador;
				mensaje += String.format("%25s %n","Cadruplo #"+i);
				arbol = new ArbolExpresion(ide,tSimb);
				ArrayList<Token> expresion = ide.getExp();
				for(Token tok: expresion){
					arbol.añadir(tok);
					men += " "+tok.getToken();
				}
				mensaje += String.format("%10s %s %s %n", ide.getNombre(),"=",men);
				mensaje += String.format("%"+space+"s "+"%"+space+"s "+"%"+space+"s "+
						"%"+space+"s %n", "Operador","Operando1","operando2","Resultado");
				String val = arbol.resuelve();
				ide.setValor(val);
				mensaje += arbol.result;
				i++;
				men = "";
			}
		}
		mensaje += delimitador;
	}
}