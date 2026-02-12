import { Button, Popconfirm, message, Space, Typography } from "antd";
import { EditOutlined, DeleteOutlined, QuestionCircleOutlined } from "@ant-design/icons";

const { Text } = Typography;

export default function EditDeleteButtons({ post, user, onEdit, dispatch, deletePostRequest }) {
  // ✅ 권한 확인: 작성자 ID와 로그인 유저 ID 비교 또는 관리자(1@1) 체크
  // 닉네임 비교보다는 식별자(ID) 비교가 훨씬 정확합니다.
  const isAuthor = user && (user.id === post?.authorId || user.email === '1@1');

  if (!isAuthor) return null;

  // 삭제 확인 버튼 클릭 시 실행
  const confirmDelete = () => {
    dispatch(deletePostRequest({ postId: post.id }));
    // Saga에서 처리해도 되지만, UI 피드백을 위해 여기서도 호출 가능
    // message.loading({ content: '삭제 중...', key: 'delete_loading' });
    // message.success('레시피가 삭제되었습니다.');
  };

  const buttonStyle = {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    gap: "6px",
    padding: "4px 10px",
    borderRadius: "6px",
    cursor: "pointer",
    transition: "all 0.2s",
    border: "1px solid transparent",
  };

  return (
    <div style={{ display: "flex", gap: "12px", justifyContent: "center", marginTop: "10px" }}>
      {/* 1. 수정 버튼 */}
      <div
        onClick={() => onEdit(post)}
        style={{ ...buttonStyle, color: "#555" }}
        className="edit-btn"
        onMouseEnter={(e) => {
          e.currentTarget.style.background = "#f0f0f0";
          e.currentTarget.style.borderColor = "#d9d9d9";
        }}
        onMouseLeave={(e) => {
          e.currentTarget.style.background = "transparent";
          e.currentTarget.style.borderColor = "transparent";
        }}
      >
        <EditOutlined style={{ fontSize: "18px" }} />
        <Text style={{ fontSize: "12px", color: "inherit" }}>수정</Text>
      </div>

      {/* 2. 삭제 버튼 (Popconfirm으로 감싸서 실수 방지) */}
      <Popconfirm
        title="레시피 삭제"
        description="정말로 이 게시글을 삭제하시겠습니까?"
        onConfirm={confirmDelete}
        okText="삭제"
        cancelText="취소"
        okButtonProps={{ danger: true }}
        icon={<QuestionCircleOutlined style={{ color: 'red' }} />}
      >
        <div
          style={{ ...buttonStyle, color: "#ff4d4f" }}
          onMouseEnter={(e) => {
            e.currentTarget.style.background = "#fff1f0";
            e.currentTarget.style.borderColor = "#ffccc7";
          }}
          onMouseLeave={(e) => {
            e.currentTarget.style.background = "transparent";
            e.currentTarget.style.borderColor = "transparent";
          }}
        >
          <DeleteOutlined style={{ fontSize: "18px" }} />
          <Text style={{ fontSize: "12px", color: "inherit" }}>삭제</Text>
        </div>
      </Popconfirm>
    </div>
  );
}