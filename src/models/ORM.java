package models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class ORM {

    private static java.sql.Connection cx; // single connection

    private static String url;
    private static String username;
    private static String password;

    public static void init(Properties props) throws ClassNotFoundException {
        url = props.getProperty("url");
        username = props.getProperty("username");
        password = props.getProperty("password");
        String driver = props.getProperty("driver");
        if (driver != null) {
            Class.forName(driver); // load driver if necessary
        }
    }

    static Connection connection() throws Exception {
        if (url == null) {
            throw new Exception("ORM not initialized");
        }
        if (cx == null || cx.isClosed()) {
            System.out.println("new connection");
            cx = DriverManager.getConnection(url, username, password);
        }
        return cx;
    }

    public static int save(Model m) throws Exception {
        if (m.getId() == 0) {
            m.insert();
        } else {
            m.update();
        }
        return m.getId();
    }

    public static Model load(Class C, int id) throws Exception {
        String table = (String) C.getField("table").get(null);

        cx = connection();
        String sql = String.format("select * from %s where id=?", table);
        //System.out.println(sql);
        PreparedStatement st = cx.prepareStatement(sql);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (!rs.next()) {
            return null;
        }
        Model m = (Model) C.newInstance();
        m.load(rs);
        return m;
    }

    public static boolean remove(Model m) throws Exception {
        if (m.getId() == 0) {
            return false;
        }
        String table = (String) m.getClass().getField("table").get(null);

        cx = connection();
        String sql = String.format("delete from %s where id=?", table);
        //System.out.println(sql);
        PreparedStatement st = cx.prepareStatement(sql);
        st.setInt(1, m.getId());
        int affected_rows = st.executeUpdate();
        return affected_rows > 0;
    }

    public static Model findOne(Class C, String extra, Object[] values)
            throws Exception {
        if (extra == null) {
            extra = "";
        }
        String table = (String) C.getField("table").get(null);

        cx = connection();
        String sql = String.format("select * from %s %s", table, extra);
        //System.out.println(sql);
        PreparedStatement st = cx.prepareStatement(sql);
        if (values != null) {
            int pos = 1;
            for (Object value : values) {
                st.setObject(pos++, value);
            }
        }
        ResultSet rs = st.executeQuery();
        if (!rs.next()) {
            return null;
        }
        Model m = (Model) C.newInstance();
        m.load(rs);
        return m;
    }

    public static Set<Model> findAll(Class C, String extra, Object[] inserts)
            throws Exception {
        String table = (String) C.getField("table").get(null);

        if (extra == null) {
            extra = "";
        }
        cx = connection();
        String sql = String.format("select * from %s %s", table, extra);
        //System.out.println(sql);
        PreparedStatement st = cx.prepareStatement(sql);

        if (inserts != null) {
            int pos = 1;
            for (Object value : inserts) {
                st.setObject(pos++, value);
            }
        }

        ResultSet rs = st.executeQuery();
        Set<Model> L = new LinkedHashSet<>();
        while (rs.next()) {
            Model m = (Model) C.newInstance();
            m.load(rs);
            L.add(m);
        }
        return L;
    }

    public static Set<Model> findAll(Class C, String extra) throws Exception {
        return findAll(C, extra, null);
    }

    public static Set<Model> findAll(Class C) throws Exception {
        return findAll(C, null, null);
    }

    static int getMaxId(String table) throws Exception {
        String sql = String.format("select max(id) from %s", table);
        cx = connection();
        Statement st = cx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        rs.next();
        return rs.getInt(1);
    }

  //===========================================================
    // Joins
    //===========================================================
    /**
     * getJoined
     *
     * get all objects of class toClass which have entries in the common join
     * table, book_user, for a given object from. Assuming either (Book,
     * User.class) or (User, Book.class).
     *
     * @param from
     * @param toClass
     * @return
     * @throws java.lang.Exception
     */
    public static Set<Model> getJoined(Model from, Class toClass) throws Exception {
        cx = connection();
        String from_table
                = (String) from.getClass().getField("table").get(null);

        String to_table = (String) toClass.getField("table").get(null);

        String sql = String.format("select %s.*\n"
                + "from tutor join student_tutor join student\n"
                + "where tutor.id = student_tutor.tutor_id\n"
                + "and student.id = student_tutor.student_id\n"
                + "and %s.id = ?", to_table, from_table
        );
        //System.out.println(sql);
        PreparedStatement st = cx.prepareStatement(sql);
        st.setInt(1, from.getId());

        ResultSet rs = st.executeQuery();
        Set<Model> L = new LinkedHashSet<>();
        while (rs.next()) {
            Model to = (Model) toClass.newInstance();
            to.load(rs);
            L.add(to);
        }
        return L;
    }

    /**
     * addJoin: see if the join id pair is already in the student_tutor join
     * table, and if not, add it
     *
     * @param tutor
     * @param student
     * @return
     * @throws java.lang.Exception
     */
    public static boolean addJoin(Student student, Tutor tutor) throws Exception {
        cx = connection();
        String sql = "select * from student_tutor where tutor_id=? and student_id=?";
        //System.out.println(sql);
        PreparedStatement st = cx.prepareStatement(sql);
        st.setInt(1, tutor.getId());
        st.setInt(2, student.getId());
        ResultSet rs = st.executeQuery();
        if (rs.next()) {  // join entry already exists
            return false;
        }
        
        
        String reportString = "SAMPLE REPORT FILL-IN TEXT ONLY.\n";
        String report ="";
      

        for (int i = 0; i<8; i++){
            report = report.concat(reportString);
        }
        
        // add it to the table
        sql = "insert into student_tutor (tutor_id,student_id,report) values(?,?,?)";
        //System.out.println(sql);
        st = cx.prepareStatement(sql);
        st.setInt(1, tutor.getId());
        st.setInt(2, student.getId());
        st.setString(3, report);
        st.executeUpdate();
        return true;
    }

    public static String getReport(Tutor tutor, Student student) throws Exception {
        cx = connection();
        String sql = "select * from student_tutor where tutor_id=? and student_id=?";

        PreparedStatement st = cx.prepareStatement(sql);
        st.setInt(1, tutor.getId());
        st.setInt(2, student.getId());
        ResultSet rs = st.executeQuery();

        String reports = "";
        while (rs.next()) {
            reports = rs.getString("report");
        }
        return reports;
    }
    
    public static void setReport(Tutor tutor, Student student, String report) throws Exception{
       
        cx = connection();
        String sql = "update student_tutor set report=? where student_id=? and tutor_id=?";
        
        PreparedStatement st = cx.prepareStatement(sql);
        st.setString(1,report);
        st.setInt(2, student.getId());
        st.setInt(3, tutor.getId());
        st.executeUpdate();
    }
    

    
    
    
    

    /**
     * removeJoin: attempt to remove joined record
     *
     * @param user
     * @param book
     * @return
     * @throws java.lang.Exception
     */
    public static boolean removeJoin(Student student, Tutor tutor) throws Exception {
     cx = connection();
     String sql = "delete from student_tutor where student_id=? and tutor_id=?";
     //System.out.println(sql);
     PreparedStatement st = cx.prepareStatement(sql);
     st.setInt(1, student.getId());
     st.setInt(2, tutor.getId());
     int affected_rows = st.executeUpdate(); // = 1 if successful delete
     return affected_rows > 0;               // = 0 if not
     }
}
