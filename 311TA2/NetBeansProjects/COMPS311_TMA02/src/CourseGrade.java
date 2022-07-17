import java.io.*;
import java.sql.*;
import java.util.*;

public class CourseGrade {
    
    // Create a static method for database connection.
    public static Connection getConnection() throws SQLException, IOException {
    /* this is an alternative way to create the database connection. But I want to try .properties method    
        String url = "jdbc:derby://localhost:1527/COREJAVA";
        String username = "dbuser";
        String password = "secret";
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");


        }
        catch (ClassNotFoundException cnfe) 
        {
            System.out.println("Error loading driver: " + cnfe);
        }
        
        return DriverManager.getConnection(url, username, password);                   
    }
    */
        var props = new Properties();
        // try-with-resources, autoclose of InputStream after use
        // use derby database
        //InputStream in = Files.newInputStream(Paths.get("database.properties")) seems not work if CLASSPATH not matched
        /* InputStream in = Files.newInputStream(Paths.get("src/database.properties")) 
            - works on MacBook but for play safe I used getResourcesAsStream() */        
        try (InputStream in = CourseGrade.class.getResourceAsStream("database.properties"))
        {
            props.load(in);
        }
        
        //get the drivers info from database.properties config file, more flexible for database setting
        String drivers = props.getProperty("jdbc.drivers");
        if(drivers != null) {
            System.setProperty("jdbc.drivers",drivers);
        }
        
        // get connection details from database.properties config file
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        
        //return Connection object
        return DriverManager.getConnection(url, username, password);     
    }
           
    public static void SearchCourseGrade(String query) throws IOException, SQLException {
        try(Connection conn = getConnection()) {
            //Set the format of prepared SQL command, accepting the query as an input.
            String preparedSQLCommand = "SELECT Name, CourseTitle FROM Study WHERE Grade = ? ORDER BY Name";
            
            //Create a prepared statement
            PreparedStatement pStat = conn.prepareStatement(preparedSQLCommand);
            //Set the grade query in the statement
            pStat.setString(1, query);
            
            //Get the ResultSet after SQL execution
            ResultSet rs = pStat.executeQuery();
            
            // Set a boolean value to detect presence of tuples/rows of data in DB
            // print "Record not found." message if the status is kept false at the end.
            boolean resultPresent = false;
            
            while(rs.next()) {
                resultPresent = true;
                System.out.println(rs.getString("Name").trim() + ", " + rs.getString("CourseTitle").trim());
              //System.out.println(rs.getString(1).trim() + ", " + rs.getString(2).trim()); also works.
            }
            
            if(!resultPresent) {
                System.out.println("Record not found.");
            }
            
        }
    }
    
    public static void main(String[] args) {
        //try-with-resources, auto close resource after use
        try(Scanner sc = new Scanner(System.in)) {
            String line = null;
            //allow user to input the grade
            System.out.print("Enter grade: ");
            //check input status, trim unnecessary space.
            if(sc.hasNextLine()) {
                line = sc.nextLine().trim();
                //if no input, print out invalid input.
                if(line.equals("")) {
                    System.out.println("Invalid input.");
                }
                else {
                    SearchCourseGrade(line);
                }
            }
            
        }
        //Handle IOException from SearchCourseGrade() method
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        //Handle SQLException from SearchCourseGrade() method        
        catch (SQLException sqle) {
            for (Throwable t: sqle) {
                System.out.println(t.getMessage());
            }            
        }
        
    }
    

}
