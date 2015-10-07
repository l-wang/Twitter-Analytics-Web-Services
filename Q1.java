import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Q1
 */
@WebServlet("/q1")
public class Q1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String X_VALUE = "6876766832351765396496377534476050002970857483815262918450355869850085167053394672634315391224052153";
	BigInteger baseNum;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Q1() {
		super();
		baseNum = new BigInteger(X_VALUE);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String primeNum = request.getParameter("key");
		BigInteger num = new BigInteger(primeNum);
		// To get the result Y value as indicated in writeup
		BigInteger result = num.divide(baseNum);

		// Give response to the client
		response.getWriter().println(result);
		response.getWriter().println(
				"SanYingZhanLvBu,9679-6671-5614,5239-8284-2426,3175-8296-3793");

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		response.getWriter().println(df.format(d));
	}

}
