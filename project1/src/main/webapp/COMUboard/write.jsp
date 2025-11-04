<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- Latest compiled and minified CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Latest compiled JavaScript -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div class="container card my-5 p-4">
    <h3 class="card-header"> COMMUNITY 글쓰기 </h3>

    <!-- ✅ 컨트롤러로 데이터 전송 -->
    <form action="<%=request.getContextPath()%>/write.co" method="post">

        <!-- 작성자 -->
        <div class="mb-3 mt-3">
            <label for="id" class="form-label">작성자:</label>
            <input type="text" class="form-control" id="id" name="id"
                   placeholder="작성자 ID를 입력해주세요" value="${dto.id}">
        </div>

        <!-- 제목 -->
        <div class="mb-3">
            <label for="title" class="form-label">제목:</label>
            <input type="text" class="form-control" id="title" name="title"
                   placeholder="제목을 입력해주세요" value="${dto.title}">
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
		
        <!-- 내용 -->
        <div class="mb-3">
            <label for="content" class="form-label">내용:</label>
            <textarea class="form-control" id="content" name="content"
                      placeholder="내용을 입력해주세요" rows="7">${dto.content}</textarea>
        </div>
        
        

        <!-- 버튼 -->
        <div class="mb-3 text-end">
            <button type="submit" class="btn btn-primary">글쓰기</button>
          <a href="<%=request.getContextPath()%>/list.co" class = "btn btn-primary">목록보기</a>
            <a href="javascript:history.go(-1)" class="btn btn-danger">BACK</a>
        </div>

    </form>
</div>

<%@ include file="../inc/footer.jsp" %>
</body>
</html>

<!-- [ communityBoard - write.jsp ] -->


<%-- <%@include file="../inc/header.jsp" %>
<h2>글쓰기</h2>
<form action="write.co" method="post">
    작성자 ID: <input type="text" name="id"><br>
    제목: <input type="text" name="title"><br>
    내용: <textarea name="content" rows="5" cols="50"></textarea><br>
    카테고리: <input type="text" name="categoryId"><br>
    <input type="submit" value="등록">
</form>
<a href="list.co">목록으로</a>
<%@include file ="../inc/footer.jsp" %> --%>
