<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 

<%@  include file="../inc/header.jsp" %>  
<script>
$(function(){
	 let result = '${success}';
	 console.log(result); 
	 if(result == "회원가입 실패"){   alert( result );   history.go(-1); }
	 else if(result == '비밀번호를 확인해주세요'){  alert( result  );  history.go(-1); } 
	 else if(result.length  != 0 ){  alert(result); }  //아까 처음 값이없을때 공백 
}); 
</script>
   
<c:if test="${not empty loginError}">
    <script>
        alert("${loginError}");
    </script>
</c:if>

<div class="container mt-5">
	<h3> 로그인</h3>
	<form action="${pageContext.request.contextPath}/login"  method="post">
	  <div class="mb-3 mt-3">
	    <label for="username" class="form-label">Email:</label>
	    <input type="email" class="form-control" id="username" 
	    			placeholder="이메일을 적어주세요"  required name="username">
	  </div>
	  <div class="mb-3">
	    <label for="password" class="form-label">Password:</label>
	    <input type="password" class="form-control" id="password" placeholder="비밀번호를 적어주세요"  
	    			required  name="password">
	  </div>
<!-- 	  <div class="form-check mb-3">
	    <label class="form-check-label">
	      <input class="form-check-input" type="checkbox" name="remember"> Remember me
	    </label>
	  </div> -->
	  <button type="submit" class="btn btn-primary">로그인</button>
	  <input  type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
	</form>
</div>

<%@  include file="../inc/footer.jsp" %>    

 
 
<!-- 	로그인폼		로그인처리
	login.jsp  → login_process.jsp   

				action="login_process.jsp"
				method="post"
				name="email"   name="password"     
				
 
-->