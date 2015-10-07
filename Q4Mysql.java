

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class Q4Mysql
 */
@WebServlet("/q4")
public class Q4Mysql extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds; 
    /**
     * @throws NamingException 
     * @see HttpServlet#HttpServlet()
     */
    public Q4Mysql() throws NamingException {
        super();
        InitialContext ctx = new InitialContext();
		ds = (DataSource)ctx.lookup("java:comp/env/jdbc/MySQLDB");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		try {
			Connection con;
			try {
				con = ds.getConnection();
			} catch (SQLException e) {
				throw new AssertionError(e);
			}
			// To get the requested info from URL and make them usable in query
			String theDate = request.getParameter("date").trim().replace("-", "");
			String theLoc = request.getParameter("location").trim();
			String theMinRank = request.getParameter("m");
			String theMaxRank = request.getParameter("n");
			response.getWriter().println("SanYingZhanLvBu,9679-6671-5614,5239-8284-2426,3175-8296-3793");
			
			
			PreparedStatement stmt = con.prepareStatement("SELECT results FROM q4 where time_place=? AND rank>=? AND rank<=?");
			
			stmt.setString(1, theDate + ":" + theLoc);
			stmt.setString(2, theMinRank);
			stmt.setString(3, theMaxRank);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				response.getWriter().println(result.getString("results"));
			}
			stmt.close();
			result.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
