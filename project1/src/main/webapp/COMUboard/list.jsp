<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../inc/header.jsp" %>

<%
    // --- 페이지 설정 부분 ---
    int pageSize = 10; // 한 페이지당 게시글 수
    int currentPage = 1; // 기본 페이지

    if (request.getParameter("page") != null) {
        currentPage = Integer.parseInt(request.getParameter("page"));
    }

    request.setAttribute("pageSize", pageSize);
    request.setAttribute("currentPage", currentPage);
%>

<c:set var="totalCount" value="${list.size()}" />
<c:set var="startIndex" value="${(currentPage - 1) * pageSize}" />
<c:set var="endIndex" value="${startIndex + pageSize - 1}" />
<c:set var="totalPage" value="${(totalCount / pageSize) + (totalCount % pageSize == 0 ? 0 : 1)}" />

<div class="container my-5">
    <div class="card shadow-lg border-0">
        <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
            <h4 class="mb-0">커뮤니티 게시판</h4>
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
                    <!-- 리스트 출력 (10개 단위로) -->
                    <c:forEach var="dto" items="${list}" varStatus="status" begin="${startIndex}" end="${endIndex}">
                        <tr>
                            <td>${totalCount - status.index}</td>
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

    <!-- 글쓰기 버튼 (로그인 여부 확인) -->
    <% if(email != null){ %>
        <p class="text-end my-3">
            <a href="<%=request.getContextPath()%>/writeView.co" class="btn btn-primary">글쓰기</a>
        </p>	
    <% } else { %>
        <p class="alert alert-primary">로그인을 하면 글쓰기가 가능합니다.</p>
    <% } %>

    <!-- 페이지네이션 -->
    <div class="mt-4 d-flex justify-content-center">
        <ul class="pagination">
            <c:if test="${currentPage > 1}">
                <li class="page-item">
                    <a class="page-link" href="?page=${currentPage - 1}">&laquo;</a>
                </li>
            </c:if>

            <c:forEach var="i" begin="1" end="${totalPage}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="?page=${i}">${i}</a>
                </li>
            </c:forEach>

            <c:if test="${currentPage < totalPage}">
                <li class="page-item">
                    <a class="page-link" href="?page=${currentPage + 1}">&raquo;</a>
                </li>
            </c:if>
        </ul>
    </div>
      <!-- 🔍 왼쪽 검색폼 -->
        <form action="<%=request.getContextPath()%>/communitySearch.co" method="get" class="d-flex align-items-center" style="gap: 8px;">
            <select name="search_target" class="form-select form-select-sm" style="width: 120px;">
                <option value="title">제목</option>
                <option value="content">내용</option>
                <option value="nick_name">닉네임</option>
            </select>
            <input type="text" name="search_keyword" class="form-control form-control-sm" placeholder="검색어 입력" style="width: 180px;">
            <button type="submit" class="btn btn-sm btn-outline-primary">검색</button>
        </form>
</div>

<%@include file="../inc/footer.jsp" %>
