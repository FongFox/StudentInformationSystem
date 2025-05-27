import {Card, Col, Flex, Row, Typography} from "antd";
import GradeTable from "components/grade.table.jsx";

const GradePage = () => {
    const { Title } = Typography;

    return (
        <Row gutter={[16, 16]}>
            <Col span={24}>
                <Card>
                    <Flex justify="flex-start" align="center">
                        <Title level={3}>Xem Điểm</Title>
                    </Flex>
                </Card>
            </Col>

            <Col span={24}>
                <Card
                    title="Quá trình học tập"
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
                    <GradeTable/>
                </Card>
            </Col>
        </Row>
    );
}

export default GradePage;