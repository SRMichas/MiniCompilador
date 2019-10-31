package main;

import java.util.ArrayList;

public class Parser {

	ArrayList<Token> compo;
	Token cp;
	String salida = "";
	public static String salida2 ="";
	private int idx = 0;
	//private int contador = 0,conIF = 0,conDV = 0;
	//private boolean /*imprime,avanza = false,*/ideC = false;
	private ArrayList<Identificador> ide;
	private final static int sinValor = 30,exceso = 31;
	private short lin;
	private boolean algo = false;
	public Parser(ArrayList<Token >c){
		compo = c;
		cp = compo.get(idx);
		ide = new ArrayList<>();
	}
	
	public String Sintactico(){
		salida2 = "";
		CD();
		return salida;
	}

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
			/*if(cp.getTipo() == Token.ID) ideC = true;
			else ideC = false;*/
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
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \"class\"\n";
				break;
			case "if":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \"if\"\n";
				break;
			case "while":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \"while\"\n";
				break;
			case "void":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \"void\"\n";
				break;
			case "static":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \"static\"\n";
				break;
			}
			break;
		case Token.SE:
			switch (to) {
			case "{":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\n";	
				break;
			case "}":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\n";	
				break;
			case "(":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\n";	
				break;
			case ")":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\n";	
				break;
			case ";":
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\n";	
				break;
			default:
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un Simbolo especial\n";
				break;
			}
			break;
		case Token.OPL:
			//if(to.equals("arit"))
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un operador logico\n";
			break;
		case Token.OPA:
			/*if( to != null || to.length()> 0)
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un operador aritmetico\n";
			else*/
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" el token \""+cp.getToken()+"\" no es un operador aritmetico\n";
			break;
		case Token.TIPO:
			salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \"int\" o \"boolean\"\n";
			break;
		case Token.MOD:
			salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un \"public\" o \"private\"\n";
			break;
		case Token.DIG:
			salida +="\tError Sintactico, Fila: "+cp.getFila()+" se espeba un digito\n";
			break;
		case Token.VAL:
			salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba \"true\" o \"false\"\n";
			break;
		case Token.ID:
				if((to.length() == 0 || to.length() != 0) && !cp.getToken().equals(to))
					salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba el identificador \""+to+"\"\n";
				else
					salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un identificador\n";
			break;
		case Token.ID_DIG:
			/*if( to != null && to.length()> 0)
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un identificador o un Digito\n";
			else*/
				salida +="\tError Sintactico, Fila: "+cp.getFila()+" el token \""+cp.getToken()+"\" no es un identificador o un Digito\n";
			break;
		case sinValor:
			salida +="\tError Sintactico, Fila: "+cp.getFila()+" se esperaba un valor numerico o booleano o una cadena\n";
			break;
		case exceso:
			salida += "\tError Sintactico, Fila: "+cp.getFila()+" \""+cp.getToken()+"\" no concuerda con la gramatica\n";
			break;
		default:
			salida += "\tError Sintactico, Fila: "+cp.getFila()+" "+to+"\n";
			break;
		}
		salida2 += "Token obtenido:"+cp.getToken()+"\n"+"Token Esperado: "+to+"\n-------------------------------------------\n";
		
	}
	
	private void CD(){
		Token c = cp,c2;
		String clss = null, id = null;
		if(!c.getToken().equals("class")){
			M();
		}
		c = cp;
		if( Acomodar(Token.PR,"class","") )
			clss = "class";
			
		c = cp;
		c2 = ID();
		id = c2.getToken();
		if( id != null )
			ide.add(new Identificador(id, "-", "-", c2.getFila(),"Global"));
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
				ide.add(new Identificador("main", "-", "", compo.get(idx-1).getFila(),"Global"));
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
		algo = false;
		if((c.getTipo() == Token.MOD || c.getTipo() == Token.TIPO) && caux.getTipo() != Token.PR){
			VDN();
			c = cp;
			Acomodar(Token.SE,";","public");
		}
		algo = true;
	}
	private void VDN(){
		//conDV++;
		Token c= null,c2;
		c = cp;
		String ty = null ,nom = null, val = null,alcance;
		if(c.getTipo() != Token.TIPO)
			M();
		ty = T();
		c2 = ID();
		if( c2 != null)
			nom = c2.getToken();
		c = cp;
		if(c.getToken().equals("=")){
			Avanza();
			val = VDR();
		}
		if( ty != null && nom != null && val == null){
			switch (ty) {
			case "int": 	val = "0"; break; 
			case "boolean": val = "false"; break;
			case "String": 	val = "\"\""; break;
			case "double": 	val = "0.0"; break;
			case "float": 	val = "0.0f"; break;
			}
			if( !algo )
				alcance = "Global";
			else
				alcance = "Local";
			ide.add(new Identificador(nom, ty, val, c2.getFila(),alcance));
		}else if( val != null){
			if( !algo )
				alcance = "Global";
			else
				alcance = "Local";
			ide.add(new Identificador(nom, ty, val, c2.getFila(),alcance));
		}
		//contador ++;
	}
	private String VDR(){
		Token c;
		c = cp;
		if(c.getTipo() == Token.DIG){
			if( c.getToken().contains(".") && c.getToken().contains("f"))
				return ((Token)FTL()).getToken();
			else if( c.getToken().contains("."))
				return ((Token)DBL()).getToken();
			else
				return ((Token)IL()).getToken();
		}else if(c.getTipo() == Token.VAL)
			return BL();
		else if( c.getTipo() == Token.STG)
			return ((Token)STGL()).getToken();
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
		String algote = null,algote2 = null;
		if(c.getTipo() == Token.DIG){
			if( c.getToken().contains(".") && c.getToken().contains("f"))
				FTL();
			else if( c.getToken().contains("."))
				DBL();
			else
				IL();
		}else if(c.getTipo() == Token.DIG){
			algote = ((Token)ID()).getToken();
			if( algote != null && !buscar(algote))
				ide.add(new Identificador(algote, "", "", lin/*,-1*/));
		}else{
			error(Token.ID_DIG, "Digito/Identificador");
			Acomodar(Token.ID_DIG, c.getToken(), "<");
		}
			
		
		c = cp;
		if(c.getToken().matches("(>|<|>=|<=|==|!=)")){
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
		}else if( c.getTipo() == Token.ID){
			algote2 = ((Token)ID()).getToken();
			if( algote2 != null && !buscar(algote2))
				ide.add(new Identificador(algote2, "", null, lin/*,-1*/));
		}else
			error(Token.ID_DIG, "Digito/Identificador");
			
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
			S();
		}else if( c.getTipo() == Token.ID){
			AE2();
			S();
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
	private void M(){
		Token c = null;
		c = cp;
		if(c.getToken().equals("public")){ 
			Avanza();
			//return "public";
		}else if(c.getToken().equals("private")){ 
			Avanza();
			//return "private";
		}else{
			error(Token.MOD, "");
			//return null;
		}
	}
	private Token IL(){
		Token intV = null;
		if( Acomodar(Token.DIG, cp.getToken(),"") ){
			intV = compo.get( idx - 1 );
			lin = (short)compo.get( idx - 1 ).getFila();
		}
		return intV;
	}
	private String BL(){
		String booleanV = cp.getToken();
		lin = (short)cp.getFila();
		Avanza();
		return booleanV;
	}
	private Token STGL(){
		Token stringV = cp;
		lin = (short)cp.getFila();
		Avanza();
		return stringV;
	}
	private Token DBL(){
		Token doubleV = cp;
		//lin = (short)cp.getFila();
		Avanza();
		return doubleV; 
	}
	private Token FTL(){
		//String floatV = cp.getToken();
		Token floatV = cp;
		//lin = (short)cp.getFila();
		Avanza();
		return floatV;
	}
	private Token ID(){
		Token c = null,cosa = null;
		c = cp;
		if ( Acomodar(Token.ID,c.getToken(),"") ){
			cosa = compo.get(idx - 1 );
			//lin = (short) compo.get(idx - 1 ).getFila();
		}
		return cosa;
	}
	private void AE(){
		Token c,res;
		c = cp;
		String nom = null, val1 = null, op = null , val2 = null;
		ArrayList<Token> exp = new ArrayList<>();
		res = ID();
		if( res != null)
			nom = res.getToken();
		short ind = 0;
		boolean caca = false,caca2 = false;
		ArrayList<Integer> pos = new ArrayList<>();
		
		if( nom != null){
			if( !buscar(nom)){
				ide.add(new Identificador(nom, "", "", res.getFila()/*,-1*/));
				ind = (short) (ide.size() - 1);
				caca = true;
			}
			
		}
		
		Acomodar(Token.SE,"=","");
		c = cp;
		if(c.getTipo() == Token.DIG){
			if( c.getToken().contains(".") && c.getToken().contains("f")){
				res = FTL();
				val1 = res.getToken();
			}else if( c.getToken().contains(".")){
				res = DBL();
				val1 = res.getToken();
			}else{
				res = IL();
				val1 = res.getToken();
			}
		}else if( c.getTipo() == Token.ID){
			res = ID();
			
			if( res != null)
				val1 = res.getToken();
			
			if( val1 != null )
				if( !buscar(val1))
					if( !nom.equals(val1) )
						ide.add(new Identificador(val1, "", "", res.getFila()/*,-1*/));
					else
						caca2 = true;
				else
					caca2 = true;
		}else
			error(Token.ID_DIG, "");
		
		pos.add((int)lin);
		exp.add(res);
		
		c = cp;
		if(c.getToken().matches("[\\+|[-]|/|\\*]")){
			op = cp.getToken();
			pos.add(cp.getFila());
			exp.add(c);
			Avanza();
		}else
			error(Token.OPA, "arit");
		
		c = cp;
		if(c.getTipo() == Token.DIG){
			if( c.getToken().contains(".") && c.getToken().contains("f")){
				res = FTL();
				val2 = res.getToken();	
			}else if( c.getToken().contains(".")){
				res = DBL();
				val2 = res.getToken();
			}else{
				res = IL();
				val2 = res.getToken();
			}
		}else if( c.getTipo() == Token.ID){
			res = ID();
			if( res != null)
				val2 = res.getToken();
			if( val2 != null)
				if( !buscar(val2))
					if( !nom.equals(val2) )
						ide.add(new Identificador(val2, "", "", res.getFila()/*,-1*/));
					else
						caca2 = true;
				else
					caca2 = true;
		}else
			error(Token.ID_DIG, "");
		
		pos.add((int)lin);
		exp.add(res);
		if( val1 != null && op != null && val2 != null)
			if( caca ){
				ide.get(ind).setExp(exp);
			}else if( nom != null)
				update(nom, val1+op+val2,caca2,exp);
		
		Acomodar(Token.SE,";","");
	}

	private void AE2(){
		Token c,res;
		int type = -1;
		c = cp;
		String nom = null, val1 = null, op = null , val2 = null;
		ArrayList<Token> exp = new ArrayList<>();
		res = ID();
		if( res != null)
			nom = res.getToken();
		short ind = 0;
		boolean caca = false,caca2 = false,simple = true;
		ArrayList<Integer> pos = new ArrayList<>();
		
		if( nom != null){
			if( !buscar(nom)){
				ide.add(new Identificador(nom, "", "", res.getFila()/*,-1*/));
				ind = (short) (ide.size() - 1);
				caca = true;
			}
			
		}
		
		Acomodar(Token.SE,"=","");
		c = cp;
		if(c.getTipo() == Token.DIG){
			if( c.getToken().contains(".") && c.getToken().contains("f")){
				res = FTL();
				val1 = res.getToken();
			}else if( c.getToken().contains(".")){
				res = DBL();
				val1 = res.getToken();
			}else{
				res = IL();
				val1 = res.getToken();
			}
		}else if( c.getTipo() == Token.ID){
			res = ID();
			
			if( res != null)
				val1 = res.getToken();
			
			if( val1 != null )
				if( !buscar(val1))
					if( !nom.equals(val1) )
						ide.add(new Identificador(val1, "", "", res.getFila()/*,-1*/));
					else
						caca2 = true;
				else
					caca2 = true;
		}else
			error(Token.ID_DIG, "DIG/ID");
		
		/*pos.add((int)lin);
		exp.add(res);*/
		exp.add(res);
		c = cp;
		type = c.getTipo();
		while( !c.getToken().equals(";") ){
			simple = false;
			if( c.getToken().matches("[\\+|[-]|/|\\*]")){
				op = cp.getToken();
				pos.add(cp.getFila());
				exp.add(c);
				Avanza();
			}else{
				error(Token.OPA, "arit");
				Avanza();
				break;
			}
			c = cp;
			type = c.getTipo();
			switch (type) {
			case Token.DIG:
				if( c.getToken().contains(".") && c.getToken().contains("f")){
					res = FTL();
					val2 = res.getToken();	
				}else if( c.getToken().contains(".")){
					res = DBL();
					val2 = res.getToken();
				}else{
					res = IL();
					val2 = res.getToken();
				}
				break;
			case Token.ID:
				res = ID();
				if( res != null)
					val2 = res.getToken();
				if( val2 != null)
					if( !buscar(val2))
						if( !nom.equals(val2) )
							ide.add(new Identificador(val2, "", "", res.getFila()/*,-1*/));
						else
							caca2 = true;
					else
						caca2 = true;
				break;
				default:
					error(Token.ID_DIG,"DIG/ID");
					Avanza();
					break;
			}
			exp.add(res);
			c = cp;
			type = c.getTipo();
		}
		c = cp;
		//update(nom, val1, true, exp);
		/*if( simple ){
			if( res.getTipo() == Token.DIG)*/
				
		//}
		/*pos.add((int)lin);
		exp.add(res);*/
		if( val1 != null && op != null && val2 != null){
			if( caca ){
				ide.get(ind).setExp(exp);
			}else if( nom != null)
				update(nom, val1+op+val2,caca2,exp);
		}
			
		
		Acomodar(Token.SE,";","");
	}
	
	public ArrayList<Identificador> r(){
		return ide;
	}
	private void update(String tok,String val,boolean algo,ArrayList<Token> e){
		for (Identificador token : ide) {
			if( token.getNombre().equals(tok)){
				if( e.size() != 1){
					if( !algo ){
						token.setExp(e);
					}else{
						token.setFaux(lin);
						token.setExp(e);
					}
				}else{
					Token t = e.get(0);
					if( t.getTipo() != Token.ID)
						token.setValor(val);
				}
				
				return;
			}
		}
	}
	private Object retTipo(String str){
		if( str.contains("f"))
			return Float.parseFloat(str);
		else if ( str.contains("."))
			return Double.parseDouble(str);
		else
			return Integer.parseInt(str);
	}
	
	private boolean buscar(String tok){
		for (Identificador token : ide) {
 			if( token.getNombre().equals(tok))
				return true;
		}
		return false;
	}
}
