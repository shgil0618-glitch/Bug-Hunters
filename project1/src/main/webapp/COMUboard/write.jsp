<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>

<div class="container card my-5 p-4">
    <h3 class="card-header"> COMMUNITY 글쓰기 </h3>

    <!-- 컨트롤러로 데이터 전송 -->
    <form action="${pageContext.request.contextPath}/write.co" method="post" onsubmit="return validateForm()">
        <input type="hidden" name="email" value="${sessionScope.email}">
        
        <!-- 제목 -->
        <div class="mb-3">
            <label for="title" class="form-label">제목:</label>
            <input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력해주세요" value="">
        </div>

        <!-- 카테고리 -->
        <div class="mb-3">
            <label for="categoryId" class="form-label">카테고리:</label>
            <select class="form-select" id="categoryId" name="categoryId">
                <option value="">카테고리를 선택하세요</option>
                <option value="1">한식</option>
                <option value="2">양식</option>
                <option value="3">중식</option>
                <option value="4">일식</option>
                <option value="5">기타</option>
            </select>
        </div>

        <!-- 내용 -->
        <div class="mb-3">
            <label for="content" class="form-label">내용:</label>
            <textarea class="form-control" id="content" name="content" placeholder="내용을 입력해주세요" rows="7"></textarea>
        </div>

        <!-- 버튼 -->
        <div class="mb-3 text-end">
            <button type="submit" class="btn btn-primary">글쓰기</button>
            <a href="${pageContext.request.contextPath}/list.co" class="btn btn-primary">목록보기</a>
            <a href="javascript:history.go(-1)" class="btn btn-danger">BACK</a>
        </div>
    </form>
</div>

<%@ include file="../inc/footer.jsp" %>

<script>
    function validateForm() {
        var title = document.getElementById("title").value;
        var categoryId = document.getElementById("categoryId").value;
        var content = document.getElementById("content").value;

        if (title == "") {
            alert("제목을 입력해주세요.");
            return false;
        }

        if (categoryId == "") {
            alert("카테고리를 선택해주세요.");
            return false;
        }

        if (content == "") {
            alert("내용을 입력해주세요.");
            return false;
        }

        return true;  // 모든 검사 통과 시 폼 제출
    }
</script>
