<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../inc/header.jsp"%>
<script>
$(function() {
    let success = '${success}';
    let error = '${error}';

    if(success) {
        alert(success);
    } else if(error) {
        alert(error);
        history.go(-1); // 실패 시 뒤로
    }
});
</script>
<div class="container card  my-5 p-4">
	<h3 class="card-header">MBTI QUEST BOARD</h3>
	<table class="table table-striped table-bordered table-hover">
		<caption>mbti</caption>
		<thead>
			<tr>
				<th scope="col">NO</th>
				<th scope="col">USERNO</th>
				<th scope="col">EMAIL</th>
				<th scope="col">MBTITYPE</th>
				<th scope="col">DATE</th>
				<th scope="col">수정</th>
				<th scope="col">삭제</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="dto" items="${List}" varStatus="status">
				<tr>
					<td>${List.size()-status.index}</td>
					<td>${dto.appUserId}</td>
					<td><a href="${pageContext.request.contextPath}/mypage.user?appUserId=${dto.appUserId}">
                 ${dto.email}
              </a> </td>
					<td>${dto.mbtiTypeId}</td>
					<td>${dto.createdAt}</td>
					<td>
						<a href="${pageContext.request.contextPath}/edit.user?appUserId=${dto.appUserId}">수정</a></td>
						<td>
							<form action="${pageContext.request.contextPath}/delete.user"
								method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
								<input type="hidden" name="appUserId" value="${dto.appUserId}" />
								<input type="hidden" name="password"  value="${dto.password}" />
								<button type="submit" class="btn btn-danger btn-sm">삭제</button>
							</form></td>
				</tr>
			</c:forEach>

			<%-- <c:forEach var="dto" items="${List}" varStatus="status">
				<tr>
					<td>${List.size()-status.index}</td>
					<td>${dto.email}</td>
					<td>${dto.createdAt}</td>
					<td><a href="${pageContext.request.contextPath}/edit.user?appUserId=${dto.appUserId}">수정</a></td>
					<td>
                        <form action="${pageContext.request.contextPath}/delete.user" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                            <input type="hidden" name="appUserId" value="${dto.appUserId}" />
                            <input type="password" name="password" placeholder="비밀번호" required />
                            <button type="submit" class="btn btn-danger btn-sm">삭제</button>
                        </form>
                    </td>
				</tr>
			</c:forEach>   --%>
		</tbody>
	</table>
</div>

<%@include file="../inc/footer.jsp"%>

<!-- [ mbtiBoard - list.jsp ]  -->