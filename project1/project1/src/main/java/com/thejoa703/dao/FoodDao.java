package com.company.jeongmin.Dao_Dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// create : insert into food(foodId,name, desciprtion)  values ( type_seq.nextval, ?,?);
// read : select * from food; select * from food where food_type_id =?;
// update : ipdate food foodId=?, name=? desciprtion=? where food_type_id =?;  
// delete : delete from mbtitype where food_type_id =?;
public class FoodDao { 
	
	public int insert(FoodDto dto) { // 새로운 음식 추가 
		int result =-1;
		String sql = " INSERT INTO FOODTB (foodId, name, categoryId, kcal, protein, carb, fat, recipe, imageUrl) " 
					+" VALUES (FOODSE.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";

            
	
    Connection conn = null; PreparedStatement pstmt = null; ResultSet rset = null;
	String driver="oracle.jdbc.driver.OracleDriver";
	String    url="jdbc:oracle:thin:@localhost:1521:xe";
	String   user="scott" , pass="tiger";
	// 드커프리
	// 1. create
	    try {    	
					Class.forName(driver);
				
	        	
	        	conn = DriverManager.getConnection(url, user,pass);
	        	
	        	pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, dto.getName());
	            pstmt.setInt(2, dto.getCategoryId());
	            pstmt.setDouble(3, dto.getKcal());
	            pstmt.setDouble(4, dto.getProtein());
	            pstmt.setDouble(5, dto.getCarb());
	            pstmt.setDouble(6, dto.getFat());
	            pstmt.setString(7, dto.getRecipe());
	            pstmt.setString(8, dto.getImageUrl());

	            if(pstmt.executeUpdate() > 0) {result = 1;}
	            
	        } catch (Exception e) { e.printStackTrace();
	        }finally {
				 if(rset != null) {try {rset.close();} catch (SQLException e) {e.printStackTrace();}}
				 if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}}
				 if(conn != null) {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
			 }
	       return result;
	    }

	  // select * from FoodDetail
	
	
	 
	  //2. read 전체조회, 카테고리 이름 포함
	    public List<FoodDto> selectAll() { //전체음식 목록 조회
	        List<FoodDto> list = new ArrayList<>();
	        String sql = "SELECT f.foodId, f.name, f.categoryId, c.categoryName, f.kcal, f.protein, f.carb, f.fat, "
	                + "f.recipe, f.imageUrl, f.regDate "
	                + "FROM FOODTB f JOIN CATEGORYTB c ON f.categoryId = c.categoryId "
	                + "ORDER BY f.foodId DESC";
	        
	        	Connection conn = null; PreparedStatement pstmt = null ; ResultSet rset = null;
	        	String driver = "oracle.jdbc.driver.OracleDriver";
				String url="jdbc:oracle:thin:@localhost:1521:xe";
				String user="scott", pass="tiger";
				 
			try {
				Class.forName(driver);	
				
	        	conn = DriverManager.getConnection(url, user, pass);
	        	
	            pstmt = conn.prepareStatement(sql);
	             
	            rset = pstmt. executeQuery();
	            while (rset.next()) {
	            	list.add(new FoodDto(
		                rset.getInt("foodId"),rset.getString("name"), rset.getInt("categoryId"),
		                rset.getString("categoryName"),rset.getDouble("kcal"), rset.getDouble("protein"),
		                rset.getDouble("carb"), rset.getDouble("fat"), rset.getString("recipe"),
		                rset.getString("imageUrl"), rset.getDate("regDate")
		                ));
	                
	            }
	        } catch (Exception e) {e.printStackTrace();
	        }finally {
	        	 if(rset != null) {try {rset.close();} catch (SQLException e) {e.printStackTrace();}}
				 if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}}
				 if(conn != null) {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
	        }
	        return list;
	    }
	    //select 선택		
	    public FoodDto select(int id) { //특정 음식 Id로 상세 정보 조회
	    	FoodDto result = new FoodDto();
	    	String sql = "select * from FOODTB where foodId=?";

	    	// 오라클 연결
	    	Connection conn = null; PreparedStatement pstmt=null; ResultSet rset = null;
	    		String driver = "oracle.jdbc.driver.OracleDriver";
	    		String url="jdbc:oracle:thin:@localhost:1521:xe";
	    		String user="scott", pass="tiger";
	    	// 드커프리
	    try {
	    	Class.forName(driver);
	    	
	    	conn = DriverManager.getConnection(url, user, pass);
	    	
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, id);
	    	
	    	rset = pstmt.executeQuery();
	    	while(rset.next()) {
	    		result = new FoodDto(
		                rset.getInt("foodId"),rset.getString("name"), rset.getInt("categoryId"),
		                rset.getDouble("kcal"), rset.getDouble("protein"),
		                rset.getDouble("carb"), rset.getDouble("fat"), rset.getString("recipe"),
		                rset.getString("imageUrl"), rset.getDate("regDate")
		                );
	    	}
	    	System.out.println("................"+result);
	    }catch(Exception e) {e.printStackTrace();
	    	
	    }finally {
	    	if(rset !=null) {try {rset.close();} catch(SQLException e) {e.printStackTrace();}}
	    	if(pstmt !=null) {try {pstmt.close();} catch(SQLException e) {e.printStackTrace();}}
	    	if(conn !=null) {try {conn.close();} catch(SQLException e) {e.printStackTrace();}}
}
	    return result;
	    }

	 //3.update 업데이트
	    public int update(FoodDto dto) { //음식정보 및 내용 수정
	    	FoodDto list = new FoodDto(); 
	        String sql = "UPDATE FOODTB SET name=?, categoryId=?,    kcal=?, protein=?, carb=?, fat=?, recipe=?, imageUrl=? WHERE foodId=?";
	        
	     
	        
	        //드 커 프 리
	        Connection conn = null; PreparedStatement pstmt = null; ResultSet rset = null;
	         String driver = "oracle.jdbc.driver.OracleDriver";
			 String url="jdbc:oracle:thin:@localhost:1521:xe";
			 String user="scott", pass="tiger";
			 
			 try {
				 Class.forName(driver);
	             
				 conn = DriverManager.getConnection(url, user, pass);
				 
				pstmt = conn.prepareStatement(sql); 
	            pstmt.setString(1, dto.getName());
	            pstmt.setInt(2, dto.getCategoryId()); 
	            pstmt.setDouble(3, dto.getKcal());
	            pstmt.setDouble(4, dto.getProtein());
	            pstmt.setDouble(5, dto.getCarb());
	            pstmt.setDouble(6, dto.getFat());
	            pstmt.setString(7, dto.getRecipe());
	            pstmt.setString(8, dto.getImageUrl());
	            pstmt.setInt(9, dto.getFoodId());

	            int presult = pstmt.executeUpdate();
	            System.out.println("........." + dto);
	            System.out.println("........." + presult);
	            int result= -1;
	            if(presult > 0) result = 1;
	            return result;
	            
	            
	        } catch (Exception e) {e.printStackTrace();
	            
	        }finally {
	        	 if(rset != null) {try {rset.close();} catch (SQLException e) {e.printStackTrace();}}
				 if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}}
				 if(conn != null) {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
	        }
			 return -1;
	    }
	    
	  //4. delete
	    public int delete(FoodDto dto) { //음식삭제
	    	int result = -1;
	        String sql = "DELETE FROM FOODTB WHERE foodId=?";
	        
	        Connection conn = null; PreparedStatement pstmt = null; ResultSet rset = null;
	         String driver = "oracle.jdbc.driver.OracleDriver";
			 String url="jdbc:oracle:thin:@localhost:1521:xe";
			 String user="scott", pass="tiger";
	        
			 try {
				 Class.forName(driver);
			 
				 conn = DriverManager.getConnection(url, user, pass);

				 
				 pstmt = conn.prepareStatement(sql);
				 pstmt.setInt(1, dto.getFoodId());
				 
				 int presult = pstmt.executeUpdate();
				 System.out.println(".........." + presult);
				 if(presult > 0) { result =1;}
				
				 
			 }catch(Exception e) {e.printStackTrace();
			 } finally {
				 if(rset != null) {try {rset.close();} catch (SQLException e) {e.printStackTrace();}}
				 if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}}
				 if(conn != null) {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
			 }
			 return result;
	        }
	        
	    }

	



	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        


