package Grafico;

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
import Formato.Documento;
import main.Archivo;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.border.MatteBorder;

public class WorkSpace extends JScrollPane{
	
	private static final long serialVersionUID = 1L;
	private JTextPane txtTrabajo,taLineas;
	private Archivo arc;
	private static final int espacio = 4;
	
	public WorkSpace(Ventana jf) {
		arc = new Archivo();
		
		taLineas = new JTextPane();
		taLineas.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
		taLineas.setEditable(false);
		taLineas.setFont(new Font("Consolas", Font.PLAIN, 16));
		setRowHeaderView(taLineas);
		
		txtTrabajo = new JTextPane();
		Documento docc = new Documento(txtTrabajo);
		txtTrabajo.setDocument(docc);
		txtTrabajo.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				int pos = txtTrabajo.getCaretPosition();
	            Element map = txtTrabajo.getDocument().getDefaultRootElement();
	            int row = map.getElementIndex(pos);
	            Element lineElem = map.getElement(row);
	            int col = pos - lineElem.getStartOffset();
	            col++;
	            row++;
	            jf.lbColumna.setText("Columna:  "+col);
	            jf.lbLinea.setText("Fila:  "+row);
			}
		});
		txtTrabajo.setFont(new Font("Consolas", Font.PLAIN, 16));
		txtTrabajo.getDocument().addDocumentListener(new DocumentListener(){
			public String getText(){
				int caretPosition = txtTrabajo.getDocument().getLength();
				Element root = txtTrabajo.getDocument().getDefaultRootElement();
				String text = "1" + System.getProperty("line.separator");
				for(int i = 2; i < root.getElementIndex( caretPosition ) + 2; i++){
					text += i + System.getProperty("line.separator");
				}
				return text;
			}
			@Override
			public void changedUpdate(DocumentEvent de) {
				taLineas.setText(getText());
			}
 
			@Override
			public void insertUpdate(DocumentEvent de) {
				taLineas.setText(getText());
				//text(de);
			}
 
			@Override
			public void removeUpdate(DocumentEvent de) {
				taLineas.setText(getText());
			}
 
		});
		tab();
		
		setViewportView(txtTrabajo);
	}
	private void tab(){
		FontMetrics fm = txtTrabajo.getFontMetrics( txtTrabajo.getFont() );
        int charWidth = fm.charWidth( 'w' );
        int tabWidth = charWidth * espacio;

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
        int length = txtTrabajo.getDocument().getLength();
        txtTrabajo.getStyledDocument().setParagraphAttributes(0, length, attributes, true);
	}

	public JTextPane getTxtTrabajo() {
		return txtTrabajo;
	}

	public JTextPane getTaLineas() {
		return taLineas;
	}
	public Archivo getArchivo(){
		return arc;
	}
}
