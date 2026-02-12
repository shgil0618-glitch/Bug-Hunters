// components/RetweetButton.js
import React from "react";
import { RetweetOutlined } from "@ant-design/icons";
import { message, Spin } from "antd";

/**
 * RetweetButton 컴포넌트
 * 
 * Props:
 * - user: 현재 로그인 사용자
 * - postId: 리트윗 대상 게시글 ID
 * - isRetweeted: 현재 사용자가 리트윗했는지 boolean
 * - retweetCount: 리트윗 수
 * - loading: 버튼 로딩 상태 boolean
 * - onToggleRetweet: 리트윗/취소 이벤트 함수
 */
export default function RetweetButton({
  user,
  postId,
  isRetweeted = false,
  retweetCount = 0,
  loading = false,
  onToggleRetweet,
}) {
  const handleClick = () => {
    if (!user) {
      message.error("로그인한 사용자만 리트윗할 수 있습니다.");
      window.location.href = "/login";
      return;
    }
    if (loading) return;

    if (typeof onToggleRetweet === "function") {
      onToggleRetweet(postId, isRetweeted);
    }
  };

  return (
    <div
      onClick={handleClick}
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        cursor: "pointer",
        padding: "4px 8px",
        borderRadius: "6px",
        transition: "background 0.2s",
      }}
      onMouseEnter={(e) => (e.currentTarget.style.background = "#f0fff0")}
      onMouseLeave={(e) => (e.currentTarget.style.background = "transparent")}
    >
      {loading && <Spin size="small" style={{ marginBottom: "2px" }} />}

      <RetweetOutlined
        style={{
          fontSize: "20px",
          color: isRetweeted ? "green" : "#555",
        }}
      />

      <div style={{ fontSize: "12px", marginTop: "4px" }}>
        리트윗 {retweetCount}
      </div>
    </div>
  );
}
