<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>

<div class="container mt-5">
    <div class="card shadow-sm mx-auto" style="max-width: 500px;">
        <div class="card-body">
            <h3 class="card-title text-center mb-4">회원가입</h3>

            <form action="<%= request.getContextPath() %>/join.do" method="post">
                <div class="mb-3">
                    <label class="form-label">닉네임</label>
                    <input type="text" name="nickname" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">이메일</label>
                    <input type="email" name="email" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">비밀번호</label>
                    <input type="password" name="password" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">휴대폰</label>
                    <input type="text" name="mobile" class="form-control">
                </div>

                <div class="d-grid">
                    <button type="submit" class="btn btn-primary">회원가입</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="../inc/footer.jsp" %>
