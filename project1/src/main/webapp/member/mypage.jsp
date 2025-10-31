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
		String  EMAIL="";   int MBTI_TYPE_ID=0;   String CREATED_AT=""; String  mbti ="";
		
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
				MBTI_TYPE_ID = rset.getInt(   "MBTI_TYPE_ID"); //칸
				CREATED_AT   = rset.getString("CREATED_AT"); //칸
			}
			
			// Q1. MBTI_TYPE_ID  1이라면   mbti=ISTJ   , 2라면  mbti=ISFJ    3이라면  mbti=INFJ	  if/switch/삼항연산자
			switch(MBTI_TYPE_ID){
			    case 1: mbti = "ISTJ"; break;
			    case 2: mbti = "ISFJ"; break;
			    case 3: mbti = "INFJ"; break;
			    case 4: mbti = "INTJ"; break;
			    case 5: mbti = "ISTP"; break;
			    case 6: mbti = "ISFP"; break;
			    case 7: mbti = "INFP"; break;
			    case 8: mbti = "INTP"; break;
			    case 9: mbti = "ESTP"; break;
			    case 10: mbti = "ESFP"; break;
			    case 11: mbti = "ENFP"; break;
			    case 12: mbti = "ENTP"; break;
			    case 13: mbti = "ESTJ"; break;
			    case 14: mbti = "ESFJ"; break;
			    case 15: mbti = "ENFJ"; break;
			    case 16: mbti = "ENTJ"; break;
			    default: mbti = "Unknown";
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
				<tr> <th scope="row">MBTI TYPE</th>    <td><%=mbti%></td> </tr>
				<tr> <th scope="row">회원가입날짜</th>    <td><%=CREATED_AT%></td></tr>
			</tbody>
		</table>
		<!-- mvc1 - 코드가 뒤죽박죽 - 스파게티 코드라고해요~! -->
	</div>
<%@ include file="../inc/footer.jsp" %>



<!-- 1. mypage -  유형 1,2,3,
	 2. first님      MbtiBaord   /  로그인 회원가입
	 3. 테이블에서 숫자자동으로 카운트 -->