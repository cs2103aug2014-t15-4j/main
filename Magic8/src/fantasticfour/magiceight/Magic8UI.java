package fantasticfour.magiceight;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Magic8UI {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static Magic8TaskList taskManager = null;
//	private static String command; 
	private static String filename;
	private static JTextField commandLine; 
	static JTextArea taskListView; 
	static JTextArea confirmDialog; 
	static JFrame frameMagic8UI;
	private JPanel colorPanel1;
	private JPanel colorPanel2;
	private JScrollPane scrollPane;
	static JLabel lblMonth, lblYear;
	static JButton btnPrev, btnNext;
	static JTable tblCalendar;
	static JComboBox cmbYear;
	static JFrame frmMain;
	static Container pane;
	static DefaultTableModel mtblCalendar; //Table model
	static JScrollPane stblCalendar; //The scrollpane	
    static JPanel pnlCalendar, calPanel; //The panel
    static int realDay, realMonth, realYear, currentMonth, currentYear;

	
	private static String clearText = "";
	private static int timerDelay = 500;
	private static int timerInitialDelay = 0;
	
	TrayIcon trayIcon;
	SystemTray tray;
	
	public static final int NORMAL = 0;
	public static final int ICONIFIED = 1;
	public static final int MAXIMIZED_HORIZ = 2;
	public static final int MAXIMIZED_VERT = 4;
	public static final int MAXIMIZED_BOTH = 6;
	
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
    
	public void launch() {
		commandLine.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg){
				String inputStr = commandLine.getText(); 
				
				System.out.println("" + inputStr);
				
				if((taskManager == null)&&(!inputStr.equalsIgnoreCase("exit")||
						!inputStr.equalsIgnoreCase("help")|| !inputStr.equalsIgnoreCase("-h"))) {
						writeln(clearText);
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
				commandLine.setText(clearText);
			}
		});
	}
	
	private void initialize() {
		constructWindow();
		
		if(checkSystemTraySupport()){
			initSystemTray();
		}
		
		//Activate Jfram windowstate listener for hiding programe into system tray
		activateWindowStateListener();
		
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
//	            clock.setOpaque(false);
//	            clock.setBackground(new Color(0,255,0,0));
	            clock.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN, 20));
	            tickTock();
	            add(clock);

	            Timer timer = new Timer(timerDelay, new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    tickTock();
	                }
	            });
	            timer.setRepeats(true);
	            timer.setCoalesce(true);
	            timer.setInitialDelay(timerInitialDelay);
	            timer.start();
	        }

	        public void tickTock() {
	            clock.setText(DateFormat.getDateTimeInstance().format(new Date()));
	        }
	    }
   
	public class calendarProgram {{
		//Prepare frame
		pane = frameMagic8UI.getContentPane(); //Get content pane
		pane.setLayout(null); //Apply null layout

		//Create controls
		lblMonth = new JLabel ("January");
		lblYear = new JLabel ("Change year:");
		cmbYear = new JComboBox();
		btnPrev = new JButton ("<<");
		btnNext = new JButton (">>");
		mtblCalendar = new DefaultTableModel();
		tblCalendar = new JTable(mtblCalendar);
		stblCalendar = new JScrollPane(tblCalendar);
		pnlCalendar = new JPanel(null);
		
		pnlCalendar.setBorder(BorderFactory.createTitledBorder("Calendar")); //Set border
		pnlCalendar.setBounds(0, 0, 320, 335);
		lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 100, 25);
		lblYear.setBounds(10, 305, 80, 20);
		cmbYear.setBounds(230, 305, 80, 20);
		btnPrev.setBounds(10, 25, 50, 25);
		btnNext.setBounds(260, 25, 50, 25);
		stblCalendar.setBounds(10, 50, 300, 250);
		
		GregorianCalendar cal = new GregorianCalendar(); //Create calendar
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
		realMonth = cal.get(GregorianCalendar.MONTH); //Get month
		realYear = cal.get(GregorianCalendar.YEAR); //Get year
		currentMonth = realMonth; //Match month and year
		currentYear = realYear;

		for (int i=realYear-100; i<=realYear+100; i++){
		    cmbYear.addItem(String.valueOf(i));
		}
		//Add headers
		String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
		for (int i=0; i<7; i++){
		    mtblCalendar.addColumn(headers[i]);
		}
		tblCalendar.getParent().setBackground(tblCalendar.getBackground());
		tblCalendar.getTableHeader().setResizingAllowed(false);
		tblCalendar.getTableHeader().setReorderingAllowed(false);
		
		tblCalendar.setColumnSelectionAllowed(true);
		tblCalendar.setRowSelectionAllowed(true);
		tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tblCalendar.setRowHeight(38);
		mtblCalendar.setColumnCount(7);
		mtblCalendar.setRowCount(6);
		
		//Prepare calendar
		for (int i=realYear-100; i<=realYear+100; i++){
		    cmbYear.addItem(String.valueOf(i));
		}
		
		refreshCalendar (realMonth, realYear); //Refresh calendar
		
	}
	}
	
	public static void refreshCalendar(int month, int year){
	  String[] months = {"January", "February", "March", "April", "May", "June", "July", 
			  "August", "September", "October", "November", "December"};
	      int nod, som; //Number Of Days, Start Of Month
	      btnPrev.setEnabled(true); //Enable buttons at first
	      btnNext.setEnabled(true);
	      if (month == 0 && year <= realYear-10){btnPrev.setEnabled(false);} //Too early
	      if (month == 11 && year >= realYear+100){btnNext.setEnabled(false);} //Too late
	      lblMonth.setText(months[month]); //Refresh the month label (at the top)
	      lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar
	      cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
	      
	    //Get first day of month and number of days
	      GregorianCalendar cal = new GregorianCalendar(year, month, 1);
	      nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
	      som = cal.get(GregorianCalendar.DAY_OF_WEEK);
	      
	    //Clear table
		for (int i=0; i<6; i++){
		    for (int j=0; j<7; j++){
		        mtblCalendar.setValueAt(null, i, j);
		    }
		}
		
		for (int i=1; i<=nod; i++){
	        int row = new Integer((i+som-2)/7);
	        int column  =  (i+som-2)%7;
	        mtblCalendar.setValueAt(i, row, column);
	    }
		
		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer()); //Apply renderer
	}

	static class tblCalendarRenderer extends DefaultTableCellRenderer{
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6){ //Week-end
                setBackground(new Color(255, 220, 220));
            }
            else{ //Week
                setBackground(new Color(255, 255, 255));
            }
            if (value != null){
                if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
                    setBackground(new Color(220, 220, 255));
                }
            }
            setBorder(null);
            setForeground(Color.black);
            return this; 
        }
    }
	
	static class btnPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
		}{
		if (currentMonth == 0){ //Back one year
                currentMonth = 11;
                currentYear -= 1;
            }
            else{ //Back one month
                currentMonth -= 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
	
    static class btnNext_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (currentMonth == 11){ //Foward one year
                currentMonth = 0;
                currentYear += 1;
            }
            else{ //Foward one month
                currentMonth += 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    
	static class cmbYear_Action implements ActionListener{
	    public void actionPerformed (ActionEvent e){
	        if (cmbYear.getSelectedItem() != null){
	            String b = cmbYear.getSelectedItem().toString();
	            currentYear = Integer.parseInt(b);
	            refreshCalendar(currentMonth, currentYear);
	        }
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
		displayPanel.setBackground(new Color(255,205,155));
		frameMagic8UI.getContentPane().add(displayPanel);
		
		taskListView = new JTextArea();
		taskListView.setEditable(false);
		taskListView.setLineWrap(true);
		taskListView.setWrapStyleWord(true);
		taskListView.setColumns(40);
		taskListView.setRows(25);
		taskListView.setBounds(0, 0, 10, 10);
		taskListView.setForeground(Color.DARK_GRAY);
		taskListView.setBackground(Color.WHITE);
		taskListView.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(330, 5, 15, 380);
		displayPanel.add(scrollPane);
		scrollPane.setViewportView(taskListView);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		writeln("Welcome to Magic 8!\nFor assisstance, "
				+ "type 'help'  or '-h' and press ENTER.\n\nPlease "
				+ "enter the specified file name to continue.");
		scrollPane.setBackground(new Color(220, 220, 220));
		displayPanel.add(taskListView);

		JPanel confirmDialogPanel = new JPanel();
		confirmDialogPanel.setBounds(430, 250, 240, 180);
		frameMagic8UI.getContentPane().add(confirmDialogPanel);
		confirmDialogPanel.setBackground(new Color(255,205,155));
		confirmDialogPanel.setLayout(null);

		confirmDialog = new JTextArea();
		confirmDialog.setTabSize(5);
		confirmDialog.setEditable(false);
		confirmDialog.setLineWrap(true);
		confirmDialog.setWrapStyleWord(true);
		confirmDialog.setColumns(10);
		confirmDialog.setRows(15);
		confirmDialog.setBounds(0, 0, 240, 170);
		confirmDialog.setForeground(Color.DARK_GRAY);
		confirmDialog.setBackground(Color.WHITE);
		confirmDialog.setFont(new Font("Arial", Font.BOLD, 12));
		confirmDialogPanel.add(confirmDialog);

		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(30, 460, 640, 50);
		frameMagic8UI.getContentPane().add(inputPanel);
		inputPanel.setBackground(new Color(255,151,4));
		
		commandLine = new JTextField("");
	    System.out.println(commandLine);
	    commandLine.setFont(new Font("Verdana", Font.PLAIN, 14));
	    commandLine.setLocation(500, 350);
	    frameMagic8UI.add(commandLine);
	    
	    commandLine = new JTextField();
		commandLine.setBounds(5, 5, 630, 40);
		commandLine.setMargin(marginInsets);
		commandLine.setCaretColor(new Color(0, 0, 0));
		commandLine.setAlignmentX(Component.RIGHT_ALIGNMENT);
		commandLine.putClientProperty("JTextField.variant", "search");
		inputPanel.setLayout(null);
		commandLine.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		commandLine.setForeground(new Color(0, 0, 0));
		commandLine.setBackground(new Color(255, 255, 255));
		commandLine.setColumns(48);
		inputPanel.add(commandLine);

		JPanel clockPanel = new JPanel();
		clockPanel.setBounds(470, 0, 240, 50);
		frameMagic8UI.getContentPane().add(clockPanel);
		clockPanel.setBackground(new Color(255,205,155));
		
		frameMagic8UI.add(new ClockPane());
		frameMagic8UI.setLocationRelativeTo(null);
		clockPanel.add(new ClockPane());
		
		JPanel calendarPanel = new JPanel();
		calendarPanel.setBounds(430, 90, 240, 150);
		frameMagic8UI.getContentPane().add(calendarPanel);
		calendarPanel.setBackground(Color.BLACK);
//		calendarPanel.setBackground(new Color(255,205,155));
		calendarPanel.setLayout(null);
		
		colorPanel1 = new JPanel();
		colorPanel1.setBackground(new Color(255, 151, 4));
		colorPanel1.setBounds(0, 444, 715, 85);
		frameMagic8UI.getContentPane().add(colorPanel1);

		colorPanel2 = new JPanel();
		colorPanel2.setBackground(new Color(255, 205, 155));
		colorPanel2.setBounds(0, 0, 715, 445);
		frameMagic8UI.getContentPane().add(colorPanel2);
	}

	void setFrameIcon(){

		frameMagic8UI.setIconImage(Toolkit.getDefaultToolkit().getImage("lib/Magic8Logo.png"));	
	}

	boolean checkSystemTraySupport(){

		if(SystemTray.isSupported()){

			System.out.println("system tray supported");
			tray = SystemTray.getSystemTray();
			return true;
		}

		System.out.println("system tray not supported, check taskbar when minimized");
		return false;
	}

	void initSystemTray(){

		//Set image when program is in system tray
		Image image = Toolkit.getDefaultToolkit().getImage("lib/Magic8Logo.png");

		//Action Listener to exit the programme ONLY when in system tray
		ActionListener exitListener = new ActionListener() {

			//If clicked on the exit option
			public void actionPerformed(ActionEvent e) {

				System.out.println("Exiting Magic 8....");
				System.exit(0);

			}
		};

		//Action Listener to open the programme frame ONLY when in system tray
		ActionListener openListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				frameMagic8UI.setVisible(true);
				frameMagic8UI.setExtendedState(JFrame.NORMAL);
			}

		};

		/* Pop up Menu @ system tray */
		PopupMenu popup = new PopupMenu();
		MenuItem defaultItem;

		//Added a 'Exit' option to the menu when right clicked
		defaultItem = new MenuItem("Exit");
		defaultItem.addActionListener(exitListener);
		popup.add(defaultItem);

		//Added a 'Option' option to the menu when right clicked
		defaultItem = new MenuItem("Open");
		defaultItem.addActionListener(openListener);
		popup.add(defaultItem);

		trayIcon = new TrayIcon(image, "Magic 8", popup);
		trayIcon.setImageAutoSize(true);

	}

	void activateWindowStateListener(){

		frameMagic8UI.addWindowStateListener(new WindowStateListener() {

			public void windowStateChanged(WindowEvent e) {

				//If click on the minimize icon on the window, this function will 
				//detect the window new "ICONFIED" state and activate system tray
				if(e.getNewState() == ICONIFIED){

					try {

						tray.add(trayIcon);
						frameMagic8UI.setVisible(false);

						System.out.println("added to SystemTray");

					} catch (AWTException ex) {

						System.out.println("unable to add to tray");
					}
				}

				//If click on the 'open' open option to re-open the program,
				//this call will reinstate the JFrame's visibility and remove trayicon
				if(e.getNewState() == MAXIMIZED_BOTH || e.getNewState() == NORMAL){

					tray.remove(trayIcon);
					frameMagic8UI.setVisible(true);

				System.out.println("Tray icon removed");

				}
			}
		});
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