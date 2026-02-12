// pages/posts/[id].js
import React, { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import { useDispatch, useSelector } from 'react-redux';
import { Button, Spin, Typography, Divider, Row, Tag, Space, Card, Steps } from 'antd';
import { 
  ArrowLeftOutlined, 
  LikeOutlined, 
  RetweetOutlined, 
  CommentOutlined, 
  UserOutlined, 
  ClockCircleOutlined 
} from '@ant-design/icons';
import { fetchPostRequest, deletePostRequest, updatePostRequest } from '../../reducers/postReducer';
import EditDeleteButtons from '../../components/EditDeleteButtons';
import EditPostModal from '../../components/EditPostModal';

const { Title, Text } = Typography;
const { Step } = Steps;

export default function PostDetail() {
  const router = useRouter();
  const { id } = router.query;
  const dispatch = useDispatch();

  const { currentPost, loading, error, updatePostLoading } = useSelector((state) => state.post);
  const { user } = useSelector((state) => state.auth);

  const [isEditModalVisible, setIsEditModalVisible] = useState(false);
  const [editPost, setEditPost] = useState(null);
  const [uploadFiles, setUploadFiles] = useState([]);

  useEffect(() => {
    if (id) {
      dispatch(fetchPostRequest({ postId: id }));
    }
  }, [id, dispatch]);

  if (loading || !currentPost) {
    return (
      <div style={{ textAlign: 'center', padding: '120px 0' }}>
        <Spin size="large" tip="레시피를 불러오고 있습니다..." />
      </div>
    );
  }

  if (error) {
    return (
      <div style={{ textAlign: 'center', marginTop: 80 }}>
        <Title level={4} type="danger">❌ 존재하지 않는 게시글이거나 삭제된 게시글입니다.</Title>
        <Button type="primary" onClick={() => router.push('/')}>홈으로 돌아가기</Button>
      </div>
    );
  }

  const handleEdit = (post) => {
    setEditPost(post);
    setIsEditModalVisible(true);
  };

  return (
    <div style={{ maxWidth: 900, margin: '40px auto', padding: '0 24px' }}>
      {/* 상단 네비게이션 */}
      <Button
        type="link"
        icon={<ArrowLeftOutlined />}
        onClick={() => router.push('/')}
        style={{ marginBottom: 20 }}
      >
        목록으로 돌아가기
      </Button>

      {/* 제목 */}
      <Title level={2} style={{ marginBottom: 24, textAlign: 'center', color: '#333' }}>
        🧑‍🍳 {currentPost.title || '제목 없는 레시피'}
      </Title>

      {/* 메타 정보 */}
      <Row justify="center" style={{ marginBottom: 20 }}>
        <Space size="middle">
          {currentPost.category && <Tag color="orange">{currentPost.category}</Tag>}
          <Text strong><UserOutlined /> {currentPost.servingSize}인분</Text>
          <Text strong><ClockCircleOutlined /> {currentPost.difficulty}</Text>
          <Text strong>작성자: {currentPost.authorNickname || "익명"}</Text>
          <Text type="secondary">{new Date(currentPost.createdAt).toLocaleString()}</Text>
        </Space>
      </Row>

      {/* 대표 이미지 */}
      {currentPost.mainImage && (
        <div style={{ textAlign: 'center', marginBottom: 15 }}>
          <img
            src={currentPost.mainImage.startsWith('http') 
              ? currentPost.mainImage 
              : `${process.env.NEXT_PUBLIC_API_BASE_URL}/${currentPost.mainImage}`}
            alt="대표 이미지"
            style={{ 
              maxWidth: '70%', 
              maxHeight: '350px', 
              borderRadius: 12, 
              boxShadow: '0 4px 12px rgba(0,0,0,0.1)' 
            }}
          />
        </div>
      )}

      {/* 좋아요/리트윗/댓글 */}
      <div style={{ textAlign: 'center', marginBottom: 30 }}>
        <Space size="large">
          <Space><LikeOutlined /> {currentPost.likeCount}</Space>
          <Space><RetweetOutlined /> {currentPost.retweetCount}</Space>
          <Space><CommentOutlined /> {currentPost.commentCount}</Space>
        </Space>
      </div>

      {/* 레시피 설명 */}
      <Card style={{ marginBottom: 30, borderRadius: 12, background: '#fffbe6' }}>
        <Divider orientation="left">
          <Text strong style={{ fontSize: '18px' }}>📝 간단 설명</Text>
        </Divider>
        <div style={{ fontSize: '17px', lineHeight: '1.8', whiteSpace: 'pre-wrap', color: '#333' }}>
          {currentPost.description || currentPost.content}
        </div>
      </Card>

      {/* 재료 */}
      <Card style={{ marginBottom: 30, borderRadius: 12, background: '#f6ffed' }}>
        <Divider orientation="left">
          <Text strong style={{ fontSize: '18px' }}>🥕 위치</Text>
        </Divider>
        <ul style={{ fontSize: '17px', lineHeight: '1.8', color: '#333', paddingLeft: '20px' }}>
          {(currentPost.ingredients || '위치 정보가 없습니다.')
            .split('\n')
            .filter(line => line.trim() !== '')
            .map((item, idx) => <li key={idx}>{item}</li>)}
        </ul>
      </Card>

      {/* 상세 조리법 */}
      <Card style={{ marginBottom: 30, borderRadius: 12, background: '#e6f7ff' }}>
        <Divider orientation="left">
          <Text strong style={{ fontSize: '18px' }}>📖 간단설명</Text>
        </Divider>
        {currentPost.instructions ? (
          <Steps direction="vertical" size="small">
            {currentPost.instructions.split('\n').filter(line => line.trim() !== '').map((step, idx) => (
              <Step key={idx} title={`Step ${idx + 1}`} description={step} />
            ))}
          </Steps>
        ) : (
          <div style={{ fontSize: '17px', lineHeight: '1.8', color: '#333' }}>
            게시글이 등록되지 않았습니다.
          </div>
        )}
      </Card>

      {/* 수정/삭제 버튼 */}
      <div style={{ textAlign: 'center', marginTop: 20 }}>
        <EditDeleteButtons 
          post={currentPost} 
          user={user} 
          onEdit={handleEdit} 
          dispatch={dispatch} 
          deletePostRequest={deletePostRequest} 
        />
      </div>

      {/* 수정 모달 */}
      <EditPostModal
        visible={isEditModalVisible}
        editPost={editPost}
        loading={updatePostLoading}
        onCancel={() => {
          setIsEditModalVisible(false);
          setEditPost(null);
        }}
        onSubmit={(values) => {
          dispatch(updatePostRequest({ ...values, postId: editPost.id }));
          setIsEditModalVisible(false);
        }}
        uploadFiles={uploadFiles}
        setUploadFiles={setUploadFiles}
      />
    </div>
  );
}