/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.event.ActionListener;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author GrantSkalski
 */
public class TheFrame extends javax.swing.JFrame {

    /**
     * setTutorsModel sets the model for the tutor list
     *
     * @param model
     */
    @SuppressWarnings("unchecked")
    public void setTutorsModel(ListModel model) {
        tutors.setModel(model);
    }

    /**
     * setStudentsModel sets the ListModel for the students JList
     *
     * @param model
     */
    @SuppressWarnings("unchecked")
    public void setStudentsModel(ListModel model) {
        students.setModel(model);
    }

    /**
     * setTutorRenderer sets the cell renderer for tutors to r
     *
     * @param r
     */
    @SuppressWarnings("unchecked")
    public void setTutorRenderer(ListCellRenderer r) {
        tutors.setCellRenderer(r);
    }

    /**
     * setStudentRenderer sets the cell renderer for students to r
     *
     * @param r
     */
    @SuppressWarnings("unchecked")
    public void setStudentRenderer(ListCellRenderer r) {
        students.setCellRenderer(r);
    }

    /**
     * addSortByEmailActionListener adds an ActionListener listener to the
     * sortStudentByEnrolled menu item
     *
     * @param listener
     */
    public void addSortByEnrolledActionListener(ActionListener listener) {
        sortStudentByEnrolled.addActionListener(listener);
    }

    /**
     * addSortByNameActionListener adds an ActionListener listener to the
     * sortStudentByName menu item
     *
     * @param listener
     */
    public void addSortByNameActionListener(ActionListener listener) {
        sortStudentByName.addActionListener(listener);
    }
    
    public void addAddSubjectActionListener(ActionListener listener) {
        addSubjectMenuItem.addActionListener(listener);
    }

    public void addAddTutorActionListener(ActionListener listener){
        addTutorMenuItem.addActionListener(listener);
    }
    
    public void addUpdateReportActionListener(ActionListener listener){
        updateReport.addActionListener(listener);
    }
    
    public void addTutorsListSelectionListener(ListSelectionListener listener) {
        tutors.addListSelectionListener(listener);
    }
    
    public void addBothListSelectionListener(ListSelectionListener listener){
        tutors.addListSelectionListener(listener);
        students.addListSelectionListener(listener);
    }

    public models.Tutor getSelectedTutor() {
        return (models.Tutor) tutors.getSelectedValue();
    }

    public void setInfoText(String text) {
        info.setText(text);
        info.setCaretPosition(0); // scroll back to top
    }

    public void addStudentsListSelectionListener(ListSelectionListener listener) {
        students.addListSelectionListener(listener);
    }

    public models.Student getSelectedStudent() {
        return (models.Student) students.getSelectedValue();
    }
    
    public void addClearAllActionListener(ActionListener listener){
        clearAll.addActionListener(listener);
    }
    
    public void clearAllSelected(){
        tutors.clearSelection();
        students.clearSelection();
    }
    
    public void addJoinActionListener(ActionListener listener){
        addJoinButton.addActionListener(listener);
    }
    
    public void addRemoveStudentActionListener(ActionListener listener){
        removeStudentButton.addActionListener(listener);
    }
    
    
    
    
  

    //add String-to-JTable map data member
  /* private Map<String,JTable> theTable = new HashMap<>();
  
     public DefaultTableModel setTableModel(String tablename, String[] columns)
     {
     DefaultTableModel tablemodel = new DefaultTableModel(columns,0){
     @Override
     public boolean isCellEditable(int row, int col){
     return false;
     }
  
     // return the class of the object in the first row (if there is one)
     // to be used for comparison determination in sorting
  
     @Override
     public Class getColumnClass(int column) {
     if(getRowCount() > 0){
     return getValueAt(0, column).getClass();
     }
     return Object.class;
     }
     };
  
     JTable table = theTable.get(tablename);
     table.setAutoCreateRowSorter(true);
  
     table.setModel(tablemodel);
  
     TableColumnModel tcm = table.getColumnModel();
     tcm.removeColumn(tcm.getColumn(0));
  
     if(tablename.equals("tutor")){
     tcm.removeColumn(tcm.getColumn(3));
     tcm.removeColumn(tcm.getColumn(2));
     // tcm.removeColumn(tcm.getColumn(4));
     // tcm.removeColumn(tcm.getColumn(5));
     }
     return tablemodel;
     }
  
  
  
     // establishing tables from database
     public DefaultTableModel setTutorTableModel(String[] columns){
     DefaultTableModel tablemodel = new DefaultTableModel(columns, 0);
     JTable table = tutor;
     table.setAutoCreateRowSorter(true);
     table.setModel(tablemodel);
     return tablemodel;
     }
  
     public DefaultTableModel setStudentTableModel(String[] columns){
     DefaultTableModel tablemodel = new DefaultTableModel(columns, 0);
     JTable table = student;
     table.setAutoCreateRowSorter(true);
     table.setModel(tablemodel);
     return tablemodel;
     }*/
    // See if you can change headers
    // edit whats viewable
    // might have to go back and use button to
    // switch the sorting
    /**
     * Creates new form TheFrame
     */
    public TheFrame() {
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        info = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        tutors = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        students = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        clearAll = new javax.swing.JButton();
        addJoinButton = new javax.swing.JButton();
        removeStudentButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        addSubjectMenuItem = new javax.swing.JMenuItem();
        addTutorMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        sortStudentByEnrolled = new javax.swing.JMenuItem();
        sortStudentByName = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        updateReport = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Tutors");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel3.setText("Information");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(jLabel3, gridBagConstraints);

        info.setColumns(20);
        info.setRows(5);
        jScrollPane3.setViewportView(info);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(jScrollPane3, gridBagConstraints);

        tutors.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        tutors.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(tutors);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(jScrollPane4, gridBagConstraints);

        students.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        students.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(students);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(jScrollPane5, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        clearAll.setText("Clear");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel2.add(clearAll, gridBagConstraints);

        addJoinButton.setText("Join");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 0);
        jPanel2.add(addJoinButton, gridBagConstraints);

        removeStudentButton.setText("Remove Student");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel2.add(removeStudentButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        getContentPane().add(jPanel2, gridBagConstraints);

        jLabel5.setText("Students");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        getContentPane().add(jLabel5, gridBagConstraints);

        jMenu1.setText("Add");

        addSubjectMenuItem.setText("Add Subject");
        jMenu1.add(addSubjectMenuItem);

        addTutorMenuItem.setText("Add Tutor");
        addTutorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTutorMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(addTutorMenuItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Sort");

        sortStudentByEnrolled.setText("Sort Students By Enrolled");
        sortStudentByEnrolled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortStudentByEnrolledActionPerformed(evt);
            }
        });
        jMenu2.add(sortStudentByEnrolled);

        sortStudentByName.setText("Sort Students By Name");
        sortStudentByName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortStudentByNameActionPerformed(evt);
            }
        });
        jMenu2.add(sortStudentByName);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Update");

        updateReport.setText("Update Report");
        jMenu3.add(updateReport);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sortStudentByEnrolledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortStudentByEnrolledActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sortStudentByEnrolledActionPerformed

    private void sortStudentByNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortStudentByNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sortStudentByNameActionPerformed

    private void addTutorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTutorMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addTutorMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TheFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TheFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TheFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TheFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TheFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addJoinButton;
    private javax.swing.JMenuItem addSubjectMenuItem;
    private javax.swing.JMenuItem addTutorMenuItem;
    private javax.swing.JButton clearAll;
    private javax.swing.JTextArea info;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JButton removeStudentButton;
    private javax.swing.JMenuItem sortStudentByEnrolled;
    private javax.swing.JMenuItem sortStudentByName;
    private javax.swing.JList students;
    private javax.swing.JList tutors;
    private javax.swing.JMenuItem updateReport;
    // End of variables declaration//GEN-END:variables
}
