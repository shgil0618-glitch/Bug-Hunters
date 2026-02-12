import { combineReducers } from 'redux';
import authReducer from './authReducer';
import postReducer from './postReducer';
import commentReducer from './commentReducer';
import followReducer from './followReducer';
import likeReducer from './likeReducer';
import retweetReducer from './retweetReducer';
import materialReducer from './material';
import chatReducer from './chatReducer'; // 1. chatReducer 불러오기

const rootReducer = combineReducers({
    auth: authReducer,
    post: postReducer,
    comment: commentReducer,
    follow: followReducer,
    like: likeReducer,
    retweet: retweetReducer,
    material: materialReducer,
    chat: chatReducer, // 2. 'chat'이라는 키로 등록 (이 이름이 state.chat이 됩니다)
});

export default rootReducer;