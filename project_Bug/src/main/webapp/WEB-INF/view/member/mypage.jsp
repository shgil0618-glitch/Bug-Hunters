<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../inc/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<main class="container my-5 flex-grow-1">
   <div class="card p-3">
      <h3 class="card-header">MYPAGE</h3> 
   
      <!-- ✅ 메시지 출력 -->
      <c:if test="${not empty success}">
          <div class="alert alert-info my-3">
              ${success}
          </div>
      </c:if>
   
      <table class="table table-striped table-bordered table-hover align-middle">
         <colgroup>
           <col style="width:10%">   <!-- 이미지 열 -->
           <col style="width:30%">   <!-- 항목명 열 -->
           <col style="width:60%">   <!-- 값 열 -->
         </colgroup>       
         <tbody class="table-info">
           <tr>
             <!-- 왼쪽 이미지 셀: 4줄 병합 -->
             <td rowspan="4">
               <img src="${pageContext.request.contextPath}/upload/${dto.bfile}" alt="" style="width:100%" />
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
         <a href="${pageContext.request.contextPath}/security/update" class="btn btn-danger">UPDATE</a>
         <a href="${pageContext.request.contextPath}/security/delete" class="btn btn-primary">DELETE</a>
      </div>
   </div>
</main>

<%@ include file="../inc/footer.jsp" %>
