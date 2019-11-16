package GUIComponents;

import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class FrameAbout extends JDialog
{
    private static final long serialVersionUID = 1L;
	JLabel data,lblIcon,lblTec,lblTextTec,labelClip;
    AboutPanel AboutPanel;
    ImageIcon icon,resized,iconTec;
    JTextArea textArea,textAreaTec;
    public FrameAbout(JFrame container)
    {   
        labelClip = new JLabel();
        textAreaTec = new JTextArea();
        lblTec = new JLabel();
        textArea = new JTextArea(50,40);
        setTitle("About vaja");
        AboutPanel = new AboutPanel();
        setSize(575,502);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(container);
        setModal(true);
        getContentPane().add(AboutPanel);
    }
    class AboutPanel extends JPanel
    {
       private static final long serialVersionUID = 1L;

		public AboutPanel()
        {
         setLayout(null);
         Font fuenteAreaTexto = new Font("Franklin Gothic Book",Font.PLAIN,16 );
         textAreaTec.setBounds(150, 5, 350, 70);
         textAreaTec.setText("    Instituto Tecnológico de Culiacán\n Ingeniería en Sistemas Computacionales");
         textAreaTec.setFont(fuenteAreaTexto);
         textAreaTec.setOpaque(false);
         
         lblTec.setBounds(5,0,75,75);
         iconTec = new ImageIcon(FrameAbout.class.getResource("/images/tec_logo.png"));
         ImageIcon rideTec = new ImageIcon(iconTec.getImage().getScaledInstance(lblTec.getWidth(), lblTec.getHeight(), Image.SCALE_DEFAULT));
         lblTec.setIcon(rideTec);
         
         data = new JLabel(); 
         data.setBounds(5, 0, 100,50);
         
         lblIcon = new JLabel();
 		 lblIcon.setBorder(null);
 		 lblIcon.setBounds(5,100,70,70);
 		 icon = new ImageIcon(FrameAbout.class.getResource("/images/icono-96.png"));
 		 ImageIcon ridemencionado = new ImageIcon(icon.getImage().getScaledInstance(lblIcon.getWidth(), lblIcon.getHeight(), Image.SCALE_DEFAULT));
 		 lblIcon.setIcon(ridemencionado);
         
         textArea.setBounds(100, 100, 420, 300);
         textArea.setOpaque(false);
         textArea.setText("2019 Version 1.0 Software compilador de texto\nCompilador desarrollado por estudiantes "
         		+ "pertenecientes\nal Insituto Tecnologico de Culiacan,\nse prohibe su venta y distribucion."
         		+ "\n\nAuthor: SRMichas");
         textArea.setFont(fuenteAreaTexto);
         textArea.setEditable(false);
         textAreaTec.setEditable(false);
         
         add(textAreaTec);
         add(lblTec);
         add(textArea);
         add(data);
         add(lblIcon);
       }
    }
}