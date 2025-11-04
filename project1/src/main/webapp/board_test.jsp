<%@page import="com.thejoa703.dto.ComuDto"%>
<%@page import="com.thejoa703.dao.ComuDao"%>

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
	  <pre class="alert alert_success">1. insert
	  </pre>

 	  <%-- <%
      ComuDao dao = new ComuDao();
	  ComuDto dto = new ComuDto();
	  
	  dto.setTitle("첫번째 글쓰기입니다.");
	  dto.setContent("내용");
	  dto.setCategoryId(1);
	  dto.setId(1);
	  out.println(dao.insert(dto));

	  %> --%>
	  
	 
	  <pre class="alert alert-success">
	  2. select all
	  </pre>
   
	  <%-- <%
	  ComuDao dao = new ComuDao();
	  out.println(dao.selectAll());
	  %>  --%>
	 

	  
	  
	  <pre class="alert alert-success">
	  3. select
	  </pre>
	
	 <%-- <%
	  ComuDao dao = new ComuDao();
	  out.println(dao.update_views(1));
	  out.println(dao.select(1));
	  %>  --%>
 
	  
	  <pre class="alert alert-success">
	  3. update
	  </pre>
  
	 <%--  <%
	  ComuDao dao = new ComuDao();
	  ComuDto dto = new ComuDto();
	  dto.setTitle("첫번째 글쓰기입니다.new");
	  dto.setContent("내용-new");
	  dto.setCategoryId(2);
	  dto.setPostId(1);
	  dto.setId(1);
	  out.println(dao.update(dto));
	 %>  --%>
		

		
		


	   
	   <pre class="alert alert-success">
	  4. delete
	  </pre>
	 <%-- <%
	 ComuDao dao = new ComuDao();
	 ComuDto dto = new ComuDto();
	  dto.setPostId(1);
	  dto.setId(1);
	  out.println(dao.delete(dto));
	  %>  --%> 

	  
   </div>
</body>
</html>