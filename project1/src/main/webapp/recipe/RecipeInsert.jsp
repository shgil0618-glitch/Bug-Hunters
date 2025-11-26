<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../inc/header.jsp"%>

<div class="container card my-5 p-4">
	<h3 class="card-header">➕ 레시피 등록</h3>

	<!-- enctype 제거 -->
	<form action="${pageContext.request.contextPath}/recipeInsert.reci"
		method="post">

		<div class="mb-3 mt-3">
			<label class="form-label">제목:</label> <input type="text"
				class="form-control" name="title" required>
		</div>

		<div class="mb-3">
			<label class="form-label">카테고리:</label> <select class="form-select"
				name="category">
				<option value="">카테고리 선택</option>
				<option value="한식">한식</option>
				<option value="양식">양식</option>
				<option value="중식">중식</option>
				<option value="일식">일식</option>
				<option value="기타">기타</option>
			</select>
		</div>

		<div class="mb-3">
			<label class="form-label">간략 설명:</label>
			<textarea class="form-control" name="description" rows="4"></textarea>
		</div>

		<div class="mb-3">
			<label class="form-label">조리 시간(분):</label> <input type="number"
				class="form-control" name="cookTime">
		</div>

		<div class="mb-3">
			<label class="form-label">난이도:</label> <select class="form-select"
				name="difficulty">
				<option value="쉬움">쉬움</option>
				<option value="중간">중간</option>
				<option value="어려움">어려움</option>
			</select>
		</div>

		<div class="mb-3">
			<label class="form-label">인원수:</label> <input type="number"
				class="form-control" name="servings">
		</div>

		<!-- <div class="mb-3">
      <label class="form-label">이미지 파일명:</label>
      <input type="text" class="form-control" name="rfile" value="default.png">
    </div> -->
		<div class="mb-3">
			<label for="bfile" class="form-label">파일 이름:</label> <input
				type="text" class="form-control" name="bfile"
				placeholder="예: mypic.png" />
		</div>


		<div class="mb-3 text-end">
			<button type="submit" class="btn btn-primary">등록</button>
			<a href="${pageContext.request.contextPath}/recipeList.reci"
				class="btn btn-danger">취소</a>
		</div>
	</form>
</div>

<%@include file="../inc/footer.jsp"%>
