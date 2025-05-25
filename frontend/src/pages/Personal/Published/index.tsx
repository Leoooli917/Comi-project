import { getMyStory, downLoadStoryImg, offlineMyStory } from "../../../api/StoryApi";
import { Table, Space, Button, Input } from 'antd';
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from "react";
import { format } from 'date-fns';
import ConfirmDialog from '../../../components/ConfirmDialog';
import '../index.less';

const { Search } = Input;

const FakeCard = ({ data: { title, posterImage, id } }) => {
    const navigate = useNavigate();
    return (
        <div className="personal-story-item" onClick={() => window.open(`/productDetail?stroyId=${id.get()}`, "_blank")}>
            <div>
                <img style={{ width: "100%", minHeight: "135px", border: "none" }} src={posterImage.get()} alt="" />
            </div>
            <div className="personal-story-item-title" style={{ height: "47px" }}>
                {title.get()}
            </div>
        </div>
    );
};

export default function Published() {
    const [pagination, setPagination] = useState({
        current: 1,
        pageSize: 10,
        total: 0
    });
    const [keyWord, setKeyWord] = useState("");
    const [loading, setLoading] = useState(false);
    const [confirmVisible, setConfirmVisible] = useState(false);
    const [storyToOffLine, setStoryToOffLine] = useState(null);

    const onSearch = (value = "") => {
        setKeyWord(value);
        LoadData(0);
    };

    const columns = [
        {
            title: '故事名称',
            dataIndex: 'title',
            key: 'title',
            render: (_, record) => (
                <Space size="middle">
                    <a onClick={() => window.open(`/productDetail?stroyId=${record.id}`, "_blank")}>
                        {record.title}
                    </a>
                </Space>
            ),
        },
        {
            title: '更新时间',
            dataIndex: 'updateTime',
            key: 'updateTime',
            render: (text) => format(new Date(text), 'yyyy-MM-dd HH:mm:ss'),
        },
        {
            title: '操作',
            key: 'action',
            render: (_, record) => (
                <Space size="middle">
                    <a type="primary" onClick={() => confirmOffLine(record)}>
                        下线
                    </a>
                    <span>|</span>
                    <a type="primary" onClick={() => downLoad(record)}>
                        下载
                    </a>
                </Space>
            ),
        }
    ];

    const confirmOffLine = (record) => {
        setStoryToOffLine(record);
        setConfirmVisible(true);
    };

    const offLine = () => {
        if (storyToOffLine) {
            offlineMyStory({ storyId: storyToOffLine.id }).then(() => {
                LoadData(1);
            });
        }
        setConfirmVisible(false);
        setStoryToOffLine(null);
    };

    const [items, setItems] = useState([]);
    const LoadData = (pageIndex = pagination.current, pageSize = pagination.pageSize) => {
        const params = {
            type: 1,
            pageIndex,
            pageSize
        };
        if (keyWord) {
            params['keywords'] = keyWord;
        }
        getMyStory(params).then(res => {
            setPagination({ ...pagination, total: res.total });
            setItems([...res.records]);
            setLoading(false);
        });
    };

    async function downLoad(record) {
        let blob = await downLoadStoryImg(record.id);
        const urlToDownload = URL.createObjectURL(blob);

        // 创建一个a标签并模拟点击  
        const link = document.createElement('a');
        link.href = urlToDownload;
        link.setAttribute('download', record.title + ".zip");
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        // 释放创建的URL对象  
        URL.revokeObjectURL(urlToDownload);
    }

    useEffect(() => {
        setLoading(true);
        LoadData();
    }, [pagination.current, pagination.pageSize, keyWord]);

    return (
        <>
            <div className="search-box">
                <Search
                    placeholder="请输入故事名称"
                    allowClear
                    onSearch={onSearch}
                    style={{
                        width: 200,
                    }}
                />
            </div>

            <Table 
                columns={columns} 
                dataSource={items} 
                pagination={{
                    ...pagination,
                    onChange: (current, pageSize) => {
                        setPagination({ ...pagination, current, pageSize });
                    },            
                }}
                loading={loading} 
                rowKey={record => record.id}
            />
            
            <ConfirmDialog
                visible={confirmVisible}
                alertType="offline"
                title="下线提醒"
                content={`是否把《${storyToOffLine?.title}》进行下线处理？`}
                cardTitle={storyToOffLine?.title}
                onConfirm={offLine}
                onCancel={() => setConfirmVisible(false)}
            />
        </>
    );
}
