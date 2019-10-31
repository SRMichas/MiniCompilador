package main;

import java.util.ArrayList;

public class Triplo{

	private ArrayList<Identificador> tSimb;
	private ArbolExpresion arbol;
	private String mensaje = "";

	public Triplo(){ }
	public Triplo(ArrayList<Identificador> t){
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
				mensaje += "\t'''''Triplo #"+i+"''''''''''''''''''''''\n";
				arbol = new ArbolExpresion(ide,tSimb);
				ArrayList<Token> expresion = ide.getExp();
				for(Token tok: expresion){
					arbol.add(tok);
				}
				String val = arbol.resuelve();
				ide.setValor(val);
				mensaje += arbol.result;
				i++;
			}
		}
	}
}