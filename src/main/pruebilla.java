package main;

import java.awt.List;
import java.util.ArrayList;
import java.util.Stack;

public class pruebilla {

	public static void main(String[] args) {
		
		ArrayList<Token> arr = new ArrayList<>();
		arr.add(new Token(6, "1", 0, 0));
		arr.add(new Token(3, "-", 0, 0));
		arr.add(new Token(6, "3", 0, 0));
		arr.add(new Token(3, "+", 0, 0));
		arr.add(new Token(6, "1", 0, 0));
		arr.add(new Token(3, "*", 0, 0));
		arr.add(new Token(6, "13", 0, 0));
		arr.add(new Token(3, "-", 0, 0));
		arr.add(new Token(6, "10", 0, 0));
		arr.add(new Token(3, "/", 0, 0));
		arr.add(new Token(9, "cacas", 0, 0));
		convierte(arr);

	}
	
	private static void convierte(ArrayList<Token> algo){
		
		Stack<Token> opr = new Stack<>(),opn = new Stack<>(),tot = new Stack<>(),
				fin = new Stack<>(),mid = new Stack<>();
		for (Token token : algo) {
			switch (token.getTipo()) {
			case Token.DIG:case Token.ID:
				opn.add(token);
				break;
			case Token.OPA:
				opr.add(token);
				break;
			}
			tot.add(token);
		}
		System.out.println("Operandores -> "+imprimir(opr));
		System.out.println("Operandores -> "+imprimir(opn));
		
		try {
		      //Algoritmo Infijo a Postfijo
		      while (!tot.isEmpty()) {
		        switch (prioridad(tot.peek())){
		          case 3:
		          case 4:
		            while(prioridad(mid.peek()) >= prioridad(tot.peek())) {
		              fin.push(mid.pop());
		            }
		            mid.push(tot.pop());
		            break;  
		          default:
		            fin.push(tot.pop()); 
		        } 
		      }

		      //Eliminacion de `impurezas´ en la expresiones algebraicas
		      /*String infix = expr.replace(" ", "");
		      String postfix = S.toString().replaceAll("[\\]\\[,]", "");*/

		      //Mostrar resultados:
		      System.out.println("Expresion Infija: " + imprimir(tot));
		      System.out.println("Expresion Postfija: " + imprimir(fin));

		    }catch(Exception ex){ 
		      System.out.println("Error en la expresión algebraica");
		      System.err.println(ex);
		    }
		
	}
	private static String imprimir(Stack<Token> l){
		String res = "";
		for (Token tok : l) {
			res += tok.getToken()+" , ";
		}
		return res;
	}
	private static int prioridad(Token t){
		if( t.getToken().equals("*") || t.getToken().equals("/"))
			return 4;
		else
			return 3;
	}
}
