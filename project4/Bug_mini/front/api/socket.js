import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

let stompClient = null;

/**
 * 소켓 연결 함수
 */
export const connectSocket = (roomId, onMessageReceived) => {
  // 이미 연결되어 있다면 중복 연결 방지
  if (stompClient && stompClient.connected) {
    console.log("이미 연결되어 있습니다.");
    return;
  }

  stompClient = new Client({
    // 백엔드 주소 확인 (8080 또는 7031 등 실제 포트에 맞게 수정하세요)
    webSocketFactory: () => new SockJS("http://localhost:8484/ws"),
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
    
    // 디버그 로그 (연결 과정을 콘솔에서 확인 가능)
    debug: (str) => {
      console.log("STOMP: " + str);
    },
  });

  stompClient.onConnect = (frame) => {
    console.log("STOMP Connected: " + frame);
    
    // 해당 채팅방 구독
    stompClient.subscribe(`/topic/${roomId}`, (message) => {
      const msg = JSON.parse(message.body);
      onMessageReceived(msg);
    });
  };

  stompClient.onStompError = (frame) => {
    console.error("Broker reported error: " + frame.headers["message"]);
    console.error("Additional details: " + frame.body);
  };

  stompClient.activate();
};

/**
 * 메시지 전송 함수
 */
export const sendMessage = (roomId, payload) => { // 인자를 payload 객체로 받음
  if (stompClient && stompClient.connected) {
    stompClient.publish({
      destination: `/app/chat.sendMessage/${roomId}`, 
      body: JSON.stringify(payload), // payload 내부의 senderId, content가 전송됨
    });
  } else {
    console.error("메시지 전송 실패: STOMP 클라이언트가 연결되지 않았습니다.");
  }
};

/**
 * 소켓 연결 해제
 */
export const disconnectSocket = () => {
  if (stompClient) {
    stompClient.deactivate();
    stompClient = null;
    console.log("STOMP 연결이 해제되었습니다.");
  }
};

/**
 * 현재 연결 상태 반환 (컴포넌트에서 버튼 활성/비활성용으로 사용)
 */
export const isConnected = () => {
  return stompClient !== null && stompClient.connected;
};