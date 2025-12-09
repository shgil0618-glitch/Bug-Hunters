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
	/* 검색/카테고리 영역 상단 고정 */
	  .search-category-bar {
	    position: sticky;
	    top: 0;               /* 화면 최상단에 붙음 */
	    z-index: 1000;        /* 다른 요소보다 위에 표시 */
	    background-color: #4F3C1B; /* 배경색 지정 */
	    padding: 15px;
	    border-radius:10px;
	    margin-bottom:10px;
	    box-shadow: 0 2px 6px rgba(0,0,0,0.1); /* 살짝 그림자 */
		.search-category-bar label,
		.search-category-bar .form-label,
		.search-category-bar .btn,
		.search-category-bar input {
		  color: #fff; /* 글자색 흰색으로 */
		}
		
		<style>
  /* 카테고리 버튼 영역 */
  .category-btns .btn {
      margin-right: 6px;
      /* margin-bottom: 6px; */
  }

  /* 버튼 활성화/선택 상태 */
  .btn-check:checked+.btn,
  .btn.active,
  .btn.show,
  .btn:first-child:active,
  :not(.btn-check)+.btn:active {
      color: var(--bs-btn-active-color);
      background-color: var(--bs-btn-active-bg);
      border-color: #198754;
  }

  /* 버튼 기본 상태 */
  [type=button]:not(:disabled),
  [type=reset]:not(:disabled),
  [type=submit]:not(:disabled),
  button:not(:disabled) {
      cursor: pointer;
  }

  /* 아웃라인 세컨더리 버튼 커스텀 */
  .btn-outline-secondary {
      --bs-btn-color: #fff;
      --bs-btn-border-color: #fff;
      --bs-btn-hover-color: #212529;
      --bs-btn-hover-bg: #6c757d;
      --bs-btn-hover-border-color: #6c757d;
      --bs-btn-focus-shadow-rgb: 108, 117, 125;
      --bs-btn-active-color: #212529;
      --bs-btn-active-bg: #fff3cd;
      --bs-btn-active-border-color: #6c757d;
      --bs-btn-active-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.125);
      --bs-btn-disabled-color: #6c757d;
      --bs-btn-disabled-bg: transparent;
      --bs-btn-disabled-border-color: #6c757d;
      --bs-gradient: none;
  }
</style>
		

<div class="container mt-5">
  <h3 class="mb-4">레시피 목록 (<span id="recipeCount">불러오는 중...</span>개)</h3>

  <!-- 검색 + 카테고리 영역을 sticky로 묶음 -->
  <div class="search-category-bar">
    <div class="mb-3 alert alert-light border">
      <label for="search" class="form-label fw-bold">SEARCH RECIPE</label>
      <div class="row">
        <div class="col-md-9">
          <input type="search" class="form-control" id="search" 
                 placeholder="검색어를 입력하세요 (비워두면 전체 출력)">
        </div>
        <c:if test="${not empty loginUser}">
        <div class="col-md-3 text-end">
          <button class="btn btn-success w-100" 
                  onclick="location.href='${pageContext.request.contextPath}/recipe/register'">
            레시피 등록
          </button>
        </div>
        </c:if>
      </div>
    </div>

    <div class="mb-2 category-btns">
      <button class="btn btn-outline-secondary filter-btn active" data-filter="전체">전체</button>
      <button class="btn btn-outline-secondary filter-btn" data-filter="한식">한식</button>
      <button class="btn btn-outline-secondary filter-btn" data-filter="양식">양식</button>
      <button class="btn btn-outline-secondary filter-btn" data-filter="중식">중식</button>
      <button class="btn btn-outline-secondary filter-btn" data-filter="일식">일식</button>
      <button class="btn btn-outline-secondary filter-btn" data-filter="디저트">디저트</button>
      <button class="btn btn-outline-secondary filter-btn" data-filter="건강식">건강식</button>
      <button class="btn btn-outline-secondary filter-btn" data-filter="기타">기타</button>
    </div>
  </div>

  <!-- 레시피 카드 영역 -->
  <div class="row g-4" id="recipeCards">
    <div class="status-msg">레시피 목록을 불러오는 중입니다...</div>
  </div>

  <!-- 페이징 영역 -->
  <div class="mt-4 text-center" id="pagingArea"></div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    // ----------------------------------------------------
    // [전역 변수]
    // ----------------------------------------------------
    const ctx = "${pageContext.request.contextPath}";
    let currentCategory = "전체"; // 현재 선택된 카테고리
    let currentPage = 1;          // 현재 페이지 번호

    // ----------------------------------------------------
    // [핵심 함수] - 검색 및 목록 조회 (AJAX)
    // ----------------------------------------------------

    /**
     * AJAX 검색 로직을 실행하는 핵심 함수
     * @param {number} page - 요청할 페이지 번호
     */
    function runSearch(page) {
        currentPage = page; // 현재 페이지 업데이트
        let keyword = $("#search").val().trim();
        
        // 검색 결과를 로드하는 동안 로딩 상태 표시
        $("#recipeCards").html('<div class="status-msg"><i class="fas fa-spinner fa-spin"></i> 레시피를 불러오는 중...</div>');

        $.ajax({
            // 서버의 레시피 검색 API 경로로 설정
            url: ctx + "/recipe/searchBothpaging", 
            type: "GET",
            data: { 
                category: currentCategory, 
                keyword: keyword,
                pstartno: currentPage // 페이지 번호 전달
                //page: currentPage
            },
            dataType: "json",
            success: function(res) {
                // 서버에서 받은 데이터 구조: res = { list: [...], paging: {...} }
                console.log(res);
                // AJAX 응답에서는 recipe 객체가 아닌 dto로 간주하고 처리합니다.
                const recipeList = res.list || []; // null 대비
                const paging = res.paging;
                
                // 1. 목록 렌더링
                renderRecipeList(recipeList);
                
                // 2. 페이징 렌더링
                renderPaging(paging);
                
                // 3. 전체 개수 업데이트
                //$("#recipeCount").text(paging.totalcount || 0); 
                $("#recipeCount").text(paging.listtotal || 0);
                
            },
            error: function(err) {
                console.error("AJAX 에러:", err);
                $("#recipeCards").empty().append('<div class="status-msg">데이터를 불러오는 중 오류가 발생했습니다. 서버 연결 또는 API 경로를 확인하세요.</div>');
                $("#pagingArea").empty().hide();
                $("#recipeCount").text("0");
            }
        });
    }

    /**
     * 레시피 목록 (카드)을 동적으로 생성하는 함수
     * list.jsp의 JSTL 부분에서 사용했던 'recipe' 객체 대신, AJAX 응답에서 받은 'dto' 객체를 사용합니다.
     * @param {Array} recipeList - 레시피 데이터 배열
     */
    function renderRecipeList(recipeList) {
        $("#recipeCards").empty();

        // 1. 결과 없음 처리
        if(recipeList.length === 0) {
            $("#recipeCards").append('<div class="status-msg">해당 조건의 레시피가 없습니다.</div>');
            return;
        }

        // 2. 결과 목록 생성
        $.each(recipeList, function(index, dto) { // list.jsp의 c:forEach에서 recipe를 사용했지만, AJAX에선 dto 사용
            // 이미지 경로 처리
            let imgPath = "";
            const defaultImg = "default-recipe.png"; // 기본 이미지 파일명
            
            if(dto.image && dto.image.startsWith("http")) {
                imgPath = dto.image;
            } else {
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
            
            // ⭐️ 카드 HTML 및 모달 HTML 생성 (list.jsp에서 recipe.xxx로 사용했던 것을 dto.xxx로 변경)
            // list.jsp의 JSTL 부분과 구조적으로 동일하게 작성해야 CSS가 적용됩니다.
            let html = `
                <div class="col-sm-6 col-md-4 col-lg-3 recipe-item">
                    <div class="recipe-card h-100 shadow-sm position-relative" 
                            data-bs-toggle="modal" 
                            data-bs-target="#recipeModal\${dto.recipeId}"
                            style="cursor:pointer;">
                            
                        <div class="category-label">\${dto.categoryName}</div>
                        <img src="\${imgPath}" alt="\${dto.title}" class="recipe-img">
                        
                        <div class="card-content">
                            <h5 class="mb-1 recipe-title">\${dto.title}</h5>
                            <p class="recipe-description">\${description}</p>
                            
                            <div class="recipe-info d-flex justify-content-start small text-secondary">
                                <span>⏱ \${dto.cookTime}분</span>
                                <span class="ms-3">👨‍🍳 \${dto.servings}인분</span>
                                <span class="ms-3">👁 \${dto.views}</span>
                            </div>
                            
                            <div class="mt-2 text-start">
                                <span class="difficulty-box \${difficultyClass}">\${dto.difficulty}</span>
                            </div>

                            <div class="text-end mt-2 small text-muted">by \${dto.nickname}</div>
                        </div>
                    </div>
                </div>
                
                <div class="modal fade" id="recipeModal\${dto.recipeId}" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog modal-md modal-dialog-centered">
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
                                <p><strong>재료</strong>
                                <span id="ingredients-\${dto.recipeId}"></span>
                                </p>
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
    }

    /**
     * 페이징 버튼을 동적으로 생성하는 함수 (list.jsp에 있던 JS 코드 그대로 유지)
     * @param {Object} paging - 페이징 정보 객체 (current, pagetotal, start, end, totalcount 등 포함)
     */
    function renderPaging(paging) {
        $("#pagingArea").empty();

        if (!paging || paging.pagetotal <= 1) { // 전체 페이지가 1개 이하면 페이징 숨김
            $("#pagingArea").hide();
            return;
        }
        
        let ul = $('<ul class="pagination justify-content-center"></ul>');
        
        // 이전 블록/첫 페이지 버튼
        if (paging.start > 1) {
            // 첫 페이지 (<<)
            ul.append($('<li class="page-item"><a class="page-link" href="#" data-page="1" aria-label="First">&laquo;</a></li>'));
            // 이전 블록 (...)
            ul.append($('<li class="page-item"><a class="page-link" href="#" data-page="' + (paging.start - 1) + '" aria-label="Previous block">...</a></li>'));
        } else if (paging.current > 1) { // 첫 블록에 있어도 현재 페이지가 1이 아니면 이전 버튼 표시
            ul.append($('<li class="page-item"><a class="page-link" href="#" data-page="' + (paging.current - 1) + '" aria-label="Previous">&lt;</a></li>'));
        }

        // 페이지 번호 버튼 (1, 2, 3...)
        for (let i = paging.start; i <= paging.end; i++) {
            let activeClass = i === paging.current ? ' active' : '';
            let li = $(`<li class="page-item \${activeClass}"></li>`);
            li.append($(`<a class="page-link" href="#" data-page="\${i}">\${i}</a>`));
            ul.append(li);
        }
        
        // 다음 블록/마지막 페이지 버튼
        if (paging.end < paging.pagetotal) {
            // 다음 블록 (...)
            ul.append($('<li class="page-item"><a class="page-link" href="#" data-page="' + (paging.end + 1) + '" aria-label="Next block">...</a></li>'));
            // 마지막 페이지 (>>)
            ul.append($(`<li class="page-item"><a class="page-link" href="#" data-page="\${paging.pagetotal}" aria-label="Last">&raquo;</a></li>`));
        } else if (paging.current < paging.pagetotal) { // 마지막 블록에 있어도 현재 페이지가 마지막 페이지가 아니면 다음 버튼 표시
            ul.append($('<li class="page-item"><a class="page-link" href="#" data-page="' + (paging.current + 1) + '" aria-label="Next">&gt;</a></li>'));
        }

        $("#pagingArea").append(ul).show();
    }
    
    // ----------------------------------------------------
    // [이벤트 바인딩]
    // ----------------------------------------------------

    $(function(){
        
        // 1. 초기 로딩 시 목록 조회
        runSearch(1);
        
        // 2. 카테고리 버튼 클릭
        $(".filter-btn").on("click", function(e){
            e.preventDefault(); 
            $(".filter-btn").removeClass("active");
            $(this).addClass("active");
            
            currentCategory = $(this).data("filter"); 
            runSearch(1); // 카테고리 변경 시 1페이지로 이동
        });

        // 3. 키워드 입력 (keyup)
        $("#search").on("keyup", function(){
            runSearch(1); // 검색어 입력 시 1페이지로 이동
        });
        
        // 4. 페이징 버튼 클릭 이벤트 처리
        $(document).on("click", "#pagingArea .page-link", function(e){
            e.preventDefault();
            const page = $(this).data("page"); 
            
            if (page) {
                runSearch(page); // 해당 페이지로 이동
            }
        });
        
    });
</script>
<script>
//모달이 열릴 때 재료 로드
$(document).on("shown.bs.modal", ".modal", function () {
    const recipeId = $(this).attr("id").replace("recipeModal", "");
    loadIngredients(parseInt(recipeId));
});
                function loadIngredients(recipeId) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/materialsearch",
                        type: "GET",
                        data: { recipeId: recipeId },
                        success: function(data) {
                        	let target = "#ingredients-" + recipeId;
                            let arr=data.result.ingredients;
                            for(let i=0; i<arr.length ;i++){
                            
                               console.log(i + "받아온 데이터:", data.result.ingredients[i].ingreName , data.result.ingredients[i].ingreNum);
                               
                               $(target).append(
                            		    "<div>" +
                            		        "<a href='${pageContext.request.contextPath}/materialtitle?title="
                            		            + encodeURIComponent(arr[i].ingreName) + 
                            		        "' target='_blank' rel='noopener noreferrer'>" +
                            		            arr[i].ingreName + " - " + arr[i].ingreNum +
                            		        "</a>" +
                            		    "</div>"
                            		);
                            }    
                        },
                        error: function() {
                            $("#ingredients-" + recipeId).html("불러오기 실패");
                        }
                    });
                }
                </script>
<%@ include file="../inc/footer.jsp"%>