// components/FollowButton.js
import React from "react";
import { Button, message } from "antd";
import { UserAddOutlined, UserDeleteOutlined } from "@ant-design/icons";

/**
 * FollowButton 컴포넌트
 * 
 * Props:
 * - user: 현재 로그인 사용자 객체
 * - authorId: 팔로우 대상 사용자 ID
 * - isFollowing: 현재 팔로우 상태 boolean
 * - isBlocked: 차단 상태 boolean
 * - loading: 버튼 로딩 상태 boolean
 * - onToggleFollow: 팔로우/언팔로우 이벤트 함수
 */
export default function FollowButton({
  user,
  authorId,
  isFollowing = false,
  isBlocked = false,
  loading = false,
  onToggleFollow,
}) {
  const handleClick = () => {
    if (!user) {
      message.info("로그인한 사용자만 팔로우할 수 있습니다.");
      window.location.href = "/login";
      return;
    }
    if (!authorId) {
      message.error("팔로우 대상 ID가 없습니다.");
      return;
    }
    if (authorId === user.id) {
      message.warning("자기 자신은 팔로우할 수 없습니다.");
      return;
    }
    if (loading) return;

    if (typeof onToggleFollow === "function") {
      onToggleFollow(authorId);
    }
  };

  return (
    <Button
      type={isBlocked ? "default" : isFollowing ? "default" : "primary"}
      danger={isFollowing}
      icon={
        isBlocked
          ? null
          : isFollowing
          ? <UserDeleteOutlined />
          : <UserAddOutlined />
      }
      loading={loading}
      onClick={handleClick}
      style={{ borderRadius: "6px" }}
      disabled={isBlocked}
    >
      {isBlocked ? "차단 해제" : isFollowing ? "언팔로우" : "팔로우"}
    </Button>
  );
}
