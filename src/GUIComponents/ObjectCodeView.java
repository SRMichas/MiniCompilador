package GUIComponents;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import format.ObjectCodeDocument;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

public class ObjectCodeView extends JDialog{
	
	private FilePanel FP;
	private boolean visible;
	public ObjectCodeView(JFrame jf) {
		setTitle("Object Code");
		setSize(600, 700);
		init();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(jf);
        setModal(true);
	}
	public void setFile(String file){
		FP.setText(file);
		FP.updateUI();
		setVisible(visible);
	}
	public void isVisible(boolean r){
		visible = true;
	}
	private void init(){
		
		JList list = new JList();
		list.setPreferredSize(new Dimension(100, 0));
		list.setBackground(Color.RED);
		getContentPane().add(list, BorderLayout.WEST);
		
		FP = new FilePanel();
		JScrollPane jsp = new JScrollPane(FP);
		getContentPane().add(jsp, BorderLayout.CENTER);
	}
	protected class FilePanel extends JTextPane{
		private static final long serialVersionUID = 1L;
		public FilePanel() {
			setBackground(Color.WHITE);
			setFont(new Font("Consolas", Font.PLAIN, 16));
			ObjectCodeDocument od = new ObjectCodeDocument();
			setStyledDocument(od);
		}
	}	
}