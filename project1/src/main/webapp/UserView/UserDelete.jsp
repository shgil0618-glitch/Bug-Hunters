<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../inc/header.jsp" %>

<div class="container mt-5">
    <div class="card shadow-sm">
        <div class="card-body">
            <h2 class="card-title text-center mb-4">회원 탈퇴</h2>

            <p class="text-muted text-center">회원 탈퇴를 위해 비밀번호를 입력해주세요.</p>

            <form action="delete.do" method="post" class="mt-4">
                <div class="mb-3">
                    <input type="password" name="password" class="form-control" placeholder="비밀번호 입력" required>
                </div>
                <div class="d-grid">
                    <input type="submit" value="회원 탈퇴하기" class="btn btn-danger">
                </div>
            </form>

            <div class="text-center mt-4">
                <a href="mypage.do" class="btn btn-outline-secondary">마이페이지로 돌아가기</a>
            </div>
        </div>
    </div>
</div>

<%@ include file="../inc/footer.jsp" %>
