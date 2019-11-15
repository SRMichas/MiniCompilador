package analysis;

import java.util.ArrayList;

import entities.Identifier;
import entities.Token;

public class Semantic {

	private ArrayList<Identifier> ide;
	private String output = "";

	public Semantic(ArrayList<Identifier> ar){
		ide = ar;
	}
	public String semanticAnalize(){
		semanticProcess();
		return output;
	}
	public void semanticProcess(){
		String exp = "";
		for (Identifier ident : ide) {
			
			switch (ident.getType()) {
			case "":
					output += "\t1st - Error Semantico, Fila: "+ident.getLine()+" la variable \""+ident.getName()+"\" no esta declarada\n";
					/*if( ident.getExp() != null)
						ident.setValor(""+resultado2(ident));*/
					if( ident.getExp() != null)
						checkExpression(ident);
				break;
			case "int":
				exp = "(true|false|([0-9]+\\.[0-9]+f?)|(\".*\"))";
				if( checkRepeated(ident) ){
					output += "\tError Semantico, Fila: "+ident.getLine()+" la variable \""+ident.getName()+"\" ya esta declarada en la linea "
							+findIndetifier(ident.getName()).getLine()+"\n";
				}else{
					if( ident.getValue().matches(exp)){
						output += "\tError Semantico, Fila: "+ident.getLine()+" \""+ident.getValue()+"\" no es un valor entero\n";
					}else{
						if( ident.getExp() != null)
							checkExpression(ident);
					}
				}
				break;
			case "double":
				exp = "(true|false|(\".*\"))";
				
				if( checkRepeated(ident) )
					output += "\tError Semantico, Fila: "+ident.getLine()+" la variable \""+ident.getName()+"\" ya esta declaradaen la linea "+
				findIndetifier(ident.getName()).getLine()+"\n";
				else
					if(ident.getValue().matches(exp) || ident.getValue().contains("f") || !ident.getValue().contains("."))
						output += "\tError Semantico, Fila: "+ident.getLine()+" \""+ident.getValue()+"\" no es un valor double\n";
					else{
						if( ident.getExp() != null)
							checkExpression(ident);
					}
				break;
			case "float":
				
				if( checkRepeated(ident) )
					output += "\tError Semantico, Fila: "+ident.getLine()+" la variable \""+ident.getName()+"\" ya esta declarada en la linea "+
				findIndetifier(ident.getName()).getLine()+"\n";
				else
					if( !ident.getValue().contains(".") && !ident.getValue().contains("f"))
						output += "\tError Semantico, Fila: "+ident.getLine()+" \""+ident.getValue()+"\" no es un valor flotante\n";
					else{
						if( ident.getExp() != null)
							checkExpression(ident);
					}
				break;
			case "boolean":
				
				if( checkRepeated(ident) )
					output += "\tError Semantico, Fila: "+ident.getLine()+" la variable \""+ident.getName()+"\" ya esta declaradaen la linea "+
				findIndetifier(ident.getName()).getLine()+"\n";
				else 
					if(!ident.getValue().matches("(false|true)")){ //tipo correcto de dato
						output += "\tError Semantico, Fila: "+ident.getLine()+" \""+ident.getValue()+"\" no es un valor booleano\n";
					}
				
				break;
			case "String":
				if(!ident.getValue().matches("\".*\"")){ //tipo correcto de dato
					output += "\tError Semantico, Fila: "+ident.getLine()+" \""+ident.getValue()+"\" no es una Cadena\n";
				}
				if( checkRepeated(ident) )
					output += "\tError Semantico, Fila: "+ident.getLine()+" la variable \""+ident.getName()+"\" ya esta declarada en la linea "+
				findIndetifier(ident.getName()).getLine()+"\n";
				break;
			}
		}
	}

	private boolean checkRepeated(Identifier id){
		int rep = 0;
		boolean cosa = false, cosa2 = false;
		//System.out.print("Indetificador["+id.getNombre()+"] ->");
		for (int i = ide.indexOf(id) - 1; i >= 0; i--) {
			//System.out.print(ide.get(i).getNombre()+" ");
			Identifier ident = ide.get(i);
			if( ident.getName().equals(id.getName())){
				rep++;
				if( ident.getType().equals(""))
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
		for (Identifier ident : ide) {
			if( ident.getName().equals(nom) && !ident.getValue().equals(""))
				//rep++;
				return true;
		}
		//if( rep > 1) return true;
		return false;
	}
	
	public boolean checkExpression(Identifier ide){
		boolean valido = true;
		ArrayList<Token> expre = ide.getExp();
		String valo;
		for (Token tok: expre) {
			if( tok.getType() == Token.ID ){
				if( revisaDeclarada(tok.getToken())){
					Identifier id = findIndetifier(tok.getToken());
					if( !id.getType().equals(ide.getType())){
						output += "\tError Semantico, Fila: "+tok.getLine()+" la variable \""+tok.getToken()+"\" y \""+ide.getName()+"\" no son de los mismos tipos\n";
						valido = false;
					}
				}
			}else if( tok.getType() == Token.DIG ){
				valo = tok.getToken();
				if( valo.contains(".")){
					if( valo.contains("f") && !ide.getType().equals("float")){
						output += "\tError Semantico, Fila: "+tok.getLine()+" el dato \""+tok.getToken()+"\" no es tipo \" "+ide.getType()+"\"\n";
						valido = false;
					}/*else if( valo.contains("f") && !ide.getTipo().equals("double")){
						salida += "\tError Semantico, Fila: "+tok.getFila()+" el dato \""+tok.getToken()+"\" no es tipo \""+ide.getTipo()+"\"\n";
						valido = false;
					}*/
					/*if( valo.contains("f")){
						if( !ide.getTipo().equals("float")){
							
						}
					}*/
				}else{
					if( !ide.getType().equals("int")){
						output += "\tError Semantico, Fila: "+tok.getLine()+" el dato \""+tok.getToken()+"\" no es "+ide.getType()+"\n";
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
	private Identifier findIndetifier(String nom){
		for (Identifier identificador : ide) {
			if (identificador.getName().equals(nom))
				return identificador;
		}
		return new Identifier("false", "dif", nom,-50,-10);
	}
}
