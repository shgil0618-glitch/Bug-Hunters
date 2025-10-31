<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  Connection conn = null;  PreparedStatement pstmt = null;   ResultSet  rset = null;
	String driver   = "oracle.jdbc.driver.OracleDriver";
	String url      = "jdbc:oracle:thin:@localhost:1521:xe";
	String user     = "scott"; 
	String pass     = "tiger";
	//0. 데이터 넘겨받기 - 아이디/비밀번호
	String email    = request.getParameter("email");
	String password = request.getParameter("password");
	out.println(email + "/" + password);
	//드커프리
	try{
		//1. 드라이버연동
		Class.forName(driver);
		//2. 커넥션
		conn = DriverManager.getConnection(url, user, pass);
		//3. PreparedStatement  select * from appuser  where email=?  and password=?
		String sql = "select count(*) cnt , APP_USER_ID  from appuser  "
		           +" where email=?  and password=?  group by APP_USER_ID";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, email);
		pstmt.setString(2, password);
		//4. ResultSet   -  select   : executeQuery() / insert, update, delete - executeUpdate()
		int result = -1;
		int APP_USER_ID=-1;
		rset = pstmt.executeQuery();  //표-줄-칸
		while(rset.next()){  
			result = rset.getInt("cnt"); 
			APP_USER_ID = rset.getInt("APP_USER_ID");
			// ## 브라우저에-서버에 정보남기기 - session (사용할수 있는 시간) / 브라우저 닫힐때까지
			session.setAttribute("email", email);
			session.setAttribute("APP_USER_ID", APP_USER_ID);
		}
		out.println(APP_USER_ID);
		if(result == 1){ out.println("<script>alert('로그인성공!'); location.href='mypage.jsp?APP_USER_ID=" + APP_USER_ID + "'; </script>"); }
		else{            out.println("<script>alert('정보를 확인해주세요'); history.go(-1); </script>");   }
		
	}catch(Exception e){e.printStackTrace();
	}finally{
		if(rset != null)  rset.close();
		if(pstmt != null) pstmt.close();
		if(conn != null)  conn.close();
	}
%>