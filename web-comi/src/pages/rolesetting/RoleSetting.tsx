import * as React from 'react';
import RoleSet from "../../components/rolesetRight/index.tsx";
import LeftStep from "../../components/leftstep/LeftStep.tsx";
import ImageComponent from './imagecomponent/index.tsx';
import { useWorkbenchState } from "@/stores/workbench.ts";



import './RoleSetting.less'
import SingleColumnImageView from '@/components/singlecolumnimageviewRole/index.tsx';
import { useState, useEffect } from 'react';
import { getPrivateActorList } from '@/api/RoleMangerApi.ts';
import { commitStoryWithActor } from '@/api/StoryApi.ts';
import { useHookstate } from '@hookstate/core';
import { useNavigate } from 'react-router-dom';
import { message } from 'antd';
import { getCurrentUrlParamValue } from "@/utils/url"
import PubSub from "pubsub-js";
import { log } from 'console';
import { ar } from 'date-fns/locale';



const SelectRole = ({ }) => {
    var [showUrl, setShowUrl] = useState('');
    const privateActorChangeState = useHookstate<number>(0);
    const workbenchState = useWorkbenchState();
    const navigate = useNavigate();
    const storyId = getCurrentUrlParamValue("storyId")
    const [roleList, setRoleList] = useState([]);
    const [statues, setStatus] = useState("finish");  

    var [privatRoleList, setPrivatRoleList] = useState([]);
    var [chosenRole, setChosenRole] = useState({})


    useEffect(() => {
        console.log("到了finish状态")
        getPrivateActorArr()

    }, [privateActorChangeState])

    useEffect(() => {
        if (workbenchState.storyId !== parseInt(storyId)) {
            workbenchState.setStoryId(parseInt(storyId))
        }
    }, [storyId])

    const handlePrivateActorChange = (status, actorId) => {
        console.log('handlePrivateActorChange status', status, actorId);
        if (status === 'begin') {
            const arr = [...privatRoleList]
            let had =false
           
            arr.forEach((item) => {
                if (item.id == actorId) {
                    item.status = 'begin'
                    setChosenRole(item);
                    had =true
                }
            })
            console.log("had",had)
            if(!had){
                arr.forEach((item) => {
                    if (item.id == -1) {
                        item.status = 'begin'
                         item.id =actorId
                        setChosenRole(item);
                    
                    }
                })

            }
             console.log('arr', arr);
            setPrivatRoleList(arr)
        } else if (status === 'finish') {
            privateActorChangeState.merge(p => p + 1);
            return
        }
    }
    
    const makeNewRole =()=>{
        console.log("创建新角色")
        let arr = [...privatRoleList]
        let isAdd =true
        arr.forEach(item=>{
            if(item.id==-1){
               
                isAdd =false
               
            }
        })
        if(isAdd){
        let role =   {
            "id": -1,
            "url": 'https://s2.loli.net/2024/04/10/GpHz8lQKimMUcf1.png',
            "index": '0',
            "showUrl": 'https://p0.ssl.qhimg.com/t0162014aa2ba4f8715.png',
            "isSelected": false
        }
        
        arr.push(role)
        setChosenRole(role)
        setShowUrl(role.showUrl)
        arr.map((item) => {
            if (item.id == role.id) {
                item.isSelected = true
            } else {
                item.isSelected = false
            }
        })
        setPrivatRoleList(arr)
    }else{
            message.warning("请添加完当前角色")
    }

    }

    const getPrivateActorArr = async (isDelete = false) => {
        let res = await getPrivateActorList({ "storyId": storyId })
        if (res != undefined) {
            setPrivatRoleList(res)
            setRoleList(res);
            
            let selectedRole = privatRoleList.find(item => item.isSelected)

            if (selectedRole==null||selectedRole.id==-1){
                 selectedRole = privatRoleList.find(item => item.id == chosenRole.id)

            }
            console.log('selectedRole', selectedRole);
            console.log('chosenRole', chosenRole);


            res.map((item) => {
                item.url = item.posterImage
                if (selectedRole && selectedRole.id == item.id) {
                    item.isSelected = true
                    setChosenRole(item)
                    setShowUrl(item.posterImage)
                } else {
                    item.isSelected = false
                }
                item.showUrl = item.posterImage
            })
            if (!selectedRole || isDelete) {
                res[0].isSelected = true
                setChosenRole(res[0])
                setShowUrl(res[0].showUrl)
            }
            setPrivatRoleList(res)
        }

    }


    const roleClick = (obj, index) => {
        console.log(JSON.stringify(obj))
        let arr = [...privatRoleList]
        setChosenRole(obj)
        setShowUrl(privatRoleList[index].showUrl)
        arr.map((item) => {
            if (item.id == obj.id) {
                item.isSelected = true
            } else {
                item.isSelected = false
            }
        })
        setPrivatRoleList(arr)
    }
    function checkPrivateRole(){

    }

    const handleClickNext = async () => {
        const actorIds = privatRoleList.map(item => {
            if (item.id !== -1) {
                return item.id + ''
            } else {
                return null;
            }
        }).filter(item => item !== null)

        if (!actorIds || actorIds.length === 0) {
            message.error("请设置角色配置")
            return;
        }
        try {
            const res = await commitStoryWithActor({
                "actorIds": actorIds,
                "storyId": storyId
            })
            console.log('commitStoryWithActor res', res);
            workbenchState.setNextStep();
            navigate(`/piccreator?storyId=${storyId}`, { replace: true })
        } catch (error) {
            console.error(error);
        }

    }

    useEffect(() => {
        PubSub.subscribe("confirmRole", () => {
            handleClickNext()
        })
        return () => {
            PubSub.unsubscribe("confirmRole");
        }
    }, [privatRoleList])

    useEffect(() => {
        PubSub.subscribe("deleteRole", () => {
            setShowUrl(roleList[0].showUrl)
        })
        return () => {
            PubSub.unsubscribe("deleteRole");
        }
    }, [])


    useEffect(() => {
        //防止页面后退
        history.pushState(null, null, document.URL);
        window.addEventListener("popstate", function () {
          
            history.pushState(null, null, document.URL);
        });
    }, [])

    return (
        <div>
            <div className="body-layout">
                <div className="left">
                    <LeftStep progress={2}></LeftStep>

                </div>
                <div className="mid">
                    <div className='img-list'>
                        <SingleColumnImageView images={privatRoleList} onImageClick={roleClick}></SingleColumnImageView>


                    </div>
                    <ImageComponent 
                         chosenRole={chosenRole} 
                         showImgUrl={showUrl} 
                         makeNewRole={makeNewRole} 
                        storyId={storyId}
                        refresh={getPrivateActorArr}
                        
                         ></ImageComponent>

                </div>
                <div className='right'>
                    <RoleSet
                        chosenRole={chosenRole}
                        onPrivateActorChange={handlePrivateActorChange}
                        storyId={storyId}
                        refresh={getPrivateActorArr} />
                </div>
            </div>
        </div>
    )
};

export default SelectRole;