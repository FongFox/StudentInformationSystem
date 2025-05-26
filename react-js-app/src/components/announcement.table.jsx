import {Alert, Table} from 'antd';
import {useEffect, useState} from "react";
import {FetchAnnouncementsAPI, RefreshAccountAPI} from "services/axios.api.service.js";
import {PacmanLoader} from "react-spinners";

const AnnouncementTable = (props) => {
    const [data, setData] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    // eslint-disable-next-line react/prop-types
    const { category } = props;

    useEffect(() => {
        const handleFetchData = async () => {
            const token = localStorage.getItem("access_token");
            if (token) {
                const responseData = await FetchAnnouncementsAPI();
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

    // lọc theo category prop (ANNOUNCE or EVENT)
    const dataFiltered = data.filter(item => item.category === category);

    // columns định nghĩa cột
    const columns = [
        {
            title: 'Tiêu đề',
            dataIndex: 'title',
            key: 'title',
            render: (title, record) => (
                <a href={record.linkURL} target="_blank" rel="noopener noreferrer">
                    {title}
                </a>
            )
        }
    ];

    // chuyển thành dataSource cho Table
    const dataSource = dataFiltered.map((item, idx) => ({
        key: idx,
        title: item.title,
        linkURL: item.linkURL,
        imageLinkUrl: item.imageLinkUrl,
        category: item.category,
    }));

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

    return (<Table columns={columns} dataSource={dataSource} pagination={false} />);
}

export default AnnouncementTable;