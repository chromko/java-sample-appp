package examples;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.sql.Statement;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;



/**
 * Simple Hello servlet.
 */

public final class Hello extends HttpServlet {


    /**
     * Respond to a GET request for the content produced by
     * this servlet.
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are producing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
     private static Connection conn;
     private static DataSource dataSource;

     public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
     throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Sample Application Servlet Page</title>");
        writer.println("</head>");
        writer.println("<body bgcolor=white>");

        writer.println("<table border=\"0\" cellpadding=\"10\">");
        writer.println("<tr>");
        writer.println("<td>");
        writer.println("<img src=\"images/springsource.png\">");
        writer.println("</td>");
        writer.println("<td>");
        writer.println("<h1>Sample Application Servlet</h1>");
  	try {
  	goToDB (writer);
  	}  catch (SQLException se) {
            se.printStackTrace();
      }
        writer.println("</td>");
        writer.println("</tr>");
        writer.println("</table>");

        writer.println("This is the output of a servlet that is part of");
        writer.println("the Hello, World application.");

        writer.println("</body>");
        writer.println("</html>");
    }
	private void goToDB (PrintWriter writer) throws SQLException {
	try {
    Context initCtx = new InitialContext();
    DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/sampledb");
    Connection conn = ds.getConnection();
	} catch (NamingException ne) {
		ne.printStackTrace();
	}

	// Look up our data source
	// Allocate and use a connection from the pool
	writer.println("Connection successful!!!");
	//... use this connection to access the database ...
	Statement stmt = conn.createStatement();
  ResultSet rs = stmt.executeQuery("SELECT id ,word  FROM samples;");
	  while (rs.next()) {
		  int num = rs.getInt(1);
  		String word = rs.getString(2);
  		writer.println("<tr>");
  		writer.format	("Word %s have %d ", word,num) ;
  		writer.println("</tr>");
		}

	rs.close();
	stmt.close();
	conn.close();
	}
}
