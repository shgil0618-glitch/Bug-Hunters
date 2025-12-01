<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../inc/header.jsp" %>

<div class="container mt-5">
    <div class="card shadow-sm">
        <div class="card-body">
            <h3 class="card-title text-center mb-4">정보 수정 요청</h3>

            <form action="edit.do" method="post">
                <input type="hidden" name="email" value="${user.EMAIL}">

                <div class="mb-3">
                    <label class="form-label">닉네임</label>
                    <input type="text" name="nickname" value="${user.NICKNAME}" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">비밀번호</label>
                    <input type="password" name="password" placeholder="새 비밀번호 입력" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">휴대폰</label>
                    <input type="text" name="mobile" value="${user.MOBILE}" class="form-control">
                </div>

                <div class="d-grid">
                    <input type="submit" value="회원정보 수정" class="btn btn-primary">
                </div>
            </form>

            <div class="text-center mt-4">
                <a href="main.do" class="btn btn-outline-secondary me-2">메인으로</a>
                <a href="deleteForm.do" class="btn btn-outline-danger">회원탈퇴</a>
            </div>
        </div>
    </div>
</div>

<%@ include file="../inc/footer.jsp" %>
