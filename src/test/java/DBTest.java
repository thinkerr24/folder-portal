import java.sql.*;

import org.junit.Test;

class MyDb {
	private Connection conn = null;
	private PreparedStatement pst = null;
	private static MyDb mydb = null;

	private MyDb() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.0:3308/db_homework?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC",
				"root", "root");
	}

	public static MyDb getMyDb() throws Exception {
		return mydb == null ? new MyDb() : mydb;
	}

	public void closeConn() throws Exception {
		if (pst != null && !pst.isClosed()) {
			pst.close();
		}
		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	}
}

public class DBTest {

	@Test
	public void dbConn() throws Exception {
		MyDb mydb = null;
		try {
			mydb = MyDb.getMyDb();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (mydb != null) {
				mydb.closeConn();
			}
		}

	}

	// 数据库连接地址（数据库mysql5【包括5和以下】）
	String url = "jdbc:mysql://127.0.0.1:3306/db_homework";
	// 数据库连接地址（数据库mysql8【包括8和8以上】）
	// String
	// url="jdbc:mysql://127.0.0.1:3306/jdbc?serverTimezone=GMT%2B8&useSSL=false"
	// 数据连接名称（在cmd进入数据库时输入的名称）
	String user = "root";
	// 数据连接密码（在cmd进入数据库时输入的密码）
	String password = "root";

	@Test
	public void testJdbc() throws ClassNotFoundException, SQLException {
		// 加载驱动mysql5(包括5和以下)
		Class.forName("com.mysql.jdbc.Driver");
		// 加载驱动mysql8（包括8和8以上）
		// Class.forName("com.mysql.cj.jdbc.Driver");
		// 获取链接对象
		Connection con = DriverManager.getConnection(url, user, password);
		// 判断数据库连接是否关闭turn就是关闭，fales就是打开
		System.out.println("mysql数据库:" + con.isClosed());
		PreparedStatement pst = con.prepareStatement("SELECT * FROM J");
		ResultSet rs = pst.executeQuery();
		int columns = rs.getMetaData().getColumnCount();
		// 显示列,表格的表头
		for (int i = 1; i <= columns; i++) {
			System.out.print(rs.getMetaData().getColumnName(i));
			System.out.print("\t\t");
		}
		while (rs.next()) {
			for (int i = 1; i <= columns; i++) {
				System.out.print(rs.getString(i));
				System.out.print("\t\t");
			}
			System.out.println();
		}
		System.out.println();
		if (!pst.isClosed()) {
			pst.close();
		}
		if (!con.isClosed()) {
			con.close();
		}

	}
}
