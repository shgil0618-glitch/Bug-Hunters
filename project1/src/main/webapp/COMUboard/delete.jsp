<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="../inc/header.jsp" %>
<%@ page import="com.thejoa703.dto.ComuDto" %>

     <div class="container card  my-5 p-4 ">
      <h3 class="card-header"> 커뮤니티 글삭제</</h3>
	  <form action="<%=request.getContextPath()%>/delete.co"  method="post"> 
      	<input type="hidden" name="postId" value="${dto.postId}">
		  <div class="my-3">
		    <label for="pass" class="form-label">ID:</label>
		    <input type="password" class="form-control" 
		    	id="pass"  placeholder="아이디를 입력해주세요" name="pass" >
		  </div> 
		  <div class="my-3  text-end">
		  	<button type="submit" class="btn btn-primary">글삭제</button>
		  	<a href="javascript:history.go(-1)"  class="btn btn-danger">BACK</a>
		  </div>
	 </form>
   </div>

<%@include file="../inc/footer.jsp" %>
