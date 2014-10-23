package fantasticfour.magiceight;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Magic8UI extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private static JTextArea commandLine = new JTextArea(1,20);
	private static JTextArea listOfTasks = new JTextArea(10, 15);
	private static JTextArea confirmDialog = new JTextArea(10, 5);
	
	private static int DIM_WIDTH = 600;
	private static int DIM_HEIGHT = 400;
	
	private static String NAME_TITLE = "Magic 8";

public Magic8UI() {
	run();
}

public void run() {
	JFrame mainFrame = new JFrame(NAME_TITLE);
	Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    setPreferredSize(new Dimension(DIM_WIDTH, DIM_HEIGHT));
    Dimension windowSize = new Dimension(getPreferredSize());
    int wdwLeft = screenSize.width / 2 - windowSize.width / 2;
    int wdwTop = screenSize.height / 2 - windowSize.height / 2;
    setLocation(wdwLeft, wdwTop);
    
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    commandLine.setFont(new Font("Verdana", Font.BOLD, 14));
    commandLine.setLocation(500, 350);
    mainFrame.add(commandLine);
    
    pack();
    setVisible(true);
  }

  public static void main(String[] args) {
	  EventQueue.invokeLater(new Runnable() {
          public void run() {
              final Magic8UI frame = new Magic8UI();
              frame.setVisible(true);
          }
	  });
	  
  }
}