import { takeLatest, put, call } from "redux-saga/effects";
import axios from "../api/axios";
import { setMessages, setRooms } from "../reducers/chatReducer";

/**
 * 1. 채팅방 생성 (또는 기존 방 조회)
 * Payload: { user1Id, user2Id, resolve }
 */
function* createRoomSaga(action) {
  try {
    const { user1Id, user2Id, resolve } = action.payload;
    // 백엔드의 ChatRequestDto { user1Id, user2Id } 와 일치
    const { data } = yield call(() => axios.post("/api/chat/room", { user1Id, user2Id }));
    
    // 성공 시, 컴포넌트(주로 index.js)에서 넘겨준 resolve 함수를 실행하여 roomId를 전달
    if (resolve) {
      yield call(resolve, data.id);
    }
  } catch (err) {
    console.error("채팅방 생성 실패:", err);
    // 에러 발생 시 사용자 피드백 (필요 시 액션으로 분리 가능)
    alert("채팅방을 생성하거나 찾는 데 실패했습니다.");
  }
}

/**
 * 2. 메시지 내역 불러오기
 * Payload: { roomId }
 */
function* fetchMessagesSaga(action) {
  try {
    const { roomId } = action.payload;
    const { data } = yield call(() => axios.get(`/api/chat/room/${roomId}/messages`));
    yield put(setMessages({ roomId, messages: data }));
  } catch (err) {
    console.error("메시지 불러오기 실패:", err);
  }
}

/**
 * 3. 내 채팅방 목록 불러오기
 * Payload: { userId }
 */
function* fetchRoomsSaga(action) {
  try {
    const { userId } = action.payload;
    // 백엔드 GetMapping("/rooms") 의 @RequestParam Long userId 대응
    const { data } = yield call(() => axios.get(`/api/chat/rooms`, { params: { userId } }));
    yield put(setRooms(data));
  } catch (err) {
    console.error("채팅 목록 불러오기 실패:", err);
  }
}

// 최종 Root Chat Saga
export default function* chatSaga() {
  yield takeLatest("chat/createRoomRequest", createRoomSaga);
  yield takeLatest("chat/fetchMessages", fetchMessagesSaga);
  yield takeLatest("chat/fetchRoomsRequest", fetchRoomsSaga);
}