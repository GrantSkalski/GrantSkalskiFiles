package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class Subject extends Model {

  public static final String table = "subject";
    
  private int id = 0;
  private String name;
 

  // must have an empty constructor defined
  public Subject() { }

  public Subject(String name) {
    this.name = name;
  }

  @Override
  public int getId() { return id;}
  public String getName() { return name; }


  public void setName(String firstname) { this.name = name; }
  

  @Override
  void load(ResultSet rs) throws Exception {
    id = rs.getInt("id");
    name = rs.getString("name");
  }
  
  @Override
  void insert() throws Exception {
    Connection cx = ORM.connection();
    String sql = String.format(
      "insert into %s (name) values (?)", table);
    PreparedStatement st = cx.prepareStatement(sql);
    int i = 0;
    st.setString(++i, name);
    st.executeUpdate();
    id = ORM.getMaxId(table);
  }

  @Override
  void update() throws Exception {
    Connection cx = ORM.connection();
    String sql = String.format(
      "update %s set name=?,email=?,subject_id=? where id=?", table);
    PreparedStatement st = cx.prepareStatement(sql);
    int i = 0;
    st.setString(++i, name);
    st.setInt(++i, id);
    st.executeUpdate();
  }
  
  @Override
  public String toString(){
      return getName();
  }
}
