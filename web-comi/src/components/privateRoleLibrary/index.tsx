import { useEffect, useState, useRef } from 'react'
import { Modal } from 'antd';
import './index.less'

import {  getMyStarActors } from '@/api/RoleMangerApi'
import ImageSelect  from  '../imageSelect/index'
import { useNavigate } from 'react-router-dom';
import endIcon from '@/assets/无数据.png'


function PrivateRoleLibrary({ title, storyId, isModalVisible, handleModalCancel, ensureSelectRole }) {
   
    const modalWidth = 640;
    const [modalOffsetRight, setModalOffsetRight] = useState(0) // modal向右偏移的距离
    const [privateList, setPrivateList] = useState([])
    const [currentTag, setCurrentTag] =useState({})


    useEffect(() => {
        getPrivateList()
    }, [isModalVisible])

    async function getPrivateList(){
        let res = await getMyStarActors({
           "keywords":'',
           "page":1,
            "storyId": storyId,
           "pageSize":200

        })
        setPrivateList([])
    } 

    const navigate = useNavigate();
    //去角色管理
    function goToRoleManger() {
        setPrivateList([])
        console.log("私有角色管理")
        navigate('/personal/private');
    }
    //选择
    function selectType(item: {}) {
        setCurrentTag(item)
    }
   //确定选择
   function  selectPrivateRole(){
      ensureSelectRole(currentTag,1)
    }

    return (
        <>
            <Modal
                title={title}
                open={isModalVisible}
                onCancel={handleModalCancel}
                closable={false}
                okText="确定"
                onOk={selectPrivateRole}
                cancelText="取消"
                width={modalWidth}
                mask={false}
                centered
            >
                <div>
                    <div className='top-right-text' onClick={goToRoleManger}>管理私有角色</div>
                    <div style={{ maxHeight: '640px', overflowY: 'auto', minHeight: '400px' }}>
                        {privateList.length > 0 ? (
                            <ImageSelect tag='isSelected' options={privateList.map(item => ({
                                ...item,
                                isSelected: currentTag.id === item.id,
                                name: item.actorName,
                                url: item.posterImage
                            })) || []} onSelect={item => selectType(item)} />
                        ) : (
                            <div className="empty-placeholder">
                                <img src={endIcon} alt="No private roles" />
                                <p>还没有私有角色呢...</p>
                            </div>
                        )}
                    </div>
                </div>
            </Modal>
        </>
    );
}
export default PrivateRoleLibrary