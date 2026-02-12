import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  posts: [],            
  likedPosts: [],        
  currentPost: null,     
  myAndRetweets: [],     
  loading: false,        
  error: null,           
  createPostDone: false, 
  updatePostDone: false, // ✅ 수정 완료 여부 추가
  deletePostDone: false, 
};

const postSlice = createSlice({
  name: 'post',
  initialState,
  reducers: {
    // 0. 상태 초기화 액션
    CREATE_POST_RESET: (state) => {
      state.createPostDone = false;
      state.error = null;
    },
    // ✅ 수정 상태 초기화 액션 추가
    UPDATE_POST_RESET: (state) => {
      state.updatePostDone = false;
      state.error = null;
    },

    // 1. 기본 조회
    fetchPostsRequest: (state) => { state.loading = true; state.error = null; },
    fetchPostsSuccess: (state, action) => {
      state.loading = false;
      state.posts = action.payload;
    },
    fetchPostsFailure: (state, action) => {
      state.loading = false;
      state.error = action.payload;
    },

    fetchPostRequest: (state) => { state.loading = true; state.error = null; },
    fetchPostSuccess: (state, action) => {
      state.loading = false;
      state.currentPost = action.payload;
    },
    fetchPostFailure: (state, action) => {
      state.loading = false;
      state.error = action.payload;
      state.currentPost = null;
    },

    // 2. 페이징 조회
    fetchPostsPagedRequest: (state) => { state.loading = true; state.error = null; },
    fetchPostsPagedSuccess: (state, action) => {
  state.loading = false;
  const { data, page } = action.payload;

  if (page === 1) {
    // ✅ 첫 페이지면 기존 posts 초기화 후 덮어쓰기
    state.posts = data;
  } else {
    // ✅ 그 외 페이지는 append
    const merged = [...state.posts, ...data];
    const unique = merged.filter(
      (post, index, self) => index === self.findIndex(p => p.id === post.id)
    );
    state.posts = unique.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
  }
},
    fetchPostsPagedFailure: (state, action) => { state.loading = false; state.error = action.payload; },

    // 3. 카테고리/해시태그 검색
    fetchCategoryPostsRequest: (state) => { state.loading = true; state.error = null; },
    fetchCategoryPostsSuccess: (state, action) => {
      state.loading = false;
      state.posts = action.payload;
    },
    fetchCategoryPostsFailure: (state, action) => { state.loading = false; state.error = action.payload; },

    searchHashtagRequest: (state) => { state.loading = true; state.error = null; },
    searchHashtagSuccess: (state, action) => {
      state.loading = false;
      state.posts = action.payload;
    },
    searchHashtagFailure: (state, action) => { state.loading = false; state.error = action.payload; },

    // 4. 활동 조회 (좋아요/내글)
    fetchLikedPostsRequest: (state) => { state.loading = true; state.error = null; },
    fetchLikedPostsSuccess: (state, action) => {
      state.loading = false;
      state.likedPosts = action.payload;
    },
    fetchLikedPostsFailure: (state, action) => { state.loading = false; state.error = action.payload; },

    fetchMyAndRetweetsRequest: (state) => { state.loading = true; state.error = null; },
    fetchMyAndRetweetsSuccess: (state, action) => {
      state.loading = false;
      state.myAndRetweets = action.payload;
    },
    fetchMyAndRetweetsFailure: (state, action) => { state.loading = false; state.error = action.payload; },

    // 5. CRUD
    createPostRequest: (state) => { 
      state.loading = true; 
      state.error = null; 
      state.createPostDone = false; 
    },
    createPostSuccess: (state, action) => {
      state.loading = false;
      state.createPostDone = true; 
      state.posts.unshift(action.payload);
    },
    createPostFailure: (state, action) => {
      state.loading = false;
      state.error = action.payload;
    },

    updatePostRequest: (state) => { 
      state.loading = true; 
      state.error = null; 
      state.updatePostDone = false; // 시작할 때 false
    },
    updatePostSuccess: (state, action) => {
      state.loading = false;
      state.updatePostDone = true; // ✅ 성공 시 true
      state.posts = state.posts.map(p => p.id === action.payload.id ? action.payload : p);
      if (state.currentPost && state.currentPost.id === action.payload.id) {
        state.currentPost = action.payload;
      }
    },
    updatePostFailure: (state, action) => { 
      state.loading = false; 
      state.error = action.payload; 
    },

    deletePostRequest: (state) => { state.loading = true; state.error = null; },
    deletePostSuccess: (state, action) => {
      state.loading = false;
      state.deletePostDone = true; 
      state.posts = state.posts.filter(p => p.id !== action.payload);
      if (state.currentPost && state.currentPost.id === action.payload) {
        state.currentPost = null;
      }
    },
    deletePostFailure: (state, action) => { state.loading = false; state.error = action.payload; },
  },
});

export const {
  CREATE_POST_RESET,
  UPDATE_POST_RESET, // ✅ 내보내기 추가
  fetchPostsRequest, fetchPostsSuccess, fetchPostsFailure,
  fetchPostRequest, fetchPostSuccess, fetchPostFailure,
  fetchPostsPagedRequest, fetchPostsPagedSuccess, fetchPostsPagedFailure,
  fetchCategoryPostsRequest, fetchCategoryPostsSuccess, fetchCategoryPostsFailure,
  searchHashtagRequest, searchHashtagSuccess, searchHashtagFailure,
  fetchLikedPostsRequest, fetchLikedPostsSuccess, fetchLikedPostsFailure,
  fetchMyAndRetweetsRequest, fetchMyAndRetweetsSuccess, fetchMyAndRetweetsFailure,
  createPostRequest, createPostSuccess, createPostFailure,
  updatePostRequest, updatePostSuccess, updatePostFailure,
  deletePostRequest, deletePostSuccess, deletePostFailure,
} = postSlice.actions;

export default postSlice.reducer;