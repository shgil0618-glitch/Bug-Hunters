import React, { useEffect, useState } from "react";
import { useRouter } from "next/router";
import { useSelector } from "react-redux";
import { List, Avatar, Button, Input, Card, Spin, message, Typography, Empty } from "antd";
import { MessageOutlined, UserOutlined, PlusOutlined } from "@ant-design/icons";
import api from "../../api/axios";

const { Title } = Typography;

export default function ChatIndex() {
  const router = useRouter();
  const { user } = useSelector((state) => state.auth);
  const [rooms, setRooms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchNickname, setSearchNickname] = useState("");
  const [isCreating, setIsCreating] = useState(false);

  useEffect(() => {
    const fetchRooms = async () => {
      try {
        setLoading(true);
        const { data } = await api.get("/api/chat/rooms");
        setRooms(data);
      } catch (err) {
        message.error("채팅 목록을 불러오지 못했습니다.");
      } finally {
        setLoading(false);
      }
    };
    if (user) fetchRooms();
  }, [user]);

  const handleCreateChat = async () => {
    if (!searchNickname.trim()) return message.warning("상대방 닉네임을 입력하세요.");
    
    setIsCreating(true);
    try {
      const { data } = await api.post("/api/chat/room/by-nickname", { 
        nickname: searchNickname 
      });
      
      message.success("채팅방으로 이동합니다.");
      // 파일명이 [roomId].js 이므로 쿼리 파라미터를 roomId로 명시적으로 전달하거나
      // 경로 뒤에 id를 붙여 이동합니다.
      router.push(`/chat/${data.id}`); 
    } catch (err) {
      message.error("존재하지 않는 사용자이거나 방 생성에 실패했습니다.");
    } finally {
      setIsCreating(false);
    }
  };

  if (!user) return <div style={{ padding: 100, textAlign: 'center' }}><Spin size="large" /></div>;

  return (
    <div style={{ maxWidth: 700, margin: "40px auto", padding: "0 20px" }}>
      <div style={{ marginBottom: 24 }}>
        <Title level={3}><MessageOutlined /> 메시지</Title>
      </div>

      <Card style={{ marginBottom: 24, borderRadius: 12, boxShadow: "0 4px 12px rgba(0,0,0,0.05)" }}>
        <div style={{ fontWeight: 'bold', marginBottom: 12 }}>새로운 대화 시작</div>
        <div style={{ display: 'flex', gap: 8 }}>
          <Input 
            prefix={<UserOutlined />}
            placeholder="상대방 닉네임 입력" 
            value={searchNickname}
            onChange={(e) => setSearchNickname(e.target.value)}
            onPressEnter={handleCreateChat}
            style={{ borderRadius: 8 }}
          />
          <Button 
            type="primary" 
            icon={<PlusOutlined />} 
            loading={isCreating} 
            onClick={handleCreateChat}
            style={{ borderRadius: 8 }}
          >
            방 만들기
          </Button>
        </div>
      </Card>

      <List
        header={<div style={{ fontWeight: 'bold', padding: '0 4px' }}>참여 중인 대화</div>}
        bordered
        style={{ background: '#fff', borderRadius: 12, overflow: 'hidden' }}
        loading={loading}
        dataSource={rooms}
        locale={{ 
          emptyText: <Empty description="진행 중인 대화가 없습니다." style={{ padding: '40px 0' }} /> 
        }}
        renderItem={(item) => (
          <List.Item
            // item.id가 제대로 넘어오는지 확인 (백엔드 필드명이 다르면 수정 필요)
            onClick={() => router.push(`/chat/${item.id}`)}
            style={{ 
              cursor: 'pointer', 
              padding: '16px 20px', 
              transition: 'all 0.2s'
            }}
            className="chat-list-item"
          >
            <List.Item.Meta
              avatar={
                <Avatar 
                  size={48} 
                  src={item.opponentProfile?.startsWith('http') 
                    ? item.opponentProfile 
                    : `${process.env.NEXT_PUBLIC_API_BASE_URL}/${item.opponentProfile}`}
                  icon={<UserOutlined />} 
                />
              }
              title={<span style={{ fontSize: 16, fontWeight: 600 }}>{item.opponentNickname}</span>}
              description={
                <div style={{ 
                  color: '#666', 
                  whiteSpace: 'nowrap', 
                  overflow: 'hidden', 
                  textOverflow: 'ellipsis',
                  maxWidth: '400px'
                }}>
                  {item.lastMessage || "대화 내용이 없습니다."}
                </div>
              }
            />
            <div style={{ fontSize: 12, color: '#999' }}>{item.lastTime}</div>
          </List.Item>
        )}
      />

      <style jsx>{`
        :global(.chat-list-item:hover) {
          background-color: #f0f5ff !important;
        }
      `}</style>
    </div>
  );
}