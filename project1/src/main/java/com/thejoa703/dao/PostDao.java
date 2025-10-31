package com.thejoa703.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.thejoa703.dto.PostDto;

public class PostDao {
/*										*/
/*										*/
//	1. [글쓰기]글쓰기 sql 
	public  int  insert( PostDto dto ){
		int result=-1;  //실패
		String sql = "insert into post ( id                 , app_user_id ,title , content , pass ) "
				   + " 		    values ( post_seq.nextval   ,          ?  , ?    ,      ?  ,    ? ) ";
		
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
			pstmt.setInt(   1, dto.getAppUserId()   );
			pstmt.setString(2, dto.getTitle()       );
			pstmt.setString(3, dto.getContent()     );
			pstmt.setString(4, dto.getPass()        );
			//4. RESULT (  select : executeQuery  / insert,update, delete: executeUpdate)
			if( pstmt.executeUpdate() > 0 ) { result = 1;   }    //성공
			
		} catch (Exception e) { e.printStackTrace();
		} finally {
			if( rset  != null ) { try { rset.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if( pstmt != null ) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if( conn  != null ) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}  
		return result;
	} 	

/*										*/
/*										*/
//	2. [전체보기]전체글가져오기 , appuser테이블에서  email 도 같이 가져오기  sql :  
   public  ArrayList<PostDto> selectAll(){
      ArrayList<PostDto> result = new ArrayList<>();
      String sql = " SELECT      p.*     ,  u.email  email      "
      		     + " FROM      post p  join appuser u   on  p.app_user_id= u.app_user_id "
      		     + "order by id desc";
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
				//  id,   appUserId,  / title,   content,   pass, LocalDateTime createdAt,   hit,  email
				result.add( new PostDto( 
					  rset.getInt("id") , rset.getInt("app_user_id") 
					, rset.getString("title"), rset.getString("content"), rset.getString("pass")
					, rset.getTimestamp("created_at").toLocalDateTime() 
					, rset.getInt("hit") , rset.getString("email") 
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
		   
/*										*/
/*										*/	
//   3. [상세보기]글번호  해당하는 글가져오기 sql :  
   public PostDto  select(int id){
      PostDto result = new PostDto();
      String sql = "select * from post  where id=? ";
      // 드 커 프 리
		Connection conn = null; PreparedStatement pstmt = null;  ResultSet rset = null;
		String driver="oracle.jdbc.driver.OracleDriver";
		String    url="jdbc:oracle:thin:@localhost:1521:xe";
		String   user="scott" , pass="tiger";  
		try {
			//1. 드라이버연동 
			Class.forName(driver);
			//2. 커넥션 
			conn = DriverManager.getConnection(url, user, pass);
			//3. PSTMT
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			//4. RESULT (  select : executeQuery  / insert,update, delete: executeUpdate)
			rset = pstmt.executeQuery();  //표
			while(rset.next()) { //줄
				result = new PostDto(
						  rset.getInt("id") , rset.getInt("app_user_id") , rset.getString("title") , rset.getString("content")
						  , rset.getString("pass") , rset.getTimestamp("created_at").toLocalDateTime()  , rset.getInt("hit")  );
			} 
		} catch (Exception e) { e.printStackTrace();
		} finally {
			if( rset  != null ) { try { rset.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if( pstmt != null ) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if( conn  != null ) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}  
      return result;
   }
	
   
   public  int  update_hit( int id ){
      int result = -1;
      String sql = "update post  set  hit=hit+1  where  id=?";
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
			pstmt.setInt(1, id);
			//4. RESULT
			int presult = pstmt.executeUpdate();
			if(presult >0 ) { result = 1; }
		} catch (Exception e) { e.printStackTrace();
		} finally {
			if( rset  != null ) { try { rset.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if( pstmt != null ) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if( conn  != null ) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		} 
      
      return result;
  }
   
	
/*										*/
/*										*/
//   4. 글수정하기 sql:
   public  int  update( PostDto dto ){
      int result = -1;
      String sql = "update post  set   title=?, content=?  where  id=?  and pass=?";
      // 드 커 프 리
		Connection conn = null; PreparedStatement pstmt = null;  ResultSet rset = null;
		String driver="oracle.jdbc.driver.OracleDriver";
		String    url="jdbc:oracle:thin:@localhost:1521:xe";
		String   user="scott" , pass="tiger"; 
		try {
			//1. 드라이버연동 
			Class.forName(driver);
			//2. 커넥션 
			conn = DriverManager.getConnection(url, user, pass);
			//3. PSTMT
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(   3, dto.getId());
			pstmt.setString(4, dto.getPass());
			//4. RESULT
			int presult = pstmt.executeUpdate();
			if(presult >0 ) { result = 1; }
			
		} catch (Exception e) { e.printStackTrace();
		} finally {
			if( rset  != null ) { try { rset.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if( pstmt != null ) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if( conn  != null ) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		} 
      return result;
   }
	

/*										*/
/*										*/
//   5. 글번호  해당하는 삭제
   public  int  delete( PostDto dto ){
      int result = -1;
      String sql = "delete from  post  where  id=?  and pass=?";
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
			pstmt.setInt(   1, dto.getId());
			pstmt.setString(2, dto.getPass());
			//4. RESULT
			int presult = pstmt.executeUpdate();
			if(presult >0 ) { result = 1; }
			
		} catch (Exception e) { e.printStackTrace();
		} finally {
			if( rset  != null ) { try { rset.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if( pstmt != null ) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if( conn  != null ) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}  
      return result;
   }	
   
   
} // end class


/*
4. dao
	[com.thejoa703.dao] - PostDao
	| 컬럼명        | 데이터 타입       | 제약 조건       | 설명 |
	|---------------|-------------------|------------------|------|
	| `id`          | `NUMBER`          | `PRIMARY KEY`    | 게시글 고유 ID |
	| `app_user_id` | `NUMBER`          | `NOT NULL`       | 작성자 ID (`appuser` 테이블 참조) |
	| `title`       | `VARCHAR2(200)`   | `NOT NULL`       | 게시글 제목 |
	| `content`     | `CLOB`            | `NOT NULL`       | 게시글 내용 (대용량 텍스트, 최대 4GB) |
	| `pass`        | `VARCHAR2(100)`   | —                | 비회원 삭제용 비밀번호 |
	| `created_at`  | `DATE`            | `DEFAULT SYSDATE`| 작성일 |
	| `hit`         | `NUMBER`          | `DEFAULT 0`      | 조회수 |

1. [글쓰기]글쓰기 sql : 
	insert into post ( id                 , app_user_id ,title , content , pass )  
			  values ( post_seq.nextval   ,          ?  , ?    ,      ?  ,    ? ) 
	
	public  int  insert( PostDto dto ){
		int result=-1;
		// 드 커 프 리
		return result;
	} 	  

2. [전체보기]전체글가져오기 , appuser테이블에서  email 도 같이 가져오기  sql : 
	SELECT		p.*     ,  u.email
	FROM		post p  join appuser u	on  p.app_user_id= u.app_user_id 
	
	public  ArrayList<PostDto dto> selectAll(){
		ArrayList<PostDto dto> result = new ArrayList<>();
		// 드 커 프 리
		return result;
	}
	

3. [상세보기]글번호  해당하는 글가져오기 sql : 
	select * from post  where id=?
	update post  set  hit=hit+1  where  id=?
	
	public PostDto  select(int id){
		PostDto result = new PostDto();
		// 드 커 프 리
		return result;
	}
	
	public  int  update_hit( int id ){
		int result = -1;
		// 드 커 프 리
		return result;
	}

4. 글수정하기 sql:
	update post  set   title=?, content=?  where  id=?  and pass=?

	public  int  update( PostDto dto ){
		int result = -1;
		// 드 커 프 리
		return result;
	}

5. 글번호  해당하는 삭제
	delete from  post  where  id=?  and pass=?
	
	public  int  delete( PostDto dto ){
		int result = -1;
		// 드 커 프 리
		return result;
	}

*/



