package tutoring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import models.DBProps;

public class AddRecords {

  public static void main(String[] args) throws Exception {

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
    ResultSet rs;

    // populate tables
    sql = "insert into subject (name) values "
        + "('Chemistry')" // 1
        + ",('Math')"     // 2
        + ",('Physics')"  // 3
        + ",('Biology')"  // 4
        ;
    stmt.executeUpdate(sql);

    sql = "insert into tutor (lastname,firstname,email,subject_id) values "
        + "('Bergmann','Michael','mberg22@gmail.com',1)"  // 1
        + ",('Guo','Cheryl','guo33@yahoo.com',1)"         // 2
        + ",('Howse','Manuel','mahow@hotmail.com',2)"     // 3
        + ",('Lanier','Jennifer','lanije@outlook.com',2)" // 4
        + ",('Sippel','John','sipp45@live.com',3)"        // 5
        + ",('Brady','Chad','chadbrad@aol.com',4)"        // 6
        + ",('Mazer','Dominique','domaz11@juno.com',3)"  // 7
        + ",('Wooding','Bernard','berwood@yandex.com',2)" // 8
        ;
    stmt.executeUpdate(sql);

    sql = "insert into student (lastname,firstname,enrolled) values "
          + "('Nimmo','Penney', '2010-08-18')"		// 1
          + ",('Carson','Susy', '2012-07-28')"		// 2
          + ",('Bard','Thomas', '2014-12-28')"		// 3
          + ",('Mcgillis','Alysa', '2010-07-20')"		// 4
          + ",('Maddock','Amal', '2012-05-09')"		// 5
          + ",('Gannaway','Brittney', '2014-01-16')"		// 6
          + ",('Carson','James', '2014-11-21')"		// 7
          + ",('Liggett','Clifford', '2014-01-08')"		// 8
          + ",('Rothman','Alonso', '2014-02-19')"		// 9
          + ",('Collier','Rosanne', '2012-07-12')"		// 10
          + ",('Valez','Sharie', '2012-09-01')"		// 11
          + ",('Farney','Tommy', '2011-10-11')"		// 12
          + ",('Seawright','Natalia', '2013-03-25')"		// 13
          + ",('Loomis','Arline', '2012-07-24')"		// 14
        ;
    stmt.executeUpdate(sql);

    // dump tables
    System.out.println("---> subject");
    rs = stmt.executeQuery("select * from subject order by id");
    while (rs.next()) {
      System.out.println(java.util.Arrays.toString(
          new Object[]{rs.getObject(1), rs.getObject(2)}
      ));
    }
    System.out.println("---> tutor");
    rs = stmt.executeQuery("select * from tutor order by id");
    while (rs.next()) {
      System.out.println(java.util.Arrays.toString(new Object[]{
        rs.getObject(1),
        rs.getObject(2), rs.getObject(3), rs.getObject(4), rs.getObject(5)}
      ));
    }
    System.out.println("---> student");
    rs = stmt.executeQuery("select * from student order by id");
    while (rs.next()) {
      System.out.println(java.util.Arrays.toString(new Object[]{
        rs.getObject(1), rs.getObject(2), rs.getObject(3), rs.getObject(4)}
      ));
    }
  }
}
