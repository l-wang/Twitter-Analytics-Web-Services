import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class Q2MySql
 */
@WebServlet("/q2")
public class Q2MySql extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataSource ds;

	/**
	 * @throws NamingException
	 * @see HttpServlet#HttpServlet()
	 */
	public Q2MySql() throws NamingException {
		super();
		InitialContext ctx = new InitialContext();
		ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MySQLDB");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		try {
			Connection con;
			try {
				con = ds.getConnection();
			} catch (SQLException e) {
				throw new AssertionError(e);
			}
			
			// To get the requested info from URL and make them usable in query
			String theID = request.getParameter("userid");
			String theTimeStamp = request.getParameter("tweet_time");
			// Replace the marks and form the key for our database

			theTimeStamp = theTimeStamp.trim().replace("-", "")
					.replace("+", "").replace(":", "").replace(" ", "");
//			 PreparedStatement stmt =
//			 con.prepareStatement("SELECT results FROM q2 where time=? and uid=?");
			PreparedStatement stmt = con.prepareStatement("SELECT results FROM q2 where id=?");
			response.getWriter()
					.println(
							"SanYingZhanLvBu,9679-6671-5614,5239-8284-2426,3175-8296-3793");
			stmt.setString(1, theTimeStamp + theID);
//			stmt.setString(1, theTimeStamp);
//			stmt.setString(2, theID);
			ResultSet result = stmt.executeQuery();
			/*
			 * ResultSet result = stmt
			 * .executeQuery("SELECT results FROM q2 where time" + "=" +
			 * theTimeStamp + " and " + "uid=" + theID);
			 */
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
