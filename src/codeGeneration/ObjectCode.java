package codeGeneration;

import java.util.ArrayList;
import entities.Cuadruple;
import entities.Identifier;

public class ObjectCode {

	public String file;
	private String cuadruple;
	private ArrayList<Cuadruple> cuadruples2;
	private short espacio = 10;
	
	public ObjectCode(){}
	
	public ObjectCode(String cuad){
		cuadruple = cuad;
	}
	public ObjectCode(String scuad,ArrayList<Cuadruple> cuad){
		cuadruple = scuad;
		cuadruples2 = cuad;
	}
	
	public void generateFile(){
		generateCode();
		//System.out.println(file);
	}
	private void generateCode(){
		char op;
		String aux = "";
		createVariables();
		//file += ".CODE\nMAIN PROC FAR \n.STARUP\n";
		file += String.format("%-"+(espacio*2)+"s%s%n%-"+(espacio*2)+"s%-"+(espacio+15)+"s%s%n"+
				"%-"+(espacio*2)+"s%s%n"
				, "",".CODE","MAIN","PROC","FAR",
				"",".STARTUP");
		for (Cuadruple var : cuadruples2) {
			op = var.getOperator().charAt(0);
			switch(op){
				case '+':
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"",";Addition section");
					if( var.getFirstOperand().matches("\\d+")){
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","MOV    "+var.getName()+","+var.getFirstOperand());
						//aux += "MOV "+var.getName()+","+var.getFirstOperand()+"\n";
						/*aux += "MOV "+var.getName()+",";
						aux += var.getFirstOperand()+"\n";*/
					}else{
						//aux += "MOV AH,"+var.getFirstOperand()+"\n";
						//aux += "MOV "+var.getName()+", AH\n";
						//aux += ""+var.getFirstOperand()+"\n";
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","MOV    AH,"+var.getFirstOperand());
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","MOV    "+var.getName()+", AH");
					}
					//------
					if( var.getSecondOperand().matches("\\d+")){
						//aux += "ADD "+var.getName()+","+var.getSecondOperand()+"\n";
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","ADD    "+var.getName()+","+var.getSecondOperand());
					}else{
						//aux += "MOV AH,"+var.getSecondOperand()+"\n";
						//aux += "MOV "+var.getName()+", AH\n";
						//aux += "ADD "+var.getName()+","+var.getSecondOperand()+"\n";
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","MOV    AH,"+var.getSecondOperand());
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","ADD    "+var.getName()+", AH");
					}
					
					break;
				case '-':
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"",";Substraction section");
					if( var.getFirstOperand().matches("\\d+")){
						//aux += "MOV "+var.getName()+","+var.getFirstOperand()+"\n";
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","MOV    "+var.getName()+","+var.getFirstOperand());
					}else{
						//aux += "MOV "+var.getName()+","+var.getFirstOperand()+"\n";
						//aux += "MOV AH,"+var.getFirstOperand()+"\n";
						//aux += "MOV "+var.getName()+", AH\n";
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","MOV    AH,"+var.getFirstOperand());
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","MOV    "+var.getName()+", AH");
					}
					//........
					if( var.getSecondOperand().matches("\\d+")){
						//aux += "SUB "+var.getName()+","+var.getSecondOperand()+"\n";
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","SUB    "+var.getName()+","+var.getSecondOperand());
					}else{
						//aux += "SUB "+var.getName()+","+var.getSecondOperand()+"\n";
						//aux += "MOV AH,"+var.getSecondOperand()+"\n";
						//aux += "MOV "+var.getName()+", AH\n";
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","MOV    AH,"+var.getSecondOperand());
						aux += String.format("%-"+(espacio*2)+"s%s%n",
								"","SUB    "+var.getName()+", AH");
					}
					break;
				case '/':
					/*if( var.getFirstOperand().matches("\\d+")){
						aux += "MOV "+var.getName()+","+var.getFirstOperand()+"\n";	
					}else{
						aux += "MOV "+var.getName()+","+var.getFirstOperand()+"\n";
					}
					if( var.getSecondOperand().matches("\\d+")){
						aux += "MUL "+var.getName()+","+var.getSecondOperand()+"\n";	
					}else{
						aux += "MUL "+var.getName()+","+var.getSecondOperand()+"\n";
					}*/
					/*aux += "MOV AX,"+var.getFirstOperand()+"\n";
					aux += "MOV BL,"+var.getSecondOperand()+"\n";
					aux += "DIV BL\n";
					aux += "MOV "+var.getName()+", AL\n";*/
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"",";Divition Section");
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"","MOV    AX,"+var.getFirstOperand());
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"","MOV    BL,"+var.getSecondOperand());
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"","DIV    BL");
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"","MOV    "+var.getName()+", AL");
					break;
				case '*':
					/*if( var.getName().matches("T\\d+")){
						aux += "MOV "+var.getName()+","+var.getFirstOperand()+"\n";	
					}else{
						aux += "MOV "+var.getName()+","+var.getFirstOperand()+"\n";
					}
					if( var.getName().matches("T\\d+")){
						aux += "DIV "+var.getName()+","+var.getSecondOperand()+"\n";	
					}else{
						aux += "DIV "+var.getName()+","+var.getSecondOperand()+"\n";
					}*/
					/*aux += "MOV AL,"+var.getFirstOperand()+"\n";
					aux += "MUL "+var.getSecondOperand()+"\n";
					aux += "MOV "+var.getName()+",AX\n";*/
					
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"",";Multiplication section");
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"","MOV    AL,"+var.getFirstOperand());
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"","MOV    AH,"+var.getSecondOperand());
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"","MUL    AH");
					aux += String.format("%-"+(espacio*2)+"s%s%n",
							"","MOV    "+var.getName()+", AX");
					break;
			}
			file += aux;
			aux = "";
		}
		//file += ".END\nMAIN ENDP\nEND";
		file += String.format("%-"+(espacio*2)+"s%s%n",
				"",";Print section");
		file += String.format("%-"+(espacio*2)+"s%s%n"+
				"%-"+(espacio*2)+"s%s%n"+
				"%-"+(espacio*2)+"s%s%n"+
				"%-"+(espacio*2)+"s%s%n"+
				"%-"+(espacio*2)+"s%s%n",
				"","ADD    "+cuadruples2.get(cuadruples2.size()-1).getName()+", 30H",
				"","MOV    BX, 0001H",
				"","MOV    DL,"+cuadruples2.get(cuadruples2.size()-1).getName(),
				"","MOV    AH, 02H",
				"","INT    21H");
		
		file += String.format("%-"+(espacio*2)+"s%s%n%-"+(espacio*2)+"s%s%n%-"+(espacio*2)+"s%s", 
				"",".EXIT","MAIN","ENDP","","END");
	}
	private void createVariables(){
		String definition = "";
		file = String.format("%-"+(espacio*2)+"s%-"+(espacio+10)+"s%"+(espacio)+"s %n"
				+"%-"+(espacio*2)+"s%-"+(espacio)+"s %n"
				,"",".MODEL","small","",".DATA");
				//".MODEL small\n.DATA\n";
		String aux = "";
		for (Cuadruple var : cuadruples2) {
			if( var.getOperator().equals("*"))
				definition = "DW";
			else
				definition = "DB";
			if( var.getFirstOperandValue() != null ){
				aux += String.format("%-"+(espacio*2)+"s%-"+(espacio+14)+"s %s %n", var.getFirstOperand(),definition,var.getFirstOperandValue());
			}
			if( var.getSecondOperandValue() != null){
				aux += String.format("%-"+(espacio*2)+"s%-"+(espacio+14)+"s %s %n", var.getSecondOperand(),definition,var.getSecondOperandValue());
			}
			file += String.format("%-"+(espacio*2)+"s%-"+(espacio+14)+"s %s %n", var.getName(),definition,"0");
			//file += var.getName()+" DB 0\n";
		}
		file += aux;
	}
	
}
