package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class Student extends Model {

  public static final String table = "student";
   
  private int id = 0;
  private String firstname;
  private String lastname;
  private String enrolled;

  public Student() { }

  public Student(String firstname, String lastname, String enrolled) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.enrolled = enrolled;
  }
  
  @Override
  public int getId() { return id; }
  public String getFirstName() { return firstname; }
  public String getLastName() { return lastname; }
  public String getEnrolled() { return enrolled; }

  public void setFirstName(String firstname) {this.firstname = firstname; }
  public void setLastName(String lastname) {this.lastname = lastname; }
  public void setEnrolled(String enrolled) { this.enrolled = enrolled; }
  
  @Override
  void load(ResultSet rs) throws Exception {
    id = rs.getInt("id");
    firstname = rs.getString("firstname");
    lastname= rs.getString("lastname");
    enrolled = rs.getString("enrolled");
  }
 
  @Override
  void insert() throws Exception {
    Connection cx = ORM.connection();
    String sql = String.format(
      "insert into %s (name,enrolled) values (?,?)", table);
    PreparedStatement st = cx.prepareStatement(sql);
    int i = 0;
    st.setString(++i, firstname);
    st.setString(++i, lastname);
    st.setString(++i, enrolled);
    st.executeUpdate();
    id = ORM.getMaxId(table);
  }

  @Override
  void update() throws Exception {
    Connection cx = ORM.connection();
    String sql = String.format(
            "update %s set name=?,enrolled=? where id=?", table);
    PreparedStatement st = cx.prepareStatement(sql);
    int i = 0;
    st.setString(++i, firstname);
    st.setString(++i, lastname);
    st.setString(++i, enrolled);
    st.setInt(++i,id);
    st.executeUpdate();
  }
}
