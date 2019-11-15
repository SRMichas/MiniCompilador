package entities;

import java.util.ArrayList;

public class Identifier {
	
	private String name,type,value,scope;
	private int line,faux;
	private ArrayList<Token> exp;
	public Identifier(String n, String t,String v,int f,int fa){
		name = n; type = t; value = v; line = f; faux = fa;
	}
	public Identifier(String n, String t,String v,int f){
		name = n; type = t; value = v; line = f;
	}
	public Identifier(String n, String t,String v,int f,String al){
		name = n; type = t; value = v; line = f; scope = al;
	}
	public String getName() {
		return name;
	}
	public void setName(String nombre) {
		this.name = nombre;
	}
	public String getType() {
		return type;
	}
	public void setType(String tipo) {
		this.type = tipo;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String valor) {
		this.value = valor;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int fila) {
		this.line = fila;
	}
	public int getFaux() {
		return faux;
	}
	public void setFaux(int faux) {
		this.faux = faux;
	}
	public ArrayList<Token> getExp(){
		return exp;
	}
	public void setExp(ArrayList<Token> e){
		exp = e;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String alcance) {
		this.scope = alcance;
	}
	public String retExpression(){
		String res = "";
		if( exp != null && exp.size() != 0){
			res = name+" =";
			for(Token token : exp) 
				res += " "+token.getToken();
		}
		return res;
	}
	
}
