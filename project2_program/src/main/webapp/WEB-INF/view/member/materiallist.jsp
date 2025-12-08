<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../inc/header.jsp" %>
   <div class="container card  my-5 p-4  userTable">
   <h3 class="card-header">재료(Material) 관리자BOARD</h3>
	<table class="table table-striped table-bordered table-hover">    	
	<thead>
    <tr>
        <th scope="col">이름</th>
        <th scope="col">이미지</th>
        <th scope="col">재료명</th>
        <th scope="col">제철</th>
        <th scope="col">열량</th>
        <th scope="col">상세보기</th>
        <th scope="col">관리</th>
    </tr>
   </thead>
    <tbody></tbody>
    </table>
    </div>
<div class="show-result container card my-5 p-4">
<from>
	<div class="mb-3">
		<img src="" alt="" style="width:120px;border-radius:10px" id="img"/>
	</div>
	<div class="mb-3 mt-3">
		<label for="title" class="from-label">재료명</label>
		<input type="text" class="from-control" id="title" required name="title">
	</div>
	<div class="mb-3">
		<label class="from-label" for="season" >재철</label>
		<input type="text" class="from-control" id="season" name="season">
	</div>
	<div class="mb-3">
		<label class="from-label" for="calories100g" >열량(100g)</label>
		<input type="number" class="from-control" id="calories100g" name="calories100g">
	</div>
	<div class="mb-3">
		<label class="from-label" for="imageurl" >이미지 URL</label>
		<input type="text" class="from-control" id="imageurl" name="imageurl">
	</div>
	<div class="userhidden_no"></div>
	<input type="button" value="재료 정보 수정"
		class="btn btn-primary" id="updateMaterial"/>
	</from>
</div>
<%@include file="../inc/footer.jsp" %>