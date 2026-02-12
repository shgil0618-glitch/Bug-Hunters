import { useEffect, useState, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { connectSocket, disconnectSocket, sendMessage } from "../api/socket";
import { addMessage } from "../reducers/chatReducer";

export default function ChatRoom({ roomId, user }) {
  const dispatch = useDispatch();
  const messages = useSelector((state) => state.chat.messages[roomId] || []);
  const [input, setInput] = useState("");
  const scrollRef = useRef();

  // 1. 초기 메시지 로드 및 소켓 연결/해제
  // ChatRoom.js 수정

useEffect(() => {
  if (!roomId) return;

  // 1. 과거 내역 불러오기
  dispatch({ type: "chat/fetchMessages", payload: { roomId } });

  // 2. 소켓 연결 및 구독
  connectSocket(roomId, (msg) => {
    console.log("실시간 수신 메시지:", msg); // 데이터가 들어오는지 확인용
    dispatch(addMessage({ roomId, message: msg }));
  });

  // 3. 클린업 함수
  return () => {
    // 여기서 disconnect를 하면 rebuild 시마다 끊길 수 있습니다.
    // 방을 나갈 때만 끊고 싶다면 이대로 유지하되, 
    // connectSocket 내부의 "이미 연결되어 있다면 리턴" 로직이 잘 작동해야 합니다.
    disconnectSocket(); 
  };
}, [roomId]); // dispatch는 변하지 않으므로 roomId만 감시
  // useEffect(() => {
  //   if (!roomId) return;

  //   // 과거 내역 불러오기 (chatSaga 호출)
  //   dispatch({ type: "chat/fetchMessages", payload: { roomId } });

  //   // 소켓 연결 및 구독
  //   connectSocket(roomId, (msg) => {
  //     dispatch(addMessage({ roomId, message: msg }));
  //   });

  //   // Clean-up: 컴포넌트 언마운트 시 소켓 리스너 제거 및 연결 종료
  //   return () => {
  //     disconnectSocket(roomId);
  //   };
  // }, [roomId, dispatch]);

  // 2. 새 메시지 올 때마다 하단 자동 스크롤
  useEffect(() => {
    if (scrollRef.current) {
      scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
    }
  }, [messages]);

  // 3. 메시지 전송 핸들러
// ChatRoom.js 내 handleSend 함수 예시
const handleSend = () => {
  if (input.trim() && user) {
    const messagePayload = {
      roomId: roomId,         // @Getter private Long roomId;
      senderId: user.id,      // @Getter private Long senderId;
      content: input          // @Getter private String content;
    };

    // socket.js의 sendMessage 호출
    sendMessage(roomId, messagePayload); 
    setInput("");
  }
};

  return (
    <div style={{ display: "flex", flexDirection: "column", height: "500px", border: "1px solid #ddd", padding: "10px", borderRadius: "8px" }}>
      <div
        ref={scrollRef}
        style={{ flex: 1, overflowY: "auto", marginBottom: 12, display: 'flex', flexDirection: 'column' }}
      >
        {messages.map((m, idx) => {
          /* ⭐ 핵심 수정: 백엔드 ChatResponseDto.Message 구조에 맞춤
             m.sender?.id 대신 m.senderNickname을 사용합니다.
          */
          const isMe = m.senderNickname === user.nickname; 
          
          return (
            <div key={idx} style={{ 
              alignSelf: isMe ? 'flex-end' : 'flex-start',
              margin: '5px 0',
              textAlign: isMe ? 'right' : 'left',
              maxWidth: '70%'
            }}>
              {/* 내가 아닌 경우에만 닉네임 표시 */}
              {!isMe && (
                <small style={{ display: 'block', color: '#888', marginBottom: '2px', marginLeft: '4px' }}>
                  {m.senderNickname}
                </small>
              )}
              
              <div style={{ 
                background: isMe ? '#1890ff' : '#e4e6eb',
                color: isMe ? '#fff' : '#000',
                padding: '8px 12px',
                borderRadius: isMe ? '15px 15px 0 15px' : '15px 15px 15px 0',
                display: 'inline-block',
                wordBreak: 'break-word',
                boxShadow: '0 1px 2px rgba(0,0,0,0.1)'
              }}>
                {m.content}
              </div>
            </div>
          );
        })}
      </div>

      {/* 입력부 */}
      <div style={{ display: "flex", gap: "8px" }}>
        <input
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyPress={(e) => e.key === 'Enter' && handleSend()}
          placeholder="메시지를 입력하세요..."
          style={{ 
            flex: 1, 
            padding: '10px', 
            borderRadius: '20px', 
            border: '1px solid #ccc',
            outline: 'none'
          }}
        />
        <button 
          onClick={handleSend}
          style={{ 
            padding: '8px 16px', 
            borderRadius: '20px', 
            border: 'none', 
            background: '#1890ff', 
            color: 'white', 
            cursor: 'pointer' 
          }}
        >
          전송
        </button>
      </div>
    </div>
  );
}