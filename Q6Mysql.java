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
 * Servlet implementation class Q6Mysql
 */
@WebServlet("/q6")
public class Q6Mysql extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds;

	/**
	 * @throws NamingException
	 * @see HttpServlet#HttpServlet()
	 */
	public Q6Mysql() throws NamingException {
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
		Connection con = null;
		try {
			con = ds.getConnection();
			long minID = Long.parseLong(request.getParameter("m").trim());
			long maxID = Long.parseLong(request.getParameter("n").trim());

			PreparedStatement stmt1 = con
					.prepareStatement("SELECT max(uid) as left1 from q6 where uid<=?");
			PreparedStatement stmt2 = con
					.prepareStatement("SELECT max(uid) as right1 from q6 where uid<=?");
			stmt1.setLong(1, minID - 1);
			stmt2.setLong(1, maxID);
			ResultSet result1 = stmt1.executeQuery();
			ResultSet result2 = stmt2.executeQuery();
			long temp01 = 0, temp02 = 0;
			if (result1.next()) {
				temp01 = result1.getLong("left1");
			}
			
			if (result2.next()) {
				temp02 = result2.getLong("right1");
			}

			result1.close();
			result2.close();
			stmt1.close();
			stmt2.close();
			
			PreparedStatement stmt3 = con.prepareStatement("SELECT results FROM q6 where uid=?");
			PreparedStatement stmt4 = con.prepareStatement("SELECT results FROM q6 where uid=?");
			stmt3.setLong(1, temp01);
			stmt4.setLong(1, temp02);
			ResultSet result3 = stmt3.executeQuery();
			ResultSet result4 = stmt4.executeQuery();
			long temp1 = 0;
			if (result3.next()) {
				temp1 = result3.getLong("results");
			}
			long temp2 = 0;
			if (result4.next()) {
				temp2 = result4.getLong("results") - temp1;
			}
			response.getWriter()
					.println(
							"SanYingZhanLvBu,9679-6671-5614,5239-8284-2426,3175-8296-3793");
			response.getWriter().println((temp2 >= 0) ? temp2 : 0);
			
			result3.close();
			result4.close();
			stmt3.close();
			stmt4.close();
//			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
