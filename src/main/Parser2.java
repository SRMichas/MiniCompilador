package main;

import java.util.ArrayList;

public class Parser2 {

	ArrayList<Token> compo;
	Token cp;
	String salida = "";
	public static String salida2 ="";
	private int idx = 0;
//	private int contador = 0,conIF = 0,conDV = 0;
	private boolean /*imprime,avanza = false,*/ideC = false;
	private ArrayList<Identificador> ide;
	private final static int sinValor = 30,exceso = 31;
	public Parser2(ArrayList<Token >c){
		compo = c;
		cp = compo.get(idx);
		ide = new ArrayList<>();
	}
	
	public String Sintactico(){
		salida2 = "";
		CD();
		return salida;
	}
	/*private void Acomodar(int tipo ,String to,String s){
		//salida2 += "Token obtenido:"+cp.getToken()+"\n"+"Token Esperado: "+s+"\n-------------------------------------------\n";
		if(cp.getTipo() == tipo && cp.getToken().matches(to)){
			if(idx < compo.size() - 1) idx++;
			try {
				cp = compo.get(idx);
			} catch (IndexOutOfBoundsException e) {
				idx--;
				Componente caux = compo.get(idx);
				cp = new Componente(19, "", caux.getColumna(), caux.getFila());
				error(tipo,s);
			}
		}else{
			error(tipo,s);
			/*if(idx < compo.size() - 1) idx++;
			try {
				cp = compo.get(idx);
			} catch (IndexOutOfBoundsException e) {
				idx--;
				Componente caux = compo.get(idx);
				cp = new Componente(19, "", caux.getColumna(), caux.getFila());
				error(tipo,s);
			}
		}
	}*/
	private boolean Acomodar(int tipo ,String s,String sig){
		if(cp.getTipo() == tipo && cp.getToken().equals(s)){
			Avanza();
			return true;
		}else{
			error(tipo,s);
			Token tok = null;
			try {
				tok = compo.get( idx + 1);
			} catch (IndexOutOfBoundsException e) {
				tok = new Token(-1, "", -1, -1);
			}
			if(tok.getToken().equals(sig))
				Avanza();
			return false;
		}
	}
	private void Avanza(){
		salida2 += "Token obtenido:"+cp.getToken()+"\n"+"Token Esperado: "+cp.getToken()+"\n-------------------------------------------\n";
		if(idx < compo.size() - 1) idx++;
		try {
			if(cp.getTipo() == Token.ID) ideC = true;
			else ideC = false;
			cp = compo.get(idx);
		} catch (IndexOutOfBoundsException e) {
			idx--;
			Token caux = compo.get(idx);
			cp = new Token(19, "", caux.getColumna(), caux.getFila());
			//error(tipo,s);
		}
	}
	private void error(int t,String to){
		switch (t) {
		case Token.PR:
			switch (to) {
			case "class":
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \"class\"\t"+cp.getToken()+"\n";
				break;
			case "if":
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \"if\"\t"+cp.getToken()+"\n";
				break;
			case "while":
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \"while\"\t"+cp.getToken()+"\n";
				break;
			}
			break;
		case Token.SE:
			switch (to) {
			case "{":
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\t"+cp.getToken()+"\n";	
				break;
			case "}":
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\t"+cp.getToken()+"\n";	
				break;
			case "(":
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\t"+cp.getToken()+"\n";	
				break;
			case ")":
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\t"+cp.getToken()+"\n";	
				break;
			case ";":
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\t"+cp.getToken()+"\n";	
				break;
			default:
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un Simbolo especial\t"+cp.getToken()+"\n";
				break;
			}
			break;
		case Token.OPL:
			//if(to.equals("arit"))
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un operador logico\t"+cp.getToken()+"\n";
			break;
		case Token.OPA:
			salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un operador aritmetico\t"+cp.getToken()+"\n";
			break;
		case Token.TIPO:
			salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \"int\" o \"boolean\"\t"+cp.getToken()+"\n";
			break;
		case Token.MOD:
			salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \"public\" o \"private\"\t"+cp.getToken()+"\n";
			break;
		case Token.DIG:
			salida +="Error Sintactico, Fila: "+cp.getFila()+" se espeba un digito\t"+cp.getToken()+"\n";
			break;
		case Token.VAL:
			salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba \"true\" o \"false\"\t"+cp.getToken()+"\n";
			break;
		case Token.ID:
				if((to.length() == 0 || to.length() != 0) && !cp.getToken().equals(to))
					salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba el identificador \""+to+"\"\t"+cp.getToken()+"\n";
				else
					salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un identificador\t"+cp.getToken()+"\n";
			break;
		case sinValor:
			salida +="Error Sintactico, Fila: "+cp.getFila()+" se ";
			break;
		case exceso:
			salida += "Error Sintactico, Fila: "+cp.getFila()+" \""+to+"\" no concuerda con la gramatica\n";
			break;


		}
		salida2 += "Token obtenido:"+cp.getToken()+"\n"+"Token Esperado: "+to+"\n-------------------------------------------\n";
		
	}
	
	private void CD(){
		Token c = cp;
		/*
		 * ,cs = null
		try {
			cs = compo.get(idx + 1);
		} catch (IndexOutOfBoundsException e) {
			cs = new Token(-1, "", -1, -1);
		}*/
		if(!c.getToken().equals("class")){
			M();
		}
		c = cp;
		Acomodar(Token.PR,"class","");
			
		c = cp;
		ID();
		c = cp;
		Acomodar(Token.SE,"{","");
		
		//-----------------FD
		c = cp;
		//if(c.getTipo() == Componente.MOD || c.getTipo() == Componente.TIPO )
		FD();
		//-----------------S
		//S();
		MD();
		Acomodar(Token.SE,"}","\uffff");
		if( cp.getTipo() != Token.EOF){
			while(cp.getTipo() != Token.EOF){
				//men = cp.getToken()+" ";	
				error(exceso, cp.getToken());
				Avanza();
			}
		}else
			Acomodar(Token.EOF, "\uffff", null);
	}
	private void MD(){
		Token c = cp;
		if( !c.getToken().equals("}")){
			Acomodar(Token.MOD, "public","static");
			Acomodar(Token.PR, "static","void");
			Acomodar(Token.PR, "void","main");
			Acomodar(Token.ID,"main","(");
			Acomodar(Token.SE, "(",")");
			Acomodar(Token.SE,")","{");
			Acomodar(Token.SE, "{","}");
			S();
			Acomodar(Token.SE, "}","}");
		}
		
	}
	private void FD(){
		Token c = cp, caux;
		try {
			caux = compo.get(idx + 1);
		} catch (IndexOutOfBoundsException e) {
			caux = new Token(-1, "", -1, -1);
		}
		if((c.getTipo() == Token.MOD || c.getTipo() == Token.TIPO) && caux.getTipo() != Token.PR){
			VDN();
			c = cp;
			Acomodar(Token.SE,";","public");
		}
	}
	private void VDN(){
		//conDV++;
		Token c= null;
		c = cp;
		if(c.getTipo() != Token.TIPO)
			M();
		T();
		ID();
		c = cp;
		if(c.getToken().equals("=")){
			Avanza();
			VDR();
		}
		//contador ++;
	}
	private void VDR(){
		Token c;
		c = cp;
		if(c.getTipo() == Token.DIG){
			if( c.getToken().contains(".") && c.getToken().contains("f"))
				FTL();
			else if( c.getToken().contains("."))
				DBL();
			else
				IL();
		}else if(c.getTipo() == Token.VAL)
			BL();
		else if( c.getTipo() == Token.STG)
			STGL();
		else 
			error(sinValor,"");
	}
	private void E(){
		TE();
	}
	private void TE(){
		Token c = null;
		c = cp;
		if(c.getTipo() == Token.DIG){
			if( c.getToken().contains(".") && c.getToken().contains("f"))
				FTL();
			else if( c.getToken().contains("."))
				DBL();
			else
				IL();
		}else
			ID();
		c = cp;
		if(c.getToken().matches("(>|<|>=|<=|==|!=)"))
			Avanza();
		else
			error(Token.OPL,"log");
		c = cp;
		if(c.getTipo() == Token.DIG){
			if( c.getToken().contains(".") && c.getToken().contains("f"))
				FTL();
			else if( c.getToken().contains("."))
				DBL();
			else
				IL();
		}else
			ID();
	}
	private void S(){
		Token c = null;
		c = cp;
		if(c.getToken().equals("if")){
			Avanza();
			IS();
		}else if(c.getToken().equals("while")){
			Avanza();
			WS();
		}else if(c.getTipo() == Token.MOD || c.getTipo() == Token.TIPO){
			VDN();
		}
	}
	private void WS(){
		/*Token c=null,caux = null,cauxa = null;
		c = cp;*/
		Acomodar(Token.SE,"(","");
		E();
		Acomodar(Token.SE,")","{");
		Acomodar(Token.SE,"{","");
		S();
		Acomodar(Token.SE,"}","");
	}
	private void IS(){
		/*Token c;
		c = cp;*/
		Acomodar(Token.SE,"(","");
		E();
		Acomodar(Token.SE,")","{");
		Acomodar(Token.SE,"{","");
		AE();
		Acomodar(Token.SE,"}","");
		
		S();
	}
	private void T(){
		TS();
	}
	private void TS(){
		Token c = null;
		c = cp;
		//if(c.getToken().matches("(int|boolean)"))
		/*if(c.getToken().equals("int"))
			Avanza();
		else if(c.getToken().equals("boolean"))
			Avanza();
		else if(c.getToken())
		else
			error(Token.TIPO, "");*/
		
		switch (c.getToken()) {
		case "int": 	Avanza(); break;
		case "boolean": Avanza(); break;
		case "String": 	Avanza(); break;
		case "double": 	Avanza(); break;
		case "float": 	Avanza(); break;
		default:
			error(Token.TIPO, "");
			break;
		}
	}
	private void M(){
		Token c = null;
		c = cp;
		if(c.getToken().equals("public")) 
			Avanza();
		else if(c.getToken().equals("private")) 
			Avanza();
		else
			error(Token.MOD, "");
	}
	private void IL(){
		Acomodar(Token.DIG, cp.getToken(),"");
	}
	private void BL(){
		
		Avanza();
	}
	private void STGL(){
		Avanza();
	}
	private void DBL(){
		Avanza();
	}
	private void FTL(){
		Avanza();
	}
	private void ID(){
		Token c = null,caux = null,cauxa=null,cauxaa=null;
		String men = "";
		c = cp;
		men = cp.getToken();
		Acomodar(Token.ID,c.getToken(),"");
		if( ideC){
			try {
				c = compo.get(idx - 1);
				caux = compo.get(idx - 2);
				cauxa = compo.get(idx);
				cauxaa = compo.get(idx + 1);
			} catch (IndexOutOfBoundsException e) {
				c = new Token(-1, "invalido", -1, -1);
				caux = new Token(-1, "invalido", -1, -1);
				cauxa = new Token(-1, "invalido", -1, -1);
				cauxaa = new Token(-1, "invalido", -1, -1);
			}
			
			if( caux.getToken().equals("class"))
				ide.add(new Identificador(c.getToken(),"class", "",c.getFila()));
			else if(caux.getTipo() == Token.TIPO && cauxa.getToken().equals(";"))
				ide.add(new Identificador(c.getToken(),caux.getToken(), "",c.getFila()));
			else if(cauxa.getToken().equals("=") && caux.getTipo() == Token.TIPO &&
					(cauxaa.getTipo() == Token.DIG || cauxaa.getTipo() == Token.VAL || cauxaa.getTipo() == Token.STG)){
				ide.add(new Identificador(c.getToken(), caux.getToken(), cauxaa.getToken(),c.getFila()));
			}else if(cauxa.getToken().equals("=") && (cauxaa.getTipo() == Token.DIG || cauxaa.getTipo() == Token.TIPO)){
				String salida  = "";
				int dig = 2;
				while(!cauxaa.getToken().equals(";")){
					salida += cauxaa.getToken();
					dig++;
					cauxaa = compo.get(idx + dig);
					ide.add(new Identificador(c.getToken(), "", salida,c.getFila()));
				}
			}
		}
			
		men = cp.getToken();
	}
	private void AE(){
		Token c;
		c = cp;
		ID();
		
		Acomodar(Token.SE,"=","");
		
		if(c.getTipo() == Token.DIG){
			if( c.getToken().contains(".") && c.getToken().contains("f"))
				FTL();
			else if( c.getToken().contains("."))
				DBL();
			else
				IL();
		}else
			ID();
		c = cp;
		if(c.getToken().matches("[\\+|-|/|\\*]"))
			Avanza();
		else
			error(Token.OPA, "arit");
		
		if(c.getTipo() == Token.DIG){
			if( c.getToken().contains(".") && c.getToken().contains("f"))
				FTL();
			else if( c.getToken().contains("."))
				DBL();
			else
				IL();
		}else
			ID();
		
		Acomodar(Token.SE,";","");
	}
	/*private int contador(String t){
		int c = 0;
		/*Nodo<Componente> aux = componentes.inicio();
		while(aux != null){
			if(aux.valor.getToken().equals(t))
				c++;
			aux = aux.sig;
			
		}
		return c;
	}*/
	public ArrayList<Identificador> r(){
		return ide;
	}
}
