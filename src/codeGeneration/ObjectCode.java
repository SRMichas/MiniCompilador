package codeGeneration;

import java.util.ArrayList;
import java.util.Hashtable;

import entities.Cuadruple;

public class ObjectCode {

	private String cuadruple,file;
	private Hashtable<String, Cuadruple> cuadruples;
	private ArrayList<Cuadruple> cuadruples2;
	
	public ObjectCode(){}
	
	public ObjectCode(String cuad){
		cuadruple = cuad;
	}
	public ObjectCode(String scuad,Hashtable<String, Cuadruple> cuad){
		cuadruple = scuad;
		cuadruples = cuad;
	}
	public ObjectCode(String scuad,ArrayList<Cuadruple> cuad){
		cuadruple = scuad;
		cuadruples2 = cuad;
	}
	
	private void generateFile(){
		generateCode();
	}
	private void generateCode(){
		createVariables();
	}
	private void createVariables(){
		for (Cuadruple var : cuadruples2) {
			file += var.getName()+"DB 0\n";
		}
	}
}
