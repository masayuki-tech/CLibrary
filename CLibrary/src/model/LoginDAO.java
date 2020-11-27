package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

	//ＤＢ接続情報の設定
	//final String URL = "jdbc:mysql://localhost:3306/project?serverTimezone=JST";
	// 	final String USER="javauser";
	//	final String PASS="java06pass";

	final String URL = "jdbc:mysql://172.16.71.108:3306/sampledb?serverTimezone=JST";
	final String USER = "CLibary";
	final String PASS = "CLibrary01";

	//ＳＱＬ文
	final String LOGIN_SQL = "select staff_id,pass,name,gender from staffs where staff_id=? && pass=?";//ログイン用
	final String REGISTER_SQL = "insert into staffs values(?,?,?,?)";//新規登録用
	final String PROFILE_SQL = "select staff_id,pass,name,gender from staffs where staff_id=? && pass=? && name=?)";

	//ログイン*****************************************************************
	//ログイン用のメソッド（引数：staffId,pass　戻り値：List<staffsDTO>）
	public staffsDTO getLoginDAO(String staffId, String pass) {

		//DBに接続して、ログイン可能かどうかのチェック
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm = conn.prepareStatement(LOGIN_SQL)) {

//			//作成したDTOを格納するArrayListを生成
//			List<staffsDTO> loginList = new ArrayList<>();

			//staffsＤＴＯ
			staffsDTO sd=new staffsDTO();
			//？に差し込む
			pstm.setInt(1, Integer.parseInt(staffId));
			pstm.setString(2, pass);

			//SQL文の実行(ResultSetの取得)
			ResultSet rs = pstm.executeQuery();

			//ResultSetのフェッチ処理（取り出すデータがある間、繰り返す)
			while (rs.next()) {
				//各列のデータをDTOにセッターを使って保存
				int staff_id = rs.getInt("staff_id"); //ＩＤ
				pass = rs.getString("pass");//パスワード
				String name = rs.getString("name");//名前
				int gender = rs.getInt("gender");//性別

				//取り出したレコードを保存するためのDTOオブジェクトの生成
				sd = new staffsDTO(staff_id, name, pass, gender);

//				//データが格納されたDTOオブジェクトをArrayListに追加保存
//				loginList.add(sd);
//			}


			}
			return sd;
		} catch (SQLException e) {
			return null;
		}
	}

	//新規登録******************************************************************
	//新規登録して、ユーザーのプロフィールをArrayListで返すメソッド
	public boolean getRegisterDAO(int staffId, String pass, String name, int gender) {
		//DBに接続して、新規登録する
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm = conn.prepareStatement(REGISTER_SQL)) {

			//取り出したレコードを保存するためのDTOオブジェクトの生成
			staffsDTO sd = new staffsDTO();

			//作成したDTOを格納するArrayListを生成
			staffsDTO list = new staffsDTO();

			//？に差し込む
			pstm.setInt(1, staffId);
			pstm.setString(2, pass);//パスワード
			pstm.setString(3, name);//名前
			pstm.setInt(4, gender);//性別

//			//SQL文の実行(ResultSetの取得)
			if (pstm.executeUpdate() !=1) {
				return false;
			}else {
				return true;
			}
//			} else {
//				//新規登録したアカウントの情報を取得する
//				list = getList(staffId, name, pass, gender);
//				return list;
//			}

		} catch (SQLException e) {
			return false;
		}

	}

//	//新規登録したアカウントの情報を取得****************************************************
//	public staffsDTO getList(int staffId, String name, String pass, int gender) {
//
//		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
//				PreparedStatement pstm = conn.prepareStatement(PROFILE_SQL)) {
//
//			staffsDTO sd=null;
//
//			//？に差し込む
//			pstm.setInt(1, staffId);//ＩＤ
//			pstm.setString(2, pass);//パスワード
//			pstm.setString(3, name);//名前
//
//			//SQL文の実行(ResultSetの取得)
//			ResultSet rs = pstm.executeQuery();
//
//			//ResultSetのフェッチ処理（取り出すデータがある間、繰り返す)
//			while (rs.next()) {
//				//各列のデータをDTOにセッターを使って保存
//				int staff_id = rs.getInt("staff_id"); //ＩＤ
//				pass = rs.getString("pass");//パスワード
//				name = rs.getString("name");//名前
//				gender = rs.getInt("gender");//性別
//
//				//取り出したレコードを保存するためのDTOオブジェクトの生成
//				sd = new staffsDTO(staff_id, name, pass, gender);
//
//				return sd;
//			}
//			return sd;
//
//		} catch (SQLException e) {
//			return null;
//		}
//	}

}