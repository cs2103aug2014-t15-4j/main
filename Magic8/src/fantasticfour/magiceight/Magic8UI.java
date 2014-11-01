package fantasticfour.magiceight;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.MatteBorder;

public class Magic8UI {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static Magic8TaskList taskManager = null;
	private static String command, filename;
	private static JTextField commandLine; 
	static JTextArea taskListView; 
	static JTextArea confirmDialog; 
	static JFrame frameMagic8UI;
	private JPanel colorPanel1;
	private JPanel colorPanel2;
	private JPanel colorPanel3;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;

	TrayIcon trayIcon;
	SystemTray tray;
	
	private static String NAME_TITLE = "Magic 8";
	private static Insets marginInsets = new Insets(3,3,3,3); 

	public Magic8UI() {
		initialize();
		launch();
	}

    private void write(String message) {
    	taskListView.setText(message);
    }
    
    private void writeln(String message) {
    	write(message + "\n");
    }
    
    private static void invoke() throws IOException {
        new Magic8Controller(command, taskManager);
    }
    
	public void launch() {
		commandLine.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg){
				String inputStr = commandLine.getText(); 
				
				System.out.println("" + inputStr);
				
			/*	if (inputStr != true){
				  while(true) {
			            try {
			                command = br.readLine();
			                invoke();
			            } catch (IOException e) {
			                System.out.println(e.toString());
			            } catch (IllegalArgumentException e) {
			                System.out.println(e.toString());
			            }
			        }
				}*/
				if((taskManager == null)&&(!inputStr.equalsIgnoreCase("exit")||
						!inputStr.equalsIgnoreCase("help")|| !inputStr.equalsIgnoreCase("-h"))) {
						writeln("");
						try {
							taskManager = new Magic8TaskList(inputStr);
						} catch (IOException | ParseException e) {
							e.printStackTrace();
						}
				} else if ((taskManager == null)&&(inputStr.equalsIgnoreCase("exit"))&&
						(!inputStr.equalsIgnoreCase("help")|| !inputStr.equalsIgnoreCase("-h"))) {
					System.exit(0);
				} else {
					try {
						new Magic8Controller(inputStr, taskManager);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		        
				//this prints the input text on the display
//				writeln(taskListView.getText());
				
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
	}
	
	private static void magic8UIInit() throws IOException, ParseException {        
        filename = br.readLine();        
        taskManager = new Magic8TaskList(filename);
    }
	
	public class ClockPane extends JPanel {
	        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
			private JLabel clock;

	        public ClockPane() {
	            setLayout(new BorderLayout());
	            clock = new JLabel();
	            clock.setHorizontalAlignment(JLabel.CENTER);
	            clock.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN, 22));
	            tickTock();
	            add(clock);

	            Timer timer = new Timer(500, new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    tickTock();
	                }
	            });
	            timer.setRepeats(true);
	            timer.setCoalesce(true);
	            timer.setInitialDelay(0);
	            timer.start();
	        }

	        public void tickTock() {
	            clock.setText(DateFormat.getDateTimeInstance().format(new Date()));
	        }
	    }
   
	private void constructWindow() {
		frameMagic8UI = new JFrame(NAME_TITLE);
		frameMagic8UI.setResizable(true);
		frameMagic8UI.getContentPane().setBackground(Color.WHITE);
		frameMagic8UI.setBounds(100, 100, 750, 600);
		frameMagic8UI.getContentPane().setLayout(null);
		frameMagic8UI.setFont(new Font("Verdana", Font.PLAIN, 12));
		frameMagic8UI.setForeground(new Color(0, 0, 0));
		frameMagic8UI.setBackground(new Color(255, 205, 155));
		frameMagic8UI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JPanel displayPanel = new JPanel();
		displayPanel.setBounds(30, 30, 370, 400);
		displayPanel.setBackground(Color.RED);
		frameMagic8UI.getContentPane().add(displayPanel);
		
		taskListView = new JTextArea();
		taskListView.setEditable(false);
		taskListView.setLineWrap(true);
		taskListView.setWrapStyleWord(true);
		taskListView.setColumns(40);
		taskListView.setRows(25);
		taskListView.setBounds(30, 30, 10, 10);
		taskListView.setForeground(Color.DARK_GRAY);
		taskListView.setBackground(Color.WHITE);
		taskListView.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(taskListView);
		writeln("Welcome to Magic 8!\nFor assisstance, "
				+ "type 'help'  or '-h' and press ENTER.\n\nPlease "
				+ "enter the specified file name to continue.");
		taskListView.setBorder(new MatteBorder(marginInsets, Color.ORANGE));
		displayPanel.add(taskListView);

		JPanel confirmDialogPanel = new JPanel();
		confirmDialogPanel.setBounds(430, 100, 240, 330);
		frameMagic8UI.getContentPane().add(confirmDialogPanel);
		confirmDialogPanel.setBackground(Color.BLACK);
		confirmDialogPanel.setLayout(null);

		confirmDialog = new JTextArea();
		confirmDialog.setTabSize(5);
		confirmDialog.setEditable(false);
		confirmDialog.setLineWrap(true);
		confirmDialog.setWrapStyleWord(true);
		confirmDialog.setColumns(20);
		confirmDialog.setRows(25);
		confirmDialog.setForeground(Color.DARK_GRAY);
		confirmDialog.setBackground(Color.WHITE);
		confirmDialog.setFont(new Font("Arial", Font.BOLD, 12));
		confirmDialog.setBorder(new MatteBorder(marginInsets, Color.orange));
		confirmDialogPanel.add(confirmDialog);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 0, 615, 116);
		confirmDialogPanel.add(scrollPane_1);
		scrollPane_1.setBackground(new Color(220, 220, 220));
		scrollPane_1.setViewportView(confirmDialog);

		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(30, 450, 640, 50);
		frameMagic8UI.getContentPane().add(inputPanel);
		inputPanel.setBackground(Color.ORANGE);
		
		commandLine = new JTextField("");
	    System.out.println(commandLine);
	    commandLine.setFont(new Font("Verdana", Font.PLAIN, 14));
	    commandLine.setLocation(500, 350);
	    frameMagic8UI.add(commandLine);
	    
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
		commandLine.setColumns(48);
		commandLine.setBorder(new MatteBorder(marginInsets, Color.orange));
		inputPanel.add(commandLine);

		JPanel clockPanel = new JPanel();
		clockPanel.setBounds(430, 30, 240, 50);
		frameMagic8UI.getContentPane().add(clockPanel);
		clockPanel.setBackground(Color.YELLOW);
		
		frameMagic8UI.add(new ClockPane());
		frameMagic8UI.setLocationRelativeTo(null);
		clockPanel.add(new ClockPane());

		colorPanel1 = new JPanel();
		colorPanel1.setBackground(new Color(255, 151, 4));
		colorPanel1.setBounds(0, 444, 715, 85);
		frameMagic8UI.getContentPane().add(colorPanel1);

		colorPanel2 = new JPanel();
		colorPanel2.setBackground(new Color(255, 205, 155));
		colorPanel2.setBounds(0, 0, 715, 445);
		frameMagic8UI.getContentPane().add(colorPanel2);

		colorPanel3 = new JPanel();
		colorPanel3.setBackground(new Color(255, 150, 0));
		colorPanel3.setBounds(-1, 0, 715, 418);
		frameMagic8UI.getContentPane().add(colorPanel3);
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
    	try {
            magic8UIInit();
        } catch (IOException e) {
            System.out.println(e.toString());
            System.exit(0);
        } catch (ParseException e) {
            System.out.println(e.toString());
            System.exit(0);
        }
    	test.launch();
    }
}