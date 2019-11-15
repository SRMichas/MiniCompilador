package GUIComponents;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;

import format.WorkSpaceDocument;
import main.CustomFile;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.border.MatteBorder;

public class WorkSpace extends JScrollPane{
	
	private static final long serialVersionUID = 1L;
	private JTextPane txtWorkPlace,taLines;
	private CustomFile file;
	private static final int space = 4;
	
	public WorkSpace(MainGUI jf) {
		file = new CustomFile();
		
		taLines = new JTextPane();
		taLines.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
		taLines.setEditable(false);
		taLines.setFont(new Font("Consolas", Font.PLAIN, 16));
		setRowHeaderView(taLines);
		
		txtWorkPlace = new JTextPane();
		WorkSpaceDocument docc = new WorkSpaceDocument(txtWorkPlace);
		txtWorkPlace.setDocument(docc);
		txtWorkPlace.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				int pos = txtWorkPlace.getCaretPosition();
	            Element map = txtWorkPlace.getDocument().getDefaultRootElement();
	            int row = map.getElementIndex(pos);
	            Element lineElem = map.getElement(row);
	            int col = pos - lineElem.getStartOffset();
	            col++;
	            row++;
	            jf.lbColumn.setText("Column:  "+col);
	            jf.lbLine.setText("Line:  "+row);
			}
		});
		txtWorkPlace.setFont(new Font("Consolas", Font.PLAIN, 16));
		txtWorkPlace.getDocument().addDocumentListener(new DocumentListener(){
			public String getText(){
				int caretPosition = txtWorkPlace.getDocument().getLength();
				Element root = txtWorkPlace.getDocument().getDefaultRootElement();
				String text = "1" + System.getProperty("line.separator");
				for(int i = 2; i < root.getElementIndex( caretPosition ) + 2; i++){
					text += i + System.getProperty("line.separator");
				}
				return text;
			}
			@Override
			public void changedUpdate(DocumentEvent de) {
				taLines.setText(getText());
			}
 
			@Override
			public void insertUpdate(DocumentEvent de) {
				taLines.setText(getText());
				//text(de);
			}
 
			@Override
			public void removeUpdate(DocumentEvent de) {
				taLines.setText(getText());
			}
 
		});
		tab();
		
		setViewportView(txtWorkPlace);
	}
	private void tab(){
		FontMetrics fm = txtWorkPlace.getFontMetrics( txtWorkPlace.getFont() );
        int charWidth = fm.charWidth( 'w' );
        int tabWidth = charWidth * space;

        TabStop[] tabs = new TabStop[10];

        for (int j = 0; j < tabs.length; j++)
        {
             int tab = j + 1;
             tabs[j] = new TabStop( tab * tabWidth );
        }

        TabSet tabSet = new TabSet(tabs);
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setTabSet(attributes, tabSet);
        StyleConstants.setFontFamily(attributes, "Consolas");
        StyleConstants.setFontSize(attributes, 16);
        int length = txtWorkPlace.getDocument().getLength();
        txtWorkPlace.getStyledDocument().setParagraphAttributes(0, length, attributes, true);
	}

	public JTextPane getTxtWorkPlace() {
		return txtWorkPlace;
	}

	public JTextPane getTaLines() {
		return taLines;
	}
	public CustomFile getFile(){
		return file;
	}
}
