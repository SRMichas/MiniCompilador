package main;

import java.util.ArrayList;

public class Analizador {
	
	private ArrayList<Token> arr = new ArrayList<>();
	private String salida = "";
	//private boolean algo = true;
	private Lexer lex = new Lexer();
	private Parser2 p;
	private Semantic sem;
	
	public ArrayList<Token> retArr(){
		return arr;
	}
	public ArrayList<Identificador> retArrS(){
		try {
			return p.r();
		} catch (NullPointerException e) {
			return new ArrayList<Identificador>(0);
		}
		
	}
	public String compilacion(String entrada){
		salida = "";
		if( lex.lexico(entrada.toCharArray())){
			salida += "\tNo hay errores Lexicos\n";
			//Sintactico(componentes.inicio());
			//p = new Parser(componentes);
			arr = lex.retComp();
			p = new Parser2(arr);
			salida += p.Sintactico();
		}else 
			salida = lex.salida;
		if( salida.equals("\tNo hay errores Lexicos\n") ){
			salida += "\tNo hay errores Sintacticos\n";
			sem = new Semantic(p.r());
			salida += sem.Semantico();
		}
		if( salida.endsWith("Sintacticos\n")){
			salida += "\tNo hay errores Semanticos\n";
		}
		return salida;
	}
	
}
