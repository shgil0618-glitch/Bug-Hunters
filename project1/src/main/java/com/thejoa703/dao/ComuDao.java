package com.thejoa703.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.thejoa703.dto.ComuDto;
import com.thejoa703.dao.ComuDao;


 
public class ComuDao {

	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////	
//	1. [글쓰기] sql : 
//  insert into post (id, app_user_id, title, content, pass) 
//  values(post_seq.nextval, ?,?,?,?);
	
	/*
	 * postId NUMBER(8) PRIMARY KEY, -- 게시글 ID id VARCHAR2(30) NOT NULL, -- 작성자
	 * title VARCHAR2(200) NOT NULL, -- 제목 content CLOB NOT NULL, -- 본문 categoryId
	 * NUMBER(3) NOT NULL, -- 카테고리 번호 views NUMBER(6) DEFAULT 0, -- 조회수 createdAt
	 * DATE DEFAULT SYSDATE, -- 작성일 updatedAt DATE, -- 수정일
	 */
	
		//    postId      NUMBER(8) PRIMARY KEY,      -- 게시글 ID
		//    id           NUMBER(30) NOT NULL,      -- 작성자
		//    title        VARCHAR2(200) NOT NULL,    -- 제목
		//    content      CLOB NOT NULL,             -- 본문
		//    categoryId  NUMBER(3) NOT NULL,           -- 카테고리 번호
	
	 public int insert(ComuDto dto){
     	int result = -1;
     	String sql = "INSERT INTO COMMUNITY_TB (postId, id, title, content, categoryId) "
                + " VALUES(COMMUNITY_TB_seq.nextval, (SELECT APPUSERID FROM USERS WHERE EMAIL = ?), ?, ?, ?)";
     	//String sql = "select * from dept where deptno=?";
     	
     	Connection conn = null; PreparedStatement pstmt = null; ResultSet rset=null;
 		String driver ="oracle.jdbc.driver.OracleDriver";
 		String url = "jdbc:oracle:thin:@localhost:1521:xe";
 		String user = "scott", pass ="tiger";
 		
     	// 드 커 프 리
 		try {
 			//1. 드라이버 연결
 			Class.forName(driver);
				//2. 커넥션
 			conn = DriverManager.getConnection(url,user,pass);
	        	//3. pstmt
 			pstmt = conn.prepareStatement(sql);
 		    pstmt.setString(1, dto.getEmail());  
 			pstmt.setString(2, dto.getTitle());
 			pstmt.setString(3, dto.getContent());
 			pstmt.setInt(4, dto.getCategoryId());
	        //4. result (select : executeQuery , insert/update/delete : executeUpdate)
 			int presult = pstmt.executeUpdate();
 			if(presult>0) {result=1;} 	//성공
 			
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				if(rset != null) {try { rset.close(); } catch (Exception e) { e.printStackTrace(); }}
				if(pstmt != null) {try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }}
				if(conn != null) {try { conn.close(); } catch (Exception e) { e.printStackTrace(); }}
			}
     	
     	return result;
     }	
	
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////	
// 2. [전체보기]전체게시글 가져오기
	 public  ArrayList<ComuDto> selectAll(){
         ArrayList<ComuDto> result = new ArrayList<>();
			/*
			 * String sql = "SELECT c.postId, c.id, c.title, c.content, c.categoryId, " +
			 * "c.views, c.createdAt, c.updatedAt, u.NICKNAME " + "FROM COMMUNITY_TB c " +
			 * "JOIN USERS u ON c.id = u.APPUSERID " +
			 * "ORDER BY NVL(c.updatedAt, c.createdAt) DESC";
			 */
         String sql = "SELECT c.postId, c.id, c.title, c.content, c.categoryId, " +
                 "c.views, c.createdAt, c.updatedAt, u.NICKNAME, cat.categoryName " +
                 "FROM COMMUNITY_TB c " +
                 "JOIN USERS u ON c.id = u.APPUSERID " +
                 "LEFT JOIN CATEGORY_TB cat ON c.categoryId = cat.categoryId " +
                 "ORDER BY NVL(c.updatedAt, c.createdAt) DESC";
         // 드 커 프 리
         Connection conn = null; PreparedStatement pstmt = null;  ResultSet rset = null;
         String driver="oracle.jdbc.driver.OracleDriver";
         String    url="jdbc:oracle:thin:@localhost:1521:xe";
         String   user="scott" , pass="tiger";
         // 드 커 프 리 
         try {
            //1. 드라이버연동 
            Class.forName(driver);
            //2. 커넥션
            conn = DriverManager.getConnection(url, user, pass);
            //3. PSTMT
            pstmt = conn.prepareStatement(sql);
            //4. RESULT (  select : executeQuery  / insert,update, delete: executeUpdate)
            rset = pstmt.executeQuery();  //표
            while(rset.next()) { //줄
            	
            	result.add(new ComuDto(
            		    rset.getInt("postId"),                   
            		    rset.getInt("id"),                       
            		    rset.getString("title"),                 
            		    rset.getString("content"),               
            		    rset.getInt("categoryId"),               
            		    rset.getInt("views"),					 
            		    rset.getTimestamp("createdAt").toLocalDateTime(), 
            		    rset.getTimestamp("updatedAt") != null ? rset.getTimestamp("updatedAt").toLocalDateTime() : null,
            		    rset.getString("nickname"),
            		    null,							//dto에 email 있지만 사용안해서 일단 null값 넣음
            		    rset.getString("categoryName")   // 여기 추가
            		));
            } 
         } catch (Exception e) { e.printStackTrace();
         } finally {
            if( rset  != null ) { try { rset.close(); } catch (SQLException e) { e.printStackTrace(); } }
            if( pstmt != null ) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
            if( conn  != null ) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
         }  
         
         return result;
      }	
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//3. [상세보기]글번호 해당하는 글가져오기 sql :  

	 public ComuDto select(int postId){
		 ComuDto result = new ComuDto();
			/* String sql = "select * from COMMUNITY_TB where postId=?"; */
		 String sql = "SELECT c.postId, c.id, c.title, c.content, c.categoryId, " +
	             "c.views, c.createdAt, c.updatedAt, u.NICKNAME, cat.categoryName " +
	             "FROM COMMUNITY_TB c " +
                 "JOIN USERS u ON c.id = u.APPUSERID " +
                 "LEFT JOIN CATEGORY_TB cat ON c.categoryId = cat.categoryId " +
	             "WHERE c.postId = ?";
		 
		 

		        	// 드 커 프 리
		 Connection conn = null; PreparedStatement pstmt = null; ResultSet rset=null;
		 String driver ="oracle.jdbc.driver.OracleDriver";
		 String url = "jdbc:oracle:thin:@localhost:1521:xe";
		 String user = "scott", pass ="tiger";

		 // 드 커 프 리
		 try {
		 	//1. 드라이버 연결
		 	Class.forName(driver);
		 	//2. 커넥션
		 	conn = DriverManager.getConnection(url, user, pass);
		 	//3. pstmt
		 	pstmt = conn.prepareStatement(sql);
		 	pstmt.setInt(1, postId);		// [?있으면 넣어줘야함]String sql = "select * from post where id=?"
		 	//4. result
		 	rset = pstmt.executeQuery();	//표
		 	while(rset.next()) {	//줄
		 		result=new ComuDto(
		 				rset.getInt("postId"),                   
            		    rset.getInt("id"),                       
            		    rset.getString("title"),                 
            		    rset.getString("content"),               
            		    rset.getInt("categoryId"),               
            		    rset.getInt("views"),					 
            		    rset.getTimestamp("createdAt").toLocalDateTime(), 
            		    rset.getTimestamp("updatedAt") != null ? rset.getTimestamp("updatedAt").toLocalDateTime() : null,	
            		    rset.getString("nickname"),
            		    null,
            		    rset.getString("categoryName")
		 				);
		 	}
		 	
		 } catch (Exception e) {
		 	e.printStackTrace();
		 } finally{
		 	if(rset != null) {try { rset.close(); } catch (Exception e) { e.printStackTrace(); }}
		 	if(pstmt != null) {try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }}
		 	if(conn != null) {try { conn.close(); } catch (Exception e) { e.printStackTrace(); }}
		 }
		        	return result;
		        }
		        
		 public int update_views(int postId){
		 int result = -1;
		 String sql = "update COMMUNITY_TB set views = views+1 where postId=?";
		 // 드 커 프 리
		 Connection conn = null; PreparedStatement pstmt = null; ResultSet rset=null;
		 String driver ="oracle.jdbc.driver.OracleDriver";
		 String url = "jdbc:oracle:thin:@localhost:1521:xe";
		 String user = "scott", pass ="tiger";

		 // 드 커 프 리
		 try {
		 	//1. 드라이버 연결
		     Class.forName(driver);
		     //2. 커넥션
		     conn = DriverManager.getConnection(url, user, pass);
		     //3. pstmt
		     pstmt = conn.prepareStatement(sql);
		     pstmt.setInt(1, postId);
		     //4. 실행
		     if(pstmt.executeUpdate()>0) {result=1;} 
		     
		 } catch (Exception e) {
		 	e.printStackTrace();
		 } finally{
		 	if(rset != null) {try { rset.close(); } catch (Exception e) { e.printStackTrace(); }}
		 	if(pstmt != null) {try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }}
		 	if(conn != null) {try { conn.close(); } catch (Exception e) { e.printStackTrace(); }}
		 }
		 return result;
		        
}        
	 
	 

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//4. 글수정하기 sql
		 public int update(ComuDto dto){			//매개변수 많은면 dto 받아!
			 int result = -1;
				/*
				 * String sql =
				 * "update COMMUNITY_TB set title=?, content=?, categoryId=? where postId=? and id=?"
				 * ;
				 */
			 
			 String sql = "UPDATE COMMUNITY_TB "
			           + "SET title = ?, content = ?, categoryId = ?, updatedAt = SYSDATE "
			           + "WHERE postId = ? AND id = ?";
			 
			 // 드 커 프 리
			 Connection conn = null; PreparedStatement pstmt = null; ResultSet rset=null;
			 String driver ="oracle.jdbc.driver.OracleDriver";
			 String url = "jdbc:oracle:thin:@localhost:1521:xe";
			 String user = "scott", pass ="tiger";

			 // 드 커 프 리
			 try {
			 	//1. 드라이버 연결
			 	Class.forName(driver);
			 	//2. 커넥션
			 	conn = DriverManager.getConnection(url,user,pass);
			 	//3. pstmt
			 	pstmt = conn.prepareStatement(sql);
			 	pstmt.setString(1, dto.getTitle());
			 	pstmt.setString(2, dto.getContent());
			 	pstmt.setInt(3, dto.getCategoryId());
			 	pstmt.setInt(4, dto.getPostId());
			 	pstmt.setInt(5, dto.getId());
			 	//4. result
			 	if(pstmt.executeUpdate()>0) {result=1;} 
			 	
			 } catch (Exception e) {
			 	e.printStackTrace();
			 } finally{
			 	if(rset != null) {try { rset.close(); } catch (Exception e) { e.printStackTrace(); }}
			 	if(pstmt != null) {try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }}
			 	if(conn != null) {try { conn.close(); } catch (Exception e) { e.printStackTrace(); }}
			 }
			 return result;    
			 }	
	

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//5. 글번호 해당하는 삭제
		 public int delete(ComuDto dto){			//매개변수 많은면 dto 받아!
			 int result = -1;
			 String sql = "delete from COMMUNITY_TB where postId=?";
			 // 드 커 프 리
			 Connection conn = null; PreparedStatement pstmt = null; ResultSet rset=null;
			 String driver ="oracle.jdbc.driver.OracleDriver";
			 String url = "jdbc:oracle:thin:@localhost:1521:xe";
			 String user = "scott", pass ="tiger";

			 // 드 커 프 리
			 try {
			 	//1. 드라이버 연결
			 	Class.forName(driver);
			 	//2. 커넥션
			 	conn = DriverManager.getConnection(url,user,pass);
			 	//3. pstmt
			 	pstmt = conn.prepareStatement(sql);
			 	pstmt.setInt(1, dto.getPostId());
			 	//4. result
			 	if(pstmt.executeUpdate()>0) {result=1;} 
			 	
			 } catch (Exception e) {
			 	e.printStackTrace();
			 } finally{
			 	if(rset != null) {try { rset.close(); } catch (Exception e) { e.printStackTrace(); }}
			 	if(pstmt != null) {try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }}
			 	if(conn != null) {try { conn.close(); } catch (Exception e) { e.printStackTrace(); }}
			 }
			 return result;
			  }
		 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//6. (제목,내용,닉네임)검색 기능
		 public ArrayList<ComuDto> search(String target, String keyword) {

		     ArrayList<ComuDto> result = new ArrayList<>();

		     // 검색 컬럼 매핑
		     String column = "";
		     switch (target) {
		         case "title":
		             column = "c.title";
		             break;
		         case "content":
		             column = "c.content";
		             break;
		         case "nick_name":   // 외래키 → USERS.nickname
		             column = "u.nickname";
		             break;
		         default:
		             column = "c.title";
		     }

		     String sql = "SELECT c.postId, c.id, c.title, c.content, c.categoryId, "
		                + "c.views, c.createdAt, c.updatedAt, u.NICKNAME, cat.categoryName "
		                + "FROM COMMUNITY_TB c "
		                + "JOIN USERS u ON c.id = u.APPUSERID "
		                + "LEFT JOIN CATEGORY_TB cat ON c.categoryId = cat.categoryId "
		                + "WHERE " + column + " LIKE ? "
		                + "ORDER BY NVL(c.updatedAt, c.createdAt) DESC";

		     Connection conn = null; 
		     PreparedStatement pstmt = null; 
		     ResultSet rs = null;

		     String driver ="oracle.jdbc.driver.OracleDriver";
		     String url = "jdbc:oracle:thin:@localhost:1521:xe";
		     String user = "scott", pass ="tiger";

		     try {
		         Class.forName(driver);
		         conn = DriverManager.getConnection(url, user, pass);
		         pstmt = conn.prepareStatement(sql);
		         pstmt.setString(1, "%" + keyword + "%");
		         rs = pstmt.executeQuery();

		         while (rs.next()) {
		             result.add(new ComuDto(
		                 rs.getInt("postId"),
		                 rs.getInt("id"),
		                 rs.getString("title"),
		                 rs.getString("content"),
		                 rs.getInt("categoryId"),
		                 rs.getInt("views"),
		                 rs.getTimestamp("createdAt").toLocalDateTime(),
		                 rs.getTimestamp("updatedAt") != null ? rs.getTimestamp("updatedAt").toLocalDateTime() : null,
		                 rs.getString("nickname"),
		                 null,
		                 rs.getString("categoryName")
		             ));
		         }

		     } catch (Exception e) {
		         e.printStackTrace();
		     } finally {
		         try { if (rs != null) rs.close(); } catch (Exception e) {}
		         try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
		         try { if (conn != null) conn.close(); } catch (Exception e) {}
		     }

		     return result;
		 }


}



