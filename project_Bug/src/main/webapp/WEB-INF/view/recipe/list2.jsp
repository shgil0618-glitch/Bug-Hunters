<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>
    /* 기존 list.jsp의 카드 스타일 유지/업데이트 */
    .recipe-card img {
        width: 100%;
        height: 200px;
        object-fit: cover;
        border-radius: 8px 8px 0 0; /* 상단만 둥글게 */
    }
    .recipe-card {
        border-radius: 10px;
        overflow: hidden;
        background: #fff;
        transition: transform .15s ease, box-shadow .15s ease;
        box-shadow: 0 1px 6px rgba(0,0,0,0.08);
        cursor: pointer; /* 클릭 가능 표시 */
        position: relative; /* 카테고리 라벨을 위한 기준 */
        display: flex; /* 내부 요소 높이 맞추기 */
        flex-direction: column;
        height: 100%; /* 부모(col) 높이에 맞춤 */
    }
    .recipe-card:hover {
        transform: translateY(-4px);
        box-shadow: 0 8px 24px rgba(0,0,0,0.12);
    }
    .category-btns .btn {
        margin-right: 6px;
        margin-bottom: 6px;
    }
    .status-msg {
        padding: 60px 0;
        text-align: center;
        color: #6c757d;
        font-size: 1.1rem;
        width: 100%;
    }
    #pagingArea a.page-link {
        cursor: pointer;
    }

    /* -------------------------------------------
    * main.jsp에서 가져온 스타일 추가
    * ------------------------------------------- */
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
        z-index: 10;
    }
    /* 모달 카테고리 라벨 */
    .modal-label {
        display: inline-block;
        padding: 5px 10px;
        border-radius: 5px;
        font-weight: bold;
        text-align: center;
        background-color: #fff8eb;
        margin-right: 8px;
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
    /* 모달 이미지 */
    .modal-img {
        max-width: 100%;
        height: auto;
        display: block;
        margin: 0px auto 15px; /* 하단 여백 추가 */
        border-radius: 8px;
    }
    /* 모달 본문 높이 제한 및 스크롤 */
    .modal-body {
        max-height: 70vh;
        overflow-y: auto;
    }
    /* 제목과 요리 정보 부분 */
    .recipe-info {
        display: flex;
        justify-content: space-between;
        font-size: small;
        color: #6c757d;
        margin-top: 5px;
    }
    .recipe-description {
        font-size: 0.9rem;
        color: #6c757d;
        overflow: hidden;
        display: -webkit-box;
        -webkit-line-clamp: 2; /* 2줄로 제한 */
        -webkit-box-orient: vertical;
        margin-top: 5px;
        margin-bottom: 10px;
    }
    .card-content {
        padding: 15px;
        flex-grow: 1; /* 카드 내용 영역이 남은 공간을 채우도록 */
    }
</style>

<div class="container mt-5">
    <h3 class="mb-4">레시피 목록</h3>

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

    <div class="row g-4" id="recipeCards">
        
        <c:forEach var="recipe" items="${list}">
            <div class="col-sm-6 col-md-4 col-lg-3 recipe-item">
                <div class="recipe-card h-100" 
                     data-bs-toggle="modal" 
                     data-bs-target="#recipeModal${recipe.recipeId}">
                    
                    <div class="category-label">
                        ${recipe.categoryName}
                    </div>

                    <c:choose>
                        <c:when test="${fn:startsWith(recipe.image, 'http')}">
                            <img src="${recipe.image}" alt="${recipe.title}">
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/upload/${recipe.image}" alt="${recipe.title}">
                        </c:otherwise>
                    </c:choose>
                    
                    <div class="card-content">
                        <h5 class="mb-1" style="font-size:1.1rem;">${recipe.title}</h5>
                        <p class="recipe-description">
                            <c:choose>
                                <c:when test="${fn:length(recipe.description) > 50}">
                                    ${fn:substring(recipe.description, 0, 50)}...
                                </c:when>
                                <c:otherwise>
                                    ${recipe.description}
                                </c:otherwise>
                            </c:choose>
                        </p>
                        
                        <div class="recipe-info">
                            <div class="d-flex justify-content-start small text-secondary">
                                <span>⏱ ${recipe.cookTime}분</span>
                                <span class="ms-3">👨‍🍳 ${recipe.servings}인분</span>
                                <span class="ms-3">👁 ${recipe.views}</span>
                            </div>
                        </div>

                        <div class="mt-2 text-start">
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
                        
                        <div class="text-end mt-2 small text-muted">by ${recipe.nickname}</div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="recipeModal${recipe.recipeId}" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">${recipe.title}</h5>
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
                            
                            <div class="mt-2 mb-3">
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
                            
                            <p>${recipe.description}</p>
                            <hr>
                            <p><strong>작성자 :</strong> ${recipe.nickname}</p>
                            <p><strong>조리 시간 :</strong> ${recipe.cookTime} 분</p>
                            <p><strong>인분 :</strong> ${recipe.servings} 인분</p>
                            <hr>
                            
                            <p><strong>재료</strong></p>
                            <pre>${recipe.ingredients}</pre>
                            
                            <c:forEach var="ingre" items="${recipe.ingredients}">
                           <a href="${pageContext.request.contextPath}/materialtitle?title=${ingre.ingreName}">${ingre.ingreName} - ${ingre.ingreNum}</a></li>
                            </c:forEach>
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
            </c:forEach>
        
        <c:if test="${empty list}">
            <div class="status-msg">등록된 레시피가 없습니다.</div>
        </c:if>

    </div>

    <div class="mt-4 text-center" id="pagingArea">
        <ul class="pagination justify-content-center">
            <c:if test="${paging.current > 1}">
                <li class="page-item"><a class="page-link" href="#" data-page="1">&laquo;</a></li>
            </c:if>
            <c:forEach var="i" begin="${paging.start}" end="${paging.end}">
                <li class="page-item ${i == paging.current ? 'active' : ''}">
                    <a class="page-link" href="#" data-page="${i}">${i}</a>
                </li>
            </c:forEach>
            <c:if test="${paging.current < paging.pagetotal}">
                <li class="page-item"><a class="page-link" href="#" data-page="${paging.pagetotal}">&raquo;</a></li>
            </c:if>
        </ul>
    </div>

</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    const ctx = "${pageContext.request.contextPath}";
    let currentCategory = "전체"; // 현재 선택된 카테고리
    
    // ----------------------------------------------------
    // [핵심 함수] - 검색 및 목록 조회
    // ----------------------------------------------------
    
    // AJAX 검색 로직을 실행하는 핵심 함수
    function runSearch(page) {
        let keyword = $("#search").val().trim();
        
        $.ajax({
            url: ctx + "/recipe/searchBothpaging", 
            type: "GET",
            data: { 
                category: currentCategory, 
                keyword: keyword,
                pstartno: page // 페이지 번호 전달
            },
            dataType: "json",
            success: function(res) {
                // 서버에서 받은 데이터 구조: res = { list: [...], paging: {...} }
                console.log(res);
                const recipeList = res.list;
                const paging = res.paging;
                
                // 1. 기존 리스트 지우기
                $("#recipeCards").empty();
                
                // 2. 결과 없음 처리
                if(recipeList.length === 0) {
                    $("#recipeCards").append('<div class="status-msg">해당 조건의 레시피가 없습니다.</div>');
                    $("#pagingArea").empty().hide(); // 페이징 영역도 숨김
                    return;
                }
                
                // 3. 결과 목록 생성 (append 방식)
                $.each(recipeList, function(index, dto) {
                    let imgPath = "";
                    let defaultImg = "default-recipe.png"; // 기본 이미지 파일명
                    
                    if(dto.image && dto.image.startsWith("http")) {
                        imgPath = dto.image;
                    } else {
                        // 이미지가 없거나 서버에 저장된 경우의 경로
                        let fileName = dto.image ? dto.image : defaultImg; 
                        imgPath = ctx + "/upload/" + fileName;
                    }

                    // 난이도 클래스 결정
                    let difficultyClass = "";
                    if (dto.difficulty === '쉬움') {
                        difficultyClass = 'difficulty-easy';
                    } else if (dto.difficulty === '보통') {
                        difficultyClass = 'difficulty-medium';
                    } else if (dto.difficulty === '어려움') {
                        difficultyClass = 'difficulty-hard';
                    }

                    // 설명 50자 제한 처리
                    let description = dto.description || '';
                    if (description.length > 50) {
                        description = description.substring(0, 50) + '...';
                    }
                    
                    // ⭐️ 모달을 띄우는 형태로 HTML 구조 변경
                    let html = `
                        <div class="col-sm-6 col-md-4 col-lg-3 recipe-item">
                            <div class="recipe-card h-100" 
                                 data-bs-toggle="modal" 
                                 data-bs-target="#recipeModal\${dto.recipeId}">
                                 
                                <div class="category-label">
                                    \${dto.categoryName}
                                </div>
                                <img src="\${imgPath}" alt="\${dto.title}">
                                
                                <div class="card-content">
                                    <h5 class="mb-1" style="font-size:1.1rem;">\${dto.title}</h5>
                                    <p class="recipe-description">\${description}</p>
                                    
                                    <div class="recipe-info">
                                        <div class="d-flex justify-content-start small text-secondary">
                                            <span>⏱ \${dto.cookTime}분</span>
                                            <span class="ms-3">👨‍🍳 \${dto.servings}인분</span>
                                            <span class="ms-3">👁 \${dto.views}</span>
                                        </div>
                                    </div>
                                    
                                    <div class="mt-2 text-start">
                                        <span class="difficulty-box \${difficultyClass}">\${dto.difficulty}</span>
                                    </div>

                                    <div class="text-end mt-2 small text-muted">by \${dto.nickname}</div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="modal fade" id="recipeModal\${dto.recipeId}" tabindex="-1" aria-hidden="true">
                            <div class="modal-dialog modal-lg modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">\${dto.title}</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
                                    </div>
                                    <div class="modal-body">
                                        <img src="\${imgPath}" alt="\${dto.title}" class="modal-img"/>
                                        
                                        <div class="mt-2 mb-3">
                                            <span class="modal-label">\${dto.categoryName}</span>
                                            <span class="difficulty-box \${difficultyClass}">\${dto.difficulty}</span>
                                        </div>
                                        
                                        <p>\${dto.description}</p>
                                        <hr>
                                        <p><strong>작성자 :</strong> \${dto.nickname}</p>
                                        <p><strong>조리 시간 :</strong> \${dto.cookTime} 분</p>
                                        <p><strong>인분 :</strong> \${dto.servings} 인분</p>
                                        <hr>
                                        <p><strong>재료</strong></p>
                                        <pre>\${dto.ingredients}</pre>
                                        <hr>
                                        <p><strong>조리 방법</strong></p>
                                        <pre>\${dto.instructions}</pre>
                                        
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    `;
                    $("#recipeCards").append(html);
                });
                
                // 4. 페이징 영역 동적 생성 및 표시
                renderPaging(paging);
            },
            error: function(err) {
                console.error("AJAX 에러:", err);
                $("#recipeCards").empty().append('<div class="status-msg">데이터를 불러오는 중 오류가 발생했습니다.</div>');
                $("#pagingArea").empty().hide();
            }
        });
    }

    // 페이징 버튼을 동적으로 생성하는 함수 (변동 없음)
    function renderPaging(paging) {
        if (!paging || paging.pagetotal <= 0) {
            $("#pagingArea").empty().hide();
            return;
        }
        
        let ul = $('<ul class="pagination justify-content-center"></ul>');
        
        // 1. 처음 버튼 (<<)
        if (paging.current > 1) {
            ul.append($('<li class="page-item"><a class="page-link" href="#" data-page="1">&laquo;</a></li>'));
        }

        // 2. 페이지 번호 버튼 (1, 2, 3...)
        if (paging.start > 0 && paging.end >= paging.start) {
            for (let i = paging.start; i <= paging.end; i++) {
                let activeClass = i === paging.current ? ' active' : '';
                let li = $(`<li class="page-item ${activeClass}"></li>`);
                li.append($(`<a class="page-link" href="#" data-page="${i}">\${i}</a>`));
                ul.append(li);
            }
        } else if (paging.pagetotal === 1) {
            ul.append($('<li class="page-item active"><a class="page-link" href="#" data-page="1">1</a></li>'));
        }

        // 3. 끝 버튼 (>>)
        if (paging.current < paging.pagetotal) {
            ul.append($(`<li class="page-item"><a class="page-link" href="#" data-page="${paging.pagetotal}">&raquo;</a></li>`));
        }

        $("#pagingArea").empty().append(ul).show();
    }
    
    // ----------------------------------------------------
    // [이벤트 바인딩]
    // ----------------------------------------------------

    $(function(){
        
        // 1. 카테고리 버튼 클릭 (변동 없음)
        $(".filter-btn").on("click", function(e){
            e.preventDefault(); 
            $(".filter-btn").removeClass("active");
            $(this).addClass("active");
            
            currentCategory = $(this).data("filter"); 
            runSearch(1);
        });

        // 2. 키워드 입력 (keyup) (변동 없음)
        $("#search").on("keyup", function(){
            runSearch(1);
        });
        
        // 3. 페이징 버튼 클릭 이벤트 처리 (변동 없음)
        $(document).on("click", "#pagingArea .page-link", function(e){
            e.preventDefault();
            const page = $(this).data("page"); 
            
            if (page) {
                runSearch(page);
            }
        });
        
    });
</script>

<%@ include file="../inc/footer.jsp"%>