package views;
 
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
 
public class StudentCellRenderer implements ListCellRenderer {
 
        //add code here later
    public java.util.Set<models.Model> studentTutors= null;
        
  @Override
  public Component getListCellRendererComponent(JList list, Object obj,
          int ind, boolean isSelected, boolean hasFocus) {
 
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5) );
    panel.setBackground(Color.white);
    panel.setLayout(new BoxLayout(panel,BoxLayout.LINE_AXIS));
 
    models.Student student = (models.Student) obj;
 
    JLabel name = new JLabel(student.getLastName()+", "+student.getFirstName());
    JLabel enrolled = new JLabel(student.getEnrolled());
 
    panel.add(name);
    panel.add(Box.createHorizontalGlue()); // horiz. fill
    panel.add(enrolled);

 
    if (isSelected) {
      panel.setBackground(Color.black);
      name.setForeground(Color.white);
      enrolled.setForeground(Color.white);
    }
    // add code here later
    if (studentTutors != null && studentTutors.contains(student)) {
      name.setForeground(Color.red);
      enrolled.setForeground(Color.red);
    } 
 
    return panel;
  }
}