import { useRouter } from 'next/router';
import {
  Card,
  Image,
  Row,
  Col,
  Typography,
  Space,
  Avatar,
  message,
} from "antd";
import {
  CommentOutlined,
  UserAddOutlined,
  UserDeleteOutlined,
} from "@ant-design/icons";
import LikeButton from "./LikeButton";
import EditDeleteButtons from "./EditDeleteButtons";
import CommentSection from "./CommentSection";
import RetweetButton from "./RetweetButton";
import { addRetweetRequest, removeRetweetRequest } from "../reducers/retweetReducer";

const { Text } = Typography;

export default function PostCard({
  post,
  user,
  likes,
  likesCount,
  retweetedPosts,
  retweetsCount,
  expandedPostId,
  setExpandedPostId,
  handleToggleLike,
  handleToggleFollow,
  handleEdit,
  dispatch,
  likeLoading,
  followingsMap,
}) {
  const hashtagList = Array.isArray(post?.hashtags) ? post.hashtags : [];
  const isFollowing = followingsMap?.[String(post?.authorId)] === true;
  const canManage =
    user && (user.id === post?.authorId || user.email === "1@1");
  const router = useRouter();
  // 작성자 프로필 이미지
  const profileImageUrl = post?.authorUfile
    ? `${process.env.NEXT_PUBLIC_API_BASE_URL}/${post.authorUfile}`
    : "/default-avatar.png";

  const onGoDetail = () => {
    if (post?.id) {
      router.push(`/posts/${post.id}`);
    }
  };

  // 대표 이미지
  const mainImage =
    post?.images?.length > 0
      ? post.images[0].src.startsWith("http")
        ? post.images[0].src
        : `${process.env.NEXT_PUBLIC_API_BASE_URL}/${post.images[0].src}`
      : post?.mainImage
      ? post.mainImage.startsWith("http")
        ? post.mainImage
        : `${process.env.NEXT_PUBLIC_API_BASE_URL}/${post.mainImage}`
      : null;

  return (
    <Card
      bordered={true}
      style={{
        marginBottom: 32,
        borderRadius: 12,
        border: "1px solid #ddd",
        background: "#fff",
      }}
      bodyStyle={{ padding: 0 }}
    >
      {/* 상단 헤더 */}
      <Row
        justify="space-between"
        align="middle"
        style={{
          padding: "12px 16px",
          borderBottom: "1px solid #f0f0f0",
        }}
      >
        <Space align="center">
          <Avatar
            src={profileImageUrl}
            size={48}
            style={{
              border: "2px solid #ff4d4f",
              cursor: "pointer",
            }}
            onClick={() => {
              message.info("스토리 보기 기능은 준비 중입니다 ✨");
            }}
          />
          <Text strong style={{ marginLeft: 8 }}>
            {post?.authorNickname || "익명"}
          </Text>

          {/* ✅ 리트윗 표시 */}
          {retweetedPosts?.[String(post?.id)] && (
            <Text type="success" style={{ marginLeft: 12, fontSize: 13 }}>
              리트윗한 게시글입니다
            </Text>
          )}
        </Space>

        {/* 팔로우/팔로잉 버튼 */}
        <div
          style={{
            cursor: "pointer",
            display: "flex",
            alignItems: "center",
            gap: 6,
          }}
          onClick={() => {
            if (!user) return message.warning("로그인이 필요합니다.");
            handleToggleFollow(post?.authorId);
          }}
        >
          {isFollowing ? (
            <>
              <UserDeleteOutlined style={{ fontSize: 28, color: "#888" }} />
              <Text strong style={{ fontSize: 14 }}>팔로잉</Text>
            </>
          ) : (
            <>
              <UserAddOutlined style={{ fontSize: 28, color: "#1890ff" }} />
              <Text strong style={{ fontSize: 14 }}>팔로우</Text>
            </>
          )}
        </div>
      </Row>

      {/* 메인 이미지 */}
    {post?.mainImage && (
      <div 
        onClick={onGoDetail} // 이미지 영역 클릭 시 이동
        style={{ 
          display: "flex", 
          justifyContent: "center", // 가로 중앙 정렬
          alignItems: "center",     // 세로 중앙 정렬
          backgroundColor: "#f9f9f9", 
          cursor: "pointer",        // 마우스 올리면 포인터로 변경
          padding: "8px 0",
          width: "100%"
        }}
      >
        <Image
          src={post.mainImage.startsWith('http') 
            ? post.mainImage 
            : `${process.env.NEXT_PUBLIC_API_BASE_URL}/${post.mainImage}`}
          preview={false} // 확대 기능 제거
          alt="post-image"
          style={{
            maxHeight: "400px",
            width: "auto",
            maxWidth: "100%",
            objectFit: "contain",
            display: "block",
            margin: "0 auto",
          }}
        />
      </div>
    )}

      {/* 액션 버튼 */}
      <Row
        justify="start"
        align="middle"
        style={{ padding: "12px 16px" }}
        gutter={16}
      >
        {/* 좋아요 버튼: 내 글은 숨김 */}
        <Col>
          {user?.id !== post?.authorId && (
            <LikeButton
              postId={post?.id}
              user={user}
              liked={likes?.[String(post?.id)] === true}
              likes={likesCount?.[String(post?.id)] ?? post.likesCount ?? 0}
              onToggleLike={handleToggleLike}
              loading={likeLoading}
            />
          )}
        </Col>

        {/* 댓글 버튼 */}
        <Col>
          <CommentOutlined
            style={{ fontSize: 22, cursor: "pointer" }}
            onClick={() => {
              if (!user) return message.warning("로그인이 필요합니다.");
              setExpandedPostId(expandedPostId === post?.id ? null : post?.id);
            }}
          />
        </Col>

        {/* 리트윗 버튼 */}
        <Col>
          <RetweetButton
            user={user}
            postId={post?.id}
            isRetweeted={!!retweetedPosts?.[String(post?.id)]}
            retweetCount={
              retweetsCount?.[String(post?.id)] ?? post?.retweetCount ?? 0
            }
            onToggleRetweet={(postId, isRetweeted) => {
              if (!user) return message.warning("로그인이 필요합니다.");
              if (isRetweeted) {
                dispatch(removeRetweetRequest({ postId }));
              } else {
                dispatch(addRetweetRequest({ originalPostId: postId }));
              }
            }}
          />
        </Col>

        {/* 수정/삭제 버튼 */}
        {canManage && (
          <Col>
            <EditDeleteButtons
              post={post}
              user={user}
              onEdit={handleEdit}
              dispatch={dispatch}
            />
          </Col>
        )}
      </Row>

      {/* 본문 텍스트 */}
      <div style={{ padding: "0 16px 12px" }}>
        <Text style={{ fontSize: 14, lineHeight: 1.6 }}>
          {post?.description || post?.content}
        </Text>
        {hashtagList.length > 0 && (
          <div style={{ marginTop: 6 }}>
            {hashtagList.slice(0, 3).map((tag, idx) => (
              <Text key={idx} style={{ color: "#1890ff", marginRight: 8 }}>
                #{tag}
              </Text>
            ))}
          </div>
        )}
      </div>

      {/* 댓글 영역 */}
      {expandedPostId === post?.id && (
        <div style={{ padding: "0 16px 16px" }}>
          <CommentSection postId={post?.id} user={user} />
        </div>
      )}
    </Card>
  );
}