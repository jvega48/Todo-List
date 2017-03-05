package Labs;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Update
 */
@WebServlet("/Labs/Update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Update()
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		 	Integer id = Integer.valueOf( request.getParameter( "id" ) );  
	        String state = request.getParameter( "state" );
	        Connection c = null;
			try{
				String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu53";
				String username = "cs3220stu53";
				String password = "!.S60cVG" ;
				String sql = "update todos set state =? where id = ?" ;			
				
				if(state.equals("done"))
				{
					c = DriverManager.getConnection(url,username,password);
					PreparedStatement pstmt = c.prepareStatement(sql);
					pstmt.setString(1,"done");
					pstmt.setInt(2, id);
					pstmt.executeUpdate();
				
				}
				else
				{	c = DriverManager.getConnection(url,username,password);
					PreparedStatement pstmt = c.prepareStatement(sql);
					pstmt.setString(1,"notdone");
					pstmt.setInt(2,id);
					pstmt.executeUpdate();
				}
				
			}
			catch(SQLException e)
			{
				throw new ServletException( e );
			}
			finally{
				try{
					if(c!=null)
					{
						c.close();
					}
				}
				catch(SQLException e){
					throw new ServletException( e );
				}
			}
			response.sendRedirect("Todo");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
		doPost(request,response);
	}	
}
