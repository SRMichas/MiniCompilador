package main;

public class Token {
	public final static int PR=0;
	public final static int SE = 1;
	public final static int OPL = 2;
	public final static int OPA = 3;
	public final static int TIPO  = 4;
	public final static int MOD = 5;
	public final static int DIG = 6;
	public final static int VAL = 7;
	public final static int STG = 8;
	public final static int ID = 9;
	public final static int EOF = 10;
	
	private String desc,token;
	private int tipo,columna,fila;
	private String [] significado = {"Palabra reservada","Simbolo especial","Operador Logico","Operador Aritmetico"
			,"Tipo","Modificador","Digito","Valor","Cadena","Identificador","Fin del archivo"};


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
