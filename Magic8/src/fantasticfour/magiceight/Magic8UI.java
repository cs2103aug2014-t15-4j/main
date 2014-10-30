package fantasticfour.magiceight;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

public class Magic8UI {
	
	private Magic8TaskList taskManager;
	private static String command;
	private static JTextField commandLine; 
	static JTextArea listOfTasks; 
	static JTextArea confirmDialog; 
	static JFrame frameMagic8UI;
	private JPanel colorPanel1;
	private JPanel colorPanel2;
	private JPanel colorPanel3;
	private JPanel colorPanel4;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;

	TrayIcon trayIcon;
	SystemTray tray;
	
	private static String NAME_TITLE = "Magic 8";
	private static int DIM_WIDTH = 600;
	private static int DIM_HEIGHT = 400;

	public Magic8UI() {
		initialize();
		launch();
	}

    private void write(String message) {
    	listOfTasks.setText(message);
    }
    
    private void writeln(String message) {
    	write(message + "\n");
    }
    
	public void launch() {
		
		commandLine.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg){
				String inputStr = commandLine.getText(); 
				
				System.out.println("this" + inputStr);
				
				if(taskManager == null) {
					try {
						taskManager = new Magic8TaskList(inputStr);
					} catch (IOException | ParseException e) {
						// Auto-generated catch block
						e.printStackTrace();
					}
				} else {
				
					try {
						new Magic8Controller(inputStr, taskManager);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				//this prints the input text on the display
				writeln(listOfTasks.getText() + inputStr);
				
				//this clears the input field
				commandLine.setText("");
			}
		});
		
	}
	
	private void initialize() {
		constructWindow();
		
		/* TODO
		if(checkSystemTraySupport()){
			initSystemTray();
		}
		
		//Activate Jfram windowstate listener for hiding programe into system tray
		activateWindowStateListener();
		*/
		
	    frameMagic8UI.pack();
	    frameMagic8UI.setVisible(true); 
	    
	    taskManager = null;
	}
	
	private void constructWindow() {
		frameMagic8UI = new JFrame();/*
		Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
	    setPreferredSize(new Dimension(DIM_WIDTH, DIM_HEIGHT));
	    Dimension windowSize = new Dimension(getPreferredSize());
	    int wdwLeft = screenSize.width / 2 - windowSize.width / 2;
	    int wdwTop = screenSize.height / 2 - windowSize.height / 2;
	    setLocation(wdwLeft, wdwTop);*/
		frameMagic8UI.setResizable(true);
		frameMagic8UI.setFont(new Font("Verdana", Font.PLAIN, 12));
		frameMagic8UI.setForeground(new Color(0, 0, 0));
		frameMagic8UI.setBackground(new Color(255, 205, 155));
		frameMagic8UI.setTitle(NAME_TITLE);
		Insets marginInsets = new Insets(2,2,2,2); 

	    frameMagic8UI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
		commandLine = new JTextField("");
	    System.out.println(commandLine);
	    commandLine.setFont(new Font("Verdana", Font.PLAIN, 14));
	    commandLine.setLocation(500, 350);
	    frameMagic8UI.add(commandLine);
		frameMagic8UI.getContentPane().setBackground(Color.WHITE);
		frameMagic8UI.setBounds(100, 100, 768, 608);
		frameMagic8UI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frameMagic8UI.getContentPane().setLayout(null);

		JPanel confirmDialogpanel = new JPanel();
		confirmDialogpanel.setBounds(37, 375, 639, 116);
		frameMagic8UI.getContentPane().add(confirmDialogpanel);
		confirmDialogpanel.setBackground(Color.WHITE);
		confirmDialogpanel.setLayout(null);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new MatteBorder(marginInsets, Color.orange));
		scrollPane_1.setBounds(12, 0, 615, 116);
		confirmDialogpanel.add(scrollPane_1);
		scrollPane_1.setBackground(new Color(220, 220, 220));

		confirmDialog = new JTextArea();
		confirmDialog.setTabSize(10);
		scrollPane_1.setViewportView(confirmDialog);
		confirmDialog.setWrapStyleWord(true);
		confirmDialog.setColumns(52);
		confirmDialog.setForeground(Color.DARK_GRAY);
		confirmDialog.setBackground(Color.WHITE);
		confirmDialog.setRows(4);
		confirmDialog.setFont(new Font("Arial", Font.BOLD, 12));
		confirmDialog.setEditable(false);
		confirmDialog.setBorder(new MatteBorder(marginInsets, Color.orange));

		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(51, 319, 625, 44);
		frameMagic8UI.getContentPane().add(inputPanel);
		inputPanel.setBackground(new Color(255, 255, 255));

		commandLine = new JTextField();
		commandLine.setBounds(12, 0, 601, 44);
		commandLine.setMargin(marginInsets);
		commandLine.setCaretColor(new Color(0, 0, 0));
		commandLine.setAlignmentX(Component.RIGHT_ALIGNMENT);
		commandLine.putClientProperty("JTextField.variant", "search");
		inputPanel.setLayout(null);
		commandLine.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		commandLine.setForeground(new Color(0, 0, 0));
		commandLine.setBackground(new Color(255, 255, 255));
		inputPanel.add(commandLine);
		commandLine.setColumns(49);
		commandLine.setBorder(new MatteBorder(marginInsets, Color.orange));

		JLayeredPane displayPanel = new JLayeredPane();
		displayPanel.setBackground(new Color(220, 220, 220));
		displayPanel.setBounds(38, 25, 600, 280);
		frameMagic8UI.getContentPane().add(displayPanel);
		displayPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(marginInsets, Color.orange));

		listOfTasks = new JTextArea();
		listOfTasks.setColumns(40);
		scrollPane.setViewportView(listOfTasks);
		listOfTasks.setRows(15);
		listOfTasks.setForeground(Color.DARK_GRAY);
		listOfTasks.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		listOfTasks.setBackground(Color.WHITE);
		listOfTasks.setEditable(false);
		writeln("Welcome to Magic 8!\nFor a quick guide, "
				+ "type help  or -h and press ENTER.");
		listOfTasks.setBounds(14, 22, 10, 10);
		displayPanel.add(listOfTasks);
		listOfTasks.setBorder(new MatteBorder(marginInsets, Color.orange));
/*
		colorPanel1 = new JPanel();
		colorPanel1.setBackground(new Color(205, 92, 92));
		colorPanel1.setBounds(37, 319, 19, 44);
		frameMagic8UI.getContentPane().add(colorPanel1);
*/
		colorPanel2 = new JPanel();
		colorPanel2.setBackground(new Color(255, 151, 4));
		colorPanel2.setBounds(0, 444, 715, 85);
		frameMagic8UI.getContentPane().add(colorPanel2);

		JPanel panel = new JPanel();
		panel.setBounds(30, 25, 400, 246);
		frameMagic8UI.getContentPane().add(panel);
		panel.setBackground(Color.WHITE);

		colorPanel3 = new JPanel();
		colorPanel3.setBounds(0, 0, 715, 445);
		frameMagic8UI.getContentPane().add(colorPanel3);
		colorPanel3.setBackground(new Color(255, 205, 155));

		colorPanel4 = new JPanel();
		colorPanel4.setBackground(new Color(255, 150, 0));
		colorPanel4.setBounds(-1, 0, 715, 418);
		frameMagic8UI.getContentPane().add(colorPanel4);
	}

	void setFrameIcon(){

		frameMagic8UI.setIconImage(Toolkit.getDefaultToolkit().getImage("Magic8Logo.png"));	
	}

	boolean checkSystemTraySupport(){

		if(SystemTray.isSupported()){

			System.out.println("system tray supported");
			tray = SystemTray.getSystemTray();
			return true;
		}

		System.out.println("System tray not supported");
		return false;
	}
	
    public static void main(String[] args) {   
    	Magic8UI test = new Magic8UI();
    	test.launch();
    }

}