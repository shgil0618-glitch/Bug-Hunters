<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../inc/header.jsp"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container mt-5">
    <h3>닉네임 / 비밀번호 수정</h3>

    <!-- ✅ 수정칸에 메시지 출력 추가 -->
    <c:if test="${not empty success}">
        <div class="alert alert-info">
            ${success}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/security/update"  
          method="post" enctype="multipart/form-data"> 
        
        <sec:csrfInput/>
        
        <!-- 사용자 ID -->
        <input type="hidden" name="appUserId" value="${dto.appUserId}"> 
        
        <!-- 이메일은 읽기 전용 -->
        <div class="mb-3 mt-3">
            <label for="email" class="form-label">Email:</label> 
            <input type="email" class="form-control" id="email"
                   name="email" value="${dto.email}" readonly>
        </div>
        
        <!-- 닉네임 수정 -->
        <div class="mb-3">
            <label for="nickname" class="form-label">Nickname:</label>  
            <input type="text" class="form-control" id="nickname" 
                   name="nickname" value="${dto.nickname}" required>
        </div>
        
        <!-- 현재 비밀번호 확인 -->
        <div class="mb-3">
            <label for="password" class="form-label">현재 비밀번호:</label> 
            <input type="password" class="form-control" id="password"
                   name="password" required>
        </div>
        
        <!-- 새 비밀번호 입력 -->
        <div class="mb-3">
            <label for="newPassword" class="form-label">새 비밀번호:</label> 
            <input type="password" class="form-control" id="newPassword"
                   name="newPassword" required>
        </div>
        
        <!-- 새 비밀번호 확인 -->
        <div class="mb-3">
            <label for="confirmPassword" class="form-label">새 비밀번호 확인:</label> 
            <input type="password" class="form-control" id="confirmPassword"
                   name="confirmPassword" required>
        </div>
        
        <!-- 프로필 이미지 (선택) -->
        <div class="mb-3">
            <label for="file" class="form-label">프로필 이미지:</label>
            <input type="file" class="form-control" id="file" name="file">
        </div>
        
        <button type="submit" class="btn btn-primary">수정하기</button>
    </form>
</div>

<%@ include file="../inc/footer.jsp"%>
