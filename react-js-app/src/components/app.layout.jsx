// const AppLayout = (props) => {
//
//     return (
//         <>
//             {props.children}
//         </>
//     );
// }
//
// export default AppLayout;

// components/AppLayout.jsx
// import {Layout, Menu, Input, Dropdown, Avatar} from 'antd';
// import {
//     HomeOutlined,
//     FolderOpenOutlined,
//     ClockCircleOutlined,
//     AppstoreOutlined,
//     SettingOutlined,
// } from '@ant-design/icons';
// import {useNavigate, useLocation} from 'react-router-dom';
//
// import logo from 'assets/logo.png';
//
// const AppLayout = (props) => {
//     const navigate = useNavigate();
//     const {pathname} = useLocation();
//
//     const {Header, Sider, Content} = Layout;
//
//     // Định nghĩa menu sidebar
//     const sidebarItems = [
//         {key: '/', icon: <HomeOutlined/>, label: 'Home'},
//         {key: '/library', icon: <FolderOpenOutlined/>, label: 'My Library'},
//         {key: '/history', icon: <ClockCircleOutlined/>, label: 'History'},
//         {
//             key: 'apps',
//             icon: <AppstoreOutlined/>,
//             label: 'Apps',
//             children: [
//                 {key: '/apps/app1', label: 'App 1'},
//                 {key: '/apps/app2', label: 'App 2'},
//             ],
//         },
//         {key: '/settings', icon: <SettingOutlined/>, label: 'Settings'},
//     ];
//
//     // Menu dropdown avatar
//     const avatarMenu = (
//         <Menu onClick={({key}) => console.log('Bạn chọn:', key)}>
//             <Menu.Item key="profile">Profile</Menu.Item>
//             <Menu.Item key="settings">Settings</Menu.Item>
//             <Menu.Item key="billing">Billing</Menu.Item>
//             <Menu.Divider/>
//             <Menu.Item key="logout">Logout</Menu.Item>
//         </Menu>
//     );
//
//     const onSidebarClick = ({key}) => {
//         // Chuyển trang theo key
//         navigate(key);
//     };
//
//     const logoutUser = () => {
//     }
//
//     return (
//         <Layout style={{minHeight: '100vh'}}>
//             <Sider collapsible>
//                 <div
//                     className="logo"
//                     style={{
//                         height: 64,
//                         margin: 16,
//                         display: 'flex',
//                         alignItems: 'center',
//                         justifyContent: 'center',
//                         background: 'transparent'
//                     }}
//                 >
//                     <img
//                         src={logo}
//                         alt="Logo"
//                         style={{
//                             maxHeight: '100%',
//                             maxWidth: '100%',
//                             objectFit: 'contain'
//                         }}
//                     />
//                 </div>
//                 <Menu
//                     theme="dark"
//                     mode="inline"
//                     selectedKeys={[pathname]}
//                     onClick={onSidebarClick}
//                     items={sidebarItems}
//                 />
//             </Sider>
//             <Layout>
//                 <Header
//                     style={{
//                         background: '#fff',
//                         padding: '0 24px',
//                         display: 'flex',
//                         alignItems: 'center',
//                         justifyContent: 'flex-end',
//                     }}
//                 >
//                     <Dropdown
//                         menu={{
//                             items: [
//                                 {key: 'profile', label: 'Profile'},
//                                 {key: 'settings', label: 'Settings'},
//                                 {key: 'billing', label: 'Billing'},
//                                 {type: 'divider'},
//                                 {key: 'logout', label: 'Logout'},
//                             ],
//                             onClick: ({key}) => {
//                                 switch (key) {
//                                     case 'profile':
//                                         navigate('/profile');
//                                         break;
//                                     case 'settings':
//                                         navigate('/settings');
//                                         break;
//                                     case 'billing':
//                                         navigate('/billing');
//                                         break;
//                                     case 'logout':
//                                         // gọi hàm logout của bạn
//                                         logoutUser();
//                                         break;
//                                     default:
//                                 }
//                             },
//                         }}
//                         placement="bottomRight"
//                         arrow
//                     >
//                         <Avatar
//                             src="https://i.pravatar.cc/40"
//                             style={{cursor: 'pointer'}}
//                         />
//                     </Dropdown>
//                 </Header>
//                 <Content
//                     style={{
//                         margin: 24,
//                         padding: 24,
//                         background: '#f5f5f5',
//                         borderRadius: 4,
//                         minHeight: 360,
//                     }}
//                 >
//                     {/* eslint-disable-next-line react/prop-types */}
//                     {props.children}
//                 </Content>
//             </Layout>
//         </Layout>
//     );
// };
//
// export default AppLayout;

// import React, { useState } from 'react';
// import { Layout, Menu, Dropdown, Avatar } from 'antd';
// import {
//     HomeOutlined,
//     KeyOutlined,
//     BookOutlined,
//     ExperimentOutlined,
//     BarChartOutlined,
//     TableOutlined,
//     CommentOutlined,
//     RobotOutlined,
//     ContactsOutlined,
//     LogoutOutlined,
//     MenuFoldOutlined,
//     MenuUnfoldOutlined,
// } from '@ant-design/icons';
// import { useNavigate, useLocation } from 'react-router-dom';
//
// import logoText from 'assets/logo-text-white.png';  // hình 2
// import logoIcon from 'assets/logo-icon-white.png';  // hình 3
//
// const { Header, Sider, Content } = Layout;
//
// const sidebarItems = [
//     { key: '/', icon: <HomeOutlined />, label: 'Trang chủ' },
//     { key: '/change-password', icon: <KeyOutlined />, label: 'Đổi mật khẩu' },
//     { type: 'group', label: 'HỒ SƠ ĐÀO TẠO' },
//     { key: '/learning', icon: <BookOutlined />, label: 'Học tập' },
//     { key: '/internship', icon: <ExperimentOutlined />, label: 'Thực tập' },
//     { key: '/survey', icon: <BarChartOutlined />, label: 'Khảo sát' },
//     { type: 'group', label: 'TIỆN ÍCH' },
//     { key: '/booking', icon: <TableOutlined />, label: 'Mượn phòng' },
//     { type: 'group', label: 'THÔNG TIN CẦN BIẾT' },
//     { key: '/feedback', icon: <CommentOutlined />, label: 'Góp ý' },
//     { key: '/ai-assist', icon: <RobotOutlined />, label: 'Trợ lý AI cho học tập' },
//     { key: '/contact', icon: <ContactsOutlined />, label: 'Liên hệ' },
// ];
//
// export default function AppLayout(props) {
//     const navigate = useNavigate();
//     const { pathname } = useLocation();
//     const [collapsed, setCollapsed] = useState(false);
//
//     const onMenuClick = ({ key }) => {
//         if (key === 'logout') {
//             // TODO: gọi hàm logout thật của bạn
//             console.log('Logout…');
//             return;
//         }
//         navigate(key);
//     };
//
//     const avatarMenuItems = [
//         { key: '/profile', label: 'Thông tin cá nhân' },
//         { key: '/settings', label: 'Cài đặt' },
//         { type: 'divider' },
//         { key: 'logout', label: 'Đăng xuất' },
//     ];
//
//     return (
//         <Layout style={{ minHeight: '100vh' }}>
//             <Sider
//                 collapsible
//                 collapsed={collapsed}
//                 onCollapse={(v) => setCollapsed(v)}
//                 collapsedWidth={80}
//                 style={{ background: '#002D72' }}
//             >
//                 <div
//                     className="logo"
//                     style={{
//                         height: 64,
//                         margin: 16,
//                         display: 'flex',
//                         alignItems: 'center',
//                         justifyContent: 'center',
//                     }}
//                 >
//                     <img
//                         src={collapsed ? logoIcon : logoText}
//                         alt="Hoa Sen University"
//                         style={{ maxHeight: '100%', objectFit: 'contain' }}
//                     />
//                 </div>
//
//                 <Menu
//                     theme="dark"
//                     mode="inline"
//                     selectedKeys={[pathname]}
//                     onClick={onMenuClick}
//                     items={[
//                         ...sidebarItems,
//                         { type: 'divider' },
//                         { key: 'logout', icon: <LogoutOutlined />, label: 'Đăng xuất' },
//                     ]}
//                 />
//             </Sider>
//
//             <Layout>
//                 <Header
//                     style={{
//                         background: '#CDA869',
//                         padding: '0 16px',
//                         display: 'flex',
//                         alignItems: 'center',
//                         justifyContent: 'space-between',
//                     }}
//                 >
//                     {/* nút gấp/mở sider */}
//                     {collapsed ? (
//                         <MenuUnfoldOutlined
//                             onClick={() => setCollapsed(false)}
//                             style={{ fontSize: 20, color: '#fff', cursor: 'pointer' }}
//                         />
//                     ) : (
//                         <MenuFoldOutlined
//                             onClick={() => setCollapsed(true)}
//                             style={{ fontSize: 20, color: '#fff', cursor: 'pointer' }}
//                         />
//                     )}
//
//                     <Dropdown
//                         menu={{ items: avatarMenuItems, onClick: onMenuClick }}
//                         placement="bottomRight"
//                         arrow
//                     >
//                         <Avatar
//                             src="https://i.pravatar.cc/40"
//                             style={{ cursor: 'pointer', background: '#fff' }}
//                         />
//                     </Dropdown>
//                 </Header>
//
//                 <Content
//                     style={{
//                         margin: 16,
//                         padding: 24,
//                         background: '#f5f5f5',
//                         borderRadius: 4,
//                         minHeight: 360,
//                     }}
//                 >
//                     {props.children}
//                 </Content>
//             </Layout>
//         </Layout>
//     );
// }

// // components/AppLayout.jsx
// import React, { useState } from 'react';
// import { Layout, Menu, Dropdown, Avatar } from 'antd';
// import {
//     HomeOutlined,
//     KeyOutlined,
//     BookOutlined,
//     ExperimentOutlined,
//     BarChartOutlined,
//     TableOutlined,
//     CommentOutlined,
//     RobotOutlined,
//     ContactsOutlined,
//     LogoutOutlined,
//     MenuOutlined,
// } from '@ant-design/icons';
// import { useNavigate, useLocation } from 'react-router-dom';
//
// import logoFull from 'assets/logo-text-white.png';  // full logo
// import logoIcon from 'assets/logo-icon-white.png';  // chỉ icon
//
// const { Header, Sider, Content } = Layout;
//
// const menuItems = [
//     { key: '/', icon: <HomeOutlined />, label: 'Trang chủ' },
//     { key: '/change-password', icon: <KeyOutlined />, label: 'Đổi mật khẩu' },
//     { type: 'group', label: 'HỒ SƠ ĐÀO TẠO' },
//     { key: '/learning', icon: <BookOutlined />, label: 'Học tập' },
//     { key: '/internship', icon: <ExperimentOutlined />, label: 'Thực tập' },
//     { key: '/survey', icon: <BarChartOutlined />, label: 'Khảo sát' },
//     { type: 'group', label: 'TIỆN ÍCH' },
//     { key: '/booking', icon: <TableOutlined />, label: 'Mượn phòng' },
//     { type: 'group', label: 'THÔNG TIN CẦN BIẾT' },
//     { key: '/feedback', icon: <CommentOutlined />, label: 'Góp ý' },
//     { key: '/ai-assist', icon: <RobotOutlined />, label: 'Trợ lý AI cho học tập' },
//     { key: '/contact', icon: <ContactsOutlined />, label: 'Liên hệ' },
// ];
//
// export default function AppLayout({ children }) {
//     const navigate = useNavigate();
//     const { pathname } = useLocation();
//     const [collapsed, setCollapsed] = useState(false);
//
//     const onMenuClick = ({ key }) => {
//         if (key === 'logout') {
//             // TODO: logout logic
//             console.log('Đăng xuất…');
//             return;
//         }
//         navigate(key);
//     };
//
//     const avatarMenu = [
//         { key: '/profile', label: 'Thông tin cá nhân' },
//         { key: '/settings', label: 'Cài đặt' },
//         { type: 'divider' },
//         { key: 'logout', label: 'Đăng xuất' },
//     ];
//
//     return (
//         <Layout style={{ minHeight: '100vh' }}>
//             <Sider
//                 collapsible
//                 collapsed={collapsed}
//                 onCollapse={setCollapsed}
//                 trigger={null}            // ẩn mũi tên ở chân
//                 collapsedWidth={80}
//                 style={{ background: '#002D72' }}
//             >
//                 <div
//                     className="logo"
//                     style={{
//                         height: 48,
//                         margin: '16px 0',
//                         display: 'flex',
//                         alignItems: 'center',
//                         justifyContent: 'center',
//                     }}
//                 >
//                     <img
//                         src={!collapsed ? logoIcon : ""}
//                         alt="Hoa Sen University"
//                         style={{
//                             maxHeight: collapsed ? 32 : 48,
//                             maxWidth: collapsed ? 32 : 120,
//                             objectFit: 'contain',
//                         }}
//                     />
//                     <img
//                         src={collapsed ? logoIcon : logoFull}
//                         alt="Hoa Sen University"
//                         style={{
//                             maxHeight: collapsed ? 32 : 48,
//                             maxWidth: collapsed ? 32 : 120,
//                             objectFit: 'contain',
//                         }}
//                     />
//                 </div>
//
//                 <Menu
//                     theme="dark"
//                     mode="inline"
//                     selectedKeys={[pathname]}
//                     onClick={onMenuClick}
//                     items={[
//                         ...menuItems,
//                         { type: 'divider' },
//                         { key: 'logout', icon: <LogoutOutlined />, label: 'Đăng xuất' },
//                     ]}
//                 />
//             </Sider>
//
//             <Layout>
//                 <Header
//                     style={{
//                         background: '#CDA869',
//                         padding: '0 16px',
//                         display: 'flex',
//                         alignItems: 'center',
//                         justifyContent: 'space-between',
//                     }}
//                 >
//                     {/* Hamburger để toggle Sider */}
//                     <MenuOutlined
//                         onClick={() => setCollapsed(!collapsed)}
//                         style={{ fontSize: 24, color: '#fff', cursor: 'pointer' }}
//                     />
//
//                     <Dropdown
//                         menu={{ items: avatarMenu, onClick: onMenuClick }}
//                         placement="bottomRight"
//                         arrow
//                     >
//                         <Avatar
//                             src="https://i.pravatar.cc/40"
//                             style={{ cursor: 'pointer', background: '#fff' }}
//                         />
//                     </Dropdown>
//                 </Header>
//
//                 <Content
//                     style={{
//                         margin: 16,
//                         padding: 24,
//                         background: '#f5f5f5',
//                         borderRadius: 4,
//                         minHeight: 360,
//                     }}
//                 >
//                     {children}
//                 </Content>
//             </Layout>
//         </Layout>
//     );
// }

// components/AppLayout.jsx
// import {useState} from 'react';
// import {Layout, Menu, Dropdown, Avatar} from 'antd';
// import {
//     HomeOutlined,
//     KeyOutlined,
//     BookOutlined,
//     ExperimentOutlined,
//     BarChartOutlined,
//     TableOutlined,
//     CommentOutlined,
//     RobotOutlined,
//     ContactsOutlined,
//     LogoutOutlined,
//     MenuOutlined,
// } from '@ant-design/icons';
// import {useNavigate, useLocation} from 'react-router-dom';
//
// import logoText from 'assets/logo-text-white.png';
// import logoIcon from 'assets/logo-icon-white.png';
//
// const {Header, Sider, Content} = Layout;
//
// const menuItems = [
//     {key: '/', icon: <HomeOutlined/>, label: 'Trang chủ'},
//     {key: '/change-password', icon: <KeyOutlined/>, label: 'Đổi mật khẩu'},
//     {type: 'group', label: 'HỒ SƠ ĐÀO TẠO'},
//     {key: '/learning', icon: <BookOutlined/>, label: 'Học tập'},
//     {key: '/internship', icon: <ExperimentOutlined/>, label: 'Thực tập'},
//     {key: '/survey', icon: <BarChartOutlined/>, label: 'Khảo sát'},
//     {type: 'group', label: 'TIỆN ÍCH'}, {key: '/booking', icon: <TableOutlined/>, label: 'Mượn phòng'},
//     {type: 'group', label: 'THÔNG TIN CẦN BIẾT'},
//     {key: '/feedback', icon: <CommentOutlined/>, label: 'Góp ý'},
//     {key: '/ai-assist', icon: <RobotOutlined/>, label: 'Trợ lý AI cho học tập'},
//     {key: '/contact', icon: <ContactsOutlined/>, label: 'Liên hệ'},
// ];
//
// export default function AppLayout(props) {
//     const navigate = useNavigate();
//     const {pathname} = useLocation();
//     const [collapsed, setCollapsed] = useState(false);
//
//     const onMenuClick = ({key}) => {
//         if (key === 'logout') {
//             console.log('Đăng xuất…');
//             return;
//         }
//         navigate(key);
//     };
//
//     const avatarMenu = [
//         {key: '/profile', label: 'Thông tin cá nhân'},
//         {key: '/settings', label: 'Cài đặt'},
//         {type: 'divider'},
//         {key: 'logout', label: 'Đăng xuất'}
//     ];
//
//     return (
//         <Layout className="min-h-screen">
//             <Sider
//                 collapsible
//                 collapsed={collapsed}
//                 onCollapse={setCollapsed}
//                 trigger={null}
//                 collapsedWidth={80}
//                 className="bg-[#002D72] h-screen"
//             >
//                 <div className="flex items-center justify-center h-16">
//                     <img
//                         src={logoIcon}
//                         alt="Hoa Sen University"
//                         className="object-contain h-10 w-10"
//                     />
//                     {!collapsed && (
//                         <img
//                             src={logoText}
//                             alt="Hoa Sen University"
//                             className="object-contain h-10 ml-3"
//                         />
//                     )}
//                 </div>
//
//                 <Menu
//                     theme="dark"
//                     mode="inline"
//                     selectedKeys={[pathname]}
//                     onClick={onMenuClick}
//                     items={[...menuItems, {type: 'divider'}, {
//                         key: 'logout',
//                         icon: <LogoutOutlined/>,
//                         label: 'Đăng xuất'
//                     },]}
//                 />
//             </Sider>
//
//             <Layout>
//                 <Header className="bg-[#CDA869] flex items-center justify-between px-4">
//                     <MenuOutlined
//                         onClick={() => setCollapsed(!collapsed)}
//                         className="text-white text-2xl cursor-pointer"
//                     />
//
//                     <Dropdown
//                         menu={{items: avatarMenu, onClick: onMenuClick}}
//                         placement="bottomRight"
//                         arrow
//                     >
//                         <Avatar
//                             src="https://i.pravatar.cc/40"
//                             className="cursor-pointer bg-white"
//                         />
//                     </Dropdown>
//                 </Header>
//
//                 <Content className="m-4 p-6 bg-gray-100 rounded min-h-[360px]">
//                     {props.children}
//                 </Content>
//             </Layout>
//         </Layout>
//     );
// }

// components/AppLayout.jsx
import {useState} from 'react';
import {useNavigate, useLocation} from 'react-router-dom';

import {Layout, Menu, Dropdown, Avatar} from 'antd';
import {
    HomeOutlined,
    KeyOutlined,
    BookOutlined,
    ExperimentOutlined,
    BarChartOutlined,
    TableOutlined,
    CommentOutlined,
    RobotOutlined,
    ContactsOutlined,
    LogoutOutlined,
    MenuOutlined,
} from '@ant-design/icons';

import logoText from 'assets/logo-text-white.png';
import logoIcon from 'assets/logo-icon-white.png';

const {Header, Sider, Content} = Layout;

const menuItems = [
    {key: '/', icon: <HomeOutlined/>, label: 'Trang chủ'},
    {key: '/change-password', icon: <KeyOutlined/>, label: 'Đổi mật khẩu'},
    {type: 'group', label: 'HỒ SƠ ĐÀO TẠO'},
    {key: '/learning', icon: <BookOutlined/>, label: 'Học tập',
        children: [
            { key: '/learning/reg-results', label: 'Kết quả ĐKMH' },
            { key: '/learning/timetable',   label: 'Thời khóa biểu' },
            { key: '/learning/attendance',  label: 'Chuyên cần' },
            { key: '/learning/exams',       label: 'Lịch thi' },
            { key: '/learning/grades',      label: 'Xem Điểm' },
            { key: '/learning/tuition',     label: 'Học phí' },
            { key: '/learning/pe',          label: 'GD Thể chất' },
            { key: '/learning/events',      label: 'Sự kiện tham gia' },
            { key: '/learning/faq',         label: 'Hỏi đáp văn bản' },
        ],
    },
    {key: '/internship', icon: <ExperimentOutlined/>, label: 'Thực tập',
        children: [
            { key: '/internship/status',      label: 'Tình trạng ứng tuyển' },
            { key: '/internship/supervisor',  label: 'Giáo viên hướng dẫn' },
            { key: '/internship/report',      label: 'Nộp báo cáo' },
        ],
    },
    {key: '/survey', icon: <BarChartOutlined/>, label: 'Khảo sát',
        children: [
            { key: '/survey/course',   label: 'Đánh giá môn học' },
            { key: '/survey/teaching', label: 'Đánh giá giảng viên' },
        ],
    },
    {type: 'group', label: 'TIỆN ÍCH'}, {key: '/booking', icon: <TableOutlined/>, label: 'Mượn phòng'},
    {type: 'group', label: 'THÔNG TIN CẦN BIẾT'},
    {key: '/feedback', icon: <CommentOutlined/>, label: 'Góp ý'},
    {key: '/ai-assist', icon: <RobotOutlined/>, label: 'Trợ lý AI cho học tập'},
    {key: '/contact', icon: <ContactsOutlined/>, label: 'Liên hệ'},
];

const AppLayout = (props) => {
    const navigate = useNavigate();
    const {pathname} = useLocation();
    const [collapsed, setCollapsed] = useState(false);

    const onMenuClick = ({key}) => {
        if (key === 'logout') {
            console.log('Đăng xuất…');
            return;
        }
        navigate(key);
    };

    const avatarMenu = [
        {key: '/profile', label: 'Thông tin cá nhân'},
        {key: '/settings', label: 'Cài đặt'},
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