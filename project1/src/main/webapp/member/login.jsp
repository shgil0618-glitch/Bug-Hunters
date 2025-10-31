<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@  include file="../inc/header.jsp" %>   
<div class="container mt-5">
	<h3> 로그인</h3>
	<form action="login_process.jsp"  method="post">
	  <div class="mb-3 mt-3">
	    <label for="email" class="form-label">Email:</label>
	    <input type="email" class="form-control" id="email" placeholder="이메일을 적어주세요"  required name="email">
	  </div>
	  <div class="mb-3">
	    <label for="password" class="form-label">Password:</label>
	    <input type="password" class="form-control" id="password" placeholder="비밀번호를 적어주세요"  required  name="password">
	  </div>
<!-- 	  <div class="form-check mb-3">
	    <label class="form-check-label">
	      <input class="form-check-input" type="checkbox" name="remember"> Remember me
	    </label>
	  </div> -->
	  <button type="submit" class="btn btn-primary">로그인</button>
	</form>
</div>
<%@  include file="../inc/footer.jsp" %>    





<!-- 	로그인폼		로그인처리
	login.jsp  → login_process.jsp   

				action="login_process.jsp"
				method="post"
				name="email"   name="password"     
				
 
-->