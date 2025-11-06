<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>

<div class="container mt-5">
    <div class="card shadow-sm mx-auto" style="max-width: 400px;">
        <div class="card-body">
            <h2 class="card-title text-center mb-4">로그인</h2>

            <form method="post" action="login.do">
                <div class="mb-3">
                    <label for="email" class="form-label">이메일</label>
                    <input type="email" name="email" id="email" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label">비밀번호</label>
                    <input type="password" name="password" id="password" class="form-control" required>
                </div>

                <div class="d-grid">
                    <button type="submit" class="btn btn-success">로그인</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="../inc/footer.jsp" %>
