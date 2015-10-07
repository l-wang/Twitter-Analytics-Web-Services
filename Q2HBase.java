import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Servlet implementation class Q2HBase
 */
@WebServlet("/q2")
public class Q2HBase extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ACCOUNT_ONE = "9679-6671-5614,";
	private static final String ACCOUNT_TWO = "5239-8284-2426,";
	private static final String ACCOUNT_THREE = "3175-8296-3793";
	private static final String TEAM_ID = "SanYingZhanLvBu,";
	private HTable myHTable;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Q2HBase() {
		super();
		// Set the configuration parameters
		Configuration config = HBaseConfiguration.create(); 
		config.clear();
		config.set("fs.hdfs.impl", "emr.hbase.fs.BlockableFileSystem");
		config.set("hbase.regionserver.handler.count", "100");
		// We put front end and master node on the same 
		// So we specify the DNS as the localhost
		config.set("hbase.zookeeper.quorum", "localhost");
		config.set("hbase.rootdir", "hdfs://localhost:9000/hbase");
		config.set("hbase.cluster.distributed", "true");
		config.set("hbase.tmp.dir", "/mnt/var/lib/hbase/tmp-data");

		// Connect to Hbase
		try {
			HBaseAdmin.checkHBaseAvailable(config);
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
			return;
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// Get table object
		try {
			myHTable = new HTable(config, "tweet"); 
		} catch (IOException e) {
			e.printStackTrace();
			return;

		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		// To get the requested info from URL and make them usable in query
		String theID = request.getParameter("userid");
		String theTimeStamp = request.getParameter("tweet_time");
		theTimeStamp = theTimeStamp.replace("-", "").replace("+", "")
				.replace(":", "").replace(" ", "");
		String queryKey = theTimeStamp + theID;
		response.getWriter().println("SanYingZhanLvBu,9679-6671-5614,5239-8284-2426,3175-8296-3793");
		Get get = new Get(Bytes.toBytes(queryKey));
		Result hBaseResult = myHTable.get(get);
		if (hBaseResult != null && hBaseResult.value() != null
				&& hBaseResult.value().length != 0) {
			String temp = new String(hBaseResult.value(), "UTF-8");
			//temp = temp.replace("\\", "\\\\").replace("\n", "\\\\n").replace("\r", "\\\\r");
			//byte[] temp1 = temp.getBytes();
			//String temp2 = new String(temp1, 1, temp1.length,"UTF8"); 
			temp = temp.replace("\\n", "\n").replace("\\\"", "\"").replace("\\\\", "\\");
			response.getWriter().println(temp);
			//response.getWriter().println(new String(hBaseResult.value(), "UTF-8"));
		}

	}

}
