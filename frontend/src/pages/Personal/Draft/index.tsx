import { useState, useEffect } from 'react';
import { Table, Space, Button, Input } from 'antd';
import { useHookstate } from '@hookstate/core';
import { useWorkbenchState } from "@/stores/workbench.ts";
import { useNavigate } from 'react-router-dom';
import { getMyStory, removeMyStory } from "../../../api/StoryApi";
import ConfirmDialog from '../../../components/ConfirmDialog';
import { format } from 'date-fns';
import '../index.less';

const { Search } = Input;

export default function Draft() {
    const navigate = useNavigate();
    const [pagination, setPagination] = useState({
        current: 1,
        pageSize: 10,
        total: 0
    });
    const [loading, setLoading] = useState(false);
    const [keyWord, setKeyWord] = useState("");
    const workbenchState = useWorkbenchState();
    const [items, setItems] = useState([]);
    const [deleteModalVisible, setDeleteModalVisible] = useState(false);
    const [recordToDelete, setRecordToDelete] = useState(null);

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
                    <a type="primary" onClick={() => confirmDelete(record)}>
                        删除
                    </a>
                    <span>|</span>
                    <a type="primary" onClick={() => editRecord(record)}>
                        编辑
                    </a>
                </Space>
            ),
        }
    ];

    const confirmDelete = (record) => {
        setRecordToDelete(record);
        setDeleteModalVisible(true);
    };

    const deleteRecord = () => {
        if (recordToDelete) {
            removeMyStory({ storyId: recordToDelete.id }).then(() => { 
                LoadData(1);
            });
        }
        setDeleteModalVisible(false);
        setRecordToDelete(null);
    };

    function editRecord({id, status}) {
        workbenchState.setStoryId(id);
        navigate(`${['', '/afflatus', '/rolesetting', '/piccreator', '/piccreator'][status]}?storyId=${id}`, { replace: true });
    }

    const onSearch = (value = "") => {
        setKeyWord(value);
        LoadData(1);
    };

    const LoadData = (pageIndex = pagination.current, pageSize = pagination.pageSize) => {
        const params = {
            type: 2,
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
            <ConfirmDialog
                visible={deleteModalVisible}
                alertType="delete"
                title="删除提醒"
                content="确认要删除该项目吗？"
                cardTitle={recordToDelete?.title}
                onConfirm={deleteRecord}
                onCancel={() => setDeleteModalVisible(false)}
            />
        </>
    );
}
