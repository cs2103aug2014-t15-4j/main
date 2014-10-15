package fantasticfour.magiceight;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class Magic8UI extends JPanel implements Runnable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame mainFrame;
    private JPanel mainFramePanel;
    private JLabel label;
	private JButton exitButton;
//	private JComponent ch;
	
    //Launch the application.
    public static void main(String[] args) {
    /*	EventQueue.invokeLater(new Runnable() {
    		public void run() {
		       Runnable app = new Magic8UI();
		        try {
    		        app.mainFrame.setVisible(true);
    		    } catch (Exception e) {
    		        e.printStackTrace();
    		    }
    		}
    		});*/
    	Runnable app = new Magic8UI();
    	try {
    		SwingUtilities.invokeAndWait(app);
    	} catch (InvocationTargetException ex){
    		ex.printStackTrace();
    	} catch (InterruptedException ex){
    		ex.printStackTrace();
    	}
    	JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(300, 280));

        Magic8UI ch = new Magic8UI();
        frame.getContentPane().add(ch);
        frame.setUndecorated(true);
        
        MoveMouseListener mml = new MoveMouseListener(ch);
        ch.addMouseListener(mml);
        ch.addMouseMotionListener(mml);
    }
    
    //Create the application
    public Magic8UI() {
    	run();
//    	addComponentsToPane();
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
	
	 public void run() {
	        mainFrame = new JFrame("Magic 8 - Best Task Manager");
//	        label = new JLabel("Hello, world!");
//	        label.setFont(new Font("Calibri", Font.PLAIN, 12));
//	        label.setForeground(Color.GREEN);
	        Rectangle bounds = new Rectangle(200, 200, 800, 500);
	        mainFrame.setMaximizedBounds(bounds);
//	        mainFrame.setBounds(100, 100, 450, 300);
//	        mainFramePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
//   		mainFramePanel.setLayout(new GridLayout(9,5));
	        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	        mainFrame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					int confirmed = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to save and exit the program?",
							"Exit Magic 8", JOptionPane.YES_NO_OPTION);
					if(confirmed == JOptionPane.YES_OPTION) {
						System.exit(0);
					} 
				}
			});
	        
//	        mainFrame.add(label);
	        mainFrame.pack();
	        mainFrame.setVisible(true);	
	    
	        JSplitPane splitPane = new JSplitPane();
	        mainFrame.getContentPane().add(splitPane, BorderLayout.NORTH);
	        TextArea textArea = new TextArea();
	        splitPane.setLeftComponent(textArea);
	        TextArea textArea_1 = new TextArea();
	        mainFrame.getContentPane().add(textArea_1, BorderLayout.CENTER);
	 }
	 private static class MoveMouseListener implements MouseListener, MouseMotionListener {
		  JComponent target;
		  Point start_drag;
		  Point start_loc;

		  public MoveMouseListener(JComponent target) {
		    this.target = target;
		  }

		  public JFrame getFrame(Container target) {
		    if (target instanceof JFrame) {
		      return (JFrame) target;
		    }
		    return getFrame(target.getParent());
		  }

		  Point getScreenLocation(MouseEvent e) {
		    Point cursor = e.getPoint();
		    Point target_location = this.target.getLocationOnScreen();
		    return new Point((int) (target_location.getX() + cursor.getX()),
		        (int) (target_location.getY() + cursor.getY()));
		  }

		  public void mouseClicked(MouseEvent e) {
		  }

		  public void mouseEntered(MouseEvent e) {
		  }

		  public void mouseExited(MouseEvent e) {
		  }

		  public void mousePressed(MouseEvent e) {
		    this.start_drag = this.getScreenLocation(e);
		    this.start_loc = this.getFrame(this.target).getLocation();
		  }

		  public void mouseReleased(MouseEvent e) {
		  }

		  public void mouseDragged(MouseEvent e) {
		    Point current = this.getScreenLocation(e);
		    Point offset = new Point((int) current.getX() - (int) start_drag.getX(),
		        (int) current.getY() - (int) start_drag.getY());
		    JFrame frame = this.getFrame(target);
		    Point new_location = new Point(
		        (int) (this.start_loc.getX() + offset.getX()), (int) (this.start_loc
		            .getY() + offset.getY()));
		    frame.setLocation(new_location);
		  }

		  public void mouseMoved(MouseEvent e) {
		  }
	 }
}

