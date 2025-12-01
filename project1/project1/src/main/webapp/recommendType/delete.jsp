<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>

<div class="container card my-5">
  <h3 class="card-header"> rec 글삭제 </h3>
  <form action="${pageContext.request.contextPath}/delete.recommend" method="post"> 
    <input type="hidden" name="id" value="${dto.id}">
    <div class="my-3">
      <label for="id" class="form-label">ID:</label>
      <input type="password" class="form-control" 
         id="id" placeholder="아이디를 입력해주세요" name="id">
    </div> 
    <div class="my-3 text-end">
       <button type="submit" class="btn btn-primary">글삭제</button>
       <a href="javascript:history.go(-1)" class="btn btn-danger">BACK</a>
    </div>
  </form>
</div>

<%@ include file="../inc/footer.jsp" %>
