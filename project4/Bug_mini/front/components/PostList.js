import React from 'react'; // React 임포트
import { Card, List } from "antd"; // ✅ 이 부분이 핵심입니다! Card와 List를 불러와야 합니다.
import PostCard from "./PostCard";

export default function PostList({
    posts, user, likes, likesCount, retweetedPosts, retweetsCount,
    expandedPostId, setExpandedPostId, handleToggleLike, handleToggleFollow,
    handleEdit, dispatch, likeLoading, followingsMap, followLoading,
    title = "피드",
}) {
    // ... 이하 동일
    // 만약 데이터가 없을 때의 처리를 추가하면 더 깔끔합니다.
    const isEmpty = !posts || posts.length === 0;

    return (
        <div style={{ minHeight: "100vh", padding: "30px 0" }}>
            <Card 
                title={<span style={{ fontSize: '1.2rem', fontWeight: 'bold' }}>{title}</span>}
                style={{ 
                    maxWidth: 700, margin: "0 auto", borderRadius: "12px",
                    boxShadow: "0 4px 12px rgba(0,0,0,0.1)", backgroundColor: "rgba(255,255,255,0.9)",
                }}
            >
                {isEmpty ? (
                    <div style={{ textAlign: 'center', padding: '50px 0' }}>등록된 레시피가 없습니다.</div>
                ) : (
                    <List 
                        itemLayout="vertical"
                        dataSource={posts}
                        rowKey={(post) => post.id}
                        renderItem={(post) => (
                            <PostCard 
                                post={post}
                                user={user}
                                likes={likes}
                                likesCount={likesCount}
                                retweetedPosts={retweetedPosts}
                                retweetsCount={retweetsCount}
                                expandedPostId={expandedPostId}
                                setExpandedPostId={setExpandedPostId}
                                handleToggleLike={handleToggleLike}
                                handleToggleFollow={handleToggleFollow}
                                handleEdit={handleEdit}
                                dispatch={dispatch}
                                likeLoading={likeLoading}
                                followingsMap={followingsMap}    
                                followLoading={followLoading}
                            />
                        )}
                    />
                )}
            </Card>
        </div>
    );
}