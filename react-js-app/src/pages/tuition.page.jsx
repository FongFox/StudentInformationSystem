import {Alert, Card, Col, Flex, Row, Table, Typography} from "antd";
import {useEffect, useState} from "react";
import {FetchTuitionAPI} from "services/axios.api.service.js";
import {PacmanLoader} from "react-spinners";

const TuitionPage = () => {
    const {Title} = Typography;
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    const [tuitionList, setTuitionList] = useState([]);

    useEffect(() => {
        const handleFetchData = async () => {
            const token = localStorage.getItem("access_token");
            if (token) {
                const responseData = await FetchTuitionAPI();
                if (responseData) {
                    setTuitionList(responseData);
                } else {
                    setError(responseData.error);
                }
            }

            setIsLoading(false);
        }

        handleFetchData();
    }, []);

    const columns = [
        {
            title: 'Học kỳ',
            dataIndex: 'semesterCode',
            key: 'semesterCode',
        },
        {
            title: 'Tổng chi phí',
            dataIndex: 'total',
            key: 'total',
        },
        {
            title: 'Đã trả',
            dataIndex: 'paid',
            key: 'paid',
        },
        {
            title: 'Tiền dư',
            dataIndex: 'refund',
            key: 'refund',
        },
        {
            title: 'Tiền còn lại',
            dataIndex: 'balance',
            key: 'balance',
        },
    ];

    if (isLoading) {
        return (
            <div className="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2">
                <PacmanLoader size={30} color="#0E2E6E"/>
            </div>
        );
    }

    if (error) {
        return (<Alert type="error" message={error}/>);
    }

    return (
        <Row gutter={[16, 16]}>
            <Col span={24}>
                <Card>
                    <Flex justify="flex-start" align="center">
                        <Title level={3}>Học phí</Title>
                    </Flex>
                </Card>
            </Col>

            <Col span={24}>
                <Card
                    title="Danh sách học phí"
                    variant={false}
                    style={{
                        display: 'flex',
                        flexDirection: 'column',
                        height: '100%'
                    }}
                    styles={{
                        body: {
                            flex: 1,
                            overflowY: 'auto',
                            maxHeight: '400px',
                        }
                    }}
                >
                    <Table columns={columns} dataSource={tuitionList} pagination={false}/>
                </Card>
            </Col>
        </Row>
    );
}

export default TuitionPage;