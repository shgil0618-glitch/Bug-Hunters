<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>  <!-- 헤더 포함 -->

<div class="container mt-5">
    <h3>회원정보 수정</h3>
    
    <!-- 회원정보 수정 폼 -->
   <%--  <form action="${pageContext.request.contextPath}/edit2.user" method="post" encType="multipart/form-data"> --%>
   	<form action="${pageContext.request.contextPath}/security/edit" method="post" encType="multipart/form-data">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <!-- appUserId (숨겨진 필드로 전달) -->
        <input type="hidden" name="appUserId" value="${dto.appUserId}" />
        
        <!-- 이메일 필드 (수정 불가) -->
        <div class="mb-3">
            <label for="email" class="form-label">Email:</label>
            <input type="email" class="form-control" id="email" name="email" value="${dto.email}" readonly />
        </div>
        
        <!-- 비밀번호 필드 -->
        <div class="mb-3">
            <label for="password" class="form-label">Password:</label>
            <input type="password" class="form-control" id="password" name="password" placeholder="새 비밀번호 입력 (변경하려면)" />
        </div>
        
        <div class="mb-3">
            <label for="nickname" class="form-label">NICKNAME:</label>
            <input type="text" class="form-control" id="nickname" name="nickname" placeholder="새 닉네임 입력 (변경하려면)" value="${dto.nickname}" />
        </div>
        
        <div class="mb-3">
            <label for="mobile" class="form-label">MOBILE:</label>
            <input type="text" class="form-control" id="mobile" name="mobile" placeholder="새 전화번호 입력 (변경하려면)" value="${dto.mobile}" />
        </div>

        <!-- MBTI 유형 선택 -->
        <div class="mb-3">
            <label for="mbtiTypeId" class="form-label">MBTI Type:</label>
            <select class="form-select" id="mbtiTypeId" name="mbtiTypeId">
                <option value="1" ${dto.mbtiTypeId == 1 ? 'selected' : ''}>INFJ</option>
                <option value="2" ${dto.mbtiTypeId == 2 ? 'selected' : ''}>ENTP</option>
                <option value="3" ${dto.mbtiTypeId == 3 ? 'selected' : ''}>INTP</option>
                <!-- 더 많은 MBTI 유형을 추가할 수 있습니다 -->
            </select>
        </div>
        
        <div class="mb-3">
		    <label for="file" class="form-label">file:</label>
		    <input type="file" class="form-control" id="file" placeholder="파일을 입력해주세요" name="file"></input>
		    <input type="text" class="form-control" readonly name="ufile" value="${dto.ufile}" />
		    <img src="${pageContext.request.contextPath}/upload/${dto.ufile}"
					alt="user image"
					style="width: 150px; height: 100%; object-fit: cover; border-radius: 6px;" />
		  </div> 

        <!-- 버튼 -->
        <button type="submit" class="btn btn-primary">정보 수정</button>
    </form>
</div>

<%@ include file="../inc/footer.jsp" %>  <!-- 푸터 포함 -->
