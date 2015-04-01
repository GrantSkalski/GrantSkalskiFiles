package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class Tutor extends Model {

  public static final String table = "tutor";
    
  private int id = 0;
  private String firstname;
  private String lastname;
  private String email;
  private int subject_id = 0;

  // must have an empty constructor defined
  public Tutor() { }

  public Tutor(String firstname, String lastname, String email, int subject_id) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.subject_id = subject_id;
  }

  @Override
  public int getId() { return id;}
  public String getFirstName() { return firstname; }
  public String getLastName() {return lastname; }
  public String getEmail() { return email; }
  public int getSubject() { return subject_id; }

  public void setFirstName(String firstname) { this.firstname = firstname; }
  public void setLastName(String lastname) { this.lastname = lastname; }
  public void setEmail(String email) { this.email = email; }
  public void setSubject(int subject_id) { this.subject_id = subject_id; }

  @Override
  void load(ResultSet rs) throws Exception {
    id = rs.getInt("id");
    firstname = rs.getString("firstname");
    lastname = rs.getString("lastname");
    email = rs.getString("email");
    subject_id = rs.getInt("subject_id");
  }
  
  @Override
  void insert() throws Exception {
    Connection cx = ORM.connection();
    String sql = String.format(
      "insert into %s (firstname,lastname,email,subject_id) values (?,?,?,?)", table);
    PreparedStatement st = cx.prepareStatement(sql);
    int i = 0;
    st.setString(++i, firstname);
    st.setString(++i, lastname);
    st.setString(++i, email);
    st.setInt(++i, subject_id);
    st.executeUpdate();
    id = ORM.getMaxId(table);
  }

  @Override
  void update() throws Exception {
    Connection cx = ORM.connection();
    String sql = String.format(
      "update %s set firstname=?,lastname=?,email=?,subject_id=? where id=?", table);
    PreparedStatement st = cx.prepareStatement(sql);
    int i = 0;
    st.setString(++i, firstname);
    st.setString(++i, lastname);
    st.setString(++i, email);
    st.setInt(++i, subject_id);
    st.setInt(++i, id);
    st.executeUpdate();
  }
}
