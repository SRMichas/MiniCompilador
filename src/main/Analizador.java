package main;

import java.util.ArrayList;

public class Analizador {
	
	private ArrayList<Token> arr = new ArrayList<>();
	private ArrayList<Identificador> tablaSimbolos;
	private String salida = "",mensaje = "";
	//private boolean algo = true;
	private Lexer lex = new Lexer();
	private Parser p;
	private Semantic2 sem;
	public boolean muestra;
	
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
	public String retMensaje(){ return mensaje; }
	public String compilacion(String entrada){
		salida = "";
		muestra = false;
		if( lex.lexico(entrada.toCharArray())){
			salida += "\tNo hay errores Lexicos\n";
			//Sintactico(componentes.inicio());
			//p = new Parser(componentes);
			arr = lex.retComp();
			p = new Parser(arr);
			salida += p.Sintactico();
		}else 
			salida = lex.salida;
		
		if( salida.equals("\tNo hay errores Lexicos\n") ){
			salida += "\tNo hay errores Sintacticos\n";
			sem = new Semantic2(p.r());
			salida += sem.Semantico();
		}
		if( salida.contains("No hay errores Sintacticos")){
			salida += "\tNo hay errores Semanticos\n"+
					"\n\tPrograma Compilado con exito";
			muestra = true;
			Triplo trip = new Triplo(p.r());
			trip.algo();
			mensaje = trip.retMensaje();
			//System.out.println(mensaje);
			tablaSimbolos = trip.retTabla();
			System.out.println(mensaje);
		}
		return salida;
	}
	
}
