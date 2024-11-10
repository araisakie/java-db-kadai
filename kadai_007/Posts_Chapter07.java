package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {

	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement  statement = null;
		ResultSet rs = null;
		
		String[][] hitokoto = {
				{"1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13"},
				{"1002", "2023-02-08", "お疲れ様です！", "12"},
				{"1003", "2023-02-09", "今日も頑張ります！	", "18"},
				{"1001", "2023-02-09", "無理は禁物ですよ！	", "17"},
				{"1002", "2023-02-10", "明日から連休ですね！", "20"},
		};
		
		try {
			
			// DB接続
			con = DriverManager.getConnection(
					 "jdbc:mysql://localhost:3307/challenge_java",
					 "root",
					 "sakie6630"
					);
			System.out.println("データベース接続成功: " + con);
			System.out.println("レコード追加を実行します");
			
			// 追加クエリ作成
//			String insertSql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?);";
//			statement = con.prepareStatement8(insertSql);
			statement = con.prepareStatement("INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?)");
			
			int rowCnt = 0;
			for(int i = 0; i < hitokoto.length; i++) {
				statement.setString(1, hitokoto[i][0]);
				statement.setString(2, hitokoto[i][1]);
				statement.setString(3, hitokoto[i][2]);
				statement.setString(4, hitokoto[i][3]);
				
				// データ追加
				statement.executeUpdate();
				
				rowCnt = rowCnt + 1;
			}
			System.out.println(rowCnt + "件のレコードが追加されました");
			
			// 検索クエリ作成
			String selectSql = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002";
			rs = statement.executeQuery(selectSql);
			System.out.println("ユーザーIDが1002のレコードを検索しました");

			// 検索結果
			int count = 1;
			while(rs.next()) {
				Date insertDate = rs.getDate("posted_at");
				String content = rs.getString("post_content");
				int like = rs.getInt("likes");
				
				System.out.println(count + "件目：投稿日時=" + insertDate + "／投稿内容=" + content + "／いいね数" + like);
				
				count = count + 1;
			}
			
		} catch(SQLException e) {
			
			System.out.println("エラー発生：" + e.getMessage());
			
		} finally {

            if( statement != null ) {
                try { statement.close(); } catch(SQLException ignore) {}
            }
            if( con != null ) {
                try { con.close(); } catch(SQLException ignore) {}
            }
		}
		

	}

}
