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
	Nodo<Token> root,curre;
	private boolean algo = false;
	int cosa = 1;
	private short variable;
	public String result = "";
	private Identificador token;
	private ArrayList<Identificador> simbolos;

	public ArbolExpresion(){}
	
	public ArbolExpresion( short var) {
		root = null;
		variable = var;
	}
	public ArbolExpresion(Identificador t){
		token = t;
		setTipo(token.getTipo());
	}
	public ArbolExpresion(Identificador t,ArrayList<Identificador> simb){
		token = t;
		simbolos = simb;
		setTipo(token.getTipo());
	}
	public void inicia(Token t){
		root = new Nodo<Token>(t);
		curre = root;
	}
	public void setTipo(String type){
		switch(type){
			case "int": 	variable = 1; break;
			case "double": 	variable = 2; break;
			case "float":	variable = 3; break;
		}
	}
	public void reset(){
		root = null;
	}
	public void add(Token t){
		curre = add2(curre,t);
		/*System.out.print("Actual -> "+curre.dato.getToken()+ "\t");
		recorreInOrden(root);
		System.out.println();*/
		algo = false;
	}
	public Nodo<Token> add(Nodo<Token> curr,Token t){
		Nodo<Token> nv;
		if( curr == null){
			nv = new Nodo<Token>(t);
			curr = nv;
			curre = curr;
			System.out.println("Se creo -> "+curr.dato.getToken());
			return curr;
		}
		nv = new Nodo<Token>(t);
		int pc = priori(curr), pnv = priori(nv);
		if( pc >= pnv){
			//Escalar el arbol
			Nodo<Token> aux = add(curr.padre,t),aux2 = curr;
			aux.izq = aux2;
			aux2.padre = aux;
			curr = aux;
			curre = curr;
			//nv = aux2;
		}else{
			curr.der = nv;
			nv.padre = curr;
			curre = curr;
			
		}
		/*if( p2 >= p ){
			if( curr.izq == null){
				Nodo<Token> aux = curr;
				curr = n;
				curr.izq = aux;
				//root = curr;
			}else{
				
			}	
		}else{
			if( curr.izq == null){
				curr.izq = n;
				//root = curr;
			}else{
				curr.der = n;
				//root = curr;
			}
		}*/
		
		return curr;
	}
	public Nodo<Token> add2(Nodo<Token> curr,Token t){
		Nodo<Token> nv, aux;
		if( curr == null){
			nv = new Nodo<Token>(t);
			//System.out.println("\t\t\tRaiz -> "+nv.dato.getToken());
			if( root != null){
				nv.izq = root;
				root.padre = nv;
			}
			root = nv;
			
			return nv;
		}
		nv = new Nodo<Token>(t);
		int pc = priori(curr), pnv = priori(nv);
		if( pc >= pnv){
			aux = add2(curr.padre,t);
			if( algo ){
				curr.padre = aux;
				aux.izq = curr;
			}
			
			curr = aux;
			algo=false; 
		}else{
			nv.padre = curr;
			curr.der = nv;
			curr = nv;
			algo = true;
		}
		
		return curr;
	}

	private int priori(Nodo<Token> n){
		int val = -1;
		Token t = (Token) n.dato;
		switch (t.getTipo()) {
		case Token.DIG: case Token.ID:
			val = 3;
			break;
		case Token.OPA:
			switch (t.getToken()) {
			case "*": case "/":
				val = 2;
				break;
			case "+": case "-":
				val = 1;
				break;
			}
			break;
		}
		return val;
	}
	 public void recorreInOrden(Nodo<Token> node) {
	     if (node != null) {
	      recorreInOrden(node.izq);
	         visit(node.dato.getToken());
	         recorreInOrden(node.der);
	     }
	 }
	 public void recorrePosOrden(Nodo<Token> node) {
	     if (node != null) {
	    	 recorrePosOrden(node.izq);
	         recorrePosOrden(node.der);
	         visit(node.dato.getToken());	
	     }
	 }
	 public String generaTriplos(Nodo<Token> node) {
	     if (node != null) {
	    	 String v1,v2;
	    	 v1 = generaTriplos(node.izq);
	         v2 = generaTriplos(node.der);
	         if( node.dato.getTipo() == Token.OPA){
	        	 //visit(node.dato.getToken());
	        	 //System.out.println("T"+cosa+":= "+v1+node.dato.getToken()+v2);
	        	 //result += "\tT"+cosa+" := "+v1+"  "+node.dato.getToken()+"  "+v2+"\n";
	        	 result += String.format("%8s %s %4s %4s %4s %n %n", "T"+cosa,":=",v1,node.dato.getToken(),v2);
	        	 //System.out.printf("%15s %s %5s %5s %5s %n%n","T"+cosa," := ",v1,node.dato.getToken(),v2);
	        	 cosa++;
	        	 return "T"+(cosa-1);
	         }
	         return node.dato.getToken();
	         
	     }
	     return "";
	 }
	 public String resuelve(){
	 	String vuelto = "";
		 switch(variable){
		 	case 1:
				vuelto = String.valueOf(resuelveInt(root));
				generaTriplos(root);
				//result += "\t"+token.getNombre()+" := "+vuelto+"\n";
				result += String.format("%8s %s %4s %n",token.getNombre(),":=",vuelto);
				break;
		 	case 2:
				vuelto = String.valueOf(resuelveDou(root));
				result += token.getNombre()+" := "+vuelto+"\n";
				break;
		 	case 3:
				vuelto = String.valueOf(resuelveFloat(root))+"f";
				result += token.getNombre()+" := "+vuelto+"\n";
				break;
		 }
		 return vuelto;
	 }
	 public int resuelveInt(Nodo<Token> node) {
	     if (node != null) {
	    	 int v1,v2;
	    	 v1 = resuelveInt(node.izq);
	         v2 = resuelveInt(node.der);
	         if( node.dato.getTipo() == Token.OPA){
	        	 //visit(node.dato.getToken());
	        	 //System.out.println("T"+cosa+":= "+v1+node.dato.getToken()+v2);
	         	char c = node.dato.getToken().charAt(0);
	         	int res = 0;
	        	 switch(c){
	        	 	case '+': res = v1 + v2; break;
	        	 	case '-': res = v1 - v2; break;
	        	 	case '*': res = v1 * v2; break;
	        	 	case '/': res = v1 / v2; break;
	        	 }
	        	 //System.out.printf("%15s %s %5s %5s %5s %n%n","T"+cosa,":=",v1,node.dato.getToken(),v2);
	        	 /*cosa++;
	        	 if( cosa != 1)
	        		 result += "T"+cosa+" := T"+(cosa-1)+"  "+c+"  "+v2+"\n";
	        	 else
	        		 result += "T"+cosa+" := "+v1+"  "+c+"  "+v2+"\n";*/
	        	 return res;
	         }else if( node.dato.getTipo() == Token.ID)
	        	 return Integer.parseInt(retValor(node.dato.getToken()));
	         else
	        	 return Integer.parseInt(node.dato.getToken());
	         
	     }
	     return 0;
	 }
	 public double resuelveDou(Nodo<Token> node) {
	     if (node != null) {
	    	 double v1,v2;
	    	 v1 = resuelveDou(node.izq);
	         v2 = resuelveDou(node.der);
	         if( node.dato.getTipo() == Token.OPA){
	        	 //visit(node.dato.getToken());
	        	 //System.out.println("T"+cosa+":= "+v1+node.dato.getToken()+v2);
	         	char c = node.dato.getToken().charAt(0);
	         	double res = 0;
	        	 switch(c){
	        	 	case '+': res = v1 + v2; break;
	        	 	case '-': res = v1 - v2; break;
	        	 	case '*': res = v1 * v2; break;
	        	 	case '/': res = v1 / v2; break;
	        	 }
	        	 //System.out.printf("%15s %s %5s %5s %5s %n%n","T"+cosa,":=",v1,node.dato.getToken(),v2);
	        	 //cosa++;
	        	 cosa++;
	        	 result += "T"+cosa+" := "+v1+"  "+c+"  "+v2+"\n";
	        	 return res;
	         }else if( node.dato.getTipo() == Token.ID)
	        	 return Double.parseDouble(retValor(node.dato.getToken()));
	         else
	        	 return Double.parseDouble(node.dato.getToken());
	         
	     }
	     return 0;
	 }
	 public float resuelveFloat(Nodo<Token> node) {
	     if (node != null) {
	    	 float v1,v2;
	    	 v1 = resuelveFloat(node.izq);
	         v2 = resuelveFloat(node.der);
	         if( node.dato.getTipo() == Token.OPA){
	        	 //visit(node.dato.getToken());
	        	 //System.out.println("T"+cosa+":= "+v1+node.dato.getToken()+v2);
	         	char c = node.dato.getToken().charAt(0);
	         	float res = 0;
	        	 switch(c){
	        	 	case '+': res = v1 + v2; break;
	        	 	case '-': res = v1 - v2; break;
	        	 	case '*': res = v1 * v2; break;
	        	 	case '/': res = v1 / v2; break;
	        	 }
	        	 //System.out.printf("%15s %s %5s %5s %5s %n%n","T"+cosa,":=",v1,node.dato.getToken(),v2);
	        	 //cosa++;
	        	 cosa++;
	        	 result += "T"+cosa+" := "+v1+"  "+c+"  "+v2+"\n";
	        	 return res;
	         }else if( node.dato.getTipo() == Token.ID)
	        	 return Float.parseFloat(retValor(node.dato.getToken()));
	         else
	        	 return Float.parseFloat(node.dato.getToken());
	         
	     }
	     return 0;
	 }

	 private void visit(String value) {
	        System.out.print(" " + value);        
	    }
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
		
		public Nodo(Token val){
			dato = val;
		}
	}
}
