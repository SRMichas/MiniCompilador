package main;

import java.util.ArrayList;

public class Semantic {

	private ArrayList<Identificador> ide;
	private String salida = "";

	public Semantic(ArrayList<Identificador> ar){
		ide = ar;
	}
	public String Semantico(){
		algo();
		return salida;
	}
	public void algo(){
		String exp = "";
		for (Identificador ident : ide) {
			
			switch (ident.getTipo()) {
			case "":
				//if( !revisaDeclarada(ident.getNombre()))
					salida += "\t1st - Error Semantico, Fila: "+ident.getFila()+" la variable \""+ident.getNombre()+"\" no esta declarada\n";
				if( ident.getExp() != null)
					ident.setValor(""+resultado2(ident));
				break;
			case "int":
				exp = "(true|false|([0-9]+\\.[0-9]+f?)|(\".*\"))";
				if( revisaRepetida(ident) ){
					salida += "\tError Semantico, Fila: "+ident.getFila()+" la variable \""+ident.getNombre()+"\" ya esta declarada en la linea "+
				ind(ident.getNombre()).getFila()+"\n";
				}else{
					if( ident.getValor().matches(exp)){
						salida += "\tError Semantico, Fila: "+ident.getFila()+" \""+ident.getValor()+"\" no es un valor entero\n";
					}else{
						if( ident.getExp() != null)
							ident.setValor(""+resultado2(ident));
					}
				}
				break;
			case "double":
				exp = "(true|false|(\".*\"))";
				
				if( revisaRepetida(ident) )
					salida += "\tError Semantico, Fila: "+ident.getFila()+" la variable \""+ident.getNombre()+"\" ya esta declaradaen la linea "+
				ind(ident.getNombre()).getFila()+"\n";
				else
					if(ident.getValor().matches(exp) || ident.getValor().contains("f") || !ident.getValor().contains("."))
						salida += "\tError Semantico, Fila: "+ident.getFila()+" \""+ident.getValor()+"\" no es un valor double\n";
					else{
						if( ident.getExp() != null)
							ident.setValor(""+resultado2(ident));
					}
				break;
			case "boolean":
				
				if( revisaRepetida(ident) )
					salida += "\tError Semantico, Fila: "+ident.getFila()+" la variable \""+ident.getNombre()+"\" ya esta declaradaen la linea "+
				ind(ident.getNombre()).getFila()+"\n";
				else 
					if(!ident.getValor().matches("(false|true)")){ //tipo correcto de dato
						salida += "\tError Semantico, Fila: "+ident.getFila()+" \""+ident.getValor()+"\" no es un valor booleano\n";
					}
				
				break;
			case "String":
				if(!ident.getValor().matches("\".*\"")){ //tipo correcto de dato
					salida += "\tError Semantico, Fila: "+ident.getFila()+" \""+ident.getValor()+"\" no es una Cadena\n";
				}
				if( revisaRepetida(ident) )
					salida += "\tError Semantico, Fila: "+ident.getFila()+" la variable \""+ident.getNombre()+"\" ya esta declarada en la linea "+
				ind(ident.getNombre()).getFila()+"\n";
				break;
			case "float":
				
				if( revisaRepetida(ident) )
					salida += "\tError Semantico, Fila: "+ident.getFila()+" la variable \""+ident.getNombre()+"\" ya esta declarada en la linea "+
				ind(ident.getNombre()).getFila()+"\n";
				else
					if( !ident.getValor().contains(".") && !ident.getValor().contains("f"))
						salida += "\tError Semantico, Fila: "+ident.getFila()+" \""+ident.getValor()+"\" no es un valor flotante\n";
					else{
						if( ident.getExp() != null)
							ident.setValor(""+resultado2(ident));
					}
				break;
			}
		}
	}

	private boolean revisaRepetida(Identificador id){
		int rep = 0;
		boolean cosa = false, cosa2 = false;
		//System.out.print("Indetificador["+id.getNombre()+"] ->");
		for (int i = ide.indexOf(id) - 1; i >= 0; i--) {
			//System.out.print(ide.get(i).getNombre()+" ");
			Identificador ident = ide.get(i);
			if( ident.getNombre().equals(id.getNombre())){
				rep++;
				if( ident.getTipo().equals(""))
					cosa2 = true;
				if(rep > 0)
					cosa = true;
			}
		}
		//System.out.println();
		if( cosa == cosa2)
			cosa = false;
		return cosa;
	}
	
	private boolean revisaDeclarada(String nom){
		//int rep = 0;
		for (Identificador ident : ide) {
			if( ident.getNombre().equals(nom) && !ident.getValor().equals(""))
				//rep++;
				return true;
		}
		//if( rep > 1) return true;
		return false;
	}
	
	private Object resultado2(Identificador val){
		Object res = null;
		String op = "",valo = "";
		ArrayList<Token> arrcaca = val.getExp();
		Object aux1 = null, aux2 = null;
		short vel = 1,ty1 = 0, ty2 = 0;
		for (Token t: arrcaca) {
			if( t.getTipo() == Token.ID ){
				if( revisaDeclarada(t.getToken()) )
					//salida += "\tError Semantico, Fila: "+t.getFila()+" la variable \""+t.getToken()+"\" no ESTA declarada\n";
				{
					Identificador id = ind(t.getToken());
					//String pre = id.getAux().get(id.getAux().size() - 1);
					
					
 					if( !id.getTipo().equals(val.getTipo()))
						//salida += "\tError Semantico, Fila: "+t.getFila()+" la variable \""+t.getToken()+"\" no ESTA declarada\n";
						salida += "\tError Semantico, Fila: "+t.getFila()+" la variable \""+t.getToken()+"\" y \""+val.getNombre()+"\" no son de los mismos tipos\n";
					else{
						valo = id.getValor();
						
						switch (id.getTipo()) {
						case "float":
							if( vel == 1){
								aux1 = Float.parseFloat(valo);
								ty1 = 1;
							}else if( vel == 3){
								aux2 = Float.parseFloat(valo);
								ty2 = 1;
							}
							break;
						case "double":
							if( vel == 1 ){
								aux1 = Double.parseDouble(valo);
								ty1 = 2;
							}else if( vel == 3){
								aux2 = Double.parseDouble(valo);
								ty2 = 2;
							}
							break;
						case "int":
							if( vel == 1){
								aux1 = Integer.parseInt(valo);
								ty1 = 3;
							}else if( vel == 3){
								aux2 = Integer.parseInt(valo);
								ty2 = 3;
							}
							break;
						}
					}	
				}
			}else if(t.getTipo() == Token.DIG){
				valo = t.getToken();
				if(valo.contains(".")){
					if( valo.contains("f")){
						if( val.getTipo().equals("float")){
							if( vel == 1){
								aux1 = Float.parseFloat(valo);
								ty1 = 1;
							}else if( vel == 3 ){
								aux2 = Float.parseFloat(valo);
								ty2 = 1;
							}
						}else
							salida += "\tError Semantico, Fila: "+t.getFila()+" el dato \""+t.getToken()+"\" no es tipo \" "+val.getTipo()+"\"\n";
					}else{
						if( val.getTipo().equals("double")){
							if( vel == 1){
								aux1 = Double.parseDouble(valo);
								ty1 = 2;
							}else if( vel == 3 ){
								aux2 = Double.parseDouble(valo);
								ty2 = 2;
							}
						}else
							salida += "\tError Semantico, Fila: "+t.getFila()+" el dato \""+t.getToken()+"\" no es tipo \""+val.getTipo()+"\"\n";
					}
				}else{
					if( val.getTipo().equals("int")){
						if( vel == 1){
							aux1 = Integer.parseInt(valo);
							ty1 = 3;
						}else if( vel == 3 ){
							aux2 = Integer.parseInt(valo);
							ty2 = 3;
						}
					}else
						salida += "\tError Semantico, Fila: "+t.getFila()+" el dato \""+t.getToken()+"\" no es Entero\n";
				}	
			}else if(t.getTipo() == Token.OPA){
				op = t.getToken();
			}
			
			valo = "";
			vel++;
		}
		if( ty1 == ty2 ){
			switch (ty1) {
			case 1:
				switch (op) {
				case "+": res = (float)aux1 + (float)aux2;
					break;
				case "-": res = (float)aux1 - (float)aux2;			
					break;
				case "*": res = (float)aux1 * (float)aux2;
					break;
				case "/": res = (float)aux1 / (float)aux2;
					break;
				}
				break;
			case 2:
				switch (op) {
				case "+": res = (double)aux1 + (double)aux2;
					break;
				case "-": res = (double)aux1 - (double)aux2;			
					break;
				case "*": res = (double)aux1 * (double)aux2;
					break;
				case "/": res = (double)aux1 / (double)aux2;
					break;
				}
				break;
			case 3:
				switch (op) {
				case "+": res = (int)aux1 + (int)aux2;
					break;
				case "-": res = (int)aux1 - (int)aux2;			
					break;
				case "*": res = (int)aux1 * (int)aux2;
					break;
				case "/": res = (int)aux1 / (int)aux2;
					break;
				}
				break;
			}
		}
		return res;
	}
	private Identificador ind(String nom){
		for (Identificador identificador : ide) {
			if (identificador.getNombre().equals(nom))
				return identificador;
		}
		return new Identificador("falso", "dif", nom,-50,-10);
	}
}
