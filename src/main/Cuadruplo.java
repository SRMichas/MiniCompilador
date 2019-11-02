package main;

import java.util.ArrayList;

public class Cuadruplo{

	private ArrayList<Identificador> tSimb;
	private ArbolExpresion arbol;
	private String mensaje = "";

	public Cuadruplo(){ }
	public Cuadruplo(ArrayList<Identificador> t){
		tSimb = t;
		//arbol = new ArbolExpresion();
	}

	public ArrayList<Identificador> retTabla(){
		return tSimb;
	}
	public String retMensaje(){
		return mensaje;
	}
	public void algo(){
		mensaje = "";
		int i = 1;
		for( Identificador ide: tSimb){
			if( !ide.getTipo().equals("-") && ide.getExp() != null){
				mensaje += "   '''''Cuadruplo #"+i+"''''''''''''''''''''''\n";
				arbol = new ArbolExpresion(ide,tSimb);
				ArrayList<Token> expresion = ide.getExp();
				for(Token tok: expresion){
					arbol.añadir(tok);
				}
				String val = arbol.resuelve();
				ide.setValor(val);
				mensaje += arbol.result;
				i++;
			}
		}
	}
}