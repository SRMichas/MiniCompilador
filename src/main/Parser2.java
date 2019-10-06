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
	private short lin;
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
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \"class\"\t"+cp.getToken()+"\n";
				break;
			case "if":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \"if\"\t"+cp.getToken()+"\n";
				break;
			case "while":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \"while\"\t"+cp.getToken()+"\n";
				break;
			case "void":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \"void\"\t"+cp.getToken()+"\n";
				break;
			case "static":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \"static\"\t"+cp.getToken()+"\n";
				break;
			}
			break;
		case Token.SE:
			switch (to) {
			case "{":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\t"+cp.getToken()+"\n";	
				break;
			case "}":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\t"+cp.getToken()+"\n";	
				break;
			case "(":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\t"+cp.getToken()+"\n";	
				break;
			case ")":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\t"+cp.getToken()+"\n";	
				break;
			case ";":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\t"+cp.getToken()+"\n";	
				break;
			default:
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un Simbolo especial\t"+cp.getToken()+"\n";
				break;
			}
			break;
		case Token.OPL:
			//if(to.equals("arit"))
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un operador logico\t"+cp.getToken()+"\n";
			break;
		case Token.OPA:
			salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un operador aritmetico\t"+cp.getToken()+"\n";
			break;
		case Token.TIPO:
			salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \"int\" o \"boolean\"\t"+cp.getToken()+"\n";
			break;
		case Token.MOD:
			salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \"public\" o \"private\"\t"+cp.getToken()+"\n";
			break;
		case Token.DIG:
			salida +="\tError Sintactico, Fila: "+cp.getFila()+" se espeba un digito\t"+cp.getToken()+"\n";
			break;
		case Token.VAL:
			salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba \"true\" o \"false\"\t"+cp.getToken()+"\n";
			break;
		case Token.ID:
				if((to.length() == 0 || to.length() != 0) && !cp.getToken().equals(to))
					salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba el identificador \""+to+"\"\t"+cp.getToken()+"\n";
				else
					salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un identificador\t"+cp.getToken()+"\n";
			break;
		case sinValor:
			salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un valor numerico o booleano o una cadena";
			break;
		case exceso:
			salida += "\tError Sintactico, Fila: "+cp.getFila()+" \""+to+"\" no concuerda con la gramatica\n";
			break;


		}
		salida2 += "Token obtenido:"+cp.getToken()+"\n"+"Token Esperado: "+to+"\n-------------------------------------------\n";
		
	}
	
	private void CD(){
		Token c = cp;
		String mod = null, clss = null, id = null;
		if(!c.getToken().equals("class")){
			mod = M();
		}
		c = cp;
		if( Acomodar(Token.PR,"class","") )
			clss = "class";
			
		c = cp;
		id = ID();
		if( id != null )
			ide.add(new Identificador(id, clss, null, lin));
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
		String ty = null;
		if( !c.getToken().equals("}")){
			Acomodar(Token.MOD, "public","static");
			Acomodar(Token.PR, "static","void");
			if( Acomodar(Token.PR, "void","main") )
				ty = "void";
			if( Acomodar(Token.ID,"main","(") && ty != null){
				ide.add(new Identificador("main", ty, "", compo.get(idx-1).getFila()));
			}
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
		String mod = null ,ty = null ,nom = null, val = "uno";
		if(c.getTipo() != Token.TIPO)
			mod = M();
		ty = T();
		nom = ID();
		c = cp;
		if(c.getToken().equals("=")){
			Avanza();
			val = VDR();
		}
		if( ty != null && nom != null && val != null){
			switch (ty) {
			case "int": 	val = "0"; break; 
			case "boolean": val = "false"; break;
			case "String": 	val = ""; break;
			case "double": 	val = "0.0"; break;
			case "float": 	val = "0.0f"; break;
			}
			ide.add(new Identificador(nom, ty, val, lin));
		}else if( val != null)
			ide.add(new Identificador(nom, ty, val, lin));
		//contador ++;
	}
	private String VDR(){
		Token c;
		c = cp;
		if(c.getTipo() == Token.DIG){
			if( c.getToken().contains(".") && c.getToken().contains("f"))
				return FTL();
			else if( c.getToken().contains("."))
				return DBL();
			else
				return IL();
		}else if(c.getTipo() == Token.VAL)
			return BL();
		else if( c.getTipo() == Token.STG)
			return STGL();
		else {
			error(sinValor,"");
			return null;
		}
	}
	private void E(){
		TE();
	}
	private void TE(){
		Token c = null;
		c = cp;
		String algote = null,op = null ,algote2 = null;
		if(c.getTipo() == Token.DIG){
			if( c.getToken().contains(".") && c.getToken().contains("f"))
				FTL();
			else if( c.getToken().contains("."))
				DBL();
			else
				IL();
		}else
			algote = ID();
		if( algote != null && !buscar(algote))
			ide.add(new Identificador(algote, "", null, lin));
		c = cp;
		if(c.getToken().matches("(>|<|>=|<=|==|!=)")){
			op = cp.getToken();
			Avanza();
		}else
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
			algote2 = ID();
		if( algote2 != null && !buscar(algote2))
			ide.add(new Identificador(algote2, "", null, lin));
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
			Acomodar(Token.SE, ";", "");
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
	private String T(){
		return TS();
	}
	private String TS(){
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
		case "int": 	Avanza(); return "int"; 
		case "boolean": Avanza(); return "boolean";
		case "String": 	Avanza(); return "String";
		case "double": 	Avanza(); return "double";
		case "float": 	Avanza(); return "float";
		default:
			error(Token.TIPO, "");
			return null;
		}
		
	}
	private String M(){
		Token c = null;
		c = cp;
		if(c.getToken().equals("public")){ 
			Avanza();
			return "public";
		}else if(c.getToken().equals("private")){ 
			Avanza();
			return "private";
		}else{
			error(Token.MOD, "");
			return null;
		}
	}
	private String IL(){
		String intV = null;
		if( Acomodar(Token.DIG, cp.getToken(),"") )
			intV = compo.get( idx - 1 ).getToken();
		return intV;
	}
	private String BL(){
		String booleanV = cp.getToken();
		Avanza();
		return booleanV;
	}
	private String STGL(){
		String stringV = cp.getToken();
		Avanza();
		return stringV;
	}
	private String DBL(){
		String doubleV = cp.getToken();
		Avanza();
		return doubleV; 
	}
	private String FTL(){
		String floatV = cp.getToken();
		Avanza();
		return floatV;
	}
	private String ID(){
		Token c = null,caux = null,cauxa=null,cauxaa=null;
		String men = "",cosa = null;
		c = cp;
		men = cp.getToken();
		if ( Acomodar(Token.ID,c.getToken(),"") ){
			cosa = compo.get(idx - 1 ).getToken();
			lin = (short) compo.get(idx - 1 ).getFila();
		}
		
		/*if( ideC){
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
		}*/
			
		men = cp.getToken();
		return cosa;
	}
	private void AE(){
		Token c;
		c = cp;
		String nom = null, val1 = null, op = null , val2 = null;
		nom = ID();
		short ind = 0;
		boolean caca = false;
		
		if( nom != null){
			if( !buscar(nom)){
				ide.add(new Identificador(nom, "", null, lin));
				ind = (short) (ide.size() - 1);
				caca = true;
			}
			
		}
		
		Acomodar(Token.SE,"=","");
		c = cp;
		if(c.getTipo() == Token.DIG){
			if( c.getToken().contains(".") && c.getToken().contains("f"))
				val1 = FTL();
			else if( c.getToken().contains("."))
				val1 = DBL();
			else
				val1 = IL();
		}else{
			val1 = ID();
			if( val1 != null)
				ide.add(new Identificador(val1, "", null, lin));
			
		}
		
		c = cp;
		if(c.getToken().matches("[\\+|[-]|/|\\*]")){
			op = cp.getToken();
			Avanza();
		}else
			error(Token.OPA, "arit");
		c = cp;
		if(c.getTipo() == Token.DIG){
			if( c.getToken().contains(".") && c.getToken().contains("f"))
				val2 = FTL();
			else if( c.getToken().contains("."))
				val2 = DBL();
			else
				val2 = IL();
		}else{
			val2 = ID();
			if( val2 != null)
				ide.add(new Identificador(val2, "", null, lin));
		}
		if( val1 != null && op != null && val2 != null)
			if( caca )
				ide.get(ind).setValor(val1+op+val2);
			else if( nom != null)
				update(nom, val1+op+val2);
		
		Acomodar(Token.SE,";","");
	}

	public ArrayList<Identificador> r(){
		return ide;
	}
	private void update(String tok,String val){
		for (Identificador token : ide) {
			if( token.getNombre().equals(tok)){
				token.setValor(val);
				return;
			}
		}
	}
	
	private boolean buscar(String tok){
		for (Identificador token : ide) {
			if( token.getNombre().equals(tok))
				return true;
		}
		return false;
	}
}
