## 🛬 여행 버킷리스트 매칭 플랫폼 – 여행 취향 기반 SNS + 상품 추천
컨셉: 사용자가 여행 버킷리스트를 작성하면, 유사한 취향의 사람들과 연결되고, 맞춤형 여행 상품을 추천받는 서비스.
B2C 요소: 사용자 ↔ 여행사, 숙소, 액티비티 업체 연결
수익 모델: 제휴 상품 판매, 프리미엄 추천 기능
SNS 기능: 여행 후기 공유, 버킷리스트 피드, 친구 매칭
Flutter 활용: 여행 일정 관리, 위치 기반 추천, 실시간 채팅

## 🧼 생활 꿀팁 공유 플랫폼 – 일상 팁 기반 SNS + 제품 리뷰
컨셉: 청소, 정리, 요리 등 생활 꿀팁을 공유하고 관련 제품을 리뷰하는 커뮤니티형 SNS.
B2C 요소: 사용자 ↔ 생활용품 브랜드 연결
수익 모델: 리뷰 기반 커머스, 광고, 브랜드 협찬
SNS 기능: 꿀팁 피드, 댓글, 리뷰 랭킹
Flutter 활용: 팁 영상 업로드, 제품 스캔 기능, 리뷰 작성

## 🎮 게임 취향 매칭 & 커뮤니티 플랫폼
컨셉: 게임 취향을 분석해 유사한 유저들과 매칭하고, 게임 관련 콘텐츠를 공유하는 SNS + 커머스 플랫폼.
B2C 요소: 사용자 ↔ 게임 굿즈/장비 판매자 연결
수익 모델: 굿즈 판매, 광고, 프리미엄 커뮤니티
SNS 기능: 플레이 후기 공유, 친구 매칭, 랭킹Flutter 활용: 게임 기록 연동, 커뮤니티 채팅, 푸시 알림



----------------------------------------------------------------------------
메뉴 추천 알고리즘
<br>
음식 DB와 사용자 성향을 매칭하여 맞춤 메뉴 제공
추천 히스토리 저장, 사용자 만족도 평가 가능
최소 기능: user_food_pref, recommendation_history 테이블

일정/기분 기반 추천
<br>
일정(점심/저녁 회식 등)과 감정(피곤, 스트레스)에 따른 맞춤 추천
최소 기능: schedule, mood 테이블

커뮤니티 & 리뷰 기능
<br>
추천 메뉴에 대한 리뷰, 평가, 공유
최소 기능: review, comment, user_like 테이블

영양/칼로리 관리
<br>
추천 음식의 영양 정보 제공
사용자의 일일 섭취량 통계 제공
최소 기능: nutrition, daily_intake 테이블



| 모듈       | 테이블                    | 설명                     |
| -------- | ---------------------- | ---------------------- |
| 사용자 선호   | user_food_pref         | 사용자 음식 선호 기록           |
| 추천       | recommendation_history | 추천 메뉴와 추천 이유 기록        |
| 일정 & 기분  | schedule               | 사용자의 일정 기록             |
| 〃        | mood                   | 사용자의 감정 기록             |
| 커뮤니티     | review                 | 메뉴 리뷰                  |
| 〃        | comment                | 리뷰 댓글                  |
| 〃        | user_like              | 좋아요/추천 기능              |
| 영양 관리    | nutrition              | 음식 영양 정보               |
| 〃        | daily_intake           | 사용자의 일일 섭취 기록          |






CREATE TABLE INGREDIENT (
    ingredient_id    NUMBER(6)        CONSTRAINT pk_ingredient PRIMARY KEY,   -- 재료 고유번호
    name             VARCHAR2(100)    CONSTRAINT nn_ingredient_name NOT NULL, -- 재료명
    type             VARCHAR2(50),                                         -- 종류 (채소, 육류, 해산물, 소스 등)
    kcal_per_100g    NUMBER(5),                                            -- 100g당 칼로리
    allergy_flag     VARCHAR2(100),                                       -- 알러지 정보 (쉼표로 구분 가능)
    created_at       DATE DEFAULT SYSDATE,                                -- 등록일
    updated_at       DATE DEFAULT SYSDATE,                                -- 수정일
    is_active        CHAR(1) DEFAULT 'Y' CHECK (is_active IN ('Y','N'))   -- 사용 여부
);






-- 1. 돼지고기 (육류)
INSERT INTO INGREDIENT (ingredient_id, name, type, kcal_per_100g, allergy_flag, created_at)
VALUES (1, '돼지고기', '육류', 370, '돼지고기', TO_DATE('2025-01-01', 'YYYY-MM-DD'));

-- 2. 배추 (채소)
INSERT INTO INGREDIENT (ingredient_id, name, type, kcal_per_100g, allergy_flag, created_at)
VALUES (2, '배추', '채소', 50, NULL, TO_DATE('2025-01-01', 'YYYY-MM-DD'));

-- 3. 대하 (해산물)
INSERT INTO INGREDIENT (ingredient_id, name, type, kcal_per_100g, allergy_flag, created_at)
VALUES (3, '대하', '해산물', 90, '갑각류', TO_DATE('2025-01-02', 'YYYY-MM-DD'));

-- 4. 간장 (소스)
INSERT INTO INGREDIENT (ingredient_id, name, type, kcal_per_100g, allergy_flag, created_at)
VALUES (4, '간장', '소스', 65, '대두,밀', TO_DATE('2025-01-02', 'YYYY-MM-DD'));

-- 5. 달걀 (기타 단백질)
INSERT INTO INGREDIENT (ingredient_id, name, type, kcal_per_100g, allergy_flag, created_at)
VALUES (5, '달걀', '단백질', 155, '계란', TO_DATE('2025-01-03', 'YYYY-MM-DD'));

