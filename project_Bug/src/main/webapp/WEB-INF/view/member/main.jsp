<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>
    /* 카드 이미지 높이를 카드의 70%로 설정 */
    .recipe-img {
        height: 70%;
    }
    .card {
        display: flex;
        flex-direction: column;
		min-width: 300px;
		max-width: 1400px;
        width: 23%;
        margin: 1px;
		
    }
    .row > .col-md-3 {
    padding-right: 0px !important; 
    padding-left: 0px !important;
	}
	
	/* 카드 본체에 내부 여백을 줘서 내용이 가장자리에 붙지 않게 합니다. */
	.card-body {
	    padding: 1rem;
	}
    /* 🚀 [추가/수정] 모달 본문 높이 제한 및 스크롤 적용 */
    .modal-body {
        max-height: 70vh; /* 화면 높이의 70%로 제한 */
        overflow-y: auto; /* 내용이 넘치면 스크롤 */
    }
    /* 카테고리 라벨 */
    .category-label {
        position: absolute;
        top: 10px;
        right: 10px;
        background-color: #fff;
        color: #333;
        padding: 5px 10px;
        border-radius: 5px;
        font-size: 0.9rem;
        box-shadow: 0 0 5px rgba(0,0,0,0.1);
    }
    /* 모달 카테고리 라벨 */
    .modal-label {
        display: inline-block;
        padding: 5px 10px;
        border-radius: 5px;
        font-weight: bold;
        text-align: center;
        background-color: #fff8eb;
        margin-right: 8px; /* 난이도 박스와 간격 */
    }

    /* 요리 정보 줄 */
    .recipe-info {
        text-align: left;
        margin-top: 20px;
    }
    .recipe-info span {
        margin: 0 4px;
        display: inline-block;
    }

    /* 난이도 박스 */
    .difficulty-box {
        display: inline-block;
        padding: 5px 10px;
        border-radius: 5px;
        font-weight: bold;
        text-align: center;
    }
    .difficulty-easy {
        background-color: #d4edda;
        color: #155724;
    }
    .difficulty-medium {
        background-color: #fff3cd;
        color: #856404;
    }
    .difficulty-hard {
        background-color: #f8d7da;
        color: #721c24;
    }

    /* 설명 부분 */
    .recipe-description {
        margin-top: 10px;
    }

    /* 모달 이미지 통일 */
    .modal-img {
        max-width: 100%;   /* 화면 너비의 70%까지만 */
        height: auto;     /* 비율 유지 */
        display: block;
        margin: 0px auto; /* 가운데 정렬 + 여백 */
    }
</style>

<div class="container mt-5">
    <h3>N개의 레시피가 있습니다</h3>
    
    <div class="mb-3 mt-3 alert alert-light border">

        <label for="search" class="form-label fw-bold">SEARCH RECIPE</label>

        <div class="row">

            <div class="col-md-9">

                <input type="search" class="form-control" id="search" placeholder="검색어를 입력하세요 (비워두면 전체 출력)">

            </div>

            <div class="col-md-3 text-end">

                <button class="btn btn-primary w-100" onclick="location.href='${pageContext.request.contextPath}/recipe/register'">레시피 등록</button>

            </div>

        </div>

    </div>



    <div class="mb-4 category-btns">

        <button class="btn btn-outline-secondary filter-btn active" data-filter="전체">전체</button>

        <button class="btn btn-outline-secondary filter-btn" data-filter="한식">한식</button>

        <button class="btn btn-outline-secondary filter-btn" data-filter="양식">양식</button>

        <button class="btn btn-outline-secondary filter-btn" data-filter="중식">중식</button>

        <button class="btn btn-outline-secondary filter-btn" data-filter="일식">일식</button>

        <button class="btn btn-outline-secondary filter-btn" data-filter="디저트">디저트</button>

        <button class="btn btn-outline-secondary filter-btn" data-filter="건강식">건강식</button>

        <button class="btn btn-outline-secondary filter-btn" data-filter="기타">기타</button>

    </div>

    <!-- 성공 메시지 -->
    <c:if test="${not empty result}">
        <div class="alert alert-success mt-4">${result}</div>
    </c:if>

    <!-- 카드 레이아웃 -->
    <div class="row gx-0">
        <c:forEach var="recipe" items="${list}" varStatus="status">
            <c:if test="${status.index < 8}">
                <div class="col-md-3 mb-4">
                    <div class="card h-100 shadow-sm position-relative" style="cursor:pointer;" 
                         data-bs-toggle="modal" data-bs-target="#recipeModal${recipe.recipeId}">
                         
                        <!-- 카테고리 라벨 -->
                        <div class="category-label">
                            ${recipe.categoryName}
                        </div>

                        <!-- 이미지 -->
                        <c:choose>
                            <c:when test="${fn:startsWith(recipe.image, 'http')}">
                                <img src="${recipe.image}" alt="${recipe.title}" class="card-img-top recipe-img">
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/upload/${recipe.image}" alt="${recipe.title}" class="card-img-top recipe-img">
                            </c:otherwise>
                        </c:choose>

                        <div class="card-body">
                            <h5 class="card-title text-center">${recipe.title}</h5>
                            <!-- 설명 -->
                            <p class="card-text text-muted text-truncate recipe-description">
                                <c:choose>
                                    <c:when test="${fn:length(recipe.description) > 50}">
                                        ${fn:substring(recipe.description, 0, 50)}...
                                    </c:when>              
                                    <c:otherwise>
                                        ${recipe.description}
                                    </c:otherwise>
                                </c:choose>
                            </p>
                            <!-- 요리 정보 -->
                            <p class="recipe-info">
                                <span>⏱${recipe.cookTime}분</span>
                                <span>🙍‍♂️${recipe.servings}인분</span>
                                <span>👁️‍🗨️${recipe.views}</span>
                                <span>👩‍🍳${recipe.nickname}</span>
                            </p>

                            <!-- 난이도 박스 -->
                            <div class="mt-2">
                                <c:choose>
                                    <c:when test="${recipe.difficulty eq '쉬움'}">
                                        <span class="difficulty-box difficulty-easy">${recipe.difficulty}</span>
                                    </c:when>
                                    <c:when test="${recipe.difficulty eq '보통'}">
                                        <span class="difficulty-box difficulty-medium">${recipe.difficulty}</span>
                                    </c:when>
                                    <c:when test="${recipe.difficulty eq '어려움'}">
                                        <span class="difficulty-box difficulty-hard">${recipe.difficulty}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="difficulty-box">${recipe.difficulty}</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 모달 -->
                <div class="modal fade" id="recipeModal${recipe.recipeId}" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog modal-md modal-dialog-centered"><!-- modal-lg → modal-md -->
                        <div class="modal-content">
                            <div class="modal-header">
                                
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
                            </div>
                            <div class="modal-body">
                                <c:choose>
                                    <c:when test="${fn:startsWith(recipe.image, 'http')}">
                                        <img src="${recipe.image}" alt="${recipe.title}" class="modal-img"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/upload/${recipe.image}" alt="${recipe.title}" class="modal-img"/>
                                    </c:otherwise>
                                </c:choose>
								<h5 class="modal-title">${recipe.title}</h5>
                                <p>${recipe.description}</p>  
								
                                <!-- 카테고리 + 난이도 박스 나란히 -->
                                <div class="mt-2">
                                    <span class="modal-label">${recipe.categoryName}</span>
                                    <c:choose>
                                        <c:when test="${recipe.difficulty eq '쉬움'}">
                                            <span class="difficulty-box difficulty-easy">${recipe.difficulty}</span>
                                        </c:when>
                                        <c:when test="${recipe.difficulty eq '보통'}">
                                            <span class="difficulty-box difficulty-medium">${recipe.difficulty}</span>
                                        </c:when>
                                        <c:when test="${recipe.difficulty eq '어려움'}">
                                            <span class="difficulty-box difficulty-hard">${recipe.difficulty}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="difficulty-box">${recipe.difficulty}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <hr>
                                <p><strong>작성자 :</strong> ${recipe.nickname}</p>                          
                                <p><strong>조리 시간 :</strong> ${recipe.cookTime} 분</p>
                                <p><strong>인분 :</strong> ${recipe.servings} 인분</p>
                                <hr>
                                <p><strong>재료</strong> </p>
                                <pre>${recipe.ingredients}</pre>
                                <hr>
                                <p><strong>조리 방법</strong></p>
                                <pre>${recipe.instructions}</pre>
                                
                                
                              
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:forEach>  
    </div>
</div>

<!-- 페이징 영역 -->
    <div class="mt-4">
        <ul class="pagination justify-content-center">
            <c:if test="${paging.current > 1}">
                <li class="page-item">
                    <a class="page-link" href="${pageContext.request.contextPath}/security/main?pstartno=1">&laquo;</a>
                </li>
            </c:if>
            <c:if test="${paging.current > 1}">
                <li class="page-item">
                    <a class="page-link" href="${pageContext.request.contextPath}/security/main?pstartno=${paging.current - 1}">&lsaquo;</a>
                </li>
            </c:if>
            <c:if test="${paging.start > 9}">
                <li class="page-item">
                    <a class="page-link" href="${pageContext.request.contextPath}/security/main?pstartno=${paging.start - 10}">이전</a>
                </li>
            </c:if>
            <c:forEach var="i" begin="${paging.start}" end="${paging.end}">
                <li class="page-item <c:if test='${i == paging.current}'>active</c:if>">
                    <a class="page-link" href="${pageContext.request.contextPath}/security/main?pstartno=${i}">${i}</a>
                </li>
            </c:forEach>
            <c:if test="${paging.pagetotal > paging.end}">
                <li class="page-item">
                    <a class="page-link" href="${pageContext.request.contextPath}/security/main?pstartno=${paging.end + 1}">다음</a>
                </li>
            </c:if>
            <c:if test="${paging.current < paging.pagetotal}">
                <li class="page-item">
                    <a class="page-link" href="${pageContext.request.contextPath}/security/main?pstartno=${paging.current + 1}">&rsaquo;</a>
                </li>
            </c:if>
            <c:if test="${paging.current < paging.pagetotal}">
                <li class="page-item">
                    <a class="page-link" href="${pageContext.request.contextPath}/security/main?pstartno=${paging.pagetotal}">&raquo;</a>
                </li>
            </c:if>
        </ul>
    </div>

<%@ include file="../inc/footer.jsp"%>
