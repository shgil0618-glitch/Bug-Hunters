import { createSlice } from "@reduxjs/toolkit";

const chatSlice = createSlice({
  name: "chat",
  initialState: {
    rooms: [],
    messages: {}, // { roomId: [messages] }
  },
  reducers: {
    setRooms: (state, action) => {
      state.rooms = action.payload;
    },
    setMessages: (state, action) => {
      const { roomId, messages } = action.payload;
      state.messages[roomId] = messages;
    },
    addMessage: (state, action) => {
      const { roomId, message } = action.payload;
      if (!state.messages[roomId]) {
        state.messages[roomId] = [];
      }
      // 중복 방지 (id가 있을 경우)
      const isDuplicate = state.messages[roomId].some(m => m.id === message.id);
      if (!isDuplicate) {
        state.messages[roomId].push(message);
      }
    },
  },
});

export const { setRooms, setMessages, addMessage } = chatSlice.actions;
export default chatSlice.reducer;