package GUIComponents;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import analysis.Analizer;
import analysis.Parser;
import entities.Token;
import format.ConsoleDocument;
import format.TableFormat;
import main.CustomFile;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

public class MainGUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	//Componentes Nativos
	private JTable table;
	private JTextPane txtTrabajo,txtConsole,txtLog,txtCuadruple;
	JLabel lbColumn,lbLine;
	private JMenuItem mnitNew,mnitSave,mnitOpen,mnitExit,mnitRun,mnitAbout;
	private JMenuItem mnitCopy,mnitPaste,mnitCut;
	private JMenuItem mnitClose;
	private JMenuItem mnitTokTab,mnitSymbTab,mnitObj;
	private JMenuItem mnitpCopy,mnitpPaste,mnitpCut;
	private JButton btnNew,btnSave,btnRun;
	private JTabbedPane workSpace;
	private JScrollPane jstb;
	private JTabbedPane tpConsole;
	//Componentes Personalizados
	private FrameAbout FAD;
	private TokensTable TSE;
	private SymbolTable TS;
	private CustomFile file;
	private ObjectCodeView OCV;
	private Analizer bot = new Analizer();
	private TokenTableModel ttm;
	//private ModeloTabla2 mt2;
	//Oyentes
	private ActListener O = new ActListener();
	
	public MainGUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainGUI.class.getResource("/images/icono-96.png")));
		setTitle("vaja");
		setSize(1200, 720);
		//setSize(700, 720);
		init();						//Metodo que inicializa todos los componentes
		try
		{
			setDefaultLookAndFeelDecorated(true);
			setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI( this );			
		}
		catch ( Exception excepcion ) {	excepcion.printStackTrace();	}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void init(){
		JSplitPane panel3 = new JSplitPane();
		TSE = new TokensTable(MainGUI.this, bot.retArr());		//Instancia de la tabla de simbolos extendida
		TS = new SymbolTable(this, bot.retArrS());
		OCV = new ObjectCodeView(this);
		FAD = new FrameAbout(this);					//Instancia de la ventana Acerca de
		workSpace = new JTabbedPane();					//Instancia del contenedor del area de trabajo
		JSplitPane panel = new JSplitPane();
		panel.setDividerSize(2);
		panel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JSplitPane panel2 = new JSplitPane();
		panel2.setResizeWeight(0.9);
		panel.setTopComponent(panel2);
		
		//panel2.setResizeWeight(0.99);
		//panel.setResizeWeight(0.1);
		panel3.setResizeWeight(0.7);
		
		
		//WorkSpace Section
		WorkSpace WS = new WorkSpace(this);
		txtTrabajo = WS.getTxtWorkPlace();
		workSpace.addTab("New", WS);
		panel2.setLeftComponent(workSpace);
		//Console Section
		ConsoleDocument doc = new ConsoleDocument();
		txtConsole = new JTextPane(doc);
		txtConsole.setFont(new Font("Consolas", Font.PLAIN, 13));
		txtConsole.setEditable(false);
		JScrollPane jscc = new JScrollPane(txtConsole);
		txtCuadruple = new JTextPane();
		txtCuadruple.setFont(new Font("Consolas", Font.PLAIN, 16));
		txtCuadruple.setText("\n\tNothing to Show");
		txtCuadruple.setEditable(false);
		JScrollPane jsct = new JScrollPane(txtCuadruple);
		tpConsole = new JTabbedPane(JTabbedPane.TOP);
		tpConsole.addTab("Console",new ImageIcon(MainGUI.class.getResource("/images/consola-16.png")),jscc);
		tpConsole.addTab("Cuadruple", jsct);
		tpConsole.addChangeListener(new OyeTab());
		panel3.setLeftComponent(tpConsole);
		panel.setBottomComponent(panel3);
		
		txtLog = new JTextPane();
		txtLog.setFont(new Font("Consolas", Font.PLAIN, 12));
		panel3.setRightComponent(new JScrollPane(txtLog));
		
		//Small Token Table Section
		createTable(null);
		jstb = new JScrollPane(table);
		jstb.setPreferredSize(new Dimension(200, 402));
		panel2.setRightComponent(jstb);
		//Column and Line counter Section
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		lbLine = new JLabel("Line:  1");
		Border border = lbLine.getBorder();
		Border margin = new EmptyBorder(0,0,0,50);
		lbLine.setBorder(new CompoundBorder(border, margin));
		panel_1.add(lbLine);
		
		lbColumn = new JLabel("Column:  1");
		border = lbColumn.getBorder();
		margin = new EmptyBorder(0,0,0,200);
		lbColumn.setBorder(new CompoundBorder(border, margin));
		panel_1.add(lbColumn);	
		//ToolBar Section
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		btnNew = new JButton("");
		btnNew.setToolTipText("New Window");
		btnNew.setIcon(new ImageIcon(MainGUI.class.getResource("/images/nuevo-16.png")));
		btnNew.addActionListener(O);
		toolBar.add(btnNew);
		toolBar.addSeparator(new Dimension(5,0));
		
		btnSave = new JButton("");
		btnSave.setToolTipText("Save");
		btnSave.setIcon(new ImageIcon(MainGUI.class.getResource("/images/guardar-16.png")));
		btnSave.addActionListener(O);
		toolBar.add(btnSave);
		toolBar.addSeparator(new Dimension(5,0));
		
		JToolBar toolBar_1 = new JToolBar();
		toolBar.add(toolBar_1);
		
		btnRun = new JButton("");
		btnRun.setToolTipText("Run");
		btnRun.setSize(20, 20);
		ImageIcon im = new ImageIcon(MainGUI.class.getResource("/images/hoja.jpeg"));
		Image r = im.getImage().getScaledInstance(btnRun.getWidth(), btnRun.getHeight(), Image.SCALE_SMOOTH);
		Icon i = new ImageIcon(r);
		btnRun.setIcon(i);
		btnRun.addActionListener(O);
		toolBar_1.add(btnRun);
		//MenSection
		createMenu();				//metodo que crea y a�ade todos los elementos del menu
	}
	private void createMenu(){
		JMenuBar menuBar = new JMenuBar();
		JPopupMenu mnppEdition = new JPopupMenu();
		JPopupMenu mnppClose = new JPopupMenu();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("File");
		menuBar.add(mnArchivo);
		
		mnitNew = new JMenuItem("New");
		mnitNew.setIcon(new ImageIcon(MainGUI.class.getResource("/images/nuevo-16.png")));
		mnitNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnitNew.addActionListener(O);
		mnArchivo.add(mnitNew);
		mnArchivo.addSeparator();
		
		mnitSave = new JMenuItem("Save");
		mnitSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		mnitSave.setIcon(new ImageIcon(MainGUI.class.getResource("/images/guardar-16.png")));
		mnitSave.addActionListener(O);
		mnArchivo.add(mnitSave);
		
		mnitOpen = new JMenuItem("Open");
		mnitOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnitOpen.setIcon(new ImageIcon(MainGUI.class.getResource("/images/abrir-16.png")));
		mnitOpen.addActionListener(O);
		mnArchivo.add(mnitOpen);
		mnArchivo.addSeparator();
		
		mnitExit = new JMenuItem("Exit");
		mnitExit.setIcon(new ImageIcon(MainGUI.class.getResource("/images/salir-16.png")));
		mnitExit.addActionListener(O);
		mnArchivo.add(mnitExit);
		
		JMenu mnEditar = new JMenu("Edit");
		menuBar.add(mnEditar);
		
		mnitCopy = new JMenuItem("Copy");
		mnitCopy.setIcon(new ImageIcon(MainGUI.class.getResource("/images/copiar-16.png")));
		mnitCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		mnitCopy.addActionListener(O);
		mnEditar.add(mnitCopy);
		
		mnitPaste = new JMenuItem("Paste");
		mnitPaste.setIcon(new ImageIcon(MainGUI.class.getResource("/images/pegar-16.png")));
		mnitPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		mnitPaste.addActionListener(O);
		mnEditar.add(mnitPaste);
		
		mnitCut = new JMenuItem("Cut");
		mnitCut.setIcon(new ImageIcon(MainGUI.class.getResource("/images/cortar-16.png")));
		mnitCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		mnitCut.addActionListener(O);
		mnEditar.add(mnitCut);
		
		JMenu mnCorrer = new JMenu("Run");
		menuBar.add(mnCorrer);
		
		mnitRun = new JMenuItem("Run");
		mnitRun.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		mnitRun.setIcon(new ImageIcon(MainGUI.class.getResource("/images/ejecutar-16.png")));
		mnitRun.addActionListener(O);
		mnCorrer.add(mnitRun);
		
		JMenu mnVer = new JMenu("Show");
		menuBar.add(mnVer);
		
		mnitTokTab = new JMenuItem("Extended Token Table");
		mnitTokTab.setIcon(new ImageIcon(MainGUI.class.getResource("/images/table-16.png")));
		mnitTokTab.addActionListener(O);
		mnVer.add(mnitTokTab);
		
		mnitSymbTab = new JMenuItem("Symbol Table");
		mnitSymbTab.setIcon(new ImageIcon(MainGUI.class.getResource("/images/table-16.png")));
		mnitSymbTab.addActionListener(O);
		mnVer.add(mnitSymbTab);
		
		mnitObj = new JMenuItem("Object Code");
		mnitObj.setIcon(new ImageIcon(MainGUI.class.getResource("/images/table-16.png")));
		mnitObj.addActionListener(O);
		mnVer.add(mnitObj);
		
		JMenu mnAyuda = new JMenu("Help");
		mnAyuda.setIcon(null);
		menuBar.add(mnAyuda);
		
		mnitAbout = new JMenuItem("About Vaja...");
		mnitAbout.setIcon(new ImageIcon(MainGUI.class.getResource("/images/info-16.png")));
		mnitAbout.addActionListener(O);
		mnAyuda.add(mnitAbout);
		
		mnitpCopy = new JMenuItem("Copy");
		mnitpCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		mnitpCopy.addActionListener(O);
		mnppEdition.add(mnitpCopy);
		
		mnitpPaste = new JMenuItem("Paste");
		mnitpPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		mnitpPaste.addActionListener(O);
		mnppEdition.add(mnitpPaste);
		
		mnitpCut = new JMenuItem("Cut");
		mnitpCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		mnitpCut.addActionListener(O);
		mnppEdition.add(mnitpCut);
		txtTrabajo.setComponentPopupMenu(mnppEdition);
		
		mnitClose = new JMenuItem("Close");
		mnitClose.addActionListener(O);
		mnppClose.add(mnitClose);
		workSpace.setComponentPopupMenu(mnppClose);
		
	}
	
	private void createTable(ArrayList<Token> a){
		ttm= new TokenTableModel(a,true);								//Creaacion del modelo de tabla
		table = new JTable();										//Instancia de la tabla
		table.setModel(ttm);
		table.getTableHeader().setResizingAllowed(false);			//Quita la opcion de redimencionar la cabezera de la tabla
		table.getTableHeader().setReorderingAllowed(false);			//quira la opcion de reordenar la cabezera de la tabla
		table.setDefaultRenderer(Object.class, new TableFormat());		//Indica el formato de las celdas
		table.setRowHeight(22);										//Tama�o del renglon
	}
	/*private void creaTabla2(ArrayList<Identificador> a){
		mt2 = new ModeloTabla2(a);								//Creaacion del modelo de tabla
		table = new JTable();										//Instancia de la tabla
		table.setModel(mt);
		table.getTableHeader().setResizingAllowed(false);			//Quita la opcion de redimencionar la cabezera de la tabla
		table.getTableHeader().setReorderingAllowed(false);			//quira la opcion de reordenar la cabezera de la tabla
		table.setDefaultRenderer(Object.class, new Formato());		//Indica el formato de las celdas
		table.setRowHeight(22);										//Tama�o del renglon
	}*/

	class ActListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if( obj == mnitNew || obj == btnNew ){
				int limit = 15;
				if( workSpace.getTabCount() < limit){
					workSpace.addTab("New", new WorkSpace(MainGUI.this));		//Crea una nueva venta�a con un nuevo area de trabajo
					workSpace.setSelectedIndex(workSpace.getTabCount()-1);		//Posiciona sobre la nueva pesta�a
				}else
					JOptionPane.showMessageDialog(MainGUI.this, "You can't have more than "+limit+" opened Tabs");
				
			}else if( obj == mnitSave || obj == btnSave ){
				WorkSpace ks = (WorkSpace) workSpace.getComponentAt(workSpace.getSelectedIndex());	//Determina el area de trabajo segun la pesta�a actual
				txtTrabajo = ks.getTxtWorkPlace();		//
				file = ks.getFile();
				try {
					file.SaveFile(txtTrabajo,MainGUI.this);	//Metodo que Guarda Archivo
				} catch (IOException e1) {
						e1.printStackTrace();
				}
			}else if( obj == mnitOpen ){
				int i = workSpace.getSelectedIndex();
				WorkSpace ks = (WorkSpace) workSpace.getComponentAt(i);
				file = ks.getFile();
				String texto = file.OpenFile(MainGUI.this), //Metodo que abre el Arhivo
						n = file.fileName/*,
						dato = ach.txt*/;
				//JOptionPane.showMessageDialog(null, dato);
				boolean p1 = !texto.equals("");		//Verifica si se abrio algun archivo
				if( p1 ){
															//obtiene el indice de la pesta�a actual
					workSpace.setTitleAt(i, n);					//renombra la ventana segun el indice
															//Determina el area de trabajo segun la pesta�a actual
					txtTrabajo = ks.getTxtWorkPlace();
					txtTrabajo.setText(texto);	//Escribe el texto del Arhivo
				}
			}else if( obj == mnitRun || obj == btnRun ){
				int d = workSpace.getSelectedIndex(),
						console_idx = tpConsole.getSelectedIndex();						//indice de la pesta�a actual
				if( TS.isVisible())
					TS.dispose();
				WorkSpace ks = (WorkSpace) workSpace.getComponentAt(d);		//Determina el area de trabajo segun la pesta�a actual
				txtTrabajo = ks.getTxtWorkPlace();							//...
				if(!txtTrabajo.getText().equals("")){
					bot.setView(txtCuadruple);
					String cs = bot.compilation(txtTrabajo.getText());				//Obtiene una cadema con los errores lexicos en caso de haber alguno
					txtLog.setText(Parser.output2);
					createTable(bot.retArr());									//llena la tabla de simbolos peque�a
					jstb.setViewportView(table);
					txtConsole.setText(cs);										//muestra los errores en caso de haber alguno
					TSE.actCat(bot.retArr());									//llena la tabla de simbolos extendida
					TS.actCat(bot.retArrS());	
					if( bot.show ){
						if( !bot.retMessage().equals("") ){
							if( console_idx != 1)
								tpConsole.setTitleAt(1, "*Cuadruple");
							txtCuadruple.setText(bot.retMessage());
						}
					}
				}else
					JOptionPane.showMessageDialog(null, "No text to Analize");
			}else if( obj == mnitTokTab){
				TSE.isVisible(true);				//hace visible la tabla de simbolos extendida
				TSE.actCat(bot.retArr());			//llena la tabla de simbolos extendida
			}else if( obj == mnitObj ){
				OCV.isVisible(true);
				//OCV.setFile(bot.retFile());
				OCV.fill(bot.cuadrupleList, bot.stringList);
			}else if( obj == mnitSymbTab){
				if( bot.show ){
					TS.actCat(bot.retArrS());
					TS.isVisible(true);
				}else if( txtTrabajo.getText().equals(""))
					JOptionPane.showMessageDialog(MainGUI.this, "No items to compile");	
				else
					JOptionPane.showMessageDialog(MainGUI.this, "Can't show until 0 errors detected\nor something has been compiled");				
			}else if( obj == mnitAbout){
				FAD.setVisible(true);		//hace visible la ventana Acerca de
			}else if( obj == mnitCopy || obj == mnitpCopy ){
				WorkSpace ks = (WorkSpace) workSpace.getComponentAt(workSpace.getSelectedIndex());		//Determina el area de trabajo segun la pesta�a actual
				txtTrabajo = ks.getTxtWorkPlace();							//...
				txtTrabajo.copy();
			}else if( obj == mnitPaste || obj == mnitpPaste ){
				WorkSpace ks = (WorkSpace) workSpace.getComponentAt(workSpace.getSelectedIndex());		//Determina el area de trabajo segun la pesta�a actual
				txtTrabajo = ks.getTxtWorkPlace();							//...
				txtTrabajo.paste();
			}else if( obj == mnitCut || obj == mnitpCut){
				WorkSpace ks = (WorkSpace) workSpace.getComponentAt(workSpace.getSelectedIndex());		//Determina el area de trabajo segun la pesta�a actual
				txtTrabajo = ks.getTxtWorkPlace();							//...
				txtTrabajo.cut();
			}else if( obj == mnitClose){
				int d = workSpace.getSelectedIndex();						//indice de la pesta�a actual
				WorkSpace ks = (WorkSpace) workSpace.getComponentAt(d);		//Determina el area de trabajo segun la pesta�a actual
				txtTrabajo = ks.getTxtWorkPlace();							//...
				file = ks.getFile();
				String data = file.txt,data2 = txtTrabajo.getText();
				//JOptionPane.showMessageDialog(null, dato+"\n"+dato2);
				if( !data.equals(data2)){
					//JOptionPane.showMessageDialog(null, "cerrado manual");
					Object[] opciones = { "Yes","No","Cancel"};
					
					int n = JOptionPane.showOptionDialog(MainGUI.this, "Do you want to save changes?"
								, "VAJA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
								, null, opciones, opciones[0]);
						if( n == JOptionPane.YES_OPTION){
							try {
								file.SaveFile(txtTrabajo, MainGUI.this);	//Metodo que Guarda el Archivo
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							//System.exit(0);
						}else if( n == JOptionPane.NO_OPTION)
							workSpace.remove(d);
				}else{
					//JOptionPane.showMessageDialog(null, "cerrado automatico");
					workSpace.remove(d);
				}
				/*try {
					ach.SaveFile(txtTrabajo,Ventana.this);					//Metodo que Guarda Archivo
				} catch (IOException e1) {
						e1.printStackTrace();
				}*/
				//					//quita la pesta�a actual
			}else{
				System.exit(0);		//Sale de la aplicacion
			}
		}
	}
	class OyeTab implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			if( tpConsole.getTitleAt(tpConsole.getSelectedIndex()).contains("*C")){
				tpConsole.setTitleAt(tpConsole.getSelectedIndex(), "Cuadruple");
			}
		}
	}
}
