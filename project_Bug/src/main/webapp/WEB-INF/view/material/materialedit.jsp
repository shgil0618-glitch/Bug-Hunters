<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp"%>

<div class="container my-5">
	<h2>재료 수정</h2>

	<form action="${pageContext.request.contextPath}/admin/materialedit"
		method="post" encType="multipart/form-data">

		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="hidden" name="materialid"
			value="${dto.materialid}">
		<div class="mb-3 mt-3">
			<label for="title" class="form-label"> 제목: </label><input type="text"
				class ="form-control" name="title" value="${dto.title}" required>
		</div>
		<div class="mb-3">
			<label for="file" class="form-label">이미지URL: </label><input
				type="file" class="form-control" id="file" name="file"><input
				class ="form-control" type="text" readonly name="imageurl" value="${dto.imageurl}">
		</div>
		<div class="mb-3">
			<label for="season" class="form-label">계절: </label><input type="text"
			class ="form-control" 	name="season" value="${dto.season}">
		</div>
		<div class="mb-3">
			<label for="temperature" class="form-label">온도: </label><input
			class ="form-control" 	type="text" name="temperature" value="${dto.temperature}">
		</div>
		<div class="mb-3">
			<label for="calories100g" class="form-label">칼로리(100g): </label><input
				class ="form-control" type="text" name="calories100g" value="${dto.calories100g}">
		</div>

		<div class="mb-3">
			<label for="efficacy" class="form-label">효능: </label>
			<textarea name="efficacy" rows="5" cols="60" class ="form-control">${dto.efficacy}</textarea>
		</div>
		<div class="mb-3">
			<label for="butyguide" class="form-label">구입 가이드: </label>
			<textarea name="buyguide" rows="5" cols="60" class ="form-control">${dto.buyguide}</textarea>
		</div>
		<div class="mb-3">
			<label for="trimguide" class="form-label">손질 방법: </label>
			<textarea name="trimguide" rows="5" cols="60" class ="form-control">${dto.trimguide}</textarea>
		</div>
		<div class="mb-3">
			<label for="storeguide" class="form-label" >보관 방법: </label>
			<textarea name="storeguide" rows="5" cols="60" class ="form-control">${dto.storeguide}</textarea>
		</div>

		<div class="mb-3 text-end">
			<button type="submit">수정</button>
			<a href="javascript:history.go(-1)" class="btn btn-danger">BACK</a>
		</div>
	</form>

</div>
<%@ include file="../inc/footer.jsp"%>