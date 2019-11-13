package main;

import java.util.ArrayList;

public class ArbolExpresion {
	/*
		1- Get the first item and initialise the tree with it.
		2- Currently the root node is also the current node. The current node is the node we currently lie on.
		3- Get the next item. This will be called the new item.

		4- Climb up the tree as long as the precedence of the current node item is greater than or equal to 
		that of the new item.When this is over, the current node item has a precedence strictly 
		less than that of the new item.
		
		5- Create a new node for the new item. Then set the left child of the new node to be the old 
		right child of the current node. Finally set the new right child of the current node to be 
		the new node (also set the parent of the new node).

		6- Set the current node to be the new node.
		
		7- Repeat steps 3, 4, 5 and 6 till there is no item left.
	*/
	private Nodo<Token> root,actual;
	private boolean bandera = false;
	private int contador = 0;
	public String result = "", temp = "";
	private Identificador token;
	private ArrayList<Identificador> simbolos;

	public ArbolExpresion(){}

	public ArbolExpresion(Identificador t,ArrayList<Identificador> simb){
		token = t; simbolos = simb;
	}
	
	public void añadir(Token t){
		actual = insertar(actual,t);
		bandera = false;
	}
	
	public Nodo<Token> insertar(Nodo<Token> current,Token t){
		Nodo<Token> nuevo, aux;
		if( current == null){
			nuevo = new Nodo<Token>(t);
			if( root != null){
				nuevo.izq = root;
				root.padre = nuevo;
			}
			root = nuevo;
			
			return nuevo;
		}
		nuevo = new Nodo<Token>(t);
		int pc = prioridad(current), pnv = prioridad(nuevo);
		if( pc >= pnv){
			aux = insertar(current.padre,t);
			if( bandera ){
				current.padre = aux;
				aux.izq = current;
			}
			
			current = aux;
			bandera=false; 
		}else{
			nuevo.padre = current;
			current.der = nuevo;
			current = nuevo;
			bandera = true;
		}
		
		return current;
	}

	private int prioridad(Nodo<Token> n){
		int val = -1;
		Token t = (Token) n.dato;
		switch (t.getTipo()) {
		case Token.DIG: 
			case Token.ID: val = 3; break;
		case Token.OPA:
			if( t.getToken().matches("(\\*|/)") ){
				//System.out.println("Entro IF con -> "+t.getToken());
				val = 2;
			}else{
				//System.out.println("Entro ELSE con -> "+t.getToken());
				val = 1;
			}
			/*switch (t.getToken()) {
			case "*": case "/":
				val = 2;
				break;
			case "+": case "-":
				val = 1;
				break;
			}*/
			break;
		}
		return val;
	}
	private boolean asignacion(){
		if( root.izq == null && root.der == null )
			return true;
		return false;
	}
	public String generaCuadruplo(Nodo<Token> node) {
	     if (node != null) {
	    	 String v1,v2;
	    	 v1 = generaCuadruplo(node.izq);
	         v2 = generaCuadruplo(node.der);
	         if( node.dato.getTipo() == Token.OPA){
	        	 int espacios = 8;
	        	 contador++;
	        	 if( node.padre == null){
	        		 result += String.format("%5s %-"+(espacios+2)+"s %-"+(espacios+2)
	        				 +"s %-"+(espacios+4)+"s %-"+(espacios+4)+"s %n","",node.dato.getToken(),v1,v2,token.getNombre());
	        	 }else
	        		 //result += String.format("%"+espacios+"s %s %4s %4s %4s %n", "T"+contador,":=",v1,node.dato.getToken(),v2);
	        	 	result += String.format("%5s %-"+(espacios+2)+"s %-"+(espacios+2)+"s %-"
	        		 +(espacios+4)+"s %-"+(espacios+4)+"s %n","",node.dato.getToken(),v1,v2,"T"+contador);
	        	 return "T"+(contador);
	         }else{
	        	 if( asignacion() )
	        		 result += String.format("%8s %s %4s %n %n", "T1",":=",node.dato.getToken());
	        	 return node.dato.getToken();
	         }
	         
	         
	     }
	     return "";
	 }
	
	 public String resuelve(){
	 	String vuelto = "";
		 switch(token.getTipo()){
		 	case "int":
				vuelto = String.valueOf((int)resuelveGen(root));
				//generaCuadruplo(root);
				//result += String.format("%8s %s %4s %n",token.getNombre(),":=",vuelto);
				break;
		 	case "double":
				vuelto = String.valueOf(resuelveGen(root));
				//result += token.getNombre()+" := "+vuelto+"\n";
				break;
		 	case "float":
				vuelto = String.valueOf(resuelveGen(root))+"f";
				//result += token.getNombre()+" := "+vuelto+"\n";
				break;
		 }
		 	generaCuadruplo(root);
			result += String.format("%8s %s %4s %n",token.getNombre(),":=",vuelto);
		 return vuelto;
	 }
	 /*public int resuelveInt(Nodo<Token> node) {
	     if (node != null) {
	    	 int v1,v2;
	    	 v1 = resuelveInt(node.izq);
	         v2 = resuelveInt(node.der);
	         if( node.dato.getTipo() == Token.OPA){
	         	char c = node.dato.getToken().charAt(0);
	         	int res = 0;
	        	 switch(c){
	        	 	case '+': res = v1 + v2; break;
	        	 	case '-': res = v1 - v2; break;
	        	 	case '*': res = v1 * v2; break;
	        	 	case '/': res = v1 / v2; break;
	        	 }
	        	 return res;
	         }else if( node.dato.getTipo() == Token.ID)
	        	 return Integer.parseInt(retValor(node.dato.getToken()));
	         else
	        	 return Integer.parseInt(node.dato.getToken());
	         
	     }
	     return 0;
	 }*/
	 public double resuelveGen(Nodo<Token> node) {
	     if (node != null) {
	    	 double v1,v2;
	    	 v1 = resuelveGen(node.izq);
	         v2 = resuelveGen(node.der);
	         if( node.dato.getTipo() == Token.OPA){
	         	char c = node.dato.getToken().charAt(0);
	         	double res = 0;
	        	 switch(c){
	        	 	case '+': res = v1 + v2; break;
	        	 	case '-': res = v1 - v2; break;
	        	 	case '*': res = v1 * v2; break;
	        	 	case '/': res = v1 / v2; break;
	        	 }
	        	 //contador++;
	        	 //result += "T"+contador+" := "+v1+"  "+c+"  "+v2+"\n";
	        	 return res;
	         }else if( node.dato.getTipo() == Token.ID)
	        	 return Double.parseDouble(retValor(node.dato.getToken()));
	         else
	        	 return Double.parseDouble(node.dato.getToken());
	         
	     }
	     return 0;
	 }
	 /*public float resuelveFloat(Nodo<Token> node) {
	     if (node != null) {
	    	 float v1,v2;
	    	 v1 = resuelveFloat(node.izq);
	         v2 = resuelveFloat(node.der);
	         if( node.dato.getTipo() == Token.OPA){
	         	char c = node.dato.getToken().charAt(0);
	         	float res = 0;
	        	 switch(c){
	        	 	case '+': res = v1 + v2; break;
	        	 	case '-': res = v1 - v2; break;
	        	 	case '*': res = v1 * v2; break;
	        	 	case '/': res = v1 / v2; break;
	        	 }
	        	 contador++;
	        	 result += "T"+contador+" := "+v1+"  "+c+"  "+v2+"\n";
	        	 return res;
	         }else if( node.dato.getTipo() == Token.ID)
	        	 return Float.parseFloat(retValor(node.dato.getToken()));
	         else
	        	 return Float.parseFloat(node.dato.getToken());
	         
	     }
	     return 0;
	 }*/
	 private String retValor(String id){
		 for (Identificador identificador : simbolos) {
			if( identificador.getNombre().equals(id) )
				return identificador.getValor();
		}
		 return null;
	 }
	class Nodo<Token>{
		Token dato;
		Nodo<Token> padre,der,izq;
		
		public Nodo(Token val){ dato = val; }
	}
}
