import {Alert, Space, Table, Tag} from 'antd';
import {useEffect, useState} from "react";
import {FetchGradesAPI} from "services/axios.api.service.js";
import {PacmanLoader} from "react-spinners";

const GradeTable = () => {
    const [data, setData] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const handleFetchData = async () => {
            const token = localStorage.getItem("access_token");
            if (token) {
                const responseData = await FetchGradesAPI();
                if (responseData) {
                    setData(responseData);
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
            title: 'Mã môn',
            dataIndex: 'code',
            key: 'code',
            render: text => <a>{text}</a>,
        },
        {
            title: 'Tên môn',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'Tín chỉ',
            dataIndex: 'credit',
            key: 'credit',
        },
        {
            title: 'Điểm',
            dataIndex: 'grade',
            key: 'grade',
        }
    ];

    if (isLoading) {
        return (
            <div className="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2">
                <PacmanLoader size={30} color="#0E2E6E" />
            </div>
        );
    }

    if (error) {
        return (<Alert type="error" message={error}/>);
    }

    return (
        <Table columns={columns} dataSource={data} pagination={false} />
    );
}

export default GradeTable;