import {Alert, Card, Col, Flex, Row, Select, Typography} from "antd";
import {useEffect, useState} from "react";
import {FetchSemesterAPI} from "services/axios.api.service.js";
import {PacmanLoader} from "react-spinners";
import ExamTable from "components/exam.table.jsx";

const ExamPage = () => {
    const {Title} = Typography;
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    const [semesterList, setSemesterList] = useState([]);
    const [semester, setSemester] = useState(null);

    useEffect(() => {
        const handleFetchData = async () => {
            try {
                const responseData = await FetchSemesterAPI();
                if (responseData) {
                    setSemesterList(responseData);
                } else {
                    setError(responseData.error);
                }
            } catch (err) {
                setError(err.message || "Lỗi khi tải danh sách học kỳ");
            } finally {
                setIsLoading(false);
            }
        }

        handleFetchData();
    }, []);

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
                        <Title level={3}>Lịch kiểm tra</Title>
                    </Flex>
                </Card>
            </Col>

            <Col span={24}>
                <Card
                    title="Danh sách lịch kiểm tra cuối kỳ"
                    extra={
                        <Select
                            placeholder="Chọn học kỳ"
                            style={{width: 200}}
                            value={semester}
                            onChange={setSemester}
                            allowClear
                            options={semesterList.map(sem => ({value: sem.code, label: sem.code}))}
                            onSelect={(value) => {setSemester(value)}}
                        />
                    }
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
                    <ExamTable semester={semester}/>
                </Card>
            </Col>
        </Row>
    );
}

export default ExamPage;