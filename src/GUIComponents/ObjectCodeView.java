package GUIComponents;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import codeGeneration.ObjectCode;
import format.ObjectCodeDocument;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import entities.Cuadruple;

public class ObjectCodeView extends JDialog implements ListSelectionListener{
	
	private static final long serialVersionUID = 1L;
	
	private FilePanel FP;
	private boolean visible;
	JSplitPane splitPane;
	JTextPane txtPCuad;
	JList<String> list;
	private ArrayList<ArrayList<Cuadruple>> aaa;
	private ArrayList<String> bbb;
	private DefaultListModel<String> model;
	private int idx = 0;
	
	public ObjectCodeView(JFrame jf) {
		setTitle("Object Code");
		setSize(1200, 700);
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
	public void fill(Object a,Object b){
		if( a != null ){
			aaa = (ArrayList<ArrayList<Cuadruple>>) a;
			bbb = (ArrayList<String>) b;
			for (int i = 1; i <= bbb.size(); i++) {
				model.addElement("Code #"+i);
			}
			list.setModel(model);
		}
		setVisible(visible);
	}
	public void isVisible(boolean r){
		visible = true;
	}
	private void init(){
		
		list = new JList<String>();
		list.setFont(new Font("Arial", Font.PLAIN, 18));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
		list.addListSelectionListener(this);
		model = new DefaultListModel<String>();
		//list.setBackground(Color.RED);
		JScrollPane jspl = new JScrollPane(list);
		jspl.setPreferredSize(new Dimension(125, 0));
		getContentPane().add(jspl, BorderLayout.WEST);
		
		splitPane = new JSplitPane();
		
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setLeftComponent(tabbedPane);
		
		txtPCuad = new JTextPane();
		txtPCuad.setFont(new Font("Consolas", Font.PLAIN, 16));
		JScrollPane jspc = new JScrollPane(txtPCuad);
		tabbedPane.addTab("Cuadruple", null, jspc, null);
		
		JTabbedPane tabbedPane2 = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane2);
		FP = new FilePanel();
		JScrollPane jspo = new JScrollPane(FP);
		tabbedPane2.addTab("Object Code", jspo);
		
		//getContentPane().add(jsp, BorderLayout.CENTER);
		/*splitPane.setDividerLocation(this.getSize().height /2);*/
		restoreDefaults();
	}
	private void restoreDefaults() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
            	splitPane.setDividerLocation((ObjectCodeView.this.getSize().width /2) - 100);
                //mainSplittedPane.setDividerLocation(mainSplittedPane.getSize().width /2);
            }
        });
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
	@Override
	public void valueChanged(ListSelectionEvent e) {
		idx = list.getSelectedIndex();
		txtPCuad.setText(bbb.get(idx));
		ObjectCode obj = new ObjectCode(null,aaa.get(idx));
		obj.generateFile();
		FP.setText(obj.file);
	}	
}