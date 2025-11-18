<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container card my-5 p-4">
  <h3 class="card-header">커뮤니티 글수정</h3>

  <form action="${pageContext.request.contextPath}/edit.co" method="post" onsubmit="return validateEditForm()">
    <!-- 글번호 -->
    <input type="hidden" name="postId" value="${dto.postId}">
    <input type="hidden" name="id" value="${dto.id}">

    <!-- 작성자 -->
    <div class="mb-3 mt-3">
      <label for="nickname" class="form-label">작성자</label>
      <input type="text" class="form-control" id="nickname" name="nickname" value="${dto.nickname}" readonly>
    </div>

    <!-- 제목 -->
    <div class="mb-3">
      <label for="title" class="form-label">제목:</label>
      <input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력해주세요" value="${dto.title}">
    </div>

    <!-- 내용 -->
    <div class="mb-3">
      <label for="content" class="form-label">내용:</label>
      <textarea class="form-control" id="content" name="content" rows="6" placeholder="내용을 입력해주세요">${dto.content}</textarea>
    </div>

    <!-- 카테고리 -->
    <div class="mb-3">
            <label for="categoryId" class="form-label">카테고리:</label>
            <select class="form-select" id="categoryId" name="categoryId">
                <option value="">카테고리를 선택하세요</option>
                <option value="1" ${dto.categoryId == 1 ? "selected" : ""}>한식</option>
                <option value="2" ${dto.categoryId == 2 ? "selected" : ""}>양식</option>
                <option value="3" ${dto.categoryId == 3 ? "selected" : ""}>중식</option>
                <option value="4" ${dto.categoryId == 4 ? "selected" : ""}>일식</option>
                <option value="5" ${dto.categoryId == 5 ? "selected" : ""}>기타</option>
            </select>
    </div>

    <!-- 버튼 -->
    <div class="mb-3 text-end">
      <button type="submit" class="btn btn-primary">글수정</button>
      <a href="${pageContext.request.contextPath}/detail.co?postId=${dto.postId}" class="btn btn-danger">돌아가기</a>
    </div>
  </form>
</div>

<%@include file="../inc/footer.jsp" %>

<script>
    function validateEditForm() {
        var title = document.getElementById("title").value;
        var content = document.getElementById("content").value;
        var categoryId = document.getElementById("categoryId").value;

        if (title == "") {
            alert("제목을 입력해주세요.");
            return false;
        }

        if (content == "") {
            alert("내용을 입력해주세요.");
            return false;
        }

        if (categoryId == "") {
            alert("카테고리를 선택해주세요.");
            return false;
        }

        return true;  // 모든 검사 통과 시 폼 제출
    }
</script>
