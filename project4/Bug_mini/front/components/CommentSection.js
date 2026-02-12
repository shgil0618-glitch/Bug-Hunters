import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Input, Button, List, Typography, Popconfirm, message } from "antd"; // 추가 컴포넌트 import

import {
  fetchCommentsRequest,
  createCommentRequest,
  updateCommentRequest,
  deleteCommentRequest,
} from "../reducers/commentReducer";

const { Text } = Typography;

export default function CommentSection({ postId, user }) {
  const dispatch = useDispatch();

  // 댓글 데이터 및 로딩 상태
  const comments = useSelector((state) => state.comment.comments[postId] || []);
  const loading = useSelector((state) => state.comment.loading);

  const [newContent, setNewContent] = useState("");  
  const [editId, setEditId] = useState(null);       
  const [editContent, setEditContent] = useState(""); 

  useEffect(() => {
    if (postId) {
      dispatch(fetchCommentsRequest({ postId }));
    }
  }, [dispatch, postId]);

  // 1. 등록 핸들러 (입력 방어 코드 및 피드백 추가)
  const handleCreate = () => {
    if (!newContent.trim()) {
      return message.warning("내용을 입력해주세요.");
    }
    dispatch(createCommentRequest({ postId, dto: { content: newContent } }));
    setNewContent("");
  };

  // 2. 수정 핸들러
  const handleUpdate = (commentId) => {
    if (!editContent.trim()) {
      return message.warning("수정할 내용을 입력해주세요.");
    }
    dispatch(updateCommentRequest({ postId, commentId, dto: { content: editContent } }));
    setEditId(null);
    setEditContent("");
  };

  // 3. 삭제 핸들러 (Saga 연동 확인 필요)
  const handleDelete = (commentId) => {
    dispatch(deleteCommentRequest({ postId, commentId }));
  };

  return (
    <div style={{
      marginTop: "20px",
      padding: "16px",
      backgroundColor: "#ffffff", // 조금 더 밝게 변경
      border: "1px solid #f0f0f0",
      borderRadius: "8px",
    }}>
      <Text strong style={{ fontSize: '16px' }}>💬 댓글 {comments.length}개</Text>

      {/* 댓글 입력창 */}
      {user ? (
        <div style={{ marginTop: "16px", marginBottom: "24px" }}>
          <Input.TextArea
            rows={3}
            value={newContent}
            onChange={(e) => setNewContent(e.target.value)}
            placeholder="상대방을 비방하는 댓글은 차단될 수 있습니다."
          />
          <div style={{ textAlign: 'right', marginTop: '8px' }}>
            <Button type="primary" onClick={handleCreate} loading={loading}>
              댓글 등록
            </Button>
          </div>
        </div>
      ) : (
        <div style={{ margin: "20px 0", color: "#888" }}>로그인 후 댓글을 남길 수 있습니다.</div>
      )}

      {/* 댓글 목록 */}
      <List
        className="comment-list"
        loading={loading}
        itemLayout="horizontal"
        dataSource={comments}
        renderItem={(c) => (
          <List.Item
            actions={
              user && user.nickname === c.authorNickname
                ? [
                    editId === c.id ? (
                      <div key="edit-actions">
                        <Button type="link" onClick={() => handleUpdate(c.id)}>저장</Button>
                        <Button type="link" danger onClick={() => setEditId(null)}>취소</Button>
                      </div>
                    ) : (
                      <Button type="link" key="edit" onClick={() => {
                        setEditId(c.id);
                        setEditContent(c.content);
                      }}>수정</Button>
                    ),
                    <Popconfirm 
                      key="delete" 
                      title="댓글을 삭제하시겠습니까?" 
                      onConfirm={() => handleDelete(c.id)}
                      okText="예"
                      cancelText="아니오"
                    >
                      <Button type="link" danger>삭제</Button>
                    </Popconfirm>,
                  ]
                : []
            }
          >
            {editId === c.id ? (
              <Input.TextArea
                rows={2}
                value={editContent}
                onChange={(e) => setEditContent(e.target.value)}
                style={{ width: '100%' }}
              />
            ) : (
              <List.Item.Meta
                title={<Text strong>{c.authorNickname}</Text>}
                description={
                  <div>
                    <div style={{ color: "#333", fontSize: "14px", margin: "4px 0" }}>{c.content}</div>
                    <div style={{ fontSize: "12px", color: "#bfbfbf" }}>
                      {c.createdAt ? new Date(c.createdAt).toLocaleString() : "방금 전"}
                    </div>
                  </div>
                }
              />
            )}
          </List.Item>
        )}
      />
    </div>
  );
}