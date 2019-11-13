package main;

import java.util.ArrayList;

import javax.swing.JTextPane;

public class Analizador {
	
	private ArrayList<Token> arr = new ArrayList<>();
	private ArrayList<Identificador> tablaSimbolos;
	private String salida = "",mensaje = "";
	//private boolean algo = true;
	private Lexer lex = new Lexer();
	private Parser p;
	private Semantic sem;
	public boolean muestra;
	private JTextPane contenedor;
	
	public ArrayList<Token> retArr(){
		return arr;
	}
	public ArrayList<Identificador> retArrS(){
		/*try {
			return p.r();
		} catch (NullPointerException e) {
			return new ArrayList<Identificador>(0);
		}*/
		return tablaSimbolos;
		
	}
	public void setView(JTextPane view){
		contenedor = view;
	}
	public String retMensaje(){ return mensaje; }
	public String compilacion(String entrada){
		salida = "";
		muestra = false;
		if( lex.lexico(entrada.toCharArray())){
			salida += "\tNo hay errores Lexicos\n";
			arr = lex.retComp();
			p = new Parser(arr);
			salida += p.Sintactico();
		}else 
			salida = lex.salida;
		
		if( salida.equals("\tNo hay errores Lexicos\n") ){
			salida += "\tNo hay errores Sintacticos\n";
			sem = new Semantic(p.r());
			salida += sem.Semantico();
		}
		if( salida.endsWith("errores Sintacticos\n")){
			salida += "\tNo hay errores Semanticos\n"+
					"\n\tPrograma Compilado con exito";
			muestra = true;
			Cuadruplo trip = new Cuadruplo(p.r(),contenedor);
			trip.algo();
			mensaje = trip.retMensaje();
			tablaSimbolos = trip.retTabla();
		}
		return salida;
	}
	
}
