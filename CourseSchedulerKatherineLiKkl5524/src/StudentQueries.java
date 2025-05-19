
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author katherineli
 */
public class StudentQueries {
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement studentNametoID;
    private static PreparedStatement clearStudents;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry studentEntry)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studentid, firstname, lastname) values (?, ?, ?)");
            addStudent.setString(1, studentEntry.getStudentID());
            addStudent.setString(2, studentEntry.getFirstName());
            addStudent.setString(3, studentEntry.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<String> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<String> student = new ArrayList<String>();
        try
        {
            getAllStudents = connection.prepareStatement("select * from app.student order by lastName, firstName");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                student.add(lastName + ",  " + firstName);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;
        
    }
    
    public static String studentNametoID(String firstName, String lastName)
    {
        connection = DBConnection.getConnection();
        String studentID = null;
        try
        {
            studentNametoID = connection.prepareStatement("select studentID from app.student where firstName = ? and lastName = ?");
            studentNametoID.setString(1, firstName);
            studentNametoID.setString(2, lastName);
            
            resultSet = studentNametoID.executeQuery();
            
            if (resultSet.next()) {
                studentID = resultSet.getString("studentID");
            } 
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentID;
        
    }
    
    public static void clearStudents(){
        connection = DBConnection.getConnection();
        try
        {
            clearStudents = connection.prepareStatement("delete from app.student");
            clearStudents.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}

