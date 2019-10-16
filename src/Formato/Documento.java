package Formato;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class Documento extends DefaultStyledDocument{
	
	private static final long serialVersionUID = 1L;
	private StyleContext cont;
	private AttributeSet attr,attrBlack,attrBlue;
	private final static Color r_PR = new Color(127, 0, 145),r_CAD = new Color(42, 0, 255);
	private ArrayList<coloreado> a;
	String currentString,oldString;
	public Documento() {
		cont = StyleContext.getDefaultStyleContext();
        attr = cont.addAttribute(cont.getEmptySet(),StyleConstants.Foreground,r_PR);
        attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        attrBlue = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, r_CAD);
        //putProperty(PlainDocument.tabSizeAttribute, 1);
        
	}
	
	@Override
	public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException {
		char car = arg1.charAt(arg1.length() - 1);
		switch (car) {
		case '{':
			arg1 += "}";
			break;
		case '(':
			arg1 += ")";
			break;
		}
		super.insertString(arg0, arg1, arg2);
		
		algo();
	}
	
	@Override
	public void remove(int arg0, int arg1) throws BadLocationException {
		// TODO Auto-generated method stub
		super.remove(arg0, arg1);
		algo();
	}
	
	private synchronized void algo() throws BadLocationException{
		MutableAttributeSet asnew = null;
		a = new ArrayList<>();
		colorea();
		asnew = new SimpleAttributeSet(attrBlack.copyAttributes());
    	StyleConstants.setBold(asnew, false);
        setCharacterAttributes(0,getText(0, getLength()).length(),asnew,true);
		
		for (int i = 0; i < a.size(); i++) {
			if(a.get(i).cad)
				asnew = new SimpleAttributeSet(attrBlue.copyAttributes());
			else
				asnew = new SimpleAttributeSet(attr.copyAttributes());
        	StyleConstants.setBold(asnew, true);
            setCharacterAttributes(a.get(i).pos, a.get(i).palabra.length(),asnew,true);
		}
	}
	
	private void colorea() throws BadLocationException{
		String t = getText(0, getLength()), P = "";
		currentString = t;
		t += " ";
		int delimitador =  0;
		
		for (int i = 0; i < t.length(); i++) {
			char car = t.charAt(i);
			if( Character.isLetterOrDigit(car) || car == '_'){
				P += car;
			}else if(car == '"'){
					i++;
					car = t.charAt(i);
					P += car;
					while(car != '"'){
						P += car;
						i++;
						try{
							car = t.charAt(i);	
						}catch(StringIndexOutOfBoundsException e){ break;}
						
					}
					i++;
					try{
						car = t.charAt(i);	
					}catch(StringIndexOutOfBoundsException e){ car = t.charAt(i-2);}
					P += car;
					delimitador = i;
					if(P.length() > 0){
						//if(palabraRes(P)){
							a.add(new coloreado(delimitador-P.length(), P, true));
						//}
						P = "";
					}
			}else{
				delimitador = i;
				if(P.length() > 0){
					if(palabraRes(P)){
						a.add(new coloreado(delimitador-P.length(), P,false));
					}
					P = "";
				}
				
			}
		}
		oldString = currentString;
	}
	private boolean palabraRes(String palabra){
		if( palabra.matches("(if|while|public|private|true|false|"
				+ "class|boolean|int|static|double|void|float)"))
			return true;
		return false;
	}
	
	class coloreado{
		int pos;
		String palabra;
		boolean cad;
		coloreado(int po, String pa, boolean c){
			pos = po;
			palabra = pa;
			cad = c;
		}
	}
}
