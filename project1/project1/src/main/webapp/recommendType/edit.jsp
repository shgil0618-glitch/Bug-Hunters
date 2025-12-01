<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>
<%@ page import="com.thejoa703.dto.SerchDto" %>

<%


    SerchDto dto = (SerchDto) request.getAttribute("dto");
%>

<div class="container card my-5">
  <h3 class="card-header">추천 수정하기</h3>

<%
    if (dto == null) {
%>
    <div class="alert alert-danger mt-3">추천 데이터를 찾을 수 없습니다.</div>
    <div class="mb-3">
      <a href="<%=request.getContextPath()%>/recommendAll.recommend" class="btn btn-secondary form-control">목록으로</a>
    </div>
<%
    } else {
%>
    <form action="<%=request.getContextPath()%>/update.recommend" method="post">
      <input type="hidden" name="recId" value="<%=dto.getRecId()%>">

      <div class="mb-3 mt-3">
        <label for="id" class="form-label">사용자 ID</label>
        <input type="text" class="form-control" id="id" name="id" value="<%=dto.getId()%>" required>
      </div>

      <div class="mb-3">
        <label for="foodId" class="form-label">음식 ID</label>
        <input type="number" class="form-control" id="foodId" name="foodId" value="<%=dto.getFoodId()%>" required>
      </div>

      <div class="mb-3">
        <label for="type" class="form-label">추천 유형</label>
        <select class="form-select" id="type" name="type" required>
          <option value="AI" <%= "AI".equals(dto.getType()) ? "selected" : "" %>>AI</option>
          <option value="랜덤" <%= "랜덤".equals(dto.getType()) ? "selected" : "" %>>랜덤</option>
          <option value="재료기반" <%= "재료기반".equals(dto.getType()) ? "selected" : "" %>>재료기반</option>
        </select>
      </div>

      <div class="mb-3">
        <label for="feedback" class="form-label">피드백</label>
        <textarea class="form-control" id="feedback" name="feedback" rows="3"><%=dto.getFeedback()%></textarea>
      </div>

      <div class="mb-3">
        <button type="submit" class="btn btn-primary form-control">수정 완료</button>
      </div>
      <div class="mb-3">
        <a href="javascript:history.go(-1)" class="btn btn-secondary form-control">BACK</a>
      </div>
    </form>
<%
    }
%>
</div>

<%@ include file="../inc/footer.jsp" %>