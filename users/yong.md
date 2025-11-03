## 🔍 ④ **RECOMMEND_TB / SEARCH_LOG_TB (추천 & 검색 담당)**

**담당자:** 메뉴 추천, 검색 히스토리 저장, AI 피드백

### `RECOMMEND_TB`

| 컬럼명          | 타입            | 제약조건            | 설명                     |
| ------------ | ------------- | --------------- | ---------------------- |
| `rec_id`     | NUMBER(8)     | PK              | 추천 고유번호                |
| `user_id`    | VARCHAR2(30)  | FK → USER_TB    | 사용자 ID                 |
| `food_id`    | NUMBER(6)     | FK → FOOD_TB    | 추천된 음식                 |
| `type`       | VARCHAR2(30)  |                 | 추천 유형 (AI, 랜덤, 재료기반 등) |
| `feedback`   | VARCHAR2(200) |          null      | AI 피드백 (예: 단백질 부족)     
|` is_favorite` |CHAR(1) | DEFAULT| 'N' 사용자가 추천| 음식을 ‘찜’했는지 여부 ('Y': 찜함, 'N': 안함) |
| `created_at` | DATE          | DEFAULT SYSDATE | 추천 일시                  |

1   1   1   


### `SEARCH_LOG_TB`

| 컬럼명          | 타입            | 제약조건            | 설명                |
| ------------ | ------------- | --------------- | ----------------- |
| `search_id`  | NUMBER(8)     | PK              | 검색 기록 ID          |
| `user_id`    | VARCHAR2(30)  | FK → USER_TB    | 사용자 ID            |
| `keyword`    | VARCHAR2(100) |                 | 검색 키워드            |
| `filter`     | VARCHAR2(100) |                 | 필터 조건 (비건, 고단백 등) |
| `recent_search` |CHAR(1) | DEFAULT | 'Y' 해당 검색어를 최근 검색 목록에 표시할지 여부 ('Y': 표시, 'N': 비표시)||
| `created_at` | DATE          | DEFAULT SYSDATE | 검색 시각             |

🔹 **CRUD 예시**

* Create: 추천 결과 저장, 검색 로그 기록
* Read: “나의 추천 기록”, “최근 검색어”
* Update: 피드백 내용 수정
* Delete: 오래된 기록 삭제

---

1️⃣ RECOMMEND_TB – 추가 컬럼: is_favorite (찜/즐겨찾기)
컬럼명	타입	제약조건	설명
is_favorite	CHAR(1)	DEFAULT 'N'	사용자가 추천 음식을 ‘찜’했는지 여부 ('Y': 찜함, 'N': 안함)

실용성 이유:

대부분의 쇼핑몰, 음식 앱, 콘텐츠 추천 사이트에서 추천된 항목을 찜하기/저장 기능 제공.

사용자가 나중에 다시 보고 싶을 때 쉽게 확인 가능.

추천 알고리즘에도 반영 가능 (찜한 음식 우선 추천).

2️⃣ SEARCH_LOG_TB – 추가 컬럼: recent_search (최근 검색 표시용)
컬럼명	타입	제약조건	설명
recent_search	CHAR(1)	DEFAULT 'Y'	해당 검색어를 최근 검색 목록에 표시할지 여부 ('Y': 표시, 'N': 비표시)

실용성 이유:

거의 모든 홈페이지/앱에서 최근 검색어 자동 표시 기능 제공.

사용자가 검색창을 클릭하면 바로 최근 검색어 보여주어 편리.

오래된 검색어는 비활성화(N) 처리 가능.


1️⃣ RECOMMEND_TB 
CREATE TABLE RECOMMEND_TB (
    rec_id NUMBER(8) PRIMARY KEY,
    user_id VARCHAR2(30) NOT NULL,
    food_id NUMBER(6) NOT NULL,
    type VARCHAR2(30) NOT NULL,
    feedback VARCHAR2(200),
    created_at DATE DEFAULT SYSDATE,
    is_favorite CHAR(1) DEFAULT 'N',
    CONSTRAINT fk_recommend_user FOREIGN KEY (user_id) REFERENCES USER_TB(id),
    CONSTRAINT fk_recommend_food FOREIGN KEY (food_id) REFERENCES FOOD_TB(fid)
);


2️⃣ SEARCH_LOG_TB
CREATE TABLE SEARCH_LOG_TB (
    search_id NUMBER(8) PRIMARY KEY,
    user_id VARCHAR2(30) NOT NULL,
    keyword VARCHAR2(100) NOT NULL,
    filter VARCHAR2(100),
    created_at DATE DEFAULT SYSDATE,
    recent_search CHAR(1) DEFAULT 'Y',
    CONSTRAINT fk_search_user FOREIGN KEY (user_id) REFERENCES USER_TB(id)

);


-------------------------------------------------------------
🍽️ 메뉴 추천 플랫폼 아이디어 
1. 기분 기반 추천 플랫폼
- 사용자가 현재 기분을 선택하면 그에 맞는 음식 추천
- 예: "우울해요" → 따뜻한 국물 요리 / "기분 좋아요" → 활기찬 색감의 샐러드
📌 핵심 모듈:
- Mood 선택지 테이블
- Mood → Food 매핑 테이블

2. 시간대/날씨 기반 추천
- 현재 시간과 날씨에 따라 메뉴 추천
- 예: 비 오는 저녁 → 전, 국물 요리 / 맑은 아침 → 샌드위치, 커피
📌 핵심 모듈:
- Weather API 연동
- TimeSlot → Food 매핑 테이블

3. 식사 목적 기반 추천
- 사용자가 식사 목적을 선택: 다이어트, 회식, 혼밥, 해장 등
- 목적에 따라 메뉴 추천
📌 핵심 모듈:
- Purpose 선택지 테이블
- Purpose → Food 매핑 테이블

4. 재료 기반 추천
- 냉장고에 있는 재료를 입력하면 만들 수 있는 메뉴 추천
- 예: "계란, 양파, 밥" → 계란볶음밥, 오믈렛
📌 핵심 모듈:
- Ingredient 테이블
- Ingredient → Recipe 매핑

5. 지역 기반 추천
- 사용자의 위치 또는 선택한 지역에 따라 지역 특산 메뉴 추천
- 예: 부산 → 밀면 / 전주 → 비빔밥
📌 핵심 모듈:
- Region 테이블
- Region → Food 매핑

6. 사용자 행동 기반 추천 (AI 학습형)
- 사용자가 자주 선택하는 메뉴를 학습해 자동 추천
- 예: 평일 점심엔 항상 면 요리 → 자동 면류 추천
📌 핵심 모듈:
- UserHistory 테이블
- 추천 알고리즘 (기초 rule 기반 or ML 확장 가능)
