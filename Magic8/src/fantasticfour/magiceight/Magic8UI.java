package fantasticfour.magiceight;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Magic8UI {
	
	
	private static JTextField commandLine; 
	static JTextArea listOfTasks; 
	static JTextArea confirmDialog; 
	static JFrame frameMagic8UI;
	private JTabbedPane tabbedPane;
	private JTable table_1;
	private JPanel colorPanel1;
	private JPanel colorPanel2;
	private JPanel colorPanel3;
	private JPanel colorPanel4;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
//	TrayIcon trayIcon;
//	SystemTray tray;
	
//	private static int DIM_WIDTH = 600;
//	private static int DIM_HEIGHT = 400;
	
	private static String NAME_TITLE = "Magic 8";

	public Magic8UI() {
		initialize();
	}
	public void launch() {
		commandLine.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg){
				/*
				 * Below is just an example of what will happen when user presses enter after 
				 * typing something in the user input text field at the bottom of the GUI.
				 */
				String inputStr = commandLine.getText(); //this is to get the user input text
				String displayStr = listOfTasks.getText();
				if(displayStr.equals("Welcome to Magic 8!\nFor a quick guide, "
						+ "	type '-h' or 'help' and press ENTER.")){
					displayStr = "";
				}

				//this prints the input text on the display
				listOfTasks.setText(displayStr + "\n");
				//this clears the input field
				commandLine.setText("");

				if (inputStr.startsWith("help")){
					try {
						Magic8UITerminal.displayHelp(inputStr);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else {
					Magic8UITerminal.display(inputStr);
				}
			}
		});
	}
	private void initialize() {
		frameMagic8UI = new JFrame();
		frameMagic8UI.setResizable(false);
		frameMagic8UI.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		frameMagic8UI.setForeground(new Color(0, 0, 0));
		frameMagic8UI.setBackground(new Color(255, 255, 255));
		frameMagic8UI.setTitle(NAME_TITLE);
	    
	    frameMagic8UI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    commandLine.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
	    commandLine.setLocation(500, 350);
	    frameMagic8UI.add(commandLine);
	    frameMagic8UI.setResizable(false);
		frameMagic8UI.getContentPane().setBackground(Color.LIGHT_GRAY);
		frameMagic8UI.setBounds(100, 100, 768, 608);
		frameMagic8UI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frameMagic8UI.getContentPane().setLayout(null);

		JPanel confirmDialogpanel = new JPanel();
		confirmDialogpanel.setBounds(37, 375, 639, 116);
		frameMagic8UI.getContentPane().add(confirmDialogpanel);
		confirmDialogpanel.setBackground(Color.WHITE);
		confirmDialogpanel.setLayout(null);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
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

		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(51, 319, 625, 44);
		frameMagic8UI.getContentPane().add(inputPanel);
		inputPanel.setBackground(new Color(255, 255, 255));

		commandLine =  new JTextField();
		commandLine.setBounds(12, 0, 601, 44);
		commandLine.setMargin(new Insets(0, 0, 0, 0));
		commandLine.setCaretColor(new Color(0, 0, 0));
		commandLine.setAlignmentX(Component.RIGHT_ALIGNMENT);
		commandLine.putClientProperty("JTextField.variant", "search");
		inputPanel.setLayout(null);
		commandLine.setFont(new Font("Tahoma", Font.PLAIN, 14));
		commandLine.setForeground(new Color(165, 42, 42));
		commandLine.setBackground(new Color(255, 255, 255));
		inputPanel.add(commandLine);
		commandLine.setColumns(49);

		JLayeredPane displayPanel = new JLayeredPane();
		displayPanel.setBackground(new Color(220, 220, 220));
		displayPanel.setBounds(38, 25, 639, 282);
		frameMagic8UI.getContentPane().add(displayPanel);
		displayPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setBackground(new Color(245, 245, 245));
		tabbedPane.setBorder(null);
		tabbedPane.setFont(new Font("Levenim MT", Font.PLAIN, 13));
		displayPanel.add(tabbedPane);
		
	    launch();
	    frameMagic8UI.pack();
	    frameMagic8UI.setVisible(true); 
	}
}