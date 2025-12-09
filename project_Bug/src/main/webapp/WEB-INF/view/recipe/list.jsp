<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container mt-5">
    <h3>레시피 목록</h3>

<c:if test="${not empty result}">
    <script>
        alert("${result}");
    </script>
</c:if>

       <table class="table ">
    <thead>
        <tr>
            <th>NO</th>
            <th>레시피 제목</th>
            <th>카테고리</th>
            <th>작성자</th>
            <th>조리 시간</th>
            <th>인분</th>
            <th>조회수</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="recipe" items="${list}" varStatus="status">
      <tr>
            <td>${paging.listtotal - ((paging.current-1) * 10) - status.index}</td> 
            <td><a href="${pageContext.request.contextPath}/recipe/detail?recipeId=${recipe.recipeId}">${recipe.title}</a></td>
            <td>${recipe.categoryName}</td>
            <td>${recipe.nickname}</td>
            <td>${recipe.cookTime}</td>
            <td>${recipe.servings}</td>
            <td>${recipe.views}</td>
            <td></td>
        </tr></c:forEach>
        <c:if test="${empty list}"><tr>
            <td colspan="8" class="text-center text-muted">등록된 레시피가 없습니다.</td>
        </tr></c:if>
    </tbody>
    
     <tfoot>
<tr>
<td colspan="8">
    <ul class="pagination justify-content-center">

        <!-- ⏮ 첫 페이지 -->
        <c:if test="${paging.current > 1}">
            <li class="page-item">
                <a class="page-link" href="${pageContext.request.contextPath}/recipe/list?pstartno=1">
                    &laquo;
                </a>
            </li>
        </c:if>

        <!-- ◀ 이전 페이지 -->
        <c:if test="${paging.current > 1}">
            <li class="page-item">
                <a class="page-link" 
                   href="${pageContext.request.contextPath}/recipe/list?pstartno=${paging.current - 1}">
                    &lsaquo;
                </a>
            </li>
        </c:if>

        <!-- 🔙 이전 10페이지 -->
        <c:if test="${paging.start > 10}">
            <li class="page-item">
                <a class="page-link" 
                   href="${pageContext.request.contextPath}/recipe/list?pstartno=${paging.start - 10}">
                    이전
                </a>
            </li>
        </c:if>

        <!-- 📄 숫자 페이지 -->
        <c:forEach var="i" begin="${paging.start}" end="${paging.end}">
            <li class="page-item <c:if test='${i == paging.current}'>active</c:if>">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/recipe/list?pstartno=${i}">
                    ${i}
                </a>
            </li>
        </c:forEach>

        <!-- 🔜 다음 10페이지 -->
        <c:if test="${paging.pagetotal > paging.end}">
            <li class="page-item">
                <a class="page-link" 
                   href="${pageContext.request.contextPath}/recipe/list?pstartno=${paging.end + 1}">
                    다음
                </a>
            </li>
        </c:if>

        <!-- ▶ 다음 페이지 -->
        <c:if test="${paging.current < paging.pagetotal}">
            <li class="page-item">
                <a class="page-link" 
                   href="${pageContext.request.contextPath}/recipe/list?pstartno=${paging.current + 1}">
                    &rsaquo;
                </a>
            </li>
        </c:if>

        <!-- ⏭ 마지막 페이지 -->
        <c:if test="${paging.current < paging.pagetotal}">
            <li class="page-item">
                <a class="page-link" 
                   href="${pageContext.request.contextPath}/recipe/list?pstartno=${paging.pagetotal}">
                    &raquo;
                </a>
            </li>
        </c:if>

    </ul>
</td>
</tr>
</tfoot>

    </table> 


        <p class="text-end">
        <button onclick="location.href='${pageContext.request.contextPath}/recipe/register'" class="btn btn-primary">레시피 등록</button>
    </p>

</div>

<%@ include file="../inc/footer.jsp"%>