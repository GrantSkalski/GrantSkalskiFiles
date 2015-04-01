package views;
 
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
 
public class TutorCellRenderer implements ListCellRenderer {
 
  //-------- we will add a data member here later
    public java.util.Set<models.Model> tutorStudents = null;
 
  @Override
  public Component getListCellRendererComponent(JList list, Object obj,
          int ind, boolean isSelected, boolean hasFocus) {
    JLabel label = new JLabel();
 
    models.Tutor tutor = (models.Tutor) obj;
    
    Color fg=list.getForeground();
    Color bg=list.getBackground();
    if (isSelected) {
      fg = list.getSelectionForeground();
      bg = Color.black;
    }
    label.setText( tutor.getLastName() + ", " + tutor.getFirstName() );
    
 
    //-------- we will add code here later
    if (tutorStudents !=null && tutorStudents.contains(tutor)) {
        fg = Color.red;
    }
    
    label.setBorder(BorderFactory.createEmptyBorder(2,5,2,5));
    //--------------------------------------
 
    // make label height larger
    label.setBorder( BorderFactory.createEmptyBorder(2, 5, 2, 5) );
    label.setBackground(bg);
    label.setForeground(fg);
    label.setOpaque(true);
 
    return label;
  }
}
