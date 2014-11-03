package fantasticfour.magiceight;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class Magic8UI {
    private final static Integer WINDOW_HEIGHT = 800;
    private final static Integer WINDOW_WIDTH = 1200;
    private final static String[][] EMPTY_ROW = {{"","","",""}};
    private final static Integer TEXT_PANEL_LENGTH = 120;
    
    private Magic8Controller controller;
    private ArrayList<Magic8Task> tasks;

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static Magic8TaskList taskManager = null;
//	private static String command; 
	private static String filename;
	private static JTextField commandLine; 
	private JTable table;
	private DefaultTableModel model; 
	static JTextArea taskListView; 
	static JTextArea confirmDialog; 
	static JFrame frameMagic8UI;
	private JPanel colorPanel2;
	private JScrollPane scrollPane;
	static JLabel lblMonth, lblYear;
	static JButton btnPrev, btnNext;
	static JTable tblCalendar;
	static JFrame frmMain;
	static DefaultTableModel mtblCalendar; //Table model
	static JScrollPane stblCalendar; //The scrollpane	
    static JPanel pnlCalendar, calPanel; //The panel
    static int realDay, realMonth, realYear, currentMonth, currentYear;

	private final static String emptyString = "";
	private static int timerDelay = 500;
	private static int timerInitialDelay = 0;
	static Object[][] objects = new Object[9][5];
	
	TrayIcon trayIcon;
	SystemTray tray;
	
	public static final int NORMAL = 0;
	public static final int ICONIFIED = 1;
	public static final int MAXIMIZED_HORIZ = 2;
	public static final int MAXIMIZED_VERT = 4;
	public static final int MAXIMIZED_BOTH = 6;
	
	private static String NAME_TITLE = "Magic 8";
	private static String MESSAGE_WELCOME = "Welcome to Magic 8!\nFor assisstance, "
			+ "type 'help'  or '-h' and press ENTER.\n\nPlease "
			+ "enter the specified file name to continue.";

	public Magic8UI() {
		initialize();
		launch();
	}
	
	public void updateTable() {
	    if (model.getRowCount() > 0) {
	        for (int i = model.getRowCount() - 1; i > -1; i--) {
	            model.removeRow(i);
	        }
	    }
	    
	    for(Integer index = 0; index < tasks.size(); index++) {
	        String id = "";
	        String desc = "";
	        String tags = "";
	        String deadline = "";
	        Magic8Task task = tasks.get(index);
	        id = (++index).toString();
	        desc = task.getDesc();
	        if(task.getTags().size() > 0) {
    	        for(String tag : task.getTags()) {
    	            tags += tag + " ";
    	        }
	        } else {
	            tags = "-";
	        }
	        if(task.getEndTime() == null) {
	            deadline = "-";
	        } else {
	            deadline = new SimpleDateFormat("dd/MM/yyyy").format(task.getEndTime().getTime());
	        }
	        model.addRow(new Object[]{id, desc, tags, deadline});
	    }
	}

	public void launch() {
		commandLine.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg){
				String confirmText = emptyString;
				String inputStr = commandLine.getText();		
				
				if((taskManager == null)&&(!inputStr.equalsIgnoreCase("exit")||
					!inputStr.equalsIgnoreCase("help")|| !inputStr.equalsIgnoreCase("-h"))) {
					try {
						taskManager = new Magic8TaskList(inputStr);
						confirmText += inputStr + " is opened\n";
					} catch (IOException | ParseException e) {
						e.printStackTrace();
					}
				} else if ((taskManager == null)&&(inputStr.equalsIgnoreCase("exit"))) {
					System.exit(0);
				} else {
					try {
						controller = new Magic8Controller(inputStr, taskManager);
						confirmText += controller.getStatusMessage();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
                tasks = taskManager.getAllTasks();
                updateTable();
				confirmDialog.setText(confirmText);
				commandLine.setText(emptyString);
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
            clock.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN, 16));
            clock.setOpaque(true);
            clock.setBackground(new Color(255,205,155));
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

	private void constructWindow() {
		frameMagic8UI = new JFrame(NAME_TITLE);
		frameMagic8UI.setResizable(false);
		frameMagic8UI.getContentPane().setBackground(Color.WHITE);
		frameMagic8UI.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		frameMagic8UI.getContentPane().setLayout(null);
		frameMagic8UI.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		frameMagic8UI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// Big Window
		JPanel displayPanel = new JPanel();
		displayPanel.setBounds(40, 70, 580, 540);
		displayPanel.setBackground(new Color(255, 205, 155));
		frameMagic8UI.getContentPane().add(displayPanel);
		/*	
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
		taskListView.setMargin(marginInsets);
		
		displayPanel.add(scrollPane);
		scrollPane.setViewportView(taskListView);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBackground(new Color(220, 220, 220));
		displayPanel.add(taskListView);
		*/
//		displayPanel.setBounds(34, 80, 705, 378);
//		frameMagic8UI.getContentPane().add(displayPanel);
//		tableDisplay = new JPanel();
//		tableDisplay.setBackground(Color.WHITE);
		
		// Table
		model = new DefaultTableModel();
		table = new JTable(model);
		model.addColumn("Id");
        model.addColumn("Description");
        model.addColumn("Tags");
        model.addColumn("Deadline");
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		
		scrollPane = new JScrollPane(table);
		displayPanel.add(scrollPane, BorderLayout.CENTER);
		displayPanel.revalidate();
        scrollPane.setMinimumSize(new Dimension(680,670));

		// Small Window
		JPanel confirmDialogPanel = new JPanel();
		confirmDialogPanel.setBounds(720, 260, 400, 400);
		frameMagic8UI.getContentPane().add(confirmDialogPanel);
		confirmDialogPanel.setBackground(new Color(255,205,155));
		confirmDialogPanel.setLayout(null);

		confirmDialog = new JTextArea();
//		confirmDialog.setTabSize(5);
		confirmDialog.setEditable(false);
		confirmDialog.setLineWrap(true);
		confirmDialog.setWrapStyleWord(true);
		confirmDialog.setColumns(10);
		confirmDialog.setRows(15);
		confirmDialog.setBounds(0, 0, 800, 350);
		confirmDialog.setForeground(Color.DARK_GRAY);
		confirmDialog.setBackground(Color.WHITE);
		confirmDialog.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		confirmDialog.setBorder(BorderFactory.createCompoundBorder(
		        confirmDialog.getBorder(), 
		        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		confirmDialogPanel.add(confirmDialog);
		confirmDialog.setText(MESSAGE_WELCOME);

        // Text Input Background Panel
		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(0, WINDOW_HEIGHT-TEXT_PANEL_LENGTH, WINDOW_WIDTH, TEXT_PANEL_LENGTH);
		frameMagic8UI.getContentPane().add(inputPanel);
		inputPanel.setBackground(new Color(255,151,4));
		
		// Input Field
	    
	    commandLine = new JTextField();
	    inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
		commandLine.putClientProperty("JTextField.variant", "search");
		commandLine.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		commandLine.setForeground(new Color(0, 0, 0));
		commandLine.setBackground(new Color(255, 255, 255));
		commandLine.setColumns(90);
		commandLine.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
		inputPanel.add(commandLine);

		// Clock Panel
		JPanel clockPanel = new JPanel();
		clockPanel.setBounds(WINDOW_WIDTH-270, WINDOW_HEIGHT-165, 180, 45);
		frameMagic8UI.getContentPane().add(clockPanel);
		clockPanel.setOpaque(false);
		clockPanel.setBackground(new Color(255,205,155,0));
		
		frameMagic8UI.add(new ClockPane());
		frameMagic8UI.setLocationRelativeTo(null);
		clockPanel.add(new ClockPane());
		
		CalendarProgram.frameSetUp(frameMagic8UI);
		frameMagic8UI.setLocationRelativeTo(null);

		// Overall Background Panel
		colorPanel2 = new JPanel();
		colorPanel2.setBackground(new Color(255, 205, 155));
		colorPanel2.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
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
	
	/*
	public class MyCellRenderer extends JTextArea implements TableCellRenderer {
        public MyCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
         }

        public Component getTableCellRendererComponent(JTable table, Object
                value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((String) value);//or something in value, like value.getNote()...
            setSize(table.getColumnModel().getColumn(column).getWidth(),
                    getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                    table.setRowHeight(row, getPreferredSize().height);
            }
            return this;
        }
    }
    */

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

		//Added a 'Option' option to the menu when right clicked
		defaultItem = new MenuItem("Open");
		defaultItem.addActionListener(openListener);
		popup.add(defaultItem);

        //Added a 'Exit' option to the menu when right clicked
        defaultItem = new MenuItem("Exit");
        defaultItem.addActionListener(exitListener);
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