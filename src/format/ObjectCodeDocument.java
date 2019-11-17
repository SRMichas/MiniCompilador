package format;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ObjectCodeDocument extends DefaultStyledDocument{
	
	private ArrayList<CustomColor> array;
	private StyleContext cont;
	private AttributeSet attr,attrBlack,attrBlue,attrOrange,attrRed,attrCom;
	public String currentString,oldString, msg = "";
	private Color cl1 = new Color(30,144,255),cl2 = new Color(220,20,60),cl3 = new Color(34,139,34);
	public ObjectCodeDocument() {
		cont = StyleContext.getDefaultStyleContext();
        //attr = cont.addAttribute(cont.getEmptySet(),StyleConstants.Foreground,Color.PINK);
        attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        attrBlue = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, cl1);
        attrOrange = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.ORANGE);
        attrRed = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, cl2);
        attrCom = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, cl3);
	}
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		// TODO Auto-generated method stub
		super.insertString(offs, str, a);
		doIt();
	}
	@Override
	public void remove(int offs, int len) throws BadLocationException {
		// TODO Auto-generated method stub
		super.remove(offs, len);
		doIt();
	}
	private synchronized void doIt() throws BadLocationException{
		MutableAttributeSet asnew = null;
		char type;
		array = new ArrayList<>();
		paintIt();
		asnew = new SimpleAttributeSet(attrBlack.copyAttributes());
		setCharacterAttributes(0,getText(0, getLength()).length(),asnew,true);
		for (int i = 0; i < array.size(); i++) {
			type = array.get(i).cad;
			if(type == '1')
				asnew = new SimpleAttributeSet(attrBlue.copyAttributes());
			else if(type == '2')
				asnew = new SimpleAttributeSet(attrOrange.copyAttributes());
			else if(type == '3')
				asnew = new SimpleAttributeSet(attrRed.copyAttributes());
			else if( type == 'a')
				asnew = new SimpleAttributeSet(attrCom.copyAttributes());
			/*else if(type == '4')
				asnew = new SimpleAttributeSet(attrBlue.copyAttributes());
        	StyleConstants.setBold(asnew, true);*/
            setCharacterAttributes(array.get(i).position, array.get(i).word.length(),asnew,true);
		}
	}
	private void paintIt() throws BadLocationException{
		String t = getText(0, getLength()), P = "";
		currentString = t;
		t += " ";
		int delimiter =  0;
		char c;
		for (int i = 0; i < t.length(); i++) {
			c = t.charAt(i);
			if( Character.isLetterOrDigit(c) || c == '_'){
				P += c;
			}else if( c == ';'){
				i++;
				c = t.charAt(i);
				P += c;
				while( c != '\n'){
					P += c;
					i++;
					try {
						c = t.charAt(i);
					} catch (StringIndexOutOfBoundsException e) { break; }
				}
				i++;
				try {
					c = t.charAt(i);
				} catch (StringIndexOutOfBoundsException e) { break; }
				P += c;
				delimiter = i;
				if( P.length() > 0){
					array.add(
							new CustomColor(delimiter-P.length(), P, 'a')
							);
					P = "";
				}
			}else{
				delimiter = i;
				if( P.length() > 0 ){
					if( wordList1(P)){
						array.add(new CustomColor(delimiter-P.length(), P, '1'));
					}else if( wordList2(P)){
						array.add(new CustomColor(delimiter-P.length(), P, '2'));
					}else if( wordList3(P)){
						array.add(new CustomColor(delimiter-P.length(), P, '3'));
					}else if( wordList4(P)){
						
					}
					P = "";
				}
			}
		}
	}
	private boolean wordList1(String str){
		if( str.matches("(MODEL|DB|DW|DD|PROC|FAR|NEAR|"
				+ "MOV|ADD|SUB|MUL|DIV|INT|END)"))
			return true;
		else if( str.matches("(model|db|dw|dd|proc|far|near|"
				+ "mov|add|sub|mul|div|int|end)"))
			return true;
		return false;
	}
	private boolean wordList2(String str){
		if( str.matches("(AL|AH|AX|EAX|BL|BH|BX|EBX|CL|CH|CX|ECX"
				+ "|DL|DH|DX|EDX)"))
			return true;
		else if( str.matches("(al|ah|ax|eax|bl|bh|bx|ebx|cl|ch|cx|ecx"
				+ "|dl|dh|dh|edx)"))
			return true;
		return false;
	}
	private boolean wordList3(String str){
		if( str.matches("\\d+(H|B|h|b)?"))
			return true;
		return false;
	}
	private boolean wordList4(String str){
		if( str.matches(";.*\n"))
			return true;
		return false;
	}
	class CustomColor{
		int position;
		String word;
		char cad;
		CustomColor(int po, String pa, char c){
			position = po;
			word = pa;
			cad = c;
		}
	}
}
