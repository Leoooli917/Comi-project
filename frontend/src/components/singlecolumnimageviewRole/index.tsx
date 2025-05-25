import * as React from 'react';
import './index.less';
import { IconEdit } from '@arco-design/web-react/icon';
import { Input } from '@arco-design/web-react';
import { message } from 'antd'; 
import { useHookstate } from '@hookstate/core';
import { useState, useEffect } from 'react';
import { changeMyPirActorName } from '@/api/RoleMangerApi';

const SingleColumnImageView = ({ images = [], width = 90, height = 90, showLabel = false, onImageClick }) => {
    async function updateActorName(value, obj) {
        if (obj.id == -1) {
            message.info("请先生成角色然后改名字");
            return;
        }

        let res = await changeMyPirActorName({
            "actorId": obj.id,
            "name": obj.actorName
        });
        if (res !== undefined) {
            message.success("修改成功");
        }
    }

    const [actorList, setActorList] = useState(images);
    useEffect(() => {
        setActorList(images);
    }, [images]);

    if (!Array.isArray(images) || images.length === 0) {
        return <div style={{ marginTop: '40px' }}>无角色配置</div>;
    }

    return (
        <div className='single-column-layout'>
            <div className='single-column-title'>角色配置</div>
            {actorList.map((image, index) => (
                <div className='single-column-card hack' key={index}>
                    {image.isSelected && (<img className={image.isSelected ? 'selceted-img-bg' : ''} src='https://p5.ssl.qhimg.com/t01bd6ebe4ea0e1f479.png'></img>)}
                    {showLabel && <div className='single-column-card-label'>{((index < 9) ? '0' + (index + 1) : (index + 1))}</div>}
                    <div>
                        <img src={image.url}
                            alt={`Image ${index + 1}`}
                            className={image.isSelected ? 'single-column-card-img-bg' : ''}
                            style={{ width: `${width}px`, height: `${height}px`, cursor: 'pointer', objectFit: 'cover', borderRadius: `${index === images.length - 1 ? '4px' : '8px'}` }}
                            onClick={() => onImageClick(image, index)} 
                        />  
                        <Input
                            className="storeTitle"
                            style={{ minWidth: 60, backgroundColor: "#fff" }}
                            maxLength="6"
                            autoWidth
                            value={image.actorName}
                            placeholder='请输入角色名称'
                            onChange={(title: string) => {
                                let arr = [...actorList];
                                arr.forEach(item => {
                                    if (item.id === image.id) {
                                        item.actorName = title;
                                    }
                                });
                                setActorList(arr);
                            }}
                            onClear={() => { image.actorName = "" }}
                            onBlur={(e) => updateActorName(e.target.value, image)}
                        />
                    </div> 
                </div>
            ))}
        </div>
    );
};

export default SingleColumnImageView;
