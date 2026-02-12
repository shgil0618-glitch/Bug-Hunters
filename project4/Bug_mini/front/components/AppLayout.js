import { Layout, Menu, Input, Row, Col, Drawer, Button, Grid } from "antd";
import { MenuOutlined, SearchOutlined } from "@ant-design/icons";
import Link from "next/link";
import { useSelector, useDispatch } from "react-redux";
import axios from "../api/axios";
import { logout, loginSuccess } from "../reducers/authReducer";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";

const { Header, Content } = Layout;
const { useBreakpoint } = Grid;

function AppLayout({ children, initialUser }){
    const { user } = useSelector((state) => state.auth);
    const dispatch = useDispatch();
    const router = useRouter();
    const screens = useBreakpoint();

    const [drawerOpen, setDrawerOpen] = useState(false);
    const [searchValue, setSearchValue] = useState("");

    // SSR 데이터와 리덕스 동기화
    useEffect(() => {
        if (initialUser && !user && initialUser.nickname) {
            dispatch(loginSuccess({ user: initialUser }));
        }
    }, [initialUser, user, dispatch]);

    // 보호할 경로 목록 (관리자 페이지 추가)
    const protectedRouter = ["/mypage", "/followers", "/followings", "/adminPage"];

    useEffect(() => {
        const hasToken = typeof window !== "undefined" && localStorage.getItem("accessToken");

        // 유저 정보가 아예 없고, 보호된 경로에 진입했을 때만 체크
        if (!user && !initialUser && protectedRouter.includes(router.pathname)) {
            if (hasToken) {
                // 토큰이 있으면 서버에 물어볼 때까지 기다림 (즉시 쫓아내지 않음)
                axios.get("/auth/me")
                    .then((res) => {
                        if (res.data && res.data.nickname) {
                            dispatch(loginSuccess({ user: res.data }));
                        }
                    })
                    .catch(() => {
                        // 정말 인증이 만료된 경우만 로그아웃
                        dispatch(logout());
                        router.replace("/login");
                    });
            } else {
                // 토큰조차 없으면 로그인으로
                router.replace("/login");
            }
        }
    }, [user, initialUser, router.pathname]);

    const handleLogout = async () => {
        try {
            await axios.post("/auth/logout");
        } catch (err) {
            console.error("로그아웃 API 실패:", err);
        } finally {
            if (typeof window !== "undefined") {
                localStorage.removeItem("accessToken");
                localStorage.removeItem("user");
            }
            dispatch(logout());
            router.replace("/login");
        }
    };

    const onSearch = (value) => {
        if (value) {
            router.push(`/hashtags?tag=${encodeURIComponent(value)}`);
            setSearchValue("");
        }
    };

    const menuItems = [
        ...(user && user.nickname
            ? [
                { key: "new", label: <Link href="/posts/new">✏️ NEW POST</Link> },
                { key: "profile", label: <Link href="/mypage">👤 MYPAGE </Link> },
                {
                    key: "logout",
                    label: <a onClick={handleLogout} style={{ cursor: "pointer" }}>🔓 LOGOUT</a>,
                },
            ]
            : [
                { key: "login", label: <Link href="/login">🔒 Login</Link> },
                { key: "signup", label: <Link href="/signup">👤 Signup</Link> },
            ]
        ),
    ];

    return (
        <Layout>
            <Header style={{ padding: "0 24px", height: 64, display: "flex", alignItems: "center" }}>
                <Row align="middle" justify="space-between" style={{ width: "100%" }}>
                    <Col flex="none">
                        <Link href="/" passHref legacyBehavior>
                            <a style={{ color: "#fff", fontWeight: "bold", fontSize: "18px", marginLeft: "12px", textDecoration: "none" }}>
                                THEJOA703
                            </a>
                        </Link>
                    </Col>
                    <Col flex="auto" xs={0} sm={0} md={16} lg={18}>
                        <Menu theme="dark" mode="horizontal" items={menuItems} overflowedIndicator={null} />
                    </Col>
                    <Col flex="none" xs={2} md={0}>
                        <Button type="text" icon={<MenuOutlined style={{ color: "white", fontSize: 20 }} />} onClick={() => setDrawerOpen(true)} />
                    </Col>
                </Row>
            </Header>

            {screens.md && (
                <div style={{ display: "flex", justifyContent: "center", alignItems: "center", padding: "16px", background: "#fafafa", borderBottom: "1px solid #eaeaea" }}>
                    <Input
                        prefix={<SearchOutlined style={{ color: "#999" }} />}
                        placeholder="해시태그 검색"
                        value={searchValue}
                        onChange={(e) => setSearchValue(e.target.value)}
                        onPressEnter={(e) => onSearch(e.target.value)}
                        style={{ maxWidth: 600, width: "100%", borderRadius: "20px", background: "#fff", padding: "6px 12px" }}
                    />
                </div>
            )}

            <Drawer title="MENU" placement="right" onClose={() => setDrawerOpen(false)} open={drawerOpen}>
                <Input.Search
                    placeholder="해시태그 검색"
                    enterButton="검색"
                    value={searchValue}
                    onChange={(e) => setSearchValue(e.target.value)}
                    onSearch={(value) => { setDrawerOpen(false); onSearch(value); }}
                    style={{ marginBottom: 16 }}
                />
                <Menu mode="vertical" items={menuItems} onClick={() => setDrawerOpen(false)} />
            </Drawer>
            <Content style={{ padding: "40px" }}>{children}</Content>
        </Layout>
    );
}

export default AppLayout;