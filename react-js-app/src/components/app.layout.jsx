// components/AppLayout.jsx
import {useState} from 'react';
import {useNavigate, useLocation} from 'react-router-dom';

import {Layout, Menu, Dropdown, Avatar, notification} from 'antd';
import {
    HomeOutlined,
    KeyOutlined,
    ContactsOutlined,
    LogoutOutlined,
    MenuOutlined,
    FileTextOutlined,
    PrinterOutlined,
} from '@ant-design/icons';

import logoText from 'assets/logo-text-white.png';
import logoIcon from 'assets/logo-icon-white.png';
import {LogoutAPI} from "services/axios.api.service.js";

const {Header, Sider, Content} = Layout;

const menuItems = [
    {key: '/', icon: <HomeOutlined/>, label: 'Trang chủ'},
    {key: '/pwd', icon: <KeyOutlined/>, label: 'Đổi mật khẩu'},
    {type: 'group', label: 'HỌC TẬP'},
    {key: '/grades', icon: <FileTextOutlined/>, label: 'Xem điểm'},
    {type: 'group', label: 'TIỆN ÍCH'},
    {key: '/photocopy', icon: <PrinterOutlined />, label: 'Tài khoản photocopy'},
    {type: 'group', label: 'THÔNG TIN CẦN BIẾT'},
    {key: '/contact', icon: <ContactsOutlined/>, label: 'Liên hệ'}
    // {key: '/learning', icon: <BookOutlined/>, label: 'Học tập',
    //     children: [
    //         { key: '/learning/reg-results', label: 'Kết quả ĐKMH' },
    //         { key: '/learning/timetable',   label: 'Thời khóa biểu' },
    //         { key: '/learning/attendance',  label: 'Chuyên cần' },
    //         { key: '/learning/exams',       label: 'Lịch thi' },
    //         { key: '/learning/grades',      label: 'Xem Điểm' },
    //         { key: '/learning/tuition',     label: 'Học phí' },
    //         { key: '/learning/pe',          label: 'GD Thể chất' },
    //         { key: '/learning/events',      label: 'Sự kiện tham gia' },
    //         { key: '/learning/faq',         label: 'Hỏi đáp văn bản' },
    //     ],
    // },
];

const AppLayout = (props) => {
    const navigate = useNavigate();
    const {pathname} = useLocation();
    const [collapsed, setCollapsed] = useState(false);

    const onMenuClick = async (props) => {
        // eslint-disable-next-line react/prop-types
        const {key} = props;

        if (key === 'logout') {
            try {
                await LogoutAPI();
                localStorage.removeItem('access_token');
                navigate("/login");
            } catch (err) {
                notification.error({
                    message: "Lỗi ngoài dự tính!",
                    description: "Vui lòng đăng xuất rồi thử lại!"
                });
                console.log(err);
            }

            return;
        }

        navigate(key);
    };

    const avatarMenu = [
        {key: '/profile', label: 'Thông tin cá nhân'},
        // {key: '/settings', label: 'Cài đặt'},
        {type: 'divider'},
        {key: 'logout', label: 'Đăng xuất'}
    ];

    return (
        <Layout className="min-h-screen">
            <Sider
                collapsible
                collapsed={collapsed}
                onCollapse={setCollapsed}
                trigger={null}
                collapsedWidth={80}
                breakpoint="lg"
                onBreakpoint={(broken) => setCollapsed(broken)}
                width={240}
                className="bg-[#002D72]"
            >
                <div className="flex items-center justify-center h-16">
                    <img
                        src={logoIcon}
                        alt="Hoa Sen University"
                        className="object-contain h-10 w-10"
                    />
                    {!collapsed && (
                        <img
                            src={logoText}
                            alt="Hoa Sen University"
                            className="object-contain h-10 ml-3"
                        />
                    )}
                </div>

                <Menu
                    theme="dark"
                    mode="inline"
                    selectedKeys={[pathname]}
                    onClick={onMenuClick}
                    items={[
                        ...menuItems,
                        {type: 'divider'},
                        {key: 'logout', icon: <LogoutOutlined/>, label: 'Đăng xuất'},
                    ]}
                />
            </Sider>

            <Layout>
                <Header className="bg-[#CDA869] flex items-center justify-between px-4">
                    <MenuOutlined
                        onClick={() => setCollapsed(!collapsed)}
                        className="text-white text-2xl cursor-pointer"
                    />

                    <Dropdown
                        menu={{items: avatarMenu, onClick: onMenuClick}}
                        placement="bottomRight"
                        arrow
                    >
                        <Avatar
                            src="https://i.pravatar.cc/40"
                            className="cursor-pointer bg-white"
                        />
                    </Dropdown>
                </Header>

                <Content className="m-4 p-6 bg-gray-100 rounded overflow-auto">
                    {/* eslint-disable-next-line react/prop-types */}
                    {props.children}
                </Content>
            </Layout>
        </Layout>
    );
}

export default AppLayout;