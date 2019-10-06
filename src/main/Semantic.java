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
			case "int":
				exp = "(true|false|([0-9]+\\.[0-9]+f?)|(\".*\"))";
				if( ident.getValor().matches(exp)){
					salida += "\tError Semantico, Fila: "+ident.getFila()+" \""+ident.getValor()+"\" no es un valor entero\n";
				}else{
					
				}
				
				if( revisaRepetida(ident) )
					salida += "\tError Semantico, Fila: "+ident.getFila()+" la variable \""+ident.getNombre()+"\" ya esta declarada\n";
				
				break;
			case "double":
				exp = "(true|false|(\".*\"))";
				if(ident.getValor().matches(exp) || ident.getValor().contains("f"))
					salida += "\tError Semantico, Fila: "+ident.getFila()+" \""+ident.getValor()+"\" no es un valor double\n";
				
				if( revisaRepetida(ident) )
					salida += "\tError Semantico, Fila: "+ident.getFila()+" la variable \""+ident.getNombre()+"\" ya esta declarada\n";
				break;
			case "boolean":
				if(!ident.getValor().matches("(false|true)")){ //tipo correcto de dato
					salida += "\tError Semantico, Fila: "+ident.getFila()+" \""+ident.getValor()+"\" no es un valor booleano\n";
				}
				if( revisaRepetida(ident) )
					salida += "\tError Semantico, Fila: "+ident.getFila()+" la variable \""+ident.getNombre()+"\" ya esta declarada\n";
				break;
			case "String":
				if(!ident.getValor().matches("\".*\"")){ //tipo correcto de dato
					salida += "\tError Semantico, Fila: "+ident.getFila()+" \""+ident.getValor()+"\" no es una Cadena\n";
				}
				if( revisaRepetida(ident) )
					salida += "\tError Semantico, Fila: "+ident.getFila()+" la variable \""+ident.getNombre()+"\" ya esta declarada\n";
				break;
			case "float":
				if( !ident.getValor().contains(".") && !ident.getValor().contains("f"))
					salida += "\tError Semantico, Fila: "+ident.getFila()+" \""+ident.getValor()+"\" no es un valor flotante\n";
				if( revisaRepetida(ident) )
					salida += "\tError Semantico, Fila: "+ident.getFila()+" la variable \""+ident.getNombre()+"\" ya esta declarada\n";
				break;
			case "":
				if( !revisaDeclarada(ident.getNombre()))
					salida += "\tError Semantico, Fila: "+ident.getFila()+" la variable\""+ident.getNombre()+"\" no esta declarada\n";
				break;
			}
		}
	}


	private boolean revisaRepetida(Identificador id){
		int rep = 0;
		boolean cosa = false;
		System.out.print("Indetificador["+id.getNombre()+"] ->");
		for (int i = ide.indexOf(id) - 1; i >= 0; i--) {
			System.out.print(ide.get(i).getNombre()+" ");
			Identificador ident = ide.get(i);
			if( ident.getNombre().equals(id.getNombre())){
				rep++;
				if(rep > 0)
					cosa = true;
			}
		}
		System.out.println();
		return cosa;
	}
	
	private boolean revisaDeclarada(String nom){
		int rep = 0;
		for (Identificador ident : ide) {
			if( ident.getNombre().equals(nom))
				rep++;
		}
		if( rep > 1) return true;
		return false;
	}
}
