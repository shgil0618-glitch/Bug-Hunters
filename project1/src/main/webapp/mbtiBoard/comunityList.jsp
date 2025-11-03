<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/inc/header.jsp" %>

<main style="max-width:1000px; margin:0 auto; padding:20px;">

    <!-- 커뮤니티 상단 -->
    <div class="community-header" style="margin-bottom:20px;">
        <h2>커뮤니티</h2>
    </div>

    <!-- 게시글 목록 -->
    <div class="post-list" style="display:flex; flex-direction:column; gap:15px;">

        <!-- 게시글 카드 -->
        <div class="post-card" style="display:flex; flex-direction:row; align-items:flex-start; padding:15px; border:1px solid #ccc; border-radius:8px;">
            <!-- 이미지 공간 -->
            <div class="post-image" style="width:100px; height:100px; background-color:#eee; border:1px solid #ccc; flex-shrink:0; margin-right:15px;">
                <!-- 이미지 자리 -->
            </div>

            <!-- 텍스트 영역 -->
            <div class="post-text" style="flex-grow:1;">
                <div class="post-title" style="font-weight:bold; font-size:1.2rem;">첫 번째 게시글 제목</div>
                <div class="post-meta" style="font-size:0.85rem; color:#666; margin-bottom:8px;">
                    작성자: user1 | 날짜: 2025-11-03 | 조회수: 12
                </div>
                <div class="post-content">
                    게시글 내용 예시입니다. 여기에 게시글 내용 일부가 들어갑니다.
                </div>
            </div>
        </div>

        <!-- 두 번째 게시글 -->
        <div class="post-card" style="display:flex; flex-direction:row; align-items:flex-start; padding:15px; border:1px solid #ccc; border-radius:8px;">
            <div class="post-image" style="width:100px; height:100px; background-color:#eee; border:1px solid #ccc; flex-shrink:0; margin-right:15px;"></div>
            <div class="post-text" style="flex-grow:1;">
                <div class="post-title" style="font-weight:bold; font-size:1.2rem;">두 번째 게시글 제목</div>
                <div class="post-meta" style="font-size:0.85rem; color:#666; margin-bottom:8px;">
                    작성자: user2 | 날짜: 2025-11-02 | 조회수: 8
                </div>
                <div class="post-content">
                    두 번째 게시글 내용 예시입니다. 요약된 내용만 표시됩니다.
                </div>
            </div>
        </div>

        <!-- 세 번째 게시글 -->
        <div class="post-card" style="display:flex; flex-direction:row; align-items:flex-start; padding:15px; border:1px solid #ccc; border-radius:8px;">
            <div class="post-image" style="width:100px; height:100px; background-color:#eee; border:1px solid #ccc; flex-shrink:0; margin-right:15px;"></div>
            <div class="post-text" style="flex-grow:1;">
                <div class="post-title" style="font-weight:bold; font-size:1.2rem;">세 번째 게시글 제목</div>
                <div class="post-meta" style="font-size:0.85rem; color:#666; margin-bottom:8px;">
                    작성자: user3 | 날짜: 2025-11-01 | 조회수: 20
                </div>
                <div class="post-content">
                    세 번째 게시글 내용 예시입니다. 내용 일부만 보여주고 전체는 클릭 시 확인 가능합니다.
                </div>
            </div>
        </div>

    </div>
	<div class="mb-3  text-end">
    	<a href="<%=request.getContextPath()%>/comwrite.jsp"  class="btn btn-primary ">게시글 작성</a>           
	</div>
</main>

<%@ include file="/inc/footer.jsp" %>
