package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {

	public static void main(String[] args) {
		
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		
		String dbUrl = "jdbc:mysql://localhost:3307/challenge_java";
		String root = "root";
		String pw = "sakie6630";
		
		try {
			
			// DB接続実行
			con = DriverManager.getConnection(dbUrl, root, pw);
			System.out.println("データベース接続成功：" + con);
			System.out.println("レコード更新を実行します");
			
			// 更新クエリ作成
			statement = con.createStatement();
			String updateSql = "UPDATE scores SET score_math = 95, score_english = 80 WHERE id = 5;";
			// 更新実行
			int count = 0;
			count = statement.executeUpdate(updateSql);
			System.out.println(count + "件のレコードが更新されました");
			
			// 検索クエリ作成
			String selectSql = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC;";
			rs = statement.executeQuery(selectSql);
			System.out.println("数学・英語の点数が高い順に並べ替えました");
			
			int dataCount = 1;
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int mathScore = rs.getInt("score_math");
				int englishScore = rs.getInt("score_english");
				
				System.out.println(dataCount + "件目：生徒ID=" + id + "／氏名=" + name + "／数学=" + mathScore + "／英語=" + englishScore);
				
				dataCount = dataCount + 1;
			}
			
		}catch(SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		}finally{
            if( statement != null ) {
                try { statement.close(); } catch(SQLException ignore) {}
            }
            if( con != null ) {
                try { con.close(); } catch(SQLException ignore) {}
            }
		}
	}
}
