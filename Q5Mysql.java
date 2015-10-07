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
 * Servlet implementation class Q5Mysql
 */
@WebServlet("/q5")
public class Q5Mysql extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds;

	/**
	 * @throws NamingException
	 * @see HttpServlet#HttpServlet()
	 */
	public Q5Mysql() throws NamingException {
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
//		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(
				"SanYingZhanLvBu,9679-6671-5614,5239-8284-2426,3175-8296-3793");
		Connection con;
		int minScore1 = 0, minScore2 = 0, minScore3 = 0, minTotal = 0, maxScore1 = 0, maxScore2 = 0, maxScore3 = 0, maxTotal = 0;
		try {
			con = ds.getConnection();
			String minID = request.getParameter("m");
			String maxID = request.getParameter("n");
			PreparedStatement stmt1 = con
					.prepareStatement("SELECT * FROM q5 where uid=?");
			stmt1.setString(1, minID);
			PreparedStatement stmt2 = con
					.prepareStatement("SELECT * FROM q5 where uid=?");
			stmt2.setString(1, maxID);

			ResultSet result1 = stmt1.executeQuery();
			ResultSet result2 = stmt2.executeQuery();
			if (result1.next()) {
				minScore1 = result1.getInt("score1");
				minScore2 = result1.getInt("score2");
				minScore3 = result1.getInt("score3");
				minTotal = result1.getInt("total");
			}

			if (result2.next()) {
				maxScore1 = result2.getInt("score1");
				maxScore2 = result2.getInt("score2");
				maxScore3 = result2.getInt("score3");
				maxTotal = result2.getInt("total");
			}

			response.getWriter().println(minID + "\t" + maxID + "\tWINNER");

			response.getWriter().print(minScore1 + "\t" + maxScore1 + "\t");
			if (minScore1 > maxScore1) {
				response.getWriter().println(minID);
			} else if (minScore1 < maxScore1) {
				response.getWriter().println(maxID);
			} else {
				response.getWriter().println("X");
			}

			response.getWriter().print(minScore2 + "\t" + maxScore2 + "\t");
			if (minScore2 > maxScore2) {
				response.getWriter().println(minID);
			} else if (minScore2 < maxScore2) {
				response.getWriter().println(maxID);
			} else {
				response.getWriter().println("X");
			}

			response.getWriter().print(minScore3 + "\t" + maxScore3 + "\t");
			if (minScore3 > maxScore3) {
				response.getWriter().println(minID);
			} else if (minScore3 < maxScore3) {
				response.getWriter().println(maxID);
			} else {
				response.getWriter().println("X");
			}

			response.getWriter().print(minTotal + "\t" + maxTotal + "\t");
			if (minTotal > maxTotal) {
				response.getWriter().println(minID);
			} else if (minTotal < maxTotal) {
				response.getWriter().println(maxID);
			} else {
				response.getWriter().println("X");
			}
			stmt1.close();
			result1.close();
			stmt2.close();
			result2.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
