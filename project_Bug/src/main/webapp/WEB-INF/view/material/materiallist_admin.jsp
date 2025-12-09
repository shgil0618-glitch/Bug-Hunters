<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../inc/header.jsp"%>
<div class="container my-5">

	<h2>재료 전체 목록 (관리자)</h2>

    <sec:authorize access="hasRole('ROLE_ADMIN')">
	  <a href="${pageContext.request.contextPath}/admin/materialinsert" class="btn btn-primary">재료 추가하기</a>
    </sec:authorize> 
	<table class="table table-striped table-bordered table-hover">
		<tr>
			<th>번호</th>
			<th>이미지</th>
			<th>이름</th>
			<th>계절</th>
			<th>온도</th>
			<th>칼로리</th>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
			<th>관리</th>
			</sec:authorize>
		</tr>
	<tbody>
		<c:forEach var="dto" items="${list}" varStatus="vs">
			<tr>
				<%-- <td>${paging.listtotal - ((paging.current-1) *10) -status.index}</td> --%>
				<td>${vs.count}</td>
				<td><img src="${pageContext.request.contextPath}/upload/${dto.imageurl}" style="width: 60px;"></td>
				<td>${dto.title}</td>
				<td>${dto.season}</td>
				<td>${dto.temperature}</td>
				<td>${dto.calories100g}</td>
				<sec:authorize access="hasRole('ROLE_ADMIN')">				
				<td>  
						<p class="text-end">
							<a href="materialedit?materialid=${dto.materialid}">수정</a> <a
								href="materialdelete?materialid=${dto.materialid}"
								onclick="return confirm('삭제할까요?');">삭제</a>
						</p>
			   </td>   
			   </sec:authorize>
			</tr>
		</c:forEach>
	</table>
	</tbody>
	<tfoot>
      	  <tr><td colspan="5"><ul class="pagination  justify-content-center"> 
	      		<!-- 이전 -->	
	      		<c:if   test="${paging.start >10 }">
	      			<li  class="page-item">
	      				<a  class="page-link"  href="?pstartno=${paging.start-1}">이전</a>
	      			</li>
	      		</c:if>
	      		
	      		<!-- 1,2,3,4,5,6,7,8,9,10 -->	
	      		<c:forEach  var="i" begin="${paging.start}"  end="${paging.end}" >
	      			<li  class="page-item  <c:if test="${i==paging.current}">  active  </c:if>">
	      				<a  class="page-link"  href="?pstartno=${i}">${i}</a>
	      			</li>
	      		</c:forEach>	 
	      		
	      		<!-- 다음 -->	
	      		<c:if   test="${ paging.pagetotal > paging.end }">
	      			<li  class="page-item">
	      				<a  class="page-link"  href="?pstartno=${paging.end+1}">다음</a>
	      			</li>
	      		</c:if> 
      	  </ul></td></tr>	
      	</tfoot>
</div>
<%@ include file="../inc/footer.jsp"%>