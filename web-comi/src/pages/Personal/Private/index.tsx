import { getMyStarActors, deletePrivateActor } from "../../../api/StoryApi";
import { Masonry } from "masonic";
import { Link, useNavigate } from 'react-router-dom';
import { Table, Space, Button, Input } from 'antd';
import { useHookstate } from '@hookstate/core';
import { useEffect, useLayoutEffect, useState } from "react";

const { Search } = Input;
import '../index.less'
import { format } from 'date-fns';


// const FakeCard = ({ data: { title, posterImage, id } }) => {
//     return (
//         <div className="personal-story-item" onClick={() => window.open(`/productDetail?stroyId=${id.get()}`, "_blank")}>
//             <div>
//                 <img style={{ width: "100%", minHeight: "135px", border: "none" }} src={posterImage.get() || "https://p5.ssl.qhimg.com/t012d86bc2230d8319a.png"} alt="" />
//             </div>
//             <div className="personal-story-item-title" style={{ height: "47px" }}>
//                 {title.get()}
//             </div>
//         </div>
//     )
// };

export default function Private() {
    const navigate = useNavigate();
    const [pagination, setPagination] = useState({
        current: 1,
        pageSize: 10,
        total: 0
    });
    const [loading, setLoading] = useState(false);
    const [keyWord, setKeyWord] = useState("");

    const columns = [
        {
            title: '角色头像',
            dataIndex: 'posterImage',
            key: 'posterImage',
            render: (_, record) => (
                <Space size="middle">
                    <img src={record.posterImage} width={82} height={82} style={{ borderRadius: 6 }} />
                </Space>
            ),


        },
        {
            title: '角色名称',
            dataIndex: 'actorName',
            key: 'actorName',
            render: (_, record) => (
                <Space size="middle">
                    {record.actorName}
                </Space>
            ),


        },
        {
            title: '角色描述',
            dataIndex: 'prompt',
            key: 'prompt',
            render: (_, record) => (
                <Space size="middle">
                    {record.prompt}
                </Space>
            ),
        },
        {
            title: '收录时间',
            dataIndex: 'createTime',
            key: 'createTime',
            render: (text) => format(new Date(text), 'yyyy-MM-dd HH:mm:ss'),

        },
        {
            title: '故事',
            dataIndex: 'storyName',
            key: 'storyName',
            render: (_, record) => (
                <Space size="middle">
                    {record.storyName}
                </Space>
            ),
        },
        {
            title: '操作',
            key: 'action',
            render: (_, record) => (
                <Space size="middle">
                    <a type="primary" onClick={() => deleteRole(record)} >
                        移除
                    </a>
                    {/* <span>|</span>
                    <a type="primary" onClick={() => editRecord(record)}>
                        编辑
                    </a> */}
                </Space>
            ),
        }

    ];
    const deleteRole = ({ id, storyId }) => {
        const params = {
            storyId,
            priActorId: id
        };
        deletePrivateActor(params).then(res => {
            LoadData(1);
        })
    };

    function editRecord({ id, status }) {
        navigate(`${[, '/afflatus', 'rolesetting', 'piccreator'][status]}?storyId=${id}`, { replace: true });

    }
    const onSearch = (value = "") => {
        setKeyWord(value);
        LoadData(1);
    };

    const [items, setItems] = useState([]);
    const LoadData = (pageIndex = pagination.current, pageSize = pagination.pageSize) => {

        const params = {
            pageIndex,
            pageSize

        }

        if (keyWord) {
            params['keywords'] = keyWord;
        }
        getMyStarActors(params).then(res => {
            console.log(res);
            setPagination({ ...pagination, total: res.total });
            setItems([...res.records]);
            setLoading(false);
        })
    };

    useEffect(() => {
        setLoading(true);
        LoadData();
    }, [pagination.current, pagination.pageSize, keyWord]);


    return (<>
        {
            console.log("ssssss", items)
        }
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
            dataSource={[...items]}
            pagination={{
                ...pagination,
                onChange: (current, pageSize) => {
                    setPagination({ ...pagination, current, pageSize });
                },
            }}
            loading={loading}
            rowKey={record => record.id}
        />
    </>)
}