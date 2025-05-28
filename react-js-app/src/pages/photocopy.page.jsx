import {Alert, Card, Col, Flex, Row, Table, Typography} from "antd";
import {useEffect, useState} from "react";
import {FetchPhotocopyAPI} from "services/axios.api.service.js";
import {PacmanLoader} from "react-spinners";

const PhotocopyPage = () => {
    const {Title} = Typography;
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    const [photocopyBalance, setPhotocopyBalance] = useState(null);
    const [photocopyTransaction, setPhotocopyTransaction] = useState([]);

    useEffect(() => {
        const handleFetchData = async () => {
            const token = localStorage.getItem("access_token");
            if (token) {
                const responseData = await FetchPhotocopyAPI();
                if (responseData) {
                    setPhotocopyBalance(responseData.photocopyBalance);
                    setPhotocopyTransaction(responseData.photocopyTransactionDTOList);
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
            title: 'Ngày',
            dataIndex: 'date',
            key: 'date',
        },
        {
            title: 'Chi phí',
            dataIndex: 'amount',
            key: 'amount',
        }
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
                        <Title level={3}>Photocopy</Title>
                    </Flex>
                </Card>
            </Col>

            <Col span={24}>
                <Card
                    title="Lịch sử giao dịch photocopy"
                    extra={<div>Số tiền còn lại: {photocopyBalance}</div>}
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
                    <Table columns={columns} dataSource={photocopyTransaction} pagination={false}/>
                </Card>
            </Col>
        </Row>
    );
}

export default PhotocopyPage;