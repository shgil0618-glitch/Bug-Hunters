<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@include file="../inc/header.jsp" %>



   <div class="container card  my-5 p-4 ">
      <h3 class="card-header"> MBTI 탈퇴</h3>
	  <%-- <form action="${pageContext.request.contextPath}/delete.do?id=${param.id}"     method="post">  --%> 
	  <form action="${pageContext.request.contextPath}/security/delete"  method="post">  
	  	  <input  type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
	      <input type="hidden"   name="appUserId"  value="${dto.appUserId}"> 
	      <input type="hidden"   name="email"  	   value="<sec:authentication  property="principal.dto.email"  />"> 
		  <div class="my-3">
		    <label for="password" class="form-label">PASS:</label>
		    <input type="password" class="form-control" 
		    	id="bpass"  placeholder="비밀번호를 입력해주세요" name="password">
		  </div> 
		  <div class="my-3  text-end">
		  	<button type="submit" class="btn btn-primary">유저탈퇴</button>
		  	<a href="javascript:history.go(-1)"  class="btn btn-danger">BACK</a>
		  </div>
	 </form>
   </div>
   
<%@include file="../inc/footer.jsp" %>

<!-- [ mbtiBoard - list.jsp ]  
    1. 빈칸 알림창  - 빈칸입니다. 알림창 + 커서가게
	2. spring 에서  ajax 사용법  
	3. 로그인중복 - dao / service / controller  
-->