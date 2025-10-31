<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- Latest compiled and minified CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
   <div class="container card  my-5">
      <h3  class="card-header"> DAO TEST </h3>
	  <pre  class="alert alert-success">
	  1. insert
	  
	     insert into post ( id                 , app_user_id ,title , content , pass )  
         values ( post_seq.nextval   ,          ?  , ?    ,      ?  ,    ? ) 
	  </pre>
	  <%@page import="com.thejoa703.dao.PostDao"%>
	  <%@page import="com.thejoa703.dto.PostDto"%>
<%--  	  <%
	  PostDao dao = new PostDao();
	  PostDto dto = new PostDto();
	  dto.setAppUserId(1);
	  dto.setTitle("첫번째 글쓰기입니다.");
	  dto.setContent("내용");
	  dto.setPass("1111");
	  out.println(    dao.insert(dto)  );   //1나오면 글쓰기 성공!
	  %>  --%> 
	  
	  <pre  class="alert alert-success">
	  2. selectAll
	  </pre>
<%-- 	  <%
	  PostDao dao = new PostDao();
	  out.println(dao.selectAll());  
	  %> --%>
	  
 	  <pre  class="alert alert-success">
	  3. select
	  </pre>
<%-- 	  <%
	  PostDao dao = new PostDao(); 
	  out.println(dao.update_hit(4));
	  out.println(dao.select(4));    // 번호는 있는번호로
	  %> --%>
	  
	  <pre  class="alert alert-success">
	  3. update
	  </pre>
	  <%
	  PostDao dao = new PostDao(); 
	  PostDto dto = new PostDto();
	  dto.setTitle("첫번째 글쓰기입니다.-new");
	  dto.setContent("내용-new");
	  dto.setPass("123");
	  dto.setId(3);
	  out.println(dao.update(dto));  
	  %>
	  	  
	  <pre  class="alert alert-success">
	  4. delete
	  </pre>
<%--  	  <%
	  PostDao dao = new PostDao(); 
	  PostDto dto = new PostDto(); 
	  dto.setPass("123");
	  dto.setId(3);
	  out.println(dao.delete(dto));  
	  %>  --%>
   </div>
</body>
</html>

<!-- board_test.jsp -->








