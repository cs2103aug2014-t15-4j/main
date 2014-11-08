package fantasticfour.magiceight;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

//@author A0115693B

public class CalendarProgram extends Component {
	static JLabel month_Label, year_Label;
	static JButton backButton, nextButton;
	static JTable cal_Table;
//	static JComboBox cmbYear;
	static Container pane;
	static DefaultTableModel mtblCalendar; //Table model
	static JScrollPane stblCalendar; //The scrollpane
	static JPanel cal_Panel;
	static int realYear, realMonth, realDay, currentYear, currentMonth;
//	static JDialog calDialog;
	
	public static void frameSetUp(JFrame frameMagic8UI) {
		//Look and feel
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}

		//Prepare frame
		pane = frameMagic8UI.getContentPane(); //Get content pane
		pane.setLayout(null); //Apply null layout

		//Create controls
		month_Label = new JLabel ("January");
//		cmbYear = new JComboBox();
		backButton = new JButton ("B");
		nextButton = new JButton ("N");
		mtblCalendar = new DefaultTableModel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex){
				return false;
				}
			};
		cal_Table = new JTable(mtblCalendar);
		stblCalendar = new JScrollPane(cal_Table);
		cal_Panel = new JPanel(null);
		
		//Register action listeners
		backButton.addActionListener(new backButton_Action());
		nextButton.addActionListener(new nextButton_Action());
//		cmbYear.addActionListener(new cmbYear_Action());
		
		//Add controls to pane
		pane.add(cal_Panel);
		cal_Panel.add(month_Label);
		cal_Panel.add(backButton);
		cal_Panel.add(nextButton);
		cal_Panel.add(stblCalendar);
		
		//Set bounds
		cal_Panel.setBounds(490, 20, 300, 180);
		cal_Panel.setBackground(new Color(255,205,155));
//		month_Label.setBounds(140-month_Label.getPreferredSize().width/2, 15, 20, 5);
		backButton.setBounds(10, 15, 20, 20);
		backButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 8));
		nextButton.setBounds(270, 15, 20, 20);
		nextButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 8));
		stblCalendar.setBounds(0, 35, 300, 200);
	
		
		//Get real month/year
		GregorianCalendar cal = new GregorianCalendar(); //Create calendar
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
		realMonth = cal.get(GregorianCalendar.MONTH); //Get month
		realYear = cal.get(GregorianCalendar.YEAR); //Get year
		currentMonth = realMonth; //Match month and year
		currentYear = realYear;
		
		//Add headers
		String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
		for (int i=0; i<7; i++){
			mtblCalendar.addColumn(headers[i]);
		}
		
		cal_Table.getParent().setBackground(cal_Table.getBackground()); //Set background

		//No resize/reorder
		cal_Table.getTableHeader().setResizingAllowed(false);
		cal_Table.getTableHeader().setReorderingAllowed(false);

		//Single cell selection
		cal_Table.setColumnSelectionAllowed(true);
		cal_Table.setRowSelectionAllowed(true);
		cal_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//Set row/column count
		cal_Table.setRowHeight(20);
		cal_Table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		mtblCalendar.setColumnCount(7);
		mtblCalendar.setRowCount(6);
	
		//Refresh calendar
		refreshCalendar (realMonth, realYear); //Refresh calendar
	}
	
	public static void refreshCalendar(int month, int year){
		//Variables
		String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int numOfDays, startOfMonth; //Number Of Days, Start Of Month
			
		//Allow/disallow buttons
		backButton.setEnabled(true);
		nextButton.setEnabled(true);
		if (month == 0 && year <= realYear-10){backButton.setEnabled(false);} //Too early
		if (month == 11 && year >= realYear+100){nextButton.setEnabled(false);} //Too late
		month_Label.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		month_Label.setText(months[month]); //Refresh the month label (at the top)
		month_Label.setBounds(150-month_Label.getPreferredSize().width/2, 5, 180, 25); //Re-align label with calendar
		
		//Clear table
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				mtblCalendar.setValueAt(null, i, j);
			}
		}
		
		//Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		numOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		startOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);
		
		//Draw calendar
		for (int i=1; i<=numOfDays; i++){
			int row = new Integer((i+startOfMonth-2)/7);
			int column  =  (i+startOfMonth-2)%7;
			mtblCalendar.setValueAt(i, row, column);
		}

		//Apply renderers
		cal_Table.setDefaultRenderer(cal_Table.getColumnClass(0), new tblCalendarRenderer());
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

	static class backButton_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
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
	
	static class nextButton_Action implements ActionListener{
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
	
}
