package Labs;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/Labs/Todo", loadOnStartup = 1)
public class Todo extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public Todo()
    {
        super();
    }

    public void init( ServletConfig config ) throws ServletException
    {
        super.init( config );

        try
        {
            Class.forName( "com.mysql.jdbc.Driver" );
        }
        catch( ClassNotFoundException e )
        {
            throw new ServletException( e );
        }
    }

    protected void doGet( HttpServletRequest request,
        HttpServletResponse response ) throws ServletException, IOException
    {
    	List<TodoBean> entries = new ArrayList<TodoBean>();
        ArrayList<TodoBean> archived = new ArrayList<TodoBean>();
        int remaining = 0;
        Connection c = null;
        try
        {
            String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu53";
            String username = "cs3220stu53";
            String password = "!.S60cVG";

            c = DriverManager.getConnection( url, username, password );
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "select * from todos" );

            while( rs.next() )
            {
                TodoBean entry = new TodoBean( rs.getInt( "id" ),rs.getString( "description" ), rs.getString( "state" ) );
              if(entry.getState().equals("archived"))
              {
            	  archived.add(entry);
              }
              else{
            	  if(entry.getState().equals("notdone"))
            	  {
            		  remaining++;
            	  }
            	 entries.add( entry );
              }
            }
        }
        catch( SQLException e )
        {
            throw new ServletException( e );
        }
        finally
        {
            try
            {
                if( c != null ) c.close();
            }
            catch( SQLException e )
            {
                throw new ServletException( e );
            }
        }
        request.setAttribute( "remaining", remaining );
        request.setAttribute( "todos", entries );
        request.setAttribute("archived", archived);
        request.getRequestDispatcher( "/WEB-INF/Todo.jsp" ).forward(
            request, response );
    }

    protected void doPost( HttpServletRequest request,
        HttpServletResponse response ) throws ServletException, IOException
    {
    	String description = request.getParameter("description");
    	String error = " ";
        Connection c = null;
        try
        {
        	String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu53";
			String username = "cs3220stu53";
			String password = "!.S60cVG" ;
			String sql = "insert into todos (description,state) values(?, 'notdone')";	
			
			
            if(description == null || description.isEmpty())
        	{	 error = "Cannot be left blank. ";
        		request.setAttribute("error", error);
        	}else
        	{
        			c = DriverManager.getConnection(url,username,password);
        			PreparedStatement pstmt = c.prepareStatement(sql);
    				pstmt.setString(1,description);
    				pstmt.executeUpdate();	
        	}
          
        }
        catch( SQLException e )
        {
            throw new ServletException( e );
        }
        finally
        {
            try
            {
                if( c != null ) c.close();
            }
            catch( SQLException e )
            {
                throw new ServletException( e );
            }
        }

    	
        doGet( request, response );
    }

}