package analysis;

import java.util.ArrayList;

import javax.swing.JTextPane;

import codeGeneration.CuadrupleGenerator;
import entities.Identifier;
import entities.Token;

public class Analizer {
	
	private ArrayList<Token> arr = new ArrayList<>();
	private ArrayList<Identifier> symTab;
	private String output = "",message = "";
	//private boolean algo = true;
	private Lexer lex = new Lexer();
	private Parser p;
	private Semantic sem;
	public boolean show;
	private JTextPane container;
	
	public ArrayList<Token> retArr(){
		return arr;
	}
	public ArrayList<Identifier> retArrS(){
		/*try {
			return p.r();
		} catch (NullPointerException e) {
			return new ArrayList<Identificador>(0);
		}*/
		return symTab;
		
	}
	public void setView(JTextPane view){
		container = view;
	}
	public String retMessage(){ return message; }
	public String compilation(String input){
		output = "";
		show = false;
		if( lex.lexico(input.toCharArray())){
			output += "\tNo Lexical Errors\n";
			arr = lex.retComp();
			p = new Parser(arr);
			output += p.sintacticAnalize();
		}else 
			output = lex.output;
		
		if( output.equals("\tNo Lexical Errors\n") ){
			output += "\tNo Sintactic Parsing Errors\n";
			sem = new Semantic(p.r());
			output += sem.semanticAnalize();
		}
		if( output.endsWith("Parsing Errors\n")){
			output += "\tNo Semantic Errors\n"+
					"\n\tProgram Successfully Compiled";
			show = true;
			CuadrupleGenerator cuad = new CuadrupleGenerator(p.r(),container);
			cuad.genarateCuadruples();
			message = cuad.retOutput();
			symTab = cuad.retTable();
		}
		return output;
	}
	
}
