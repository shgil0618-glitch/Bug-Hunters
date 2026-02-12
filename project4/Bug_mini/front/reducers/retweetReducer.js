import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  retweets: {},       
  retweetsCount: {},  
  loading: false,
  error: null,
};

const retweetSlice = createSlice({
  name: 'retweet',
  initialState,
  reducers: {
    addRetweetRequest: (state) => { 
      state.loading = true; 
      state.error = null;
    },
    addRetweetSuccess: (state, action) => {
      state.loading = false;
      // ✅ 백엔드/Saga와 변수명 통일: originalPostId
      const { originalPostId, retweetCount } = action.payload; 
      state.retweets[String(originalPostId)] = true;
      state.retweetsCount[String(originalPostId)] = retweetCount;      
    },
    addRetweetFailure: (state, action) => {
      state.loading = false;
      state.error = action.payload;
    },

    removeRetweetRequest: (state) => { state.loading = true; },
    removeRetweetSuccess: (state, action) => {
      state.loading = false;
      // ✅ 취소 시에도 originalPostId 사용 (Saga 응답 데이터 기준)
      const { originalPostId, retweetCount } = action.payload; 
      state.retweets[String(originalPostId)] = false;
      state.retweetsCount[String(originalPostId)] = retweetCount;     
    },
    removeRetweetFailure: (state, action) => {
      state.loading = false;
      state.error = action.payload;
    },

    hasRetweetedRequest: (state) => { state.loading = true; },
    hasRetweetedSuccess: (state, action) => {
      state.loading = false;
      const { postId, hasRetweeted } = action.payload;
      state.retweets[String(postId)] = hasRetweeted;
    },
    hasRetweetedFailure: (state, action) => {
      state.loading = false;
      state.error = action.payload;
    },

    fetchMyRetweetsRequest: (state) => { state.loading = true; },
    fetchMyRetweetsSuccess: (state, action) => {
      state.loading = false;
      const retweetedPosts = action.payload; 
      state.retweets = { ...state.retweets, ...retweetedPosts };  
    },
    fetchMyRetweetsFailure: (state, action) => {
      state.loading = false;
      state.error = action.payload;
    },
  },
});

export const {
  addRetweetRequest, addRetweetSuccess, addRetweetFailure,
  removeRetweetRequest, removeRetweetSuccess, removeRetweetFailure,
  hasRetweetedRequest, hasRetweetedSuccess, hasRetweetedFailure,
  fetchMyRetweetsRequest, fetchMyRetweetsSuccess, fetchMyRetweetsFailure,
} = retweetSlice.actions;

export default retweetSlice.reducer;