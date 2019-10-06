package main;

public class Identificador {
	
	private String nombre,tipo,valor;
	private int fila;
	public Identificador(String n, String t,String v,int f){
		nombre = n; tipo = t; valor = v; fila = f;
	}
	public String getNombre() {
		return nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
	public String getValor() {
		return valor;
	}
	public int getFila(){
		return fila;
	}
	
	
	
	
}
