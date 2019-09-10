package main;

public class Token {
	final static int PR=0;
	final static int SE = 1;
	final static int OPL = 2;
	final static int OPA = 3;
	final static int TIPO  = 4;
	final static int MOD = 5;
	final static int DIG = 6;
	final static int VAL = 7;
	final static int STG = 8;
	final static int ID = 9;
	
	private String desc,token;
	private int tipo,columna,fila;
	private String [] significado = {"Palabra reservada","Simbolo especial","Operador Logico","Operador Aritmetico"
			,"Tipo","Modificador","Digito","Valor","Cadena","Identificador"};


	public Token(int tp,String t,int col, int fi){
		tipo = tp;
		token = t;
		columna = col;
		fila = fi;
		if(tipo != -1)
			desc = significado[tipo];
		else
			desc ="";
	}

	public String getDesc() {
		return desc;
	}

	public String getToken() {
		return token;
	}
	
	public int getColumna() {
		return columna;
	}

	public int getFila() {
		return fila;
	}
	public int getTipo(){
		return tipo;
	}
}
