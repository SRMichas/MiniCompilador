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
					salida += "\t1st - Error Semantico, Fila: "+ident.getFila()+" la variable \""+ident.getNombre()+"\" no esta declarada\n";
					/*if( ident.getExp() != null)
						ident.setValor(""+resultado2(ident));*/
					if( ident.getExp() != null)
						verificaExp(ident);
				break;
			case "int":
				exp = "(true|false|([0-9]+\\.[0-9]+f?)|(\".*\"))";
				if( revisaRepetida(ident) ){
					salida += "\tError Semantico, Fila: "+ident.getFila()+" la variable \""+ident.getNombre()+"\" ya esta declarada en la linea "
							+ind(ident.getNombre()).getFila()+"\n";
				}else{
					if( ident.getValor().matches(exp)){
						salida += "\tError Semantico, Fila: "+ident.getFila()+" \""+ident.getValor()+"\" no es un valor entero\n";
					}else{
						if( ident.getExp() != null)
							verificaExp(ident);
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
							verificaExp(ident);
					}
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
							verificaExp(ident);
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
	
	public boolean verificaExp(Identificador ide){
		boolean valido = true;
		ArrayList<Token> expre = ide.getExp();
		String valo;
		for (Token tok: expre) {
			if( tok.getTipo() == Token.ID ){
				if( revisaDeclarada(tok.getToken())){
					Identificador id = ind(tok.getToken());
					if( !id.getTipo().equals(ide.getTipo())){
						salida += "\tError Semantico, Fila: "+tok.getFila()+" la variable \""+tok.getToken()+"\" y \""+ide.getNombre()+"\" no son de los mismos tipos\n";
						valido = false;
					}
				}
			}else if( tok.getTipo() == Token.DIG ){
				valo = tok.getToken();
				if( valo.contains(".")){
					if( valo.contains("f") && !ide.getTipo().equals("float")){
						salida += "\tError Semantico, Fila: "+tok.getFila()+" el dato \""+tok.getToken()+"\" no es tipo \" "+ide.getTipo()+"\"\n";
						valido = false;
					}else if( valo.contains("f") && !ide.getTipo().equals("double")){
						salida += "\tError Semantico, Fila: "+tok.getFila()+" el dato \""+tok.getToken()+"\" no es tipo \""+ide.getTipo()+"\"\n";
						valido = false;
					}
				}else{
					if( !ide.getTipo().equals("int")){
						salida += "\tError Semantico, Fila: "+tok.getFila()+" el dato \""+tok.getToken()+"\" no es Entero\n";
						valido = false;
					}
				}
			}
		}
			/*if( tok.getTipo() == Token.ID){
				if( revisaDeclarada(tok.getToken())){
					Identificador id = ind(t.getToken());
					if( !id.getTipo().equals(val.getTipo())){
						salida += "\tError Semantico, Fila: "+t.getFila()+" la variable \""+t.getToken()+"\" y \""+val.getNombre()+"\" no son de los mismos tipos\n";
						valido = false;
						}
					}
				}
				--if(){
					if(){

					}
				}else if(){

				}--
			}else if( tok.getToken() == Token.DIG){
				valo = tok.getToken();
				if( valo.contains(".")){
					if( valo.contains("f") && !tok.getTipo().equals("float") )
						salida += "\tError Semantico, Fila: "+t.getFila()+" el dato \""+t.getToken()+"\" no es tipo \" "+val.getTipo()+"\"\n";
					else if( valo.contains("f") && !tok.getTipo.equals("double"))
						salida += "\tError Semantico, Fila: "+t.getFila()+" el dato \""+t.getToken()+"\" no es tipo \""+val.getTipo()+"\"\n";
				}else{
					if( !val.getTipo().equals("int") )
						salida += "\tError Semantico, Fila: "+t.getFila()+" el dato \""+t.getToken()+"\" no es Entero\n";
				}
			}
		}*/
		return valido;
	}
	private Identificador ind(String nom){
		for (Identificador identificador : ide) {
			if (identificador.getNombre().equals(nom))
				return identificador;
		}
		return new Identificador("falso", "dif", nom,-50,-10);
	}
}
