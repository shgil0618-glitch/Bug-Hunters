<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 

<%@ include file="../inc/header.jsp" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
$(function(){
     let result = '${success}';
     if(result == "회원가입 실패"){ alert(result); history.go(-1); }
     else if(result == '비밀번호를 확인해주세요'){ alert(result); history.go(-1); } 
     else if(result.length != 0 ){ alert(result); }  
}); 
</script>
   
<c:if test="${not empty loginError}">
    <script>
        alert("${loginError}");
    </script>
</c:if>

<!-- 로그인 폼을 가운데 정렬 + 적당한 크기 -->
<main class="d-flex justify-content-center align-items-center mt-5">
  <div class="card shadow-sm" style="width: 400px;">
    <div class="card-body">
      <h3 class="text-center mb-4">로그인</h3>
      <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="mb-3">
          <label for="username" class="form-label">Email:</label>
          <input type="email" class="form-control" id="username" 
                 placeholder="이메일을 적어주세요" required name="username">
        </div>
        <div class="mb-3">
          <label for="password" class="form-label">Password:</label>
          <input type="password" class="form-control" id="password" 
                 placeholder="비밀번호를 적어주세요" required name="password">
        </div>
        <button type="submit" class="btn btn-primary w-100">로그인</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      </form>
      <div class="mt-3 text-center">
        <a href="${pageContext.request.contextPath}/security/findEmail" class="btn btn-link">이메일 찾기</a>
        <a href="${pageContext.request.contextPath}/security/findPassword" class="btn btn-link">비밀번호 찾기</a>
      </div>
    </div>
  </div>
</main>

<%@ include file="../inc/footer.jsp" %>
