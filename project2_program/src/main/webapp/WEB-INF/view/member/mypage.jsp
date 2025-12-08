<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../inc/header.jsp" %>
   <div class="container card  my-5 p-3">
      <h3  class="card-header"> MYPAGE </h3> 
		<table class="table table-striped table-bordered table-hover align-middle">
		  <colgroup>
		    <col style="width:10%">   <!-- 이미지 열 -->
		    <col style="width:30%">   <!-- 항목명 열 -->
		    <col style="width:60%">   <!-- 값 열 -->
		  </colgroup>		
		  <tbody class="table-info">
		    <tr>
		      <!-- 왼쪽 이미지 셀: 3줄 병합 -->
		      <td rowspan="4">
		        <img src="${pageContext.request.contextPath}/upload/${dto.bfile}"   alt="" style="width:100%" />
		      </td>
		      <th scope="row">Email</th>
		      <td>${dto.email}</td>
		    </tr>
		    <tr>
		      <th scope="row">Nickname</th>
		      <td>${dto.nickname}</td>
		    </tr>
		    <tr>
		      <th scope="row">Mobile</th>
		      <td>${dto.mobile}</td>
		    </tr>
		    <tr>
		      <th scope="row">회원가입날짜</th>
		      <td>${dto.joindate}</td>
		    </tr>
		  </tbody>
		</table>
	  	<div class="text-end">
	  		<a href="${pageContext.request.contextPath}/security/update"  class="btn btn-danger">UPDATE</a>
            <a href="${pageContext.request.contextPath}/security/delete"  class="btn btn-primary">DELETE</a>
 		 </div>
	</div>
<%@ include file="../inc/footer.jsp" %>



<!-- 1. mypage -  유형 1,2,3,
	 2. first님      MbtiBaord   /  로그인 회원가입
	 3. 테이블에서 숫자자동으로 카운트 -->