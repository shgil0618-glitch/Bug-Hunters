package com.thejoa703.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.thejoa703.dto.UserDto;

public class UserDao {
///////////////////////////////////////////////회원가입/////////////////////////////////////////////////////////
	public int insert(UserDto dto) {
		int result = -1;

		String sql = "INSERT INTO USERS (APPUSERID, PASSWORD, NICKNAME, EMAIL, MOBILE, JOINDATE) "
				+ "VALUES (USERS_SEQ.NEXTVAL, ?, ?, ?, ?, SYSDATE)";

		Connection conn = null;
		PreparedStatement pstmt = null;
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott", pass = "tiger";

		try {
			Class.forName(driver);

			conn = DriverManager.getConnection(url, user, pass);

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPassword());
			pstmt.setString(2, dto.getNickname());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getMobile());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("DB 오류: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}

		return result;
	}

//////////////////////////////////////////////////로그인////////////////////////////////////////////////////////
	public UserDto login(String EMAIL, String PASSWORD) {
		UserDto dto = null;
		String sql = "SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott", pass = "tiger";

		try {
			Class.forName(driver);

			conn = DriverManager.getConnection(url, user, pass);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, EMAIL);
			pstmt.setString(2, PASSWORD);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new UserDto();
				dto.setEmail(rs.getString("EMAIL"));
				dto.setPassword(rs.getString("PASSWORD"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

/////////////////////////////////////////////////마이페이지/////////////////////////////////////////////////////////
	public UserDto mypage(String EMAIL) {
		UserDto dto = null;
		String sql = "SELECT * FROM USERS WHERE EMAIL = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott", pass = "tiger";

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, EMAIL);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new UserDto();
				dto.setPASSWORD(rs.getString("PASSWORD"));
				dto.setNICKNAME(rs.getString("NICKNAME"));
				dto.setEMAIL(rs.getString("EMAIL"));
				dto.setMOBILE(rs.getString("MOBILE"));

				java.sql.Timestamp ts = rs.getTimestamp("JOINDATE");
				if (ts != null) {
					dto.setJOINDATE(ts.toLocalDateTime());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}

		return dto;
	}

/////////////////////////////////////////////회원정보 수정///////////////////////////////////////////////////////	  
	public int edit(UserDto dto) {
		int result = 0;
		String sql = "UPDATE USERS SET NICKNAME = ?, PASSWORD = ?, MOBILE = ? WHERE EMAIL = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String pass = "tiger";

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getNICKNAME());
			pstmt.setString(2, dto.getPASSWORD());
			pstmt.setString(3, dto.getMOBILE());
			pstmt.setString(4, dto.getEMAIL());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return result;
	}
////////////////////////////////////////////회원탈퇴///////////////////////////////////////////////////////

	public int delete(String EMAIL) {
		int result = 0;
		String sql = "DELETE FROM USERS WHERE EMAIL = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott", pass = "tiger";

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, EMAIL);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}

		return result;
	}

}
