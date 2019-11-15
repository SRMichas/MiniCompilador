package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class CustomFile {
//	Variables de Control
			public String fileName = null;
			private File save = null;
			boolean saved = false;
			public String txt = "";
	
	public void makeItNull(){
		save = null;
		fileName = null;
	}
	
	public String OpenFile(JFrame jf){	 //Open File
		String document="", aux = "";
		JFileChooser file = new JFileChooser();
		File open = null;
		if(file.showOpenDialog(jf) == JFileChooser.APPROVE_OPTION){
			try  {
			   open=file.getSelectedFile();
			   //JOptionPane.showMessageDialog(null, abre);
			   if(open!=null) {  
				  save=open;
				  FileReader files=new FileReader(save);
			      BufferedReader read=new BufferedReader(files);
			      
			      while((aux=read.readLine())!=null)
			      {
			         document+= aux+ "\n";
			      }			
			      fileName = removeExtension(save.getName());
			      read.close();
			    }
			   }catch(IOException ex){
				   JOptionPane.showMessageDialog(null, open.getPath()+"\n"+open.getName());
			     JOptionPane.showMessageDialog(null,ex+"" +
			           "\nCan't found specified file",
			                 "WARNING!!!",JOptionPane.WARNING_MESSAGE);
			    }
		}
		txt = document;
		return document;   //El texto se almacena en el JTextArea
	}
	
	public void SaveFile( JTextPane ta,JFrame jf ) throws IOException{ //Save File
		String lines = "";
		JFileChooser explorer = new JFileChooser(System.getProperty("user.dir"));
		do{
			try {
				if(!save.exists() ){
					fileName = save.getName();
					String completeFileName = save+".txt";
					FileWriter fw = new FileWriter( completeFileName);
					BufferedWriter writer = new BufferedWriter( fw );
					lines = ta.getText();
					char[] caracBytes = new char[ lines.length() ];
					
					for (int i = 0; i < lines.length(); i++) {
						caracBytes[ i ]  = lines.charAt( i );
					}
					for (int i = 0; i < lines.length(); i++) {
						if( ! ( lines.charAt(i) == 10 ) ){
							writer.write( lines.charAt( i ) );
						}else{
							writer.newLine();
						}
					}
					writer.close();
					saved = true;
					
					break;
				}else{
					fileName = removeExtension(save.getName());
					FileWriter fw = new FileWriter( save);
					BufferedWriter writer = new BufferedWriter( fw );
					lines = ta.getText();
					char[] caracBytes = new char[ lines.length() ];
					
					for (int i = 0; i < lines.length(); i++) {
						caracBytes[ i ]  = lines.charAt( i );
					}
					for (int i = 0; i < lines.length(); i++) {
						if( ! ( lines.charAt(i) == 10 ) ){
							writer.write( lines.charAt( i ) );
						}else{
							writer.newLine();
						}
					}
					writer.close();
					saved = true;
					break;
					
				}
			} catch (Exception e) {
				
				if( explorer.showSaveDialog(jf) == JFileChooser.APPROVE_OPTION){
					save = explorer.getSelectedFile();
				}
			}
		}while( save != null);
		txt = lines;
	}
	public String removeExtension(String s){
		String vuel = "";
		
		for (int i = 0; i < s.length(); i++) {
			if( i < s.length()-4)
				vuel += s.charAt(i);
		}
		
		return vuel;
	}
}
