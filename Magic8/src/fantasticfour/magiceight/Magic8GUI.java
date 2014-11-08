package fantasticfour.magiceight;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Magic8GUI extends javax.swing.JFrame {

    /**
     * Creates new form Magic8UI
     */
    public Magic8GUI() {
        initComponents();
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

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Description", "Start", "End", "Tags"
            }
        ) {
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
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
    private javax.swing.JPanel tablePanel;
    private javax.swing.JScrollPane tableScrollPane;
               
}
