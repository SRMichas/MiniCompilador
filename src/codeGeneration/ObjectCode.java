package codeGeneration;

import java.util.ArrayList;
import java.util.HashMap;
import entities.Cuadruple;

public class ObjectCode {

	public String file,cuadruple;
	private String lastVar;
	private ArrayList<Cuadruple> cuadruples2;
	private short espacio = 10;
	private HashMap<String, Character> some2;
	private boolean someB = false;
	
	public ObjectCode(){}
	public ObjectCode(String scuad,ArrayList<Cuadruple> cuad){
		cuadruple = scuad;
		cuadruples2 = cuad;
		some2 = new HashMap<>();
	}
	
	public void generateFile(){
		generateCode();
	}
	private void createVariables(){
		String definition = "";
		file = String.format("%-"+(espacio*2)+"s%-"+(espacio+10)+"s%"+(espacio)+"s %n"
				+"%-"+(espacio*2)+"s%-"+(espacio)+"s %n"
				,"",".MODEL","small","",".DATA");
				
		String aux = "";
		Cuadruple var = null;
		for (int i=0;i<cuadruples2.size();i++) {
			var = cuadruples2.get(i);
			if( var.getOperator().equals("*")){
				definition = "DW";
				some2.put(var.getName(), 'D');
				someB = true;
			}else{
				definition = "DW";
				some2.put(var.getName(), 'W');
			}
			
			if( var.getFirstOperandValue() != null ){
				aux += String.format("%-"+(espacio*2)+"s%-"+(espacio+14)+"s %s %n", var.getFirstOperand(),definition,var.getFirstOperandValue());
			}
			if( var.getSecondOperandValue() != null){
				aux += String.format("%-"+(espacio*2)+"s%-"+(espacio+14)+"s %s %n", var.getSecondOperand(),definition,var.getSecondOperandValue());
			}
			if( ((cuadruples2.size()-1) == i) && someB ){
				//file += String.format("%-"+(espacio*2)+"s%-"+(espacio+14)+"s %s %n", var.getName(),"DD","0");
				file += String.format("%-"+(espacio*2)+"s%-"+(espacio+14)+"s %s %n", var.getName(),"DW","0");
				lastVar = var.getName();
				someB = false;
			}else{
				file += String.format("%-"+(espacio*2)+"s%-"+(espacio+14)+"s %s %n", var.getName(),definition,"0");
			}
		}
		file += aux;
		file += String.format("%-"+(espacio*2)+"s%-"+(espacio+14)+"s %s %n", 
				"RES","DB","10 DUP ('$')");
		
	}
	private void generateCode(){
		char op;
		String aux = "",register="";
		createVariables();
		file += String.format("%-"+(espacio*2)+"s%s%n%-"+(espacio*2)+"s%-"+(espacio+15)+"s%s%n"+
				"%-"+(espacio*2)+"s%s%n"
				, "",".CODE","MAIN","PROC","FAR",
				"",".STARTUP");
	
		for (Cuadruple var: cuadruples2) {
			op = var.getOperator().charAt(0);
			switch(op){
				case '+':
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"",";Addition section");
					if( var.getFirstOperand().matches("\\d+")){
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","MOV    "+var.getName()+","+var.getFirstOperand());
					}else{
						if( some2.get(var.getFirstOperand()) == 'D')
							register = "AX";
						else
							register = "AX";
						
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","MOV    "+register+","+var.getFirstOperand());
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","MOV    "+var.getName()+", "+register);
					}
					//------
					if( var.getSecondOperand().matches("\\d+")){
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","ADD    "+var.getName()+","+var.getSecondOperand());
					}else{
						if( some2.get(var.getSecondOperand()) == 'D')
							register = "AX";
						else
							register = "AX";
						
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","MOV    "+register+","+var.getSecondOperand());
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","ADD    "+var.getName()+", "+register);
					}
					
					break;
				case '-':
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"",";Substraction section");
					if( var.getFirstOperand().matches("\\d+")){
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","MOV    "+var.getName()+","+var.getFirstOperand());
					}else{
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","MOV    AX,"+var.getFirstOperand());
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","MOV    "+var.getName()+", AX");
					}
					//........
					if( var.getSecondOperand().matches("\\d+")){
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","SUB    "+var.getName()+","+var.getSecondOperand());
					}else{
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","MOV    AX,"+var.getSecondOperand());
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","SUB    "+var.getName()+", AX");
					}
					break;
				case '/':
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"",";Divition Section");
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"","MOV    AX,"+var.getFirstOperand());
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"","MOV    BX,"+var.getSecondOperand());
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"","DIV    BX");
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"","MOV    "+var.getName()+", AX"); 
					break;
				case '*':
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"",";Multiplication section");
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"","MOV    AX,"+var.getFirstOperand());
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"","MOV    BX,"+var.getSecondOperand());
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"","MUL    BX");
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"","MOV    "+var.getName()+", AX");
					break;
			}
			file += aux;
			aux = "";
		}		
		
		file += String.format("%-"+(espacio*2)+"s%s%n",
				"",";Print Section");
		println("MOV","AX", lastVar, false);
		println("LEA", "SI", "RES", false);
		println("CALL", "PRINT", "", true);
		println("LEA", "DX", "RES", false);
		println("MOV", "AH", "9", false);
		println("INT", "21H", "", true);
		println("MOV", "AH", "4CH", false);
		println("INT", "21H", "", true);
		file += String.format("%-"+(espacio*2)+"s%s%n%-"+(espacio*2)+"s%s%n", 
				"",".EXIT","MAIN","ENDP");
		print();
		file += String.format("%-"+(espacio*2)+"s%s", 
				"","END");
	}
	private void print(){
		file += String.format("%-20s%-"+(espacio+15)+"s%s%n"
				,"PRINT","PROC","NEAR");
		
		println("MOV", "CX", "0", false);
		println("MOV", "BX", "10", false);
		file += String.format("%-"+(espacio*2)+"s%s%n",
				"","LOOP1:");
		println("MOV", "DX", "0", false);
		println("DIV", "BX", "", true);
		println("ADD", "DL", "30H", false);
		println("PUSH", "DX", "", true);
		println("INC", "CX", "", true);
		println("CMP", "AX", "9", false);
		println("JG", "LOOP1", "", true);
		println("ADD", "AL", "30H", false);
		println("MOV", "[SI]", "AL", false);
		file += String.format("%-"+(espacio*2)+"s%s%n",
				"","LOOP2:");
		println("POP", "AX", "", true);
		println("INC", "SI", "", true);
		println("MOV", "[SI]", "AL", false);
		println("LOOP", "LOOP2", "", true);
		file += String.format("%-"+(espacio*2)+"s%s%n",
				"","RET");
		
		file += String.format("%-"+(espacio*2)+"s%s%n",
				"PRINT","ENDP");
	}
	private void println(String dir,String arg1,String arg2,boolean single){
		if( !single )
			file += String.format("%-"+(espacio*2)+"s%s%n",
					"",dir+"    "+arg1+", "+arg2);
		else
			file += String.format("%-"+(espacio*2)+"s%s%n",
					"",dir+"    "+arg1);	
	}
}
