<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../inc/header.jsp" %>

<div class="container my-5">
    <div class="card shadow-lg border-0">
        <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
            <h4 class="mb-0">커뮤니티 게시판</h4>
            <a href="writeView.co" class="btn btn-light btn-sm">글쓰기</a>
        </div>

        <div class="card-body p-0">
            <table class="table table-hover align-middle text-center mb-0">
                <thead class="table-light">
                    <tr>
                        <th scope="col" style="width: 7%">번호</th>
                        <th scope="col" style="width: 30%">제목</th>
                        <th scope="col" style="width: 15%">작성자</th>
                        <th scope="col" style="width: 15%">카테고리</th>
                        <th scope="col" style="width: 10%">조회수</th>
                        <th scope="col" style="width: 20%">작성일</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${list}">
                        <tr>
                            <td>${dto.postId}</td>
                            <td class="text-start ps-3">
                                <a href="<%=request.getContextPath()%>/detail.co?postId=${dto.postId}" 
                                   class="text-decoration-none fw-semibold text-dark">
                                    ${dto.title}
                                </a>
                            </td>
                            <td>${dto.id}</td>
                            <td>${dto.categoryId}</td>
                            <td>${dto.views}</td>
                            <td>${dto.createdAt}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty list}">
                        <tr>
                            <td colspan="6" class="text-muted py-4">등록된 게시글이 없습니다.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@include file="../inc/footer.jsp" %>
