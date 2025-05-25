import { PhotoProvider, PhotoView } from 'react-photo-view';
import {  Progress, message } from 'antd';

import 'react-photo-view/dist/react-photo-view.css';
import './index.less'; // 引入样式文件  

import { useHookstate } from '@hookstate/core';
import { saveMyStarActors, savePrivateActor, updatePrivateActor, chooseOnePriStarActor } from '@/api/RoleMangerApi'
import { useEffect, useState, useRef } from 'react';
import PrivateRoleLibrary from '@/components/privateRoleLibrary/index';
import HistoryRoleLibrary from '@/components/historyRoleLibrary/index';
import { json } from 'stream/consumers';



function ImageComponent({ chosenRole = {}, showImgUrl, storyId, makeNewRole, refresh }){
  const [privateRoleModeVisable, setPrivateRoleModeVisable] = useState(false) 
  const [historyRoleModeVisable, setHistoryRoleModeVisable] = useState(false) 
 
  //选择私有角色
 async function checkPrivateRole( item={},type){
    setPrivateRoleModeVisable(false)
    setHistoryRoleModeVisable(false)
     let isAdd =0
   let currentActorId=''
    if(chosenRole.id==-1){
      isAdd=1
    }else{
      isAdd = 0
      currentActorId =chosenRole.id

    }
    let res= await chooseOnePriStarActor({
      "storyId":storyId,
      "type":type,
      "isAdd":isAdd,
      "chooseActorId":item.id,
      "currentActorId":currentActorId
    
    })
   
    if(res!=undefined&&res!=null){
      refresh()

    }
  

   

  }
  //创建新角色
  const useNewRole = () => {
    makeNewRole()
   
  }

  //设为私有角色
  async function saveStarActors (){
    let res = await saveMyStarActors(
      {
        "actorName": chosenRole.actorName ,
        "priActorId": chosenRole.id,
        "storyId": storyId
      }
    )

  
    if (res!=undefined){
      message.success("保存成功")
    }
    
  } 

  //使用私有角色库
  const usePrivateRoleLibrary = async () => {
    setPrivateRoleModeVisable(true)
   
  }
  //使用历史角色
  const useHistoryLibrary = async () => {
    setHistoryRoleModeVisable(true)

   
  }
  const buttonUrls = [
    {
      url: 'https://p4.ssl.qhimg.com/t11b673bcd601be2141edaf4786.png',
      alt_value: '私有角色库',
      action: usePrivateRoleLibrary,
    },
    {
      url: 'https://p1.ssl.qhimg.com/t11b673bcd6a4cf8f5c86153b24.png',
      alt_value: '历史角色库',
      action: useHistoryLibrary,
    },
    {
      url: 'https://p5.ssl.qhimg.com/t110b9a93015ba62f2698960a31.png',
      alt_value: '新建角色',
      action: useNewRole,
    },
   
  ];
  const progress = useHookstate<number>(5)
  let interval = useRef(null)

  useEffect(() => {
    console.log('wxf-chosenRole', chosenRole);

    const updateProgress = () => {
      // console.log('updateProgress', progress.get());
      if (progress.get() >= 98) {
        progress.set(98);
      } else {
        progress.merge(p => p + 5)
      }
    }
        if(chosenRole.status=="begin"){
          interval.current = setInterval(updateProgress, 2000);

        }
   
     
    
    return () => clearInterval(interval.current);
  }, [chosenRole])
  return (
    <div className="image-component">
      
      <div className="image-wrapper">

        {chosenRole.status == 'begin'&& <div className='loading'>
          <div className='loading-tips'>
            <Progress percent={progress.get()} status="active" showInfo={false} strokeColor={{ from: '#9065fb', to: '#546bfd' }} />
            <span>正在努力生成中……</span>
          </div>
        </div>}
        {chosenRole.status != 'begin'&&showImgUrl && <PhotoProvider>
          <PhotoView src={showImgUrl}>
            <div className='img-div'>
              <img src={showImgUrl} alt="图片" className="main-image" />
            </div>
          </PhotoView>
        </PhotoProvider>}
        <div className="button-container" >
          {buttonUrls.map((item, index) => (
           
              <div key={index} className='img-btn-shape-hover' onClick={item.action}>
                <div className='img-btn-shape'>
                  <img src={item.url} alt={`Button ${item.alt_value}`} className="image-button" />
                </div>
                <p className="hover-text">{item.alt_value}</p>
              </div>
            
          ))}
        </div>

      </div>
      {
         chosenRole?.id != -1 && <img src='https://p1.ssl.qhimg.com/t110b9a9301ac2304c8542c464b.png' className='bottom-img' onClick={saveStarActors}></img>

      }
     
      
      <PrivateRoleLibrary
        title='私有角色库'
        storyId={storyId}
        isModalVisible={privateRoleModeVisable}
        handleModalCancel={() => setPrivateRoleModeVisable(false)}
        ensureSelectRole={checkPrivateRole}

      />
      <HistoryRoleLibrary
        title='历史角色'
        isModalVisible={historyRoleModeVisable}
        storyId={storyId}
        handleModalCancel={() => setHistoryRoleModeVisable(false)}
        ensureSelectRole={checkPrivateRole}
      
      />


    </div>
  );
};

export default ImageComponent;