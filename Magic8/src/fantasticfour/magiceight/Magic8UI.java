package fantasticfour.magiceight;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class Magic8UI implements Runnable{
    private JFrame mainFrame;
    private JPanel mainFramePanel;
    private JLabel label;
	private JButton exitButton;
	
    //Launch the application.
    public static void main(String[] args) {
    	EventQueue.invokeLater(new Runnable() {
    		public void run() {
    		    try {
    		        Magic8UI window = new Magic8UI();
    		        window.mainFrame.setVisible(true);
    		    } catch (Exception e) {
    		        e.printStackTrace();
    		    }
    		}
    		});
    }
    
    //Create the application
    public Magic8UI() {
    	run();
		//addExitListener(ExitListener);
    }
    
	protected void exit() {
		//getMainFrame().setTitle("ConfirmExit");
		exitButton = new JButton("Exit application");
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	/*	addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to save and exit the program?",
						"Exit Magic 8?", JOptionPane.YES_NO_OPTION);
				if(confirmed == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}*/
	
	 public void run() {
	        mainFrame = new JFrame("Magic 8 - Best Task Manager");
	        label = new JLabel("Hello, world!");
	        label.setFont(new Font("Calibri", Font.PLAIN, 12));
	        label.setForeground(Color.GREEN);
	        mainFrame.setBounds(100, 100, 450, 300);
//	        mainFramePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	        mainFramePanel.setLayout(new GridLayout(9,5));
	        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        mainFrame.addWindowListener(new WindowAdapter() {
	        	public void windowClosing(WindowEvent e) {
	        		mainFrame.setVisible(false);
	        		//Perform whatever operations needed before exit
	        		System.exit(0);
	        	}
	        });
	        
	        mainFrame.add(label);
	        mainFrame.pack();
	        mainFrame.setVisible(true);	        		
	       
	        JSplitPane splitPane = new JSplitPane();
	        mainFrame.getContentPane().add(splitPane, BorderLayout.NORTH);
	        TextArea textArea = new TextArea();
	        splitPane.setLeftComponent(textArea);
	        TextArea textArea_1 = new TextArea();
	        mainFrame.getContentPane().add(textArea_1, BorderLayout.CENTER);
	 }
}


