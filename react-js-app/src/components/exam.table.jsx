import {useEffect, useState} from "react";
import {FetchCourseExamAPI} from "services/axios.api.service.js";
import {PacmanLoader} from "react-spinners";
import {Alert, Table} from "antd";

const ExamTable = (props) => {
    const [data, setData] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    // eslint-disable-next-line react/prop-types
    const { semester } = props;

    useEffect(() => {
        const handleFetchData = async () => {
            if(semester) {
                const responseData = await FetchCourseExamAPI(semester);
                if (responseData) {
                    setData(responseData);
                } else {
                    setError(responseData.error);
                }
            }

            setIsLoading(false);
        }

        handleFetchData();
    }, [semester]);

    // columns định nghĩa cột
    const columns = [
        {
            title: 'Mã môn',
            dataIndex: 'courseCode',
            key: 'courseCode',
        },
        {
            title: 'Tên môn',
            dataIndex: 'courseName',
            key: 'courseName',
        },
        {
            title: 'Ngày kiểm tra',
            dataIndex: 'examDate',
            key: 'examDate',
        },
        {
            title: 'Giờ kiểm tra',
            dataIndex: 'examTime',
            key: 'examTime',
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

    return (<Table columns={columns} dataSource={data} pagination={false}/>);
}

export default ExamTable;