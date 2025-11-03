### 🍽️ P1: 음식 데이터 등록 (JSP + Oracle)

- 음식/재료/영양소/카테고리 등록
- 관리자 초기 데이터 입력 화면
- Oracle 기반 음식 DB 구축
- "영양소"는 통계 대시보드(P3/P6)와도 연결되므로, P1은 기초 데이터 입력에 집중

 

### 👥 5명 역할 분담 제안

| 담당자 | 역할 설명 | 담당 테이블 |
|--------|-----------|--------------|
| **A (관리자)** | 전체 시스템 설계 및 초기 데이터 입력 | `CATEGORY`, `NUTRIENT` |
| **B (음식 등록자)** | 음식 등록 및 음식-재료/영양소 매핑 관리 | `FOOD`, `FOOD_INGREDIENT`, `FOOD_NUTRIENT` |
| **C (재료 관리자)** | 재료 정보 등록 및 알레르기 관리 | `INGREDIENT` |
| **D (회원 관리자)** | 사용자 등록 및 권한 관리 | `USER` |
| **E (사용자 기능 담당자)** | 즐겨찾기 및 리뷰 기능 구현 | `FAVORITE`, `FOOD_REVIEW` |

---

### 📌 역할별 주요 작업

#### A. 관리자
- 음식 카테고리 및 영양소 종류 초기 등록
- 영양소 단위 및 이름 표준화

#### B. 음식 등록자
- 음식 기본 정보 입력
- 음식별 재료 및 영양소 매핑
- 음식 등록자 정보(`created_by`) 연결

#### C. 재료 관리자
- 재료명, 단위 등록
- 알레르기 여부 관리 (`is_allergen`)

#### D. 회원 관리자
- 사용자 가입 처리
- 이메일 중복 체크, 비밀번호 암호화 등

#### E. 사용자 기능 담당자
- 즐겨찾기 기능 구현
- 음식 리뷰 작성 및 평점 처리


---

### 0. 공통 테이블 (`USER`) + 시퀀스 (`user_seq`)
| 컬럼명      | 데이터 타입       | 제약 조건             | 설명 |
|-------------|-------------------|------------------------|------|
| `userid`    | `NUMBER`          | `PRIMARY KEY`          | 사용자 고유 ID |
| `nickname`  | `VARCHAR2(100)`   | `NOT NULL`             | 닉네임 |
| `email`     | `VARCHAR2(200)`   | `NOT NULL`, `UNIQUE`   | 이메일 주소 |
| `pass`      | `VARCHAR2(100)`   | `NOT NULL`             | 비밀번호 |
| `createdat` | `VARCHAR2(200)`   | `NOT NULL`             | 가입 날짜 |

---

### 1. 음식 테이블 (`FOOD`)
| 컬럼명        | 데이터 타입       | 제약 조건                              | 설명 |
|----------------|-------------------|----------------------------------------|------|
| `food_id`       | `NUMBER`          | `PRIMARY KEY`                          | 음식 고유 ID |
| `food_name`     | `VARCHAR2(100)`   | `NOT NULL`                             | 음식 이름 |
| `category_id`   | `NUMBER`          | `FOREIGN KEY REFERENCES CATEGORY(category_id)` | 카테고리 참조 |
| `calories`      | `NUMBER`          |                                        | 칼로리 (kcal) |
| `description`   | `VARCHAR2(500)`   |                                        | 음식 설명 |
| `created_by`    | `NUMBER`          | `FOREIGN KEY REFERENCES USER(userid)`  | 등록자 (사용자 ID) |

---

### 2. 재료 테이블 (`INGREDIENT`)
| 컬럼명           | 데이터 타입       | 제약 조건             | 설명 |
|------------------|-------------------|------------------------|------|
| `ingredient_id`   | `NUMBER`          | `PRIMARY KEY`          | 재료 고유 ID |
| `ingredient_name` | `VARCHAR2(100)`   | `NOT NULL`             | 재료 이름 |
| `unit`            | `VARCHAR2(50)`    |                        | 단위 (g, ml 등) |
| `is_allergen`     | `CHAR(1)`         | `DEFAULT 'N'`          | 알레르기 여부 (Y/N) |

---

### 3. 음식-재료 매핑 테이블 (`FOOD_INGREDIENT`)  n:n
| 컬럼명         | 데이터 타입       | 제약 조건                                                        | 설명 |
|----------------|-------------------|------------------------------------------------------------------|------|
| `food_id`       | `NUMBER`          | `FOREIGN KEY REFERENCES FOOD(food_id)`                          | 음식 ID |
| `ingredient_id` | `NUMBER`          | `FOREIGN KEY REFERENCES INGREDIENT(ingredient_id)`              | 재료 ID |
| `amount`        | `NUMBER`          |                                                                  | 사용량 |
| **PK 설정**     |                   | `PRIMARY KEY (food_id, ingredient_id)`                          | 복합키 |

---

### 4. 영양소 테이블 (`NUTRIENT`)
| 컬럼명         | 데이터 타입       | 제약 조건             | 설명 |
|----------------|-------------------|------------------------|------|
| `nutrient_id`   | `NUMBER`          | `PRIMARY KEY`          | 영양소 ID |
| `nutrient_name` | `VARCHAR2(100)`   | `NOT NULL`             | 영양소 이름 (단백질, 탄수화물 등) |
| `unit`          | `VARCHAR2(50)`    |                        | 단위 (g, mg 등) |

---

### 5. 음식-영양소 매핑 테이블 (`FOOD_NUTRIENT`)  n:n
| 컬럼명         | 데이터 타입       | 제약 조건                                                        | 설명 |
|----------------|-------------------|------------------------------------------------------------------|------|
| `food_id`       | `NUMBER`          | `FOREIGN KEY REFERENCES FOOD(food_id)`                          | 음식 ID |
| `nutrient_id`   | `NUMBER`          | `FOREIGN KEY REFERENCES NUTRIENT(nutrient_id)`                  | 영양소 ID |
| `amount`        | `NUMBER`          |                                                                  | 포함량 |
| **PK 설정**     |                   | `PRIMARY KEY (food_id, nutrient_id)`                            | 복합키 |

---

### 6. 카테고리 테이블 (`CATEGORY`)
| 컬럼명         | 데이터 타입       | 제약 조건             | 설명 |
|----------------|-------------------|------------------------|------|
| `category_id`   | `NUMBER`          | `PRIMARY KEY`          | 카테고리 ID |
| `category_name` | `VARCHAR2(100)`   | `NOT NULL`             | 카테고리 이름 (한식, 중식, 저탄수 등) |

---

### 7. 즐겨찾기 테이블 (`FAVORITE`)
| 컬럼명       | 데이터 타입       | 제약 조건                                                        | 설명 |
|--------------|-------------------|------------------------------------------------------------------|------|
| `userid`      | `NUMBER`          | `FOREIGN KEY REFERENCES USER(userid)`                           | 사용자 ID |
| `food_id`     | `NUMBER`          | `FOREIGN KEY REFERENCES FOOD(food_id)`                          | 음식 ID |
| `added_at`    | `VARCHAR2(200)`   |                                                                  | 즐겨찾기 등록일 |
| **PK 설정**   |                   | `PRIMARY KEY (userid, food_id)`                                 | 복합키 |

---

### 8. 음식 리뷰 테이블 (`FOOD_REVIEW`)
| 컬럼명       | 데이터 타입       | 제약 조건                                                        | 설명 |
|--------------|-------------------|------------------------------------------------------------------|------|
| `review_id`   | `NUMBER`          | `PRIMARY KEY`                                                   | 리뷰 ID |
| `userid`      | `NUMBER`          | `FOREIGN KEY REFERENCES USER(userid)`                           | 작성자 |
| `food_id`     | `NUMBER`          | `FOREIGN KEY REFERENCES FOOD(food_id)`                          | 음식 대상 |
| `rating`      | `NUMBER(1)`       | `CHECK (rating BETWEEN 1 AND 5)`                                | 평점 (1~5) |
| `comment`     | `VARCHAR2(500)`   |                                                                  | 리뷰 내용 |
| `created_at`  | `VARCHAR2(200)`   |                                                                  | 작성일 |
 


--- 

아주 좋아요 👏
지금은 **프로젝트 전체 구조가 큰 그림으로 잘 짜여있고**,
이제 첫 단계로 **오라클 CRUD 기반 DB 설계 (역할별 테이블 + 멤버변수 정의)** 를 만드는 단계군요.

말씀하신 대로 “5가지 역할”로 나눠서 진행할게요:

> 💡 유저 / 음식 / 재료 / 추천·검색 / 커뮤니티(리뷰·게시글 등)

(통계나 관리자 기능은 이 5개 데이터를 조합해서 나중에 조회하는 구조로 처리)

---

# 🍽 스마트 푸드 플랫폼 – Oracle DB 설계 (CRUD 중심)

---

## 👤 ① **USER_TB (회원/유저 관리 담당)**

**담당자:** 로그인·회원가입·마이페이지 CRUD

| 컬럼명          | 타입            | 제약조건                        | 설명                          |
| ------------ | ------------- | --------------------------- | --------------------------- |
| `id`    | VARCHAR2(30)  | PK                          | 사용자 고유 ID                   |
| `password`   | VARCHAR2(100) | NOT NULL                    | 비밀번호 (암호화 저장)               |
| `nickname`       | VARCHAR2(50)  | NOT NULL                    |닉네임                    |
| `email`      | VARCHAR2(100) | UNIQUE                      | 이메일 (로그인용)                  |
| `gender`     | CHAR(1)       | CHECK (gender IN ('M','F')) | 성별                          |
| `birth_date` | DATE          |                             | 생년월일                        |
| `preference` | VARCHAR2(100) |                             | 선호 음식 or 식단 유형 (비건, 다이어트 등) |
| `allergy`    | CHAR(1) |                             | 알러지 정보 (y/n)   or  설명       |
| `join_date`  | DATE          | DEFAULT SYSDATE             | 가입일                         |
| `role`       | VARCHAR2(20)  | DEFAULT 'USER'              | 관리자/일반유저 구분                 |

1   '1111'   'a'   '123@123'   'm'   '2025.01.01'   '육식'   'y'   '2025.10.31' ,'관리자'    



🔹 **CRUD 예시**

* Create: 회원가입
* Read: 로그인, 내 정보 보기
* Update: 프로필 수정, 비밀번호 변경
* Delete: 회원 탈퇴

---

## 🍜 ② **FOOD_TB (음식 관리 담당)**

**담당자:** 음식 등록 / 수정 / 삭제 / 조회

| 컬럼명         | 타입            | 제약조건            | 설명                     |
| ----------- | ------------- | --------------- | ---------------------- |
| `fid`   | NUMBER(6)     | PK              | 음식 고유번호                |
| `name`      | VARCHAR2(100) | NOT NULL        | 음식명                    |
| `category`  | VARCHAR2(50)  |                 | 음식 카테고리 (한식, 양식, 중식, 일식 등) |
| `kcal`      | NUMBER(5)     |                 | 칼로리                    |
| `protein`   | NUMBER(5,1)   |                 | 단백질(g)                 |
| `carb`      | NUMBER(5,1)   |                 | 탄수화물(g)                |
| `fat`       | NUMBER(5,1)   |                 | 지방(g)                  |
| `recipe`    | CLOB          |                 | 조리 방법                  |
| `image_url` | VARCHAR2(200) |       null          | 음식 이미지 경로              |
| `reg_date`  | DATE          | DEFAULT SYSDATE | 등록일                    |

1   '삼겹살'   '한식'   '370kcal'      '10'   '100'   '5'    '조리설명'      'null'    '2025.01.01'

🔹 **CRUD 예시**

* Create: 음식 등록 (관리자 페이지)
* Read: 음식 리스트, 상세보기
* Update: 영양소 수정, 이미지 변경
* Delete: 음식 삭제

---

## 🧄 ③ **INGREDIENT_TB (재료 관리 담당)**

**담당자:** 재료 등록 / 수정 / 삭제 / 음식-재료 매핑 관리

| 컬럼명             | 타입            | 제약조건            | 설명                     |
| --------------- | ------------- | --------------- | ---------------------- |
| `ingredient_id` | NUMBER(6)     | PK              | 재료 고유번호                |
| `name`          | VARCHAR2(100) | NOT NULL        | 재료명                    |
| `type`          | VARCHAR2(50)  |                 | 종류 (채소, 육류, 해산물, 소스 등) |
| `kcal_per_100g` | NUMBER(5)     |                 | 100g당 칼로리              |
| `allergy_flag`  | CHAR(1)       | DEFAULT 'N'     | 알러지 유발 여부              |
| `created_at`    | DATE          | DEFAULT SYSDATE | 등록일                    |



1   '돼지'   '육류'   '370'      'y'   '2025.01.01'
2   '배추'   '채소'   '50'      'y'   '2025.01.01'


### 📦 음식-재료 매핑 테이블 (N:N 관계)
#### `FOOD_INGREDIENT_TB`

| 컬럼명             | 타입           | 제약조건               | 설명                     |
| --------------- | ------------ | ------------------ | ---------------------- |
| `food_id`       | NUMBER(6)    | FK → FOOD_TB       | 음식 ID                  |
| `ingredient_id` | NUMBER(6)    | FK → INGREDIENT_TB | 재료 ID                  |
| `amount`        | VARCHAR2(50) |                    | 사용량 (예: “200g”, “1큰술”) |

10   2   '370'
10   2   '370'


🔹 **CRUD 예시**

* Create: 재료 등록, 음식-재료 매핑 추가
* Read: 재료별 사용 음식 조회
* Update: 재료 정보 수정
* Delete: 재료 삭제

---

## 🔍 ④ **RECOMMEND_TB / SEARCH_LOG_TB (추천 & 검색 담당)**

**담당자:** 메뉴 추천, 검색 히스토리 저장, AI 피드백

### `RECOMMEND_TB`

| 컬럼명          | 타입            | 제약조건            | 설명                     |
| ------------ | ------------- | --------------- | ---------------------- |
| `rec_id`     | NUMBER(8)     | PK              | 추천 고유번호                |
| `user_id`    | VARCHAR2(30)  | FK → USER_TB    | 사용자 ID                 |
| `food_id`    | NUMBER(6)     | FK → FOOD_TB    | 추천된 음식                 |
| `type`       | VARCHAR2(30)  |                 | 추천 유형 (AI, 랜덤, 재료기반 등) |
| `feedback`   | VARCHAR2(200) |                 | AI 피드백 (예: 단백질 부족)     |
| `created_at` | DATE          | DEFAULT SYSDATE | 추천 일시                  |

1   1   1   


### `SEARCH_LOG_TB`

| 컬럼명          | 타입            | 제약조건            | 설명                |
| ------------ | ------------- | --------------- | ----------------- |
| `search_id`  | NUMBER(8)     | PK              | 검색 기록 ID          |
| `user_id`    | VARCHAR2(30)  | FK → USER_TB    | 사용자 ID            |
| `keyword`    | VARCHAR2(100) |                 | 검색 키워드            |
| `filter`     | VARCHAR2(100) |                 | 필터 조건 (비건, 고단백 등) |
| `created_at` | DATE          | DEFAULT SYSDATE | 검색 시각             |

🔹 **CRUD 예시**

* Create: 추천 결과 저장, 검색 로그 기록
* Read: “나의 추천 기록”, “최근 검색어”
* Update: 피드백 내용 수정
* Delete: 오래된 기록 삭제

---

## 🧑‍🍳 ⑤ **COMMUNITY_TB / REVIEW_TB (커뮤니티 담당)**

**담당자:** 게시글/리뷰 CRUD

### `COMMUNITY_TB` — 레시피 공유 게시판

| 컬럼명          | 타입            | 제약조건            | 설명                 |
| ------------ | ------------- | --------------- | ------------------ |
| `post_id`    | NUMBER(8)     | PK              | 게시글 번호             |
| `user_id`    | VARCHAR2(30)  | FK → USER_TB    | 작성자                |
| `title`      | VARCHAR2(200) | NOT NULL        | 제목                 |
| `content`    | CLOB          | NOT NULL        | 내용 (레시피)           |
| `image_url`  | VARCHAR2(200) |                 | 첨부 이미지             |
| `category`   | VARCHAR2(50)  |                 | 레시피 유형 (디저트, 국물 등) |
| `created_at` | DATE          | DEFAULT SYSDATE | 작성일                |
| `views`      | NUMBER(6)     | DEFAULT 0       | 조회수                |
| `likes`      | NUMBER(6)     | DEFAULT 0       | 좋아요 수              |

### `REVIEW_TB` — 음식/추천 평가

| 컬럼명          | 타입            | 제약조건                           | 설명       |
| ------------ | ------------- | ------------------------------ | -------- |
| `review_id`  | NUMBER(8)     | PK                             | 리뷰 번호    |
| `user_id`    | VARCHAR2(30)  | FK → USER_TB                   | 작성자      |
| `food_id`    | NUMBER(6)     | FK → FOOD_TB                   | 리뷰 대상 음식 |
| `rating`     | NUMBER(2,1)   | CHECK (rating BETWEEN 0 AND 5) | 별점       |
| `comment`    | VARCHAR2(300) |                                | 후기       |
| `created_at` | DATE          | DEFAULT SYSDATE                | 작성일      |

🔹 **CRUD 예시**

* Create: 게시글 등록, 리뷰 작성
* Read: 게시판 목록, 리뷰 조회
* Update: 게시글 수정, 후기 수정
* Delete: 게시글/댓글 삭제

---

## 📊 전체 관계 요약 (ERD 개념)

```
USER_TB (1) ───< RECOMMEND_TB >───(1) FOOD_TB
   │                         │
   │                         └──< REVIEW_TB >─── FOOD_TB
   │
   ├──< COMMUNITY_TB
   └──< SEARCH_LOG_TB

FOOD_TB ───< FOOD_INGREDIENT_TB >─── INGREDIENT_TB
```

---

## ✅ 정리 요약표

| 역할      | 주요 테이블                            | 핵심 컬럼                       | 담당 CRUD 기능     |
| ------- | --------------------------------- | --------------------------- | -------------- |
| ① 유저 관리 | USER_TB                           | user_id, email, preference  | 회원가입, 로그인, 프로필 |
| ② 음식 관리 | FOOD_TB                           | food_id, name, kcal, recipe | 음식 등록, 수정, 삭제  |
| ③ 재료 관리 | INGREDIENT_TB, FOOD_INGREDIENT_TB | ingredient_id, name, amount | 재료 등록, 매핑      |
| ④ 추천·검색 | RECOMMEND_TB, SEARCH_LOG_TB       | rec_id, keyword, type       | 추천, 검색 히스토리    |
| ⑤ 커뮤니티  | COMMUNITY_TB, REVIEW_TB           | post_id, review_id          | 게시글/리뷰 CRUD    |

---

원하신다면 다음 단계로,
이 테이블들을 기반으로 **Oracle용 CREATE TABLE 쿼리문** (DDL)을 바로 생성해드릴 수도 있어요.
그렇게 할까요?
