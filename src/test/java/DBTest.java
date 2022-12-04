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

	// ���ݿ����ӵ�ַ�����ݿ�mysql5������5�����¡���
	String url = "jdbc:mysql://127.0.0.1:3306/db_homework";
	// ���ݿ����ӵ�ַ�����ݿ�mysql8������8��8���ϡ���
	// String
	// url="jdbc:mysql://127.0.0.1:3306/jdbc?serverTimezone=GMT%2B8&useSSL=false"
	// �����������ƣ���cmd�������ݿ�ʱ��������ƣ�
	String user = "root";
	// �����������루��cmd�������ݿ�ʱ��������룩
	String password = "root";

	@Test
	public void testJdbc() throws ClassNotFoundException, SQLException {
		// ��������mysql5(����5������)
		Class.forName("com.mysql.jdbc.Driver");
		// ��������mysql8������8��8���ϣ�
		// Class.forName("com.mysql.cj.jdbc.Driver");
		// ��ȡ���Ӷ���
		Connection con = DriverManager.getConnection(url, user, password);
		// �ж����ݿ������Ƿ�ر�turn���ǹرգ�fales���Ǵ�
		System.out.println("mysql���ݿ�:" + con.isClosed());
		PreparedStatement pst = con.prepareStatement("SELECT * FROM J");
		ResultSet rs = pst.executeQuery();
		int columns = rs.getMetaData().getColumnCount();
		// ��ʾ��,���ı�ͷ
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
