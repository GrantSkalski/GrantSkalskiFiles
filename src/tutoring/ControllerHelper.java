package tutoring;


import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.DefaultListModel;
import java.util.Collection;
import java.util.Set;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Model;
import models.ORM;
import models.Student;
import models.Subject;
import models.Tutor;
import views.TheFrame;

public class ControllerHelper {

    static <E> void  loadComboBoxModel(DefaultComboBoxModel model, Collection<E> coll) {
        model.removeAllElements();
        for (E elt: coll) {
            model.addElement(elt);
        }
    }

   

   

   public static class MyException extends Exception {

        public MyException(String message) {
            super(message);
        }
    }

    static <E> void loadListModel(DefaultListModel<E> model, Collection<E> coll) {
        model.clear();
        for (E elt : coll) {
            model.addElement(elt);
        }
    }

    static void loadTutorsTable(Collection<Model> tutors,
            DefaultTableModel tutorTableModel) {
        tutorTableModel.setNumRows(0);
        for (Model obj : tutors) {
            Tutor tutor = (Tutor) obj;
            tutorTableModel.addRow(new Object[]{
                tutor.getId(),
                tutor.getFirstName(),
                tutor.getLastName(),
                tutor.getEmail(),
                tutor.getSubject(),});
        }
    }

    static void loadStudentsTable(Collection<Model> students,
            DefaultTableModel studentTableModel) {
        studentTableModel.setNumRows(0);
        for (Model obj : students) {
            Student student = (Student) obj;
            studentTableModel.addRow(new Object[]{
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEnrolled(),});

        }
    }

    
    static String getInfo(TheFrame frame, Tutor tutor, Student student) throws Exception {

        Student currStudent = frame.getSelectedStudent();
        Tutor currTutor = frame.getSelectedTutor();
        String info = "";
        if(currTutor != null && currStudent ==null){
        Object[] values = {currTutor.getSubject()};
        Model subjectName = ORM.findOne(Subject.class, "where id = ?", values);
         info = "Tutor Information: \nEmail: " + currTutor.getEmail() + "\n"
                + "Subject: " + ((Subject) subjectName).getName();
        }
       
        
        else if (currTutor != null && currStudent != null){
            String report = ORM.getReport(tutor, student);
            Object[] values = {currTutor.getSubject()};
            Model subjectName = ORM.findOne(Subject.class, "where id = ?", values);
        
              info = "Tutor: " + currTutor.getLastName() + ", " + currTutor.getFirstName()
                + "\nSubject: " + ((Subject) subjectName).getName()
                + "\nStudent: " + currStudent.getLastName()+", "+currStudent.getFirstName()
                +"\n-----------------\n"
                + report;
        }
        
        else if (currTutor == null && currStudent == null){
            info = "";
        }
        return info;
    }
    

    static String getReport(TheFrame frame, Tutor tutor, Student student) throws Exception {
        String report = "";
        if (tutor != null && student != null) {
          try{
               report = ORM.getReport(tutor, student);
          }
          catch (Exception ex) {
                ex.printStackTrace(System.err);
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        }

        return report;
    }
    
     static void updateReport(TheFrame frame, views.UpdateReport updateReport) throws Exception{
        
         String report = updateReport.getReportText(); 
         ORM.setReport(frame.getSelectedTutor(),frame.getSelectedStudent(),report);
         updateReport.setReportText("");
         updateReport.setVisible(false);
         frame.setInfoText(getInfo(frame,frame.getSelectedTutor(),frame.getSelectedStudent()));
         frame.repaint();
         
    }

    static void assignTutor(TheFrame frame,
            views.StudentCellRenderer studentRenderer,
            views.TutorCellRenderer tutorRenderer)
            throws MyException, Exception {

        Tutor tutor = frame.getSelectedTutor();
        Student student = frame.getSelectedStudent();
        boolean hasSubject = false;
        boolean hasTutor = false;
        if(tutor == null || student == null){
            throw new MyException("Please select a tutor and student to join.");
        }
        if(tutor != null && student !=null){
            
        
        Set<Model> tutors = ORM.getJoined(student, Tutor.class);
        for (Model currTutor : tutors){
        if (tutor.getSubject() == ((Tutor)currTutor).getSubject()){
            hasSubject= true;
        }
        if(tutor.getId() == ((Tutor)currTutor).getId()){
            hasTutor = true;
        }
        }
        
        if (hasTutor){
            String tutName= tutor.getFirstName()+" "+tutor.getLastName();
            String stuName= student.getFirstName()+" "+student.getLastName();
                    
             throw new MyException(String.format("%s is already being tutored by %s."
                     + "\n Please try again.",stuName, tutName));
        }
       else if (hasSubject){
           String stuName= student.getFirstName()+" "+student.getLastName();
             throw new MyException(String.format("%s is studying %s already.",stuName,ORM.load(Subject.class, tutor.getSubject())));
        }
               
        
        else if ((ORM.addJoin(student, tutor))) {
        
            frame.setInfoText(getInfo(frame,frame.getSelectedTutor(),frame.getSelectedStudent()));
            studentRenderer.studentTutors = ORM.getJoined(tutor, Student.class);
            frame.repaint();
            tutorRenderer.tutorStudents = ORM.getJoined(student, Tutor.class);
            frame.repaint();
            throw new MyException("Successful Join");
        }
        
        
        }

    }

    static void addSubject(TheFrame frame, views.AddSubject addSubject)
            throws Exception {
        String name = addSubject.getSubjectText().trim();
        if (name.length() < 3) {
            throw new MyException("Subject names must be at least "
                    + "3 characters long.\n"
                    + "Please try again.");
        }
        try {
            Subject subject = new Subject(name);
            ORM.save(subject);
            addSubject.setVisible(false);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }
    }

    static void addTutor(TheFrame frame, views.AddTutor addTutor,
            DefaultListModel<Model> tutorsModel) throws Exception {
        String firstname = addTutor.getFirstNameText().trim();
        String lastname = addTutor.getLastNameText().trim();
        String email = addTutor.getEmailText();
        Subject subject = (Subject)addTutor.getSubject();
        
        if (lastname.length() < 2 || firstname.length() < 2) {
            throw new MyException("Names must have at least 2 letters");
        }
        if(isValidEmailAddress(email)){
                Tutor tutor = new Tutor(firstname, lastname, email, subject.getId());
        ORM.save(tutor);
        loadListModel(tutorsModel, ORM.findAll(Tutor.class, "order by lastname,firstname"));
        frame.repaint();
        addTutor.setVisible(false);
        }
        else throw new Exception("Invalid email");
    
    }
    
    static void removeStudent(TheFrame frame,
            DefaultListModel<Model> studentsModel,
            DefaultListModel<Model> tutorsModel) throws Exception {
    Student student = frame.getSelectedStudent();
    Tutor tutor = frame.getSelectedTutor();
 
    if (student == null) {
      throw new MyException("You must select a student first.");
    }
    Set<Model> tutors = ORM.getJoined(student, Tutor.class);
    if (!tutors.isEmpty()) {
    for (Model currTutor : tutors){
        ORM.removeJoin(student, ((Tutor) currTutor));
    }
    }
    if (!confirm(frame, "Are you sure?")) {
      return;
    }
    ORM.remove(student);
    loadListModel(studentsModel, ORM.findAll(Student.class,"order by lastname,firstname"));
    loadListModel(tutorsModel,ORM.findAll(Tutor.class,"order by lastname,firstname"));
    frame.setInfoText(getInfo(frame,tutor,student));
    frame.repaint();
  }
 
  static boolean confirm(TheFrame frame, Object message) {
    int response = JOptionPane.showOptionDialog(
            frame, message, null, JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE, null,
            new String[]{"yes", "no"}, "no");
    return response == JOptionPane.YES_OPTION;
  }
  
  public static boolean isValidEmailAddress(String email) {
   boolean result = true;
   try {
      InternetAddress emailAddr = new InternetAddress(email);
      emailAddr.validate();
   } catch (AddressException ex) {
      result = false;
   }
   return result;
}

}
