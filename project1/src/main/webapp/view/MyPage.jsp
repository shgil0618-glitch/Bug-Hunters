<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.thejoa703.dto.UserDto"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../inc/header.jsp"%>

<body>
    <div class="container mt-5">
        <div class="card shadow-sm">
            <div class="card-body">
                <h2 class="card-title text-center mb-4">마이페이지</h2>

                <c:if test="${not empty message}">
                    <div class="alert alert-success">${message}</div>
                </c:if>

                <c:if test="${not empty user}">
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><strong>닉네임:</strong> ${user.NICKNAME}</li>
                        <li class="list-group-item"><strong>이메일:</strong> ${user.EMAIL}</li>
                        <li class="list-group-item"><strong>휴대폰:</strong> ${user.MOBILE}</li>
                        <li class="list-group-item"><strong>가입일:</strong> ${user.JOINDATE}</li>
                    </ul>
                </c:if>

                <c:if test="${empty user}">
                    <div class="alert alert-danger mt-3">사용자 정보를 불러올 수 없습니다.</div>
                </c:if>

                <div class="d-flex justify-content-center gap-3 mt-4 flex-wrap">
                    <a href="main.do" class="btn btn-outline-primary">메인으로</a>
                    <a href="editForm.do" class="btn btn-outline-warning">정보수정</a>
                    <a href="deleteForm.do" class="btn btn-outline-danger">회원탈퇴</a>
                </div>
            </div>
        </div>
    </div>
</body>

<%@ include file="../inc/footer.jsp"%>
