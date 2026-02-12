// pages/chat/[roomId].js
import { useRouter } from "next/router";
import { useSelector } from "react-redux";
import ChatRoom from "../../components/ChatRoom";

export default function ChatPage() {
  const router = useRouter();
  const { roomId } = router.query;

  // 로그인된 유저 정보 (store에서 가져오기)
  const user = useSelector((state) => state.auth.user);

  if (!roomId) {
    return <div>채팅방을 불러오는 중...</div>;
  }

  if (!user) {
    return <div>로그인이 필요합니다.</div>;
  }

  return (
    <div style={{ maxWidth: 800, margin: "0 auto", padding: 20 }}>
      <h2>채팅방 #{roomId}</h2>
      <ChatRoom roomId={roomId} user={user} />
    </div>
  );
}