<%--Import class files/libraries for jsp--%>
<%@page import="java.io.*"
        import="java.sql.*"
        import="java.util.Properties" %>

<%-- Database Configuration--%>
<%!
    public static Connection getConnection(ServletContext app)
            throws ClassNotFoundException, IOException, SQLException
    {
        Properties props = new Properties();
        try(InputStream in = app.getResourceAsStream("/WEB-INF/database.properties")) {
            props.load(in);
        }
        String driver = props.getProperty("jdbc.drivers");
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Students</title>
    </head>
    <body>
        <h1>Study Records</h1>
        
        <%
           try(Connection conn = getConnection(application))
                {
                //Set the format of prepared SQL command, accepting the query as an input.
                String preparedSQLCommand = "SELECT Name, CourseTitle FROM Study WHERE Grade=? ORDER BY Name";
                
                //Create a prepared statement                
                PreparedStatement pStat = conn.prepareStatement(preparedSQLCommand);
                
                //PreparedStatement pStat = conn.prepareStatement(preparedSQLCommand, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //This method can avoid creating a </table> in HTML file when no record was found.
                
                //Get the data from user using GET method
                String query = request.getParameter("grade");
                /*  String gradeQuery = request.getParameter("grade").trim() not appropriate 
                    because user type in browser using GET method   */       
                
                //If no input, show message
                if(query == null) { %>
                    <h2>Invalid input.<h2>
                <%}
                else {
                    //Set the grade query in the statement
                    pStat.setString(1, query);
                    
                    //Get the ResultSet after SQL execution
                    ResultSet rs = pStat.executeQuery();
                    
                    // Set a boolean value to detect presence of tuples/rows of data in DB
                    boolean queryStatus = false;
                    %>
                    
                    <%while(rs.next()) {
                        //First row is present, create table and headers
                        if(rs.getRow()== 1) {%>
                                            <table border="1">
                        <h2>Students who got <%=query %></h2>
                        <tr><th>Name</th><th>Course Title</th></tr>                            
                        <%}
                        queryStatus = true;%>
                        
                        <tr><td><%=rs.getString(1).trim()%></td><td><%=rs.getString(2).trim()%></td></tr>
                    <%}%>
                    <% //if(rs.last()) Avoid creating </table> when no record was found.%></table><% %>
                    <%if(!queryStatus) {%>
                    <h2>Record not found</h2>
                    <%}
                        
                }

            }
        %>    
    </body>
</html>
