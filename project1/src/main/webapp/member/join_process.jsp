<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Connection conn = null;  PreparedStatement pstmt = null;   ResultSet  rset = null;
	String driver   = "oracle.jdbc.driver.OracleDriver";
	String url      = "jdbc:oracle:thin:@localhost:1521:xe";
	String user     = "scott"; 
	String pass     = "tiger";
	//0. 데이터 넘겨받기 - request.getParameter() 
	String email    	= request.getParameter("email");
	String password 	= request.getParameter("password");
	int    mbti_type_id =  Integer.parseInt(request.getParameter("mbti_type_id")  ); 
	out.println(email + "/" + password + "/" + mbti_type_id);  //form 실행 → 값넘어오는지 확인
	//드커프리
	try{
		//1. 드라이버연동
		Class.forName(driver);
		//2. 커넥션
		conn = DriverManager.getConnection(url, user, pass);
		//3. PreparedStatement #
		String sql = 
		"insert into appuser (APP_USER_ID , EMAIL , PASSWORD , MBTI_TYPE_ID,CREATED_AT) values (appuser_seq.nextval,?,?,?,SYSDATE)";
		pstmt = conn.prepareStatement(sql);   //기본값 - 오류날가능성 있음....? 겹치지 않게 카운트하는방법은?  시퀀스
		pstmt.setString(1, email);	
		pstmt.setString(2, password);	
		pstmt.setInt(   3, mbti_type_id); 		
		//4. 결과 #  select - pstmt.executeQuery() / insert, update, delete - pstmt.executeUpdate()
		int result = pstmt.executeUpdate();  // 실행한 줄수              주소표시창줄을 로그인폼으로   
		if(result > 0){ out.println("<script> alert('회원가입성공!'); location.href='login.jsp'; </script>"); }
		else{       out.println("<script> alert('관리자에게 문의바람'); history.go(-1); </script>");   }
								//  오류시 안내문구                    뒤로가기
	}catch(Exception e){ out.println("오류 : " + e.getMessage()); 
	}finally{  if(rset != null ) rset.close();  if(pstmt != null) pstmt.close();  	if(conn != null ) conn.close();  }
%>


<!-- SQL> desc appuser;
 Name                                      Null?    Type
 ----------------------------------------- -------- ----------------------------
 APP_USER_ID                               NOT NULL NUMBER(5)
 EMAIL                                     NOT NULL VARCHAR2(100)
 PASSWORD                                           VARCHAR2(100)
 MBTI_TYPE_ID                                       NUMBER(3)
 CREATED_AT 																-->