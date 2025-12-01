<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../inc/header.jsp" %>

<div class="container card my-5">
  <h3 class="card-header">추천 상세보기</h3>
  		<%-- <c:forEach  var="dto"  items="${list}"  varStatus="status">
		  <div  class="col-sm-3  ">
		    <div class="mb-3 mt-3">
		      <label for="recId" class="form-label">추천 번호</label>
		      <input type="text" class="form-control" id="recId" name="recId" readonly value="${dto.recId}">
		    </div>
		    <div class="mb-3">
		      <label for="id" class="form-label">사용자 ID</label>
		      <input type="text" class="form-control" id="id" name="id" readonly value="${dto.id}">
		    </div>
		    <div class="mb-3">
		      <label for="foodId" class="form-label">음식 ID</label>
		      <input type="text" class="form-control" id="foodId" name="foodId" readonly value="${dto.foodId}">
		    </div>
		    <div class="mb-3">
		      <label for="type" class="form-label">추천 유형</label>
		      <input type="text" class="form-control" id="type" name="type" readonly value="${dto.type}">
		    </div>
		    <div class="mb-3">
		      <label for="feedback" class="form-label">피드백</label>
		      <textarea class="form-control" id="feedback" name="feedback" readonly>${dto.feedback}</textarea>
		    </div>
		    <div class="mb-3">
		      <label for="createdAt" class="form-label">추천 일시</label>
		      <input type="text" class="form-control" id="createdAt" name="createdAt" readonly value="${dto.createdAt}">
		    </div>
		 
		
		    <div class="mb-3">
		      <a href="<%=request.getContextPath()%>/recommendAll.recommend" class="btn btn-dark form-control">목록보기</a>
		    </div>
		  </div>
  
  </c:forEach> --%> 
   <table class="table table-dark table-striped">
      <thead>
        <tr>
          <th scope="col">NO</th>
          <th scope="col">사용자 ID</th>
          <th scope="col">음식 ID</th>
          <th scope="col">추천 유형</th>
          <th scope="col">피드백</th>
          <th scope="col">추천일시</th>
          <th scope="col">추천삭제</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="dto" items="${list}" varStatus="status">
			<tr>
			<td>${list.size() - status.index}  </td>
			<td>${dto.id}</td>
			<td>${dto.foodId}  </td>
			<td>${dto.type}  </td>
			<td>${dto.feedback}  </td>
			<td>${dto.createdAt}  </td>
			<td><a href="<%=request.getContextPath()%>/delete.recommend?recId=${dto.recId}" class= "btn btn-danger">
	      					추천삭제 
	      				</a>
 </td>
			
			</tr>
        </c:forEach>
      </tbody>
    </table>  
</div>

<%@ include file="../inc/footer.jsp" %>