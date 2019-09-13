package main;

import java.text.ParseException;
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
		for (Identificador ident : ide) {
			switch (ident.getTipo()) {
			case "int":
				String exp = "(true|false|([0-9]+\\.[0-9]+f?)|(\".*\"))";
				if( ident.getValor().matches(exp)){
					salida += "\tError Semantico, Fila: "+ident.getFila()+" \""+ident.getValor()+"\" no es un valor entero\n";
				}else{
					try {	//verifica si el valor es un digito
						Integer.parseInt(ident.getValor());
					} catch (Exception e) {
						// verifica si el valor es un identificador
						

					}
				}
				break;
			case "double":
				
				break;
			case "boolean":
				if(!ident.getValor().matches("(false|true)")){ //tipo correcto de dato
					//
					salida += "\tError Semantico, Fila: "+ident.getFila()+" \""+ident.getValor()+"\" no es un valor booleano\n";
				}
				break;
			case "String":
				if(!ident.getValor().matches("\".*\"")){ //tipo correcto de dato
					salida += "\tError Semantico, Fila: "+ident.getFila()+" \""+ident.getValor()+"\" no es una Cadena\n";
				}
				break;
			case "float":
				
				break;

			default:
				break;
			}
		}
	}
	private void checa(String exp){
		for (int i = 0; i < exp.length() ; i++) {
			char car = exp.charAt(i);
		}
	}
	private boolean busca(String nom){
		for (Identificador ident : ide) {
			if( ident.getNombre().equals(nom))
				return true;
		}
		return false;
	}
}
