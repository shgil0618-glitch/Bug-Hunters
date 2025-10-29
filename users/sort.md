홈  
├─ 메뉴 추천  
│  ├ 오늘의 추천 (AI 맞춤)  
│  ├ 재료 기반 추천  
│  └ 미니게임형 추천 (룰렛/운세)  
├─ 음식 검색  
│  ├ 키워드/카테고리 검색  
│  └ 음식 상세보기  
├─ 커뮤니티  
│  ├ 레시피 공유  
│  └ 리뷰 게시판  
├─ 통계 대시보드  
│  ├ 나의 식습관 분석  
│  └ 영양 시각화 그래프  
├─ 마이페이지  
│  ├ 내 기록 / 북마크  
│  └ 프로필 설정  
└─ 로그인 / 회원가입




---

#### 💡 **PROJECT1** 메뉴 추천 웹페이지 (기초 CRUD 중심)

**기술 스택**: JSP + Oracle
**기능**: 음식·재료·사용자 선호 정보 CRUD 및 기본 추천 관리

> 프론트엔드보다는 **데이터 중심 설계 / 실무형 CRUD 구조**에 초점을 둔 JSP 프로젝트 구성

---

### ✅ **기능 구성**

1. JSP + Oracle: **기초 CRUD + 조건 기반 데이터 조회**
2. 사용자 입력 기반의 추천 데이터를 DB에서 직접 조회
3. Ajax나 REST 없이 JSP 내에서 form 기반 요청 처리

---

### 📦 **주요 구현 가능 기능**

| 기능 구분                 | 설명                       | JSP + Oracle로 구현 가능 여부           | 난이도 |
| --------------------- | ------------------------ | -------------------------------- | --- |
| 🔸 **회원가입 / 로그인**     | 회원 등록, 로그인/로그아웃, 회원정보 수정 | ✅ 가능                             | 중   |
| 🔸 **음식 정보 등록/수정/삭제** | 음식명, 영양정보, 카테고리 CRUD     | ✅ 가능                             | 하   |
| 🔸 **재료 정보 관리**       | 재료 테이블 생성 및 음식-재료 매핑     | ✅ 가능                             | 중   |
| 🔸 **메뉴 추천(조건 검색형)**  | 선호 카테고리나 재료 입력 후 음식 검색   | ✅ 가능 (단순 SQL 조건문 기반)             | 중   |
| 🔸 **레시피 게시판**        | 게시글 CRUD + 댓글 테이블        | ✅ 가능                             | 하   |
| 🔸 **리뷰/평가 관리**       | 음식ID 기준 후기 등록 및 평균 평점 계산 | ✅ 가능                             | 중   |
| 🔸 **통계 데이터 저장**      | 추천 내역을 Log 테이블에 기록       | ✅ 가능 (SELECT + COUNT + GROUP BY) | 중   |
| 🔸 **관리자용 데이터 관리**    | 음식, 재료, 회원 데이터 관리 JSP    | ✅ 가능                             | 중   |
| ⚪ **AI 기반 추천**        | 행동 로그 기반 ML 추천           | ❌ JSP 단독 불가 (Python/ML 필요)       | -   |
| ⚪ **그래프 시각화**         | JSP 내 단독 구현 어려움 (JS 필요)  | ⚠️ 부분 가능                         | -   |
| ⚪ **미니게임 / 인터랙션형 UI** | 룰렛, 애니메이션 등              | ❌ (JS/Canvas 필요)                 | -   |

---

### 🗂 **주요 테이블 설계 예시**

| 테이블명                 | 설명                                                          |
| -------------------- | ----------------------------------------------------------- |
| `USER_TB`            | 사용자 기본정보 (user_id, pw, nickname, join_date 등)               |
| `FOOD_TB`            | 음식 정보 (food_id, name, category, kcal, protein, carb, fat 등) |
| `INGREDIENT_TB`      | 재료 정보 (ingredient_id, name, type, allergy_flag 등)           |
| `FOOD_INGREDIENT_TB` | 음식-재료 매핑 테이블 (food_id, ingredient_id)                       |
| `RECIPE_TB`          | 사용자 업로드 레시피 (recipe_id, title, content, user_id, date)      |
| `REVIEW_TB`          | 음식 리뷰 (review_id, food_id, user_id, rating, comment, date)  |
| `PREFERENCE_TB`      | 사용자 선호도 (user_id, taste_type, allergy_info 등)               |
| `RECOMMEND_LOG_TB`   | 추천 히스토리 (user_id, food_id, date, source_type)               |

---

### 💡 **아이디어 (JSP+Oracle 한정 확장 포인트)**

1. **조건 기반 추천**

   * SQL WHERE문 조합으로:

     ```sql
     SELECT * FROM FOOD_TB 
     WHERE kcal < 600 
     AND category = '한식' 
     AND food_id NOT IN (SELECT food_id FROM RECOMMEND_LOG_TB WHERE user_id = ?)
     ```
   * 단순하지만 “맞춤형 추천” 흉내 가능

2. **레시피 & 리뷰 게시판 연동**

   * FOOD_TB 외래키 기반으로 게시글/후기 연결
   * JSP 페이지에서 음식 상세보기 → 후기 목록 JOIN 조회

3. **추천 로그 기반 인기 메뉴 TOP5**

   * Oracle GROUP BY / ORDER BY로 집계

     ```sql
     SELECT food_id, COUNT(*) AS cnt 
     FROM RECOMMEND_LOG_TB 
     GROUP BY food_id 
     ORDER BY cnt DESC FETCH FIRST 5 ROWS ONLY;
     ```

4. **알러지 필터링 검색**

   * USER_TB의 allergy_info와 INGREDIENT_TB 비교
   * SQL에서 제외 조건으로 처리

5. **관리자 페이지 확장 가능성**

   * 음식 / 재료 / 사용자 데이터 관리 JSP
   * 단순 CRUD로 실무 관리 시스템 설계 감각 어필 가능

---

### 🔧 **예상 폴더 구조 (JSP 기준)**

```
📁 WebContent
├─ index.jsp
├─ login.jsp / signup.jsp
├─ food
│  ├─ foodList.jsp
│  ├─ foodDetail.jsp
│  ├─ foodForm.jsp (등록/수정)
├─ recipe
│  ├─ recipeList.jsp
│  ├─ recipeForm.jsp
│  ├─ recipeDetail.jsp
├─ review
│  ├─ reviewList.jsp
│  ├─ reviewForm.jsp
├─ mypage
│  ├─ myInfo.jsp
│  ├─ myHistory.jsp
├─ admin
│  ├─ adminFood.jsp
│  ├─ adminUser.jsp
│  └─ adminReview.jsp
📁 src
├─ dao / dto / service
📁 sql
└─ create_tables.sql
```

---

### 📈 **요약**

| 구분                | JSP + Oracle 가능 범위 | 비고                  |
| ----------------- | ------------------ | ------------------- |
| 🔹 기본 CRUD        | ✅                  | 모든 주요 테이블 적용 가능     |
| 🔹 조건 검색 추천       | ✅                  | SQL 기반 추천 로직        |
| 🔹 사용자/리뷰 시스템     | ✅                  | JOIN + INSERT 중심    |
| 🔹 통계 집계          | ✅                  | Oracle GROUP BY로 처리 |
| 🔹 시각화/AI/게임형     | ❌                  | JS or Python 연동 필요  |
| 🔹 REST API 기반 구조 | ❌                  | JSP 단독 프로젝트에서는 비추천  |

---

---

# 💾 **1️⃣ ERD (Entity Relationship Diagram)**

> JSP + Oracle로 구현 가능한 데이터 중심 구조

```
[USER_TB]
└ user_id (PK)
└ user_pw
└ nickname
└ allergy_info
└ taste_type
└ join_date

[FOOD_TB]
└ food_id (PK)
└ name
└ category
└ kcal
└ protein
└ carb
└ fat
└ cooking_method
└ image_url

[INGREDIENT_TB]
└ ingredient_id (PK)
└ name
└ type
└ allergy_flag

[FOOD_INGREDIENT_TB]
└ food_id (FK → FOOD_TB)
└ ingredient_id (FK → INGREDIENT_TB)
★ 다대다 관계 연결 테이블

[RECIPE_TB]
└ recipe_id (PK)
└ title
└ content
└ user_id (FK → USER_TB)
└ food_id (FK → FOOD_TB)
└ created_at

[REVIEW_TB]
└ review_id (PK)
└ food_id (FK → FOOD_TB)
└ user_id (FK → USER_TB)
└ rating (1~5)
└ comment
└ created_at

[PREFERENCE_TB]
└ user_id (FK → USER_TB)
└ taste_type
└ diet_goal
└ updated_at

[RECOMMEND_LOG_TB]
└ log_id (PK)
└ user_id (FK → USER_TB)
└ food_id (FK → FOOD_TB)
└ source_type (AI / ingredient / manual)
└ created_at
```

### 🔗 **ERD 관계 요약**

| 관계                                 | 설명                             |
| ---------------------------------- | ------------------------------ |
| `USER_TB` 1 ─ N `RECIPE_TB`        | 유저별 레시피 등록                     |
| `USER_TB` 1 ─ N `REVIEW_TB`        | 유저별 리뷰 작성                      |
| `FOOD_TB` 1 ─ N `RECIPE_TB`        | 음식과 관련 레시피 연결                  |
| `FOOD_TB` 1 ─ N `REVIEW_TB`        | 음식별 리뷰                         |
| `FOOD_TB` N ─ M `INGREDIENT_TB`    | 다대다 → `FOOD_INGREDIENT_TB`로 연결 |
| `USER_TB` 1 ─ 1 `PREFERENCE_TB`    | 개인 선호도                         |
| `USER_TB` 1 ─ N `RECOMMEND_LOG_TB` | 추천 히스토리 기록                     |

---

# 🧭 **2️⃣ 기능 흐름도 (UX / JSP 페이지 이동 구조)**

> JSP 기반 페이지 이동 중심으로 설계 (Controller 없이 JSP Form Action으로 처리하는 구조 기준)

```
[홈.jsp]
 ├─▶ [로그인.jsp]
 │     ├─ 로그인 성공 → [홈.jsp] 리다이렉트
 │     └─ 회원가입 버튼 → [회원가입.jsp]
 │
 ├─▶ [메뉴추천.jsp]
 │     ├─ 조건 입력 (taste / kcal / category)
 │     └─ ▶ [추천결과.jsp] (DB 조건검색 결과)
 │
 ├─▶ [음식검색.jsp]
 │     ├─ 검색어 입력 → Oracle SELECT LIKE 쿼리
 │     └─ ▶ [음식상세.jsp]
 │           ├─ 음식정보 조회
 │           ├─ “레시피 보기” → [레시피상세.jsp]
 │           └─ “리뷰 보기” → [리뷰목록.jsp]
 │
 ├─▶ [커뮤니티.jsp]
 │     ├─ 레시피 공유 ▶ [레시피목록.jsp]
 │     │                   ├─ [레시피작성.jsp]
 │     │                   └─ [레시피상세.jsp]
 │     └─ 리뷰 게시판 ▶ [리뷰목록.jsp]
 │                         └─ [리뷰작성.jsp]
 │
 ├─▶ [통계대시보드.jsp]
 │     ├─ 인기 음식 TOP5 (GROUP BY + COUNT)
 │     ├─ 나의 리뷰 수 / 등록 레시피 수
 │     └─ 나의 추천 로그 (최근 10개)
 │
 └─▶ [마이페이지.jsp]
       ├─ [내정보.jsp] (회원정보 수정)
       ├─ [내레시피.jsp] (본인 작성글 관리)
       ├─ [내리뷰.jsp]
       └─ [추천기록.jsp]
```

---

# 📑 **3️⃣ 페이지별 주요 기능 흐름 요약**

| 페이지                | 주요 기능               | DB 연동 방식                |
| ------------------ | ------------------- | ----------------------- |
| **홈.jsp**          | 주요 메뉴 링크, 오늘의 추천 버튼 | -                       |
| **로그인.jsp**        | 사용자 인증              | SELECT + SESSION        |
| **회원가입.jsp**       | 신규 유저 등록            | INSERT                  |
| **메뉴추천.jsp**       | 조건 기반 추천 입력         | FORM 전송                 |
| **추천결과.jsp**       | 조건 기반 음식 조회         | SELECT + WHERE          |
| **음식검색.jsp**       | 키워드 검색              | SELECT LIKE             |
| **음식상세.jsp**       | 음식 상세정보 표시          | SELECT JOIN             |
| **레시피목록.jsp**      | 사용자 레시피 목록          | SELECT ORDER BY         |
| **레시피작성.jsp**      | 레시피 등록              | INSERT                  |
| **리뷰목록.jsp**       | 리뷰 목록 / 평균 평점       | SELECT + AVG            |
| **리뷰작성.jsp**       | 리뷰 등록               | INSERT                  |
| **통계대시보드.jsp**     | 집계 데이터 시각화          | SELECT + COUNT/GROUP BY |
| **마이페이지.jsp**      | 내정보 수정 / 로그보기       | UPDATE + SELECT         |
| **관리자.jsp** *(선택)* | 음식, 회원, 리뷰 관리       | CRUD 전체                 |

---

# ⚙️ **4️⃣ Oracle 주요 쿼리 샘플**

**(1) 추천 음식 조회 쿼리**

```sql
SELECT f.food_id, f.name, f.kcal, f.category
FROM FOOD_TB f
JOIN FOOD_INGREDIENT_TB fi ON f.food_id = fi.food_id
WHERE f.kcal < 600
  AND fi.ingredient_id NOT IN (
    SELECT ingredient_id 
    FROM INGREDIENT_TB 
    WHERE allergy_flag = 'Y'
  )
ORDER BY DBMS_RANDOM.VALUE;
```

**(2) 인기 메뉴 통계**

```sql
SELECT f.name, COUNT(*) AS cnt
FROM RECOMMEND_LOG_TB r
JOIN FOOD_TB f ON r.food_id = f.food_id
GROUP BY f.name
ORDER BY cnt DESC FETCH FIRST 5 ROWS ONLY;
```

---

