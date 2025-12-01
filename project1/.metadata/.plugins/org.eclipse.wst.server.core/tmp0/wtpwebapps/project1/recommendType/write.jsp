<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>

<div class="container card my-5">
  <h3 class="card-header">추천 등록하기</h3>
  <form action="<%=request.getContextPath()%>/reg.recommend" method="post">
    <div class="mb-3 mt-3">
      <label for="id" class="form-label">사용자 ID</label>
      <input type="text" class="form-control" id="id" name="id" placeholder="사용자 ID를 입력해주세요" required>
    </div>

    <div class="mb-3">
      <label for="foodId" class="form-label">음식 ID</label>
      <input type="number" class="form-control" id="foodId" name="foodId" placeholder="음식 ID를 입력해주세요" required>
    </div>

    <div class="mb-3">
      <label for="type" class="form-label">추천 유형</label>
      <select class="form-select" id="type" name="type" required>
        <option value="AI">AI</option>
        <option value="랜덤">랜덤</option>
        <option value="재료기반">재료기반</option>
      </select>
    </div>

    <div class="mb-3">
      <label for="feedback" class="form-label">피드백</label>
      <textarea class="form-control" id="feedback" name="feedback" placeholder="피드백을 입력해주세요" rows="3"></textarea>
    </div>

    <button type="submit" class="btn btn-primary">등록하기</button>
    <a href="<%=request.getContextPath()%>/recommendAll.recommend" class="btn btn-secondary">목록보기</a>
  </form>
</div>

<%@ include file="../inc/footer.jsp" %>