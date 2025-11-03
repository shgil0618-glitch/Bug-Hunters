<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../inc/header.jsp" %>
   <div class="container card  my-5">
      <h3  class="card-header"> MYPAGE </h3>
      <%@page import="java.sql.*"%>
	  <%
	  	Connection conn = null;  PreparedStatement pstmt = null;   ResultSet  rset = null;
		String driver   = "oracle.jdbc.driver.OracleDriver";
		String url      = "jdbc:oracle:thin:@localhost:1521:xe";
		String user     = "scott"; String pass     = "tiger";
		String EMAIL    =""; String CREATED_AT=""; String NICKNAME =""; String MOBILE ="";
		
		// 0. 데이터 넘겨받기  - APP_USER_ID   (request.getParameter)
		int  APP_USER_ID = Integer.parseInt( request.getParameter("APP_USER_ID") );
		// 드커프리
		try{
			// 1. 드라이버연동  
			Class.forName(driver);
			// 2. 커넥션
			conn = DriverManager.getConnection(url,user,pass);
			// 3. PreparedStatement  select * from appuser  where   APP_USER_ID=?
			String sql = "select * from appuser  where   APP_USER_ID=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, APP_USER_ID);
			// 4. ResultSet  - 표줄칸   (  executeQuery: select ) / (executeUpdate : insert, update, delete)

			rset = pstmt.executeQuery();  //표
			while(rset.next()){// 줄
				EMAIL        = rset.getString("EMAIL");  //칸
				NICKNAME 	 = rset.getString("NICKNAME"); //칸
				MOBILE		 = rset.getString("MOBILE");
				CREATED_AT   = rset.getString("CREATED_AT"); //칸
			}
			
		}catch(Exception e){ e.printStackTrace();
		}finally{
			if(rset != null)  rset.close();
			if(pstmt != null) pstmt.close();
			if(conn != null)  conn.close();
		}
	  %>

		<table class="table  table-striped  table-bordered  table-hover">
			<tbody class="table-info ">
				<tr> <th scope="row">Email</th>        <td><%=EMAIL%></td> </tr>
				<tr> <th scope="row">NICKNAME</th>    <td><%=NICKNAME%></td> </tr>
				<tr> <th scope="row">회원가입날짜</th>    <td><%=CREATED_AT%></td></tr>
			</tbody>
		</table>
		<!-- mvc1 - 코드가 뒤죽박죽 - 스파게티 코드라고해요~! -->
	</div>
<%@ include file="../inc/footer.jsp" %>



<!-- 1. mypage -  유형 1,2,3,
	 2. first님      MbtiBaord   /  로그인 회원가입
	 3. 테이블에서 숫자자동으로 카운트 -->