package main;

import java.util.ArrayList;

public class Analizador {
	
	private ArrayList<Token> arr = new ArrayList<>();
	private String salida = "";
	//private boolean algo = true;
	private Lexer lex = new Lexer();
	private Parser p;
	private Semantic sem;
	public boolean muestra;
	
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
			sem = new Semantic(p.r());
			salida += sem.Semantico();
		}
		if( salida.endsWith("Sintacticos\n")){
			salida += "\tNo hay errores Semanticos\n"+
					"\n\tPrograma Compilado con exito";
			muestra = true;
		}
		return salida;
	}
	
}
