안녕하세요 버그 헌터즈 test2입니다.

|no|역할|소개|
|-|-|-|
|01|팀장|안녕하세요 결석하지 못하는 길상현 입니다.|
|02|팀원|안녕하세요 강서현 입니다. ♥|
|03|팀원|안녕하세요 김정민 입니다. 🕳|
|04|팀원|안녕하세요 김영민 입니다.|
|05|팀원|안녕하세요 유희재 입니다.|
|06|팀원|안녕하세요. 불꽃마법사입니다.🔥🔥🔥🔥🔥 |  


|no|이름|메시지|
|-|-|-|
|01|길상현|원트원클~!|
|02|강서현|모두 한 번에 붙었으면 좋겠습니다!|
|03|김정민|화이팅.|
|04|김영민|모두 화이팅해서 합격 했으면 좋겠습니다!!|
|05|유희재|두가자~!|


ps) 자격증응시료 생각보다 비싸네요 돈 땅바닥에 버리지 맙시다....😥😥



★ 자격증 공부
|no|이름|공부완료 현황|
|-|-|-|
|01|길상현|✅ day001 완|
|02|유희재|✅ day001 완|
|03|김정민|✅ day001 완|
|04|김영민|✅ day001 완|
||||
||||



## 👥 **Oracle + JSP 기반 5인 역할 분담 (커뮤니티·마이페이지 제외 버전)**

| 팀원                                | 담당 영역                             | 주요 구현 기능                                                                                                                            | 핵심 DB 테이블                                         | 주요 JSP 페이지                                             |
| --------------------------------- | --------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------- | ------------------------------------------------------ |
| **1️⃣ 메뉴 추천 담당 (추천 로직 메인)**       | **오늘의 추천 / 재료 기반 추천 / 룰렛형 추천 구현** | - 사용자 입력 조건(kcal, category, taste) 기반 SQL 작성<br>- `RECOMMEND_LOG_TB`에 추천 결과 기록<br>- 랜덤 추천(`DBMS_RANDOM.VALUE`) 구현<br>- 추천 결과 페이지 구성 | `FOOD_TB`, `PREFERENCE_TB`, `RECOMMEND_LOG_TB`    | `recommend.jsp`, `recommendResult.jsp`, `roulette.jsp` |
| **2️⃣ 음식 등록·관리 담당 (관리자/기초 CRUD)** | **음식 및 재료 데이터 CRUD 관리**           | - 음식 등록/수정/삭제 기능 구현<br>- 음식-재료 매핑(`FOOD_INGREDIENT_TB`) 처리<br>- Oracle 제약조건 / FK 설계<br>- 관리자용 JSP 페이지 구성                            | `FOOD_TB`, `INGREDIENT_TB`, `FOOD_INGREDIENT_TB`  | `foodForm.jsp`, `ingredientForm.jsp`, `adminFood.jsp`  |
| **3️⃣ 음식 검색 담당 (검색 기능 메인)**       | **키워드/카테고리 기반 검색 구현**             | - `LIKE`, `WHERE` 조건 검색 SQL 작성<br>- 음식 카테고리별/칼로리별 필터링<br>- 결과 목록 페이징 처리 (Oracle ROWNUM 활용)<br>- 검색결과 JSP 제작                         | `FOOD_TB`, `INGREDIENT_TB`                        | `foodSearch.jsp`, `foodList.jsp`                       |
| **4️⃣ 음식 상세보기 담당 (상세 페이지 + 연동)**  | **음식 상세정보 조회 및 연동 기능**            | - 음식 상세조회 SQL (`JOIN` 활용)<br>- 영양정보, 재료, 평균 평점 등 표시<br>- 추후 레시피·리뷰 기능 연결될 수 있도록 설계<br>- 관련 음식 추천 (같은 카테고리 등)                        | `FOOD_TB`, `FOOD_INGREDIENT_TB`, `REVIEW_TB`(참조용) | `foodDetail.jsp`, `foodInfo.jsp`                       |
| **5️⃣ 통계 대시보드 담당 (Oracle 분석 중심)** | **추천 로그 및 음식 데이터 통계 시각화**         | - 인기 음식 TOP5, 카테고리별 통계<br>- `GROUP BY`, `COUNT`, `AVG` SQL 집계<br>- 내 추천 이력 조회 기능<br>- JSP에서 표나 그래프로 표시                              | `RECOMMEND_LOG_TB`, `FOOD_TB`                     | `dashboard.jsp`, `stats.jsp`                           |

---

## 🔗 **기능 흐름 예시**

```
[홈.jsp]
 ├─▶ 메뉴 추천 (팀원1)
 │     ├ 조건 기반 추천 (taste/kcal 등)
 │     └ 추천 결과 출력 + 로그 기록
 │
 ├─▶ 음식 등록/관리 (팀원2)
 │     └ 관리자용 CRUD (음식·재료)
 │
 ├─▶ 음식 검색 (팀원3)
 │     ├ 키워드/카테고리 검색
 │     └ ▶ 결과 리스트(foodList.jsp)
 │
 ├─▶ 음식 상세보기 (팀원4)
 │     ├ 상세정보 / 영양 / 관련메뉴
 │
 └─▶ 통계 대시보드 (팀원5)
       ├ 인기메뉴 TOP5
       └ 카테고리별 통계 / 로그분석
```

---

## 🧩 **요약 버전**

| 팀원  | 역할명             | 핵심 키워드                      |
| --- | --------------- | --------------------------- |
| 1️⃣ | **메뉴 추천 담당**    | 추천 SQL / 재료 기반 추천 / 룰렛 / 로그 |
| 2️⃣ | **음식 등록·관리 담당** | 음식 CRUD / 재료 매핑 / 관리자 페이지   |
| 3️⃣ | **음식 검색 담당**    | 키워드 검색 / 카테고리 필터 / 페이징      |
| 4️⃣ | **음식 상세보기 담당**  | 상세정보 JOIN / 영양정보 / 관련메뉴     |
| 5️⃣ | **통계 대시보드 담당**  | TOP5 통계 / GROUP BY / 분석 JSP |

---

---

## 👥 **1️⃣ 메뉴 추천 담당**

> **추천 로직 / 조건 기반 추천 / 추천 로그 기록**

### 🔸 핵심 DB 테이블

#### `FOOD_TB` — 음식 기본정보

| 컬럼명         | 타입            | 설명             |
| ----------- | ------------- | -------------- |
| `food_id`   | NUMBER(5)     | 음식 고유번호 (PK)   |
| `name`      | VARCHAR2(100) | 음식명            |
| `category`  | VARCHAR2(30)  | 한식 / 중식 / 양식 등 |
| `kcal`      | NUMBER(4)     | 칼로리            |
| `protein`   | NUMBER(4,1)   | 단백질(g)         |
| `carb`      | NUMBER(4,1)   | 탄수화물(g)        |
| `fat`       | NUMBER(4,1)   | 지방(g)          |
| `image_url` | VARCHAR2(200) | 음식 이미지 경로      |

#### `PREFERENCE_TB` — 사용자 선호 정보

| 컬럼명            | 타입            | 설명                 |
| -------------- | ------------- | ------------------ |
| `user_id`      | VARCHAR2(20)  | 사용자 ID (FK)        |
| `taste_type`   | VARCHAR2(20)  | 선호 맛 (매운맛, 단맛 등)   |
| `allergy_info` | VARCHAR2(100) | 알러지 정보 (우유, 견과류 등) |
| `diet_goal`    | VARCHAR2(30)  | 다이어트 / 근육증진 / 유지 등 |
| `updated_at`   | DATE          | 최근 수정일             |

#### `RECOMMEND_LOG_TB` — 추천 기록

| 컬럼명           | 타입           | 설명                                       |
| ------------- | ------------ | ---------------------------------------- |
| `log_id`      | NUMBER(6)    | 로그 고유번호 (PK)                             |
| `user_id`     | VARCHAR2(20) | 추천받은 사용자 ID (FK)                         |
| `food_id`     | NUMBER(5)    | 추천된 음식 ID (FK)                           |
| `source_type` | VARCHAR2(20) | 추천 종류 (‘AI’, ‘ingredient’, ‘roulette’ 등) |
| `created_at`  | DATE         | 추천된 시점                                   |

---

## 👥 **2️⃣ 음식 등록·관리 담당**

> **음식, 재료, 음식-재료 매핑 CRUD 담당**

### 🔸 핵심 DB 테이블

#### `FOOD_TB`

(↑ 메뉴 추천 담당과 공유 — CRUD 구현의 중심)

#### `INGREDIENT_TB` — 재료 목록

| 컬럼명             | 타입           | 설명                     |
| --------------- | ------------ | ---------------------- |
| `ingredient_id` | NUMBER(5)    | 재료 ID (PK)             |
| `name`          | VARCHAR2(50) | 재료 이름                  |
| `type`          | VARCHAR2(30) | 식재료 종류 (육류, 채소, 해산물 등) |
| `allergy_flag`  | CHAR(1)      | 알러지 유발 여부 (Y/N)        |
| `kcal_per_100g` | NUMBER(4)    | 100g당 칼로리              |

#### `FOOD_INGREDIENT_TB` — 음식-재료 연결 테이블

| 컬럼명             | 타입           | 설명                    |
| --------------- | ------------ | --------------------- |
| `food_id`       | NUMBER(5)    | 음식 ID (FK)            |
| `ingredient_id` | NUMBER(5)    | 재료 ID (FK)            |
| `amount`        | VARCHAR2(20) | 사용량 (예: ‘200g’, ‘1컵’) |

> ⚙️ 이 담당자는 실제 CRUD (등록·수정·삭제) 기능을 JSP에서 구현.

---

## 👥 **3️⃣ 음식 검색 담당**

> **키워드, 카테고리, 칼로리 기반 검색 담당**

### 🔸 핵심 DB 테이블

#### `FOOD_TB`

| 컬럼명              | 타입            | 설명                |
| ---------------- | ------------- | ----------------- |
| `name`           | VARCHAR2(100) | 검색 키워드 대상         |
| `category`       | VARCHAR2(30)  | 카테고리 필터           |
| `kcal`           | NUMBER(4)     | 칼로리 필터            |
| `cooking_method` | VARCHAR2(30)  | 조리법 (볶음, 찜, 튀김 등) |

#### `INGREDIENT_TB`

| 컬럼명    | 타입           | 설명            |
| ------ | ------------ | ------------- |
| `name` | VARCHAR2(50) | 재료명으로 검색 가능   |
| `type` | VARCHAR2(30) | 재료 종류 (검색 필터) |

> ✅ 검색 담당자는 `LIKE`, `BETWEEN`, `IN` 등을 활용한 검색 SQL을 담당.
> 예:
>
> ```sql
> SELECT * FROM FOOD_TB
> WHERE name LIKE '%김치%'
>   OR category = '한식'
>   OR kcal BETWEEN 300 AND 600;
> ```

---

## 👥 **4️⃣ 음식 상세보기 담당**

> **음식 클릭 시 세부 정보 / 영양소 / 관련 메뉴 표시 담당**

### 🔸 핵심 DB 테이블

#### `FOOD_TB`

| 주요 필드                            | 설명           |
| -------------------------------- | ------------ |
| `food_id`                        | PK, 상세조회 키   |
| `name`, `category`               | 기본정보         |
| `kcal`, `protein`, `carb`, `fat` | 영양정보 표시용     |
| `cooking_method`, `image_url`    | 조리법 / 이미지 출력 |

#### `FOOD_INGREDIENT_TB`

| 필드              | 설명              |
| --------------- | --------------- |
| `ingredient_id` | JOIN하여 사용 재료 조회 |
| `amount`        | 사용량 표시          |

#### (참조용) `REVIEW_TB`

| 필드        | 설명              |
| --------- | --------------- |
| `rating`  | 평균 평점 계산용       |
| `comment` | 상세보기에서 후기 일부 노출 |

> 🍽 **JOIN 예시**
>
> ```sql
> SELECT f.name, f.kcal, i.name AS ingredient, fi.amount
> FROM FOOD_TB f
> JOIN FOOD_INGREDIENT_TB fi ON f.food_id = fi.food_id
> JOIN INGREDIENT_TB i ON fi.ingredient_id = i.ingredient_id
> WHERE f.food_id = ?;
> ```

---

## 👥 **5️⃣ 통계 대시보드 담당**

> **Oracle GROUP BY / COUNT / AVG 활용한 데이터 분석 담당**

### 🔸 핵심 DB 테이블

#### `RECOMMEND_LOG_TB`

| 필드            | 설명                      |
| ------------- | ----------------------- |
| `food_id`     | 어떤 음식이 몇 번 추천되었는지 COUNT |
| `user_id`     | 사용자별 추천 내역 필터링          |
| `source_type` | 추천 유형별 통계 구분            |
| `created_at`  | 기간별 통계용 DATE            |

#### `FOOD_TB`

| 필드         | 설명                 |
| ---------- | ------------------ |
| `category` | 카테고리별 추천/검색 횟수 통계용 |
| `kcal`     | 평균 칼로리 통계용         |

> 📊 **예시 쿼리**
>
> ```sql
> SELECT f.category, COUNT(*) AS cnt
> FROM RECOMMEND_LOG_TB r
> JOIN FOOD_TB f ON r.food_id = f.food_id
> GROUP BY f.category
> ORDER BY cnt DESC;
> ```

---

## 🧩 **정리 요약**

| 팀원           | 핵심 테이블                                           | 주요 데이터 내용               |
| ------------ | ------------------------------------------------ | ----------------------- |
| 1️⃣ 메뉴 추천    | `FOOD_TB`, `PREFERENCE_TB`, `RECOMMEND_LOG_TB`   | 음식 기본정보, 사용자 선호도, 추천 기록 |
| 2️⃣ 음식 등록/관리 | `FOOD_TB`, `INGREDIENT_TB`, `FOOD_INGREDIENT_TB` | 음식·재료 데이터 및 매핑 관계       |
| 3️⃣ 음식 검색    | `FOOD_TB`, `INGREDIENT_TB`                       | 음식명/카테고리/재료 검색 필드       |
| 4️⃣ 음식 상세보기  | `FOOD_TB`, `FOOD_INGREDIENT_TB`, `REVIEW_TB(참조)` | 상세정보, 영양소, 재료, 후기       |
| 5️⃣ 통계 대시보드  | `RECOMMEND_LOG_TB`, `FOOD_TB`                    | 추천 로그, 인기 통계, 카테고리별 집계  |

