package fantasticfour.magiceight;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;

public class Magic8GUI extends javax.swing.JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private javax.swing.JTextField commandField;
    private javax.swing.JPanel commandPanel;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JTextArea notificationsField;
    private javax.swing.JPanel notificationsPanel;
    private javax.swing.JScrollPane notificationsScrollPane;
    private javax.swing.JTextArea outputField;
    private javax.swing.JPanel outputPanel;
    private javax.swing.JScrollPane outputScrollPane;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JSplitPane splitPaneMain;
    private javax.swing.JSplitPane splitPaneSub;
    private javax.swing.JTable table;
    private DefaultTableModel model;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JScrollPane tableScrollPane;
    
    private static String emptyString = "";
    private Magic8Controller controller;
    private ArrayList<Magic8Task> tasks;
    private ArrayList<String> keywords = new ArrayList<String>(5);
    private static final String COMMIT_ACTION = "commit";
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static Magic8TaskList taskManager = null;
    private static String filename;
    
    private static String NAME_TITLE = "Magic 8";
    private static String MESSAGE_WELCOME = "Welcome to Magic 8!\nFor assisstance, "
            + "type 'help'  or '-h' and press ENTER.\n\nPlease "
            + "enter the specified file name to continue.";
    public static final int NORMAL = 0;
    public static final int ICONIFIED = 1;
    public static final int MAXIMIZED_HORIZ = 2;
    public static final int MAXIMIZED_VERT = 4;
    public static final int MAXIMIZED_BOTH = 6;
    private static int timerDelay = 500;
    private static int timerInitialDelay = 0;
    
    TrayIcon trayIcon;
    SystemTray tray;
    
    /**
     * Creates new form Magic8UI
     */
    public Magic8GUI() {
        initComponents();
    }
    
    private static void magic8GUIInit() throws IOException, ParseException {        
        filename = br.readLine();        
        taskManager = new Magic8TaskList(filename);
    }
    
    public void launch() {
        commandField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg){
                String confirmText = emptyString;
                String inputStr = commandField.getText();        
                if((taskManager == null)&&(!inputStr.equalsIgnoreCase("exit")&&
                    !inputStr.equalsIgnoreCase("help")&& !inputStr.equalsIgnoreCase("-h"))) {
                    try {
                        taskManager = new Magic8TaskList(inputStr);
                        confirmText += inputStr + " is opened\n";
                        tasks = taskManager.getAllTasks(false);
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                } else if ((taskManager == null)&&(inputStr.equalsIgnoreCase("exit"))) {
                    System.exit(0);
                } else if ((inputStr.equalsIgnoreCase("help")||(inputStr.equalsIgnoreCase("-h")))){
                	helpPopup();
                } else if ((inputStr.equalsIgnoreCase("cal")||(inputStr.equalsIgnoreCase("-c")))){
                } else {
                    try {
                        controller = new Magic8Controller(inputStr, taskManager);
                        confirmText += controller.getStatusMessage();
                        tasks = controller.getTaskList();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                updateTable();
                notificationsField.setText(confirmText);
                commandField.setText(emptyString);
            }
        });
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
            String startTime = "";
            String endTime = "";
            String tags = "";
            Magic8Task task = tasks.get(index);
            Integer i = index+1;
            id = (i).toString();
            desc = task.getDesc();
            if(task.getTags().size() > 0) {
                for(String tag : task.getTags()) {
                    tags += tag + " ";
                }
            } else {
                tags = "-";
            }
            
            startTime = "-";
            endTime = "-";
            
            if (task.getStartTime() != null){
            	startTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS").format(task.getStartTime().getTime());
            }
            
            if (task.getEndTime() != null){
            	endTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS").format(task.getEndTime().getTime());
            }
            
            model.addRow(new Object[]{id, desc, startTime, endTime, tags});
        }
    }
    
    private void helpPopup() {
    	JOptionPane.showMessageDialog(splitPaneMain, 
    	        "                                              Help for Magic 8 Software:\n\n"
    	        + "Opening a file in Magic 8:\n"
    	        + "open [filename]\n\n"
    	        + "Adding a new task:\n"
    	        + "add [task description] from [dd/MM/yyyy HH:mm:SS] to [dd/MM/yyyy HH:mm:SS] [tags]\n\n"
    	        + "Marking a task as done:\n"
    	        + "done [task ID]\n\n"
    	        + "Deleting tasks:\n"
    	        + "delete [task ID]\n"
    	        + "Deleting tasks in a range:\n"
    	        + "delete [task ID] to [task ID]\n"
    	        + "Deleting selected tasks:\n"
    	        + "delete [task ID], [task ID], [task ID] to [task ID]\n\n"
    	        + "Editing the description of one task:\n"
    	        + "edit [task Id] [new task description]\n\n"
    	        + "Searching using keywords:\n"
    	        + "search [keyword]\n\n"
    	        + "Displaying all tasks:\n"
    	        + "display\n"
    	        + "display done\n"
    	        + "display undone\n\n"
    	        + "Undoing the last action:\n"
    	        + "undo\n"
    	        + "Redoing the last action:\n"
    	        + "redo\n\n"
    	        + "Clearing all tasks off the display:\n"
    	        + "clear\n\n"
    	        + "Press [ESC] key to exit this help sheet",
    	        "Magic 8 Help Sheet", 
    	        JOptionPane.PLAIN_MESSAGE,
    	        new ImageIcon(Toolkit.getDefaultToolkit().getImage("lib/Magic8Logo2.png")));
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
    
    @SuppressWarnings("unchecked")                       
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        splitPaneMain = new javax.swing.JSplitPane();
        leftPanel = new javax.swing.JPanel();
        tablePanel = new javax.swing.JPanel();
        tableScrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable()
        {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }
        }
        ;
        commandPanel = new javax.swing.JPanel();
        commandField = new javax.swing.JTextField();
        rightPanel = new javax.swing.JPanel();
        splitPaneSub = new javax.swing.JSplitPane();
        notificationsPanel = new javax.swing.JPanel();
        notificationsScrollPane = new javax.swing.JScrollPane();
        notificationsField = new javax.swing.JTextArea();
        outputPanel = new javax.swing.JPanel();
        outputScrollPane = new javax.swing.JScrollPane();
        outputField = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Magic8");
        setPreferredSize(new java.awt.Dimension(1000, 700));

        splitPaneMain.setBackground(new java.awt.Color(255, 255, 255));
        splitPaneMain.setDividerLocation(700);
        splitPaneMain.setResizeWeight(0.8);

        leftPanel.setBackground(new java.awt.Color(255, 255, 255));
        leftPanel.setLayout(new java.awt.GridBagLayout());

        tablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tasks", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 18))); // NOI18N
        tablePanel.setLayout(new java.awt.BorderLayout());
        
        model = new DefaultTableModel();
        table = new JTable(model);
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Description", "Start", "End", "Tags"
            }
        ) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table.setFocusable(false);
        table.setRowHeight(50);
        table.getTableHeader().setReorderingAllowed(false);
        tableScrollPane.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(50);
            table.getColumnModel().getColumn(1).setPreferredWidth(300);
            table.getColumnModel().getColumn(2).setPreferredWidth(100);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);
            table.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

        tablePanel.add(tableScrollPane, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.9;
        leftPanel.add(tablePanel, gridBagConstraints);

        commandPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Command", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 18))); // NOI18N
        commandPanel.setLayout(new java.awt.BorderLayout());

        commandField.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        commandPanel.add(commandField, java.awt.BorderLayout.CENTER);
        keywords.add("add"); //Add function
		keywords.add("display");//Display function
		keywords.add("delete");//Delete function
		keywords.add("edit");//Update function
		keywords.add("undo");//Undo function
		keywords.add("redo");//Re do function
		keywords.add("search");//Search function
		keywords.add("by");//'by' deadline 
		keywords.add("exit"); //Exit function

		// Without this, cursor always leaves text field
		commandField.setFocusTraversalKeysEnabled(false);
		AutoComplete auto = new AutoComplete();
		AutoComplete.Autocomplete autoComplete = auto.new Autocomplete(commandField, keywords);
		
		//To prevent autoscrolling
		DefaultCaret caret1 = (DefaultCaret) notificationsField.getCaret();
		caret1.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

		commandField.getDocument().addDocumentListener(autoComplete);

		// Maps the tab key to the commit action, which finishes the autocomplete
		// when given a suggestion
		commandField.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		commandField.getActionMap().put(COMMIT_ACTION, autoComplete.new CommitAction());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.1;
        leftPanel.add(commandPanel, gridBagConstraints);

        splitPaneMain.setLeftComponent(leftPanel);

        rightPanel.setLayout(new java.awt.BorderLayout());

        splitPaneSub.setDividerLocation(330);
        splitPaneSub.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        splitPaneSub.setResizeWeight(0.5);

        notificationsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Notifications", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 18))); // NOI18N
        notificationsPanel.setLayout(new java.awt.BorderLayout());

        notificationsField.setEditable(false);
        notificationsField.setColumns(20);
        notificationsField.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        notificationsField.setRows(5);
        notificationsScrollPane.setViewportView(notificationsField);

        notificationsPanel.add(notificationsScrollPane, java.awt.BorderLayout.CENTER);

        splitPaneSub.setTopComponent(notificationsPanel);

        outputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Output", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 18))); // NOI18N
        outputPanel.setLayout(new java.awt.BorderLayout());

        outputField.setEditable(false);
        outputField.setColumns(20);
        outputField.setFont(new java.awt.Font("Monospaced", 0, 14));
        outputField.setRows(5);
        outputScrollPane.setViewportView(outputField);

        outputPanel.add(outputScrollPane, java.awt.BorderLayout.CENTER);

        splitPaneSub.setRightComponent(outputPanel);

        rightPanel.add(splitPaneSub, java.awt.BorderLayout.CENTER);

        splitPaneMain.setRightComponent(rightPanel);

        getContentPane().add(splitPaneMain, java.awt.BorderLayout.CENTER);

        pack();
    }                        

    void setFrameIcon(){

        Magic8GUI.setIconImage(Toolkit.getDefaultToolkit().getImage("lib/Magic8Logo.png")); 
    }

    /**
     * @param args the command line arguments
     * @throws Exception 
     * @throws IOException 
     */
    public static void main(String args[]) throws IOException, Exception {

        try {
        	magic8GUIInit();
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Magic8UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Magic8UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Magic8UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Magic8UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Magic8GUI().setVisible(true);
            }
        });
    }

               
}
