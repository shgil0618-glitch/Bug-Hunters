<img width="1510" height="879" alt="화면 캡처 2025-10-29 2033551" src="https://github.com/user-attachments/assets/de157fd7-89dd-4a2d-a40a-3741a1d7992a" />


import { useState } from "react";
import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Smile, CloudRain, Sun, Utensils, Heart } from "lucide-react";

export default function Home() {
  const [mood, setMood] = useState(null);

  const moods = [
    { name: "기분 좋아요", icon: <Smile className="text-yellow-400" /> },
    { name: "피곤해요", icon: <CloudRain className="text-blue-400" /> },
    { name: "배고파요", icon: <Utensils className="text-red-400" /> },
    { name: "우울해요", icon: <Heart className="text-pink-400" /> },
  ];

  const recommendations = [
    {
      title: "따뜻한 소고기 미역국",
      desc: "피곤한 하루를 회복시켜주는 따뜻한 국물 요리",
      img: "https://images.unsplash.com/photo-1617196039897-4e97d69ac5cc",
    },
    {
      title: "상큼한 연어 샐러드",
      desc: "기분이 우울할 때 활력을 주는 가벼운 샐러드",
      img: "https://images.unsplash.com/photo-1504674900247-0877df9cc836",
    },
    {
      title: "매콤한 제육덮밥",
      desc: "스트레스 날려주는 인기 한식 메뉴",
      img: "https://images.unsplash.com/photo-1598514982901-9e3f8e65d87a",
    },
  ];

  return (
    <div className="min-h-screen bg-gradient-to-b from-sky-100 to-white p-8">
      <header className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold text-sky-700">🍽 오늘의 메뉴 추천</h1>
        <input
          type="text"
          placeholder="재료나 음식을 검색하세요..."
          className="border border-gray-300 rounded-full px-4 py-2 w-80 focus:outline-none focus:ring-2 focus:ring-sky-400"
        />
      </header>

      <section className="text-center mb-10">
        <h2 className="text-xl font-semibold mb-3 text-gray-700">오늘의 기분은 어떤가요?</h2>
        <div className="flex justify-center gap-4 flex-wrap">
          {moods.map((m) => (
            <Button
              key={m.name}
              onClick={() => setMood(m.name)}
              variant={mood === m.name ? "default" : "outline"}
              className="flex items-center gap-2 px-4 py-2 text-lg rounded-full"
            >
              {m.icon} {m.name}
            </Button>
          ))}
        </div>
      </section>

      <section>
        <h2 className="text-2xl font-semibold mb-6 text-gray-800 text-center">
          {mood ? `${mood}에 어울리는 추천 메뉴 🍱` : "AI 추천 인기 메뉴 🍱"}
        </h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {recommendations.map((food) => (
            <Card key={food.title} className="shadow-lg hover:shadow-2xl transition-shadow duration-300">
              <img src={food.img} alt={food.title} className="h-48 w-full object-cover rounded-t-2xl" />
              <CardContent className="p-4">
                <h3 className="text-lg font-semibold text-gray-800">{food.title}</h3>
                <p className="text-sm text-gray-600 mt-2">{food.desc}</p>
              </CardContent>
            </Card>
          ))}
        </div>
      </section>

      <footer className="mt-16 text-center text-sm text-gray-500">
        © 2025 Team MenuMate | Data from 쿠팡 API & FatSecret | Made with 💙
      </footer>
    </div>
  );
}





## 📂 포트폴리오 진행 예정

## 1. 주제 선정
음식 추천 및, 영양소 알리미

## 2. 기획의도 & 아이디어

ex)
기획의도 - 오늘의 메뉴 알리미는 사용자의 식습관, 영양 선호, 기분 상태 등을 기반으로 맞춤형 음식메뉴를 추천하는  영양소 알리미입니다,
 해당 알리미는 개인의 건강 목표, 선호도, 알레르기 정보 등을 반영하여 영양 균형과 만족도를 동시에 높이는 식사 제안을 제공합니다.
 또한, 커뮤니티 기능을 통해 사용자 간 레시피 공유 및 피드백을 주고받을 수 있습니다.

아이디어 - 필요로하는 기능 모두 적으주시면 감사하겠습니다. (최소 3개이상)
- 1. 사용자 선호 음식/영양소 질문 및 보기 등록 (예: "단백질 많은 음식 좋아하시나요?")
- 2. 선호도 기반 유사 음식 카테고리 통계 및 추천
- 3. 사용자 기분/상태 기반 메뉴 추천 (예: 스트레스 → 진정 효과 있는 음식 추천)
- 4. 알레르기/기피 성분 필터링 기능
- 5. 커뮤니티 기반 레시피 공유 및 좋아요/댓글 기능
- 6. 추천 메뉴의 영양 정보 시각화 (예: 탄단지 비율 그래프)

모듈별 주요 테이블 : Question (질문 문항 저장 / 어떤맛을 선호하시나요) , Choice (각 질문에 대한 선택지 저장) , FoodType (음식 카테고리 정보 / 양식, 한식, 고단백, 중식 등), EnergyType (영양소 정보 / 단백질, 탄수화물, 지방),
                      질문 선호 등록                                    질문 선호 등록                            음식 분류                                                영양소 분류
                    UserProfile (사용자 기본 정보 및 선호도 저장) , Recommendation (추천된 메뉴 및 이유 저장 ), Recipe (사용자 레시피 등록 정보), Comment(레시피에 대한 댓글 및 피드백), Allergy (알레르기 성분 정보 및 사용자 연결)
                    사용자 정보                                        추천결과                                커뮤니티                          커뮤니티                                필터링 ← 필터링은 선택지






<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>































💬  커뮤니케이션은 자주, 간단하게

- 매일 또는 격일로 짧은 스탠드업 미팅 (진행 상황 공유)

- 질문은 빠르게, 모르면 바로 물어보기

- 카카오톡, 디스코드 활용하면 좋아요


💬 1. 간단한 실시간 채팅 웹앱 만들기
기술 스택: HTML/CSS, JavaScript, Firebase 또는 Socket.io 목표: 사용자들이 실시간으로 메시지를 주고받을 수 있는 웹 기반 채팅 앱

왜 좋은가요?

프론트엔드와 백엔드 역할을 나눌 수 있어 협업에 적합해요.

실시간 통신의 기본 개념을 배울 수 있어요.

UI/UX 디자인, 사용자 인증, 메시지 저장 등 다양한 기능을 추가하며 확장 가능해요.

추가 아이디어:

닉네임 설정 기능

이모지 전송

채팅방 분리 기능

🎮 2. 미니 게임 개발 (예: 숫자 맞히기, 틱택토, 카드 뒤집기)
기술 스택: HTML/CSS, JavaScript (또는 Python with Pygame) 목표: 간단한 규칙을 가진 게임을 직접 구현하고 플레이 할 수 있게 만들기

왜 좋은가요?

게임 로직을 짜면서 조건문, 반복문, 이벤트 처리 등 기초 문법을 자연스럽게 익혀요

UI 구성과 사용자 인터랙션을 직접 다뤄보며 재미있게 배울 수 있어요.

팀원 간 역할 분담이 쉬워요 (디자인, 로직, 테스트 등)

추가 아이디어 : 

점수판 기능

난이도 조절

모바일 대응 UI
