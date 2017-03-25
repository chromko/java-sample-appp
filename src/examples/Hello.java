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
     private static String query = "SELECT id, word from samples";

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
  	connect();
	executeQuery(query,writer);
  	}  catch (SQLException se) {
            se.printStackTrace();
  	}  catch (NamingException ne) {
            ne.printStackTrace();
      	}
        writer.println("</td>");
        writer.println("</tr>");
        writer.println("</table>");
        writer.println("This is the output of a servlet that is part of");
        writer.println("the Hello, World application.");
        writer.println("</body>");
        writer.println("</html>");
    }
	public void connect() throws SQLException,NamingException {
    	Context initCtx = new InitialContext();
    	Context envCtx = (Context) initCtx.lookup("java:comp/env");
    	DataSource ds = (DataSource) envCtx.lookup("jdbc/sampledb");
    	conn = ds.getConnection();
	}
	public void executeQuery (String query,PrintWriter writer) throws SQLException,NamingException {
	// Look up our data source
	// Allocate and use a connection from the pool
	//... use this connection to access the database ...
	Statement stmt = conn.createStatement();
	ResultSet rs = stmt.executeQuery(query);
	while (rs.next()) {
		int num = rs.getInt("id");
  		String word = rs.getString("word");
  		writer.println("<tr>");
  		writer.format	("Word %s have %d ", word,num) ;
  		writer.println("</tr>");
		}
	rs.close();
	stmt.close();
	conn.close();
	}
}
