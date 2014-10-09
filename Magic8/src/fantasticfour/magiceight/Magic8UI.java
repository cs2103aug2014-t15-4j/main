package fantasticfour.magiceight;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import java.awt.TextArea;
import javax.swing.JSplitPane;

public class Magic8UI {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Magic8UI window = new Magic8UI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
        frame = new JFrame();
        frame.setBounds(100, 100, 665, 520);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JSplitPane splitPane = new JSplitPane();
        frame.getContentPane().add(splitPane, BorderLayout.NORTH);
        
        TextArea textArea = new TextArea();
        splitPane.setLeftComponent(textArea);
        
        TextArea textArea_1 = new TextArea();
        frame.getContentPane().add(textArea_1, BorderLayout.CENTER);
    }

}
