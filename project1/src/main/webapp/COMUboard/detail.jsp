<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../inc/header.jsp" %>

<div class="container my-5">
    <div class="card shadow border-0">
        <div class="card-header bg-primary text-white">
            <h4 class="mb-0">게시글 상세보기</h4>
        </div>

        <div class="card-body">
            <table class="table table-borderless">
                <tr>
                    <th style="width: 120px;">제목</th>
                    <td>${dto.title}</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${dto.id}</td>
                </tr>
                <tr>
                    <th>카테고리</th>
                    <td>${dto.categoryId}</td>
                </tr>
                <tr>
                    <th>조회수</th>
                    <td>${dto.views}</td>
                </tr>
                <tr>
                    <th>작성일</th>
                    <td>${dto.createdAt}</td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td class="border rounded p-3" style="white-space: pre-line;">
                        ${dto.content}
                    </td>
                </tr>
            </table>

            <div class="mb-3 text-end">
                <a href="<%=request.getContextPath()%>/editView.co?postId=${dto.postId}" class="btn btn-success">게시글 수정</a>
                <a href="<%=request.getContextPath()%>/delete.co?postId=${dto.postId}" class="btn btn-danger">게시글 삭제</a>
                <a href="list.co" class="btn btn-secondary">목록</a>
            </div>
        </div>
    </div>
</div>

<%@include file="../inc/footer.jsp" %>
