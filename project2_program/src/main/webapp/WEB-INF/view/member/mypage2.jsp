<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../inc/header.jsp" %>

<c:if test="${not empty deleteError }">
	<script>
		alert("${deleteError}");
	</script>
</c:if>

   <div class="container card  my-5 p-3">
      <h3  class="card-header"> MYPAGE </h3> 
      <!-- <pre>
      principal    현재사용자정보
      </pre>  -->
       <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
		<table class="table table-striped table-bordered table-hover align-middle">
		  <colgroup>
		    <col style="width:10%">   <!-- 이미지 열 -->
		    <col style="width:30%">   <!-- 항목명 열 -->
		    <col style="width:60%">   <!-- 값 열 -->
		  </colgroup>		
		 <%-- <tbody>
		    <tr>
		      <th scope="row">MBTI TYPE</th>
		      <td><sec:authentication property="principal" /></td>
		    </tr>
		  </tbody>   --%>
		  
		    <tbody class="table-info">
		    <tr>
		      <!-- 왼쪽 이미지 셀: 3줄 병합 -->
		      <td rowspan="3">
		        <img src="${pageContext.request.contextPath}/upload/${dto.ufile}"   alt="" style="width:100%" />
		      </td>
		      <th scope="row">Email</th>
		      <td>${dto.email}</td>
		    </tr>
		    <tr>
		      <th scope="row">MBTI TYPE</th>
		      <td>${dto.mbtiTypeId}</td>
		    </tr>
		    <tr>
		      <th scope="row">회원가입날짜</th>
		      <td>${dto.createdAt}</td>
		    </tr>
		  </tbody> 
		</table>
	  	<div class="text-end">
	  		<a href="${pageContext.request.contextPath}/edit.users?appUserId=${dto.appUserId}"  class="btn btn-danger">UPDATE</a>
            <a href="${pageContext.request.contextPath}/delete.users?appUserId=${dto.appUserId}"  
 					class="btn btn-primary">DELETE</a>
 		 </div>
	</div>
<%@ include file="../inc/footer.jsp" %>



<!-- 1. mypage -  유형 1,2,3,
	 2. first님      MbtiBaord   /  로그인 회원가입
	 3. 테이블에서 숫자자동으로 카운트 -->