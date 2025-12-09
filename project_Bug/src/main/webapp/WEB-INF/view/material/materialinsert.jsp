<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp"%>
<div class="container my-5">


	<h2>재료 등록</h2>

	<form action="${pageContext.request.contextPath}/admin/materialinsert"
		method="post" encType="multipart/form-data">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<div class="mb-3 mt-3">
			<label for="title" class="form-lable"> 제목: </label><input type="text"
				 class ="form-control" name="title" required>
		</div>
		<div class="mb-3">
			<label for="file" class="form-lable">이미지URL:</label> <input
				class ="form-control" type="file" name="file">
		</div>
		<div class="mb-3">
			<label for="text" class="form-lable">계절: </label><input type="text"
				class ="form-control" name="season">
		</div>
		<div class="mb-3">
			<label for="text" class="form-lable">온도: </label><input type="text"
				class ="form-control" name="temperature">
		</div>
		<div class="mb-3">
			<label for="text" class="form-lable">칼로리(100g): </label><input
				class ="form-control" type="text" name="calories100g">
		</div>
		<div class="mb-3">
			<label for="text" class="form-lable">효능: </label>
			<textarea name="efficacy" rows="5" cols="60" class ="form-control"></textarea>
		</div>
		<div class="mb-3">
			<label for="text" class="form-lable">구입 가이드: </label>
			<textarea name="buyguide" rows="5" cols="60" class ="form-control"></textarea>
		</div>
		<div class="mb-3">
			<label for="text" class="form-lable">손질 방법: </label>
			<textarea name="trimguide" rows="5" cols="60" class ="form-control"></textarea>
		</div>
		<div class="mb-3">
			<label for="text" class="form-lable">보관 방법: </label>
			<textarea name="storeguide" rows="5" cols="60"class ="form-control" ></textarea>
		</div>

		<div class="mb-3 text-end">
			<button type="submit" class="btn btn-primary">등록</button>
		</div>
	</form>

</div>
<%@ include file="../inc/footer.jsp"%>
