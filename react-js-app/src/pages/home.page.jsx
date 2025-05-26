import {Card, Col, Row} from "antd";
import AnnouncementTable from "components/announcement.table.jsx";

const HomePage = () => {
    return (
        <Row gutter={16} className="h-full">
            <Col span={8} className="h-full flex">
                <Card title="Sự kiện" variant={false}
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
                          },
                      }}
                >
                    <AnnouncementTable category={"EVENT"} />
                </Card>
            </Col>

            <Col span={16} className="h-full flex">
                <Card
                    title="Thông báo"
                    variant={false}
                    style={{display: 'flex', flexDirection: 'column', height: '100%'}}
                    styles={{
                        body: {
                            flex: 1,
                            overflowY: 'auto',
                            maxHeight: '400px',
                        },
                    }}
                >
                    <AnnouncementTable category={"ANNOUNCE"} />
                </Card>
            </Col>
        </Row>
    );
}

export default HomePage;