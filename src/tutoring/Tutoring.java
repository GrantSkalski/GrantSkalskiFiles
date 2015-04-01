/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import models.DBProps;
import models.Model;
import models.ORM;
import models.Student;
import models.Subject;
import models.Tutor;
import views.AddSubject;
import views.AddTutor;
import views.StudentCellRenderer;
import views.TheFrame;
import views.TutorCellRenderer;
import views.UpdateReport;

   
/**
 *
 * @author GrantSkalski
 */
public class Tutoring {
   
    
    
    private final TheFrame frame = new TheFrame();
    
    //table fields 
    private final String tutorFields[] = {"id", "firstname", "lastname",
        "email", "subject_id",};
    private final String studentFields[] ={"id", "firstname",
        "lastname","enrolled",};
    

    // list model objects
    private final DefaultListModel<Model> tutorsModel = new DefaultListModel<>();
    private final DefaultListModel<Model> studentsModel = new DefaultListModel<>();
    
     // renderer objects
     private final TutorCellRenderer tutorRenderer = new TutorCellRenderer();
     private final StudentCellRenderer studentRenderer = new StudentCellRenderer();
     
     // dialogs
     private final AddSubject addSubject = new AddSubject(frame, true);
     private final AddTutor addTutor = new AddTutor(frame,true);
     private final UpdateReport updateReport = new UpdateReport(frame,true);
     
     private final DefaultComboBoxModel<Model> subjectDropdown = new DefaultComboBoxModel<>();
    
    public Tutoring() throws Exception {
    ORM.init(DBProps.getProps());
 
    frame.setTitle(
      String.format("%s - %s", getClass().getSimpleName(), DBProps.which)
    );
    frame.setSize(700, 400);
    
    //assign models to list in frame
    frame.setTutorsModel(tutorsModel);
    frame.setStudentsModel(studentsModel);
    
    //load lists from database
    ControllerHelper.loadListModel(tutorsModel, ORM.findAll(Tutor.class,
            "order by lastname,firstname"));
    ControllerHelper.loadListModel(studentsModel, 
      ORM.findAll(Student.class, "order by lastname,firstname"));
    
    ControllerHelper.loadComboBoxModel(subjectDropdown, ORM.findAll(Subject.class));
    
    // set cell renderers
    frame.setTutorRenderer(tutorRenderer);
    frame.setStudentRenderer(studentRenderer);
    
    //initalize dialogs
    
    addSubject.setTitle("Add a Subject");
    addSubject.setSize(400, 200);
    addTutor.setTitle("Add a Tutor");
    addTutor.setSize(400,200);
    updateReport.setTitle("Update Report");
    updateReport.setSize(400,200);
    
    //=========================================== Event Handlers
    
    //add menu actions
    frame.addAddSubjectActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            addSubject.setSubjectText("");
            addSubject.setVisible(true);
        }
    });
    
    frame.addAddTutorActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            addTutor.setFirstNameText("");
            addTutor.setLastNameText("");
            addTutor.setEmailText("");
            addTutor.setSubject(subjectDropdown); 
            try {
                ControllerHelper.loadComboBoxModel(subjectDropdown, ORM.findAll(Subject.class));
            } catch (Exception ex) {
                Logger.getLogger(Tutoring.class.getName()).log(Level.SEVERE, null, ex);
            }
            addTutor.setVisible(true);
        }
    });
    
   
    
    //sort menu actions
    //addSortByEnrolledActionListener sorts students by
    //decending order of their enrolled dates
    frame.addSortByEnrolledActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ControllerHelper.loadListModel(studentsModel, 
                ORM.findAll(Student.class, "order by enrolled DESC")
                 );
            }
            catch (Exception ex) {
                ex.printStackTrace(System.err);
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        }
    });
    //addSortByNameActionListener sorts students by
    // order of their last names
    frame.addSortByNameActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ControllerHelper.loadListModel(studentsModel, 
                ORM.findAll(Student.class, "order by lastname,firstname")
                 );
            }
            catch (Exception ex) {
                ex.printStackTrace(System.err);
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        }
    });
    
    // update menu actions
    
    frame.addUpdateReportActionListener(new ActionListener() {

         @Override
        public void actionPerformed(ActionEvent e) {
            
             try {
                 if(frame.getSelectedStudent() == null || frame.getSelectedTutor() == null){
                     throw new Exception("Please select a tutor and a student to update their record.");
                 }
                 
                 if(ORM.getReport(frame.getSelectedTutor(), frame.getSelectedStudent()).equals("")){
                     throw new Exception("There is no report for this Tutor/Student pairing.");
                 }
                 updateReport.setReportText(ControllerHelper.getReport(frame, frame.getSelectedTutor(), frame.getSelectedStudent()));
                 updateReport.setVisible(true);
             } 
             catch (Exception ex) {
                 ex.printStackTrace(System.err);
                 JOptionPane.showMessageDialog(frame, ex.getMessage());
             }
            
        }
    });
    
    
    
    //when tutor is selected display tutor info
    frame.addTutorsListSelectionListener(new ListSelectionListener() {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) { return; }
        try {
           Tutor tutor = frame.getSelectedTutor();
           Student student = frame.getSelectedStudent();
          if (tutor == null) {
            return;
          }
          frame.setInfoText(ControllerHelper.getInfo(frame,tutor,student));
        } 
        catch (Exception ex) {
          ex.printStackTrace(System.err);
          JOptionPane.showMessageDialog(frame, ex.getMessage());
        }
        }
    });
    
    // when student that is joined to tutor is selected
    // change color of tutor
    frame.addStudentsListSelectionListener(new ListSelectionListener() {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
          return;
        }
        try {
            Student student = frame.getSelectedStudent();
            
              tutorRenderer.tutorStudents = 
              student == null ? null : ORM.getJoined(student, Tutor.class);
              frame.repaint();
              frame.setInfoText(ControllerHelper.getInfo(frame, frame.getSelectedTutor(), student));

          
          
          
        } 
        catch (Exception ex) {
          ex.printStackTrace(System.err);
          JOptionPane.showMessageDialog(frame, ex.getMessage());
        }
      
        }
    });
    
    
    
    //when tutor that is joined to student is selected
    // change color of student
    frame.addTutorsListSelectionListener(new ListSelectionListener() {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()){
                return;
            }
            try {
                Tutor tutor = frame.getSelectedTutor();
                studentRenderer.studentTutors =
                        tutor == null ? null : ORM.getJoined(tutor, Student.class);
                frame.repaint();
            }
            catch (Exception ex) {
                ex.printStackTrace(System.err);
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        }
    });
    
    // when both selected then print report
    frame.addBothListSelectionListener(new ListSelectionListener() {

        Tutor tutor = frame.getSelectedTutor();
        Student student = frame.getSelectedStudent();
        
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(e.getValueIsAdjusting()){
                return;
            }
            try {
                ControllerHelper.getReport(frame,tutor,student);
            }
            catch (Exception ex) {
                ex.printStackTrace(System.err);
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        }
    });
    
    
    // button handlers
    frame.addClearAllActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        tutorRenderer.tutorStudents = null;
        studentRenderer.studentTutors= null;
        frame.clearAllSelected();
        frame.setInfoText(null);
      }
    });
    
    
    
    frame.addJoinActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                ControllerHelper.assignTutor(frame, studentRenderer, tutorRenderer);
            }
            catch (ControllerHelper.MyException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
            catch (Exception ex) {
                ex.printStackTrace(System.err);
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        }
    });
    
     frame.addRemoveStudentActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ControllerHelper.removeStudent(frame, studentsModel,tutorsModel);
            }
            catch (Exception ex) {
                ex.printStackTrace(System.err);
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        }
    });
    
    
    
    //addSubject dialog event handler
    addSubject.addAddActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ControllerHelper.addSubject(frame, addSubject);
            }
            catch (ControllerHelper.MyException | java.sql.SQLException ex){
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
            catch (Exception ex) {
                ex.printStackTrace(System.err);
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        }
    });
    
    addSubject.addCancelActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            addSubject.setVisible(false);
        }
    });
    
    addTutor.addAddActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ControllerHelper.addTutor(frame, addTutor, tutorsModel);
            }
            catch (ControllerHelper.MyException | java.sql.SQLException ex){
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
            catch (Exception ex) {
                ex.printStackTrace(System.err);
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        }
    });
    
     addTutor.addCancelActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            addTutor.setVisible(false);
        }
    });
     
     addTutor.addDropdownItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent evt) {
        if (evt.getStateChange() == ItemEvent.DESELECTED) {
          return;
        }
        System.out.println(addTutor.getSubject());
      }
    });
     
     updateReport.addUpdateReportActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ControllerHelper.updateReport(frame, updateReport);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        }
    });
     
     updateReport.addCancelActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateReport.setVisible(false);
        }
    });
    
 
  }  // end LibraryMVC constructor

    
    
    
    
        public static void main(String[] args) {
        try {
            Tutoring app = new Tutoring();
            app.frame.setVisible(true);
        }
        catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(0);
        }
    }
    
}

