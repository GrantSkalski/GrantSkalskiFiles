package tutoring;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import models.DBProps;

public class CreateTables {

  public static void main(String[] args) throws
    java.io.IOException, ClassNotFoundException, SQLException 
  {
    Properties props = DBProps.getProps();

    System.out.format("\nUsing DB: %s\n\n", DBProps.which);

    String url = props.getProperty("url");
    String username = props.getProperty("username");
    String password = props.getProperty("password");
    String driver = props.getProperty("driver");
    if (driver != null) {
      Class.forName(driver); // load driver if necessary
    }
    Connection cx = DriverManager.getConnection(url, username, password);

    Statement stmt = cx.createStatement();

    String sql;
    String filename;

    for (String table : new String[] {
      "subject", "tutor", "student", "student_tutor"
    }) {
      sql = String.format("drop table if exists %s", table);
      System.out.println(sql);
      stmt.execute(sql);

      filename = String.format("%s-%s.sql", table, DBProps.which);
      sql = new String(Files.readAllBytes(Paths.get(filename)));
      System.out.println(sql);
      stmt.execute(sql);
    }
  }
}
