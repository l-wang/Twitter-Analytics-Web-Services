
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
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Servlet implementation class Q3HBase
 */
@WebServlet("/q4")
public class Q4HBase extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ACCOUNT_ONE = "9679-6671-5614,";
	private static final String ACCOUNT_TWO = "5239-8284-2426,";
	private static final String ACCOUNT_THREE = "3175-8296-3793";
	private static final String TEAM_ID = "SanYingZhanLvBu,";
	private HTable myHTable;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Q4HBase() {
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
			myHTable = new HTable(config, "tweet4");
		} catch (IOException e) {
			e.printStackTrace();
			return;

		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		// To get the requested info from URL and make them usable in query
		String date = request.getParameter("date").trim().replace("-", "");
		String location = request.getParameter("location").trim();
		int mInt = Integer.parseInt(request.getParameter("m"));
		int nInt = Integer.parseInt(request.getParameter("n"));
		String m = String.format("%07d", mInt);
		String n = String.format("%07d", nInt + 1);
		
		response.getWriter().println("SanYingZhanLvBu,9679-6671-5614,5239-8284-2426,3175-8296-3793");
		Scan scan = new Scan(Bytes.toBytes(date + ":" + location + ":" + m), Bytes.toBytes(date + ":"
				+ location + ":" + n));
		ResultScanner resultScanner = myHTable.getScanner(scan);
		StringBuilder sb = new StringBuilder();
		
		/*Result hBaseResult = resultScanner.next();
		while (hBaseResult != null) {
			if (hBaseResult.value() != null && hBaseResult.value().length != 0) {
				String temp = new String(hBaseResult.value(), "UTF-8");
				
				sb.append(temp + "\n");
				
			}
			hBaseResult = resultScanner.next();
		}*/
		
		int num = nInt + 1 - mInt;
		Result[] results = resultScanner.next(num);
		for (Result hBaseResult : results) {
			//if (hBaseResult.value() != null && hBaseResult.value().length != 0) {
				String temp = new String(hBaseResult.value(), "UTF-8");
				
				sb.append(temp + "\n");
			//}
		}
		response.getWriter().print(sb.toString());
		
		resultScanner.close();
	}

}
