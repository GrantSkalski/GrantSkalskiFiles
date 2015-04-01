package tutoring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.DBProps;

public class AddJoins {

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
    //Statement stmt = cx.createStatement();

    String directory = "reports";
    DirectoryStream<Path> directoryStream 
      = Files.newDirectoryStream(Paths.get(directory));
    
    
    
    for (Path path : directoryStream) {
      // legitimate filename in reports directory is of form:
      // <tutor_id>-<student_id>.txt
      
      // extract tutor_id, student_id from filename
      String filename = path.getFileName().toString();
      String pattern_string = "(\\d+)-(\\d+)\\.txt";
      Pattern pattern = Pattern.compile(pattern_string);
      Matcher matcher = pattern.matcher(filename);
      if (!matcher.find()) {
        System.out.println("invalid file name: " + filename);
        continue;
      }
      int tutor_id = Integer.parseInt(matcher.group(1));
      int student_id = Integer.parseInt(matcher.group(2));
      
      // read file contents as initial report
      String report = new String(Files.readAllBytes(path)); 
      
      // insert (tutor_id, student_id, report) into join table
      String sql = 
        "insert into student_tutor (tutor_id,student_id,report) values (?,?,?)"
      ;
      PreparedStatement pstmt = cx.prepareStatement(sql);
      pstmt.setInt(1, tutor_id);
      pstmt.setInt(2, student_id);
      pstmt.setString(3, report);
      pstmt.executeUpdate();
    }
    
    // dump join table
    Statement stmt = cx.createStatement();
    ResultSet rs = stmt.executeQuery("select * from student_tutor");
    while (rs.next()) {
      System.out.format(
        "report for tutor #%s, student #%s\n-----------\n%s\n",
        rs.getObject("tutor_id"), rs.getObject("student_id"), 
        rs.getObject("report")
      );
    }
  }
}
