package fantasticfour.magiceight;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
//import javax.swing.JTextPane;
//import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JSplitPane;

public class Magic8UI implements Runnable{

    private JFrame frame;
    private JLabel label;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
    	Runnable app = new Magic8UI();
    	try {
    		SwingUtilities.invokeAndWait(app);
    	} catch (InvocationTargetException ex) {
    		ex.printStackTrace();
    	} catch (InterruptedException ex) {
    		ex.printStackTrace();
    	}
 /*       EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Magic8UI window = new Magic8UI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/
    }
    

    /**
     * Create the application.
     */
    public Magic8UI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("Magic8 - V0.1");
        label = new JLabel("Hello, world!");
        label.setFont(new Font("SansSerif", Font.PLAIN, 20));
        frame.setBounds(100, 100, 665, 520);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		frame.setVisible(false);
        		//Perform whatever operations needed before exit
        		System.exit(0);
        	}
        });
        
        frame.add(label);
        frame.pack();
        frame.setVisible(true);
        		
        JSplitPane splitPane = new JSplitPane();
        frame.getContentPane().add(splitPane, BorderLayout.NORTH);
        
        TextArea textArea = new TextArea();
        splitPane.setLeftComponent(textArea);
        
        TextArea textArea_1 = new TextArea();
        frame.getContentPane().add(textArea_1, BorderLayout.CENTER);
    }


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
