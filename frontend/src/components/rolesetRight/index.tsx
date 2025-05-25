import './index.less'
import { useState, useEffect, useRef } from 'react'
import ImageSelect from '../imageSelect'
import { Input, Flex, Button, message } from 'antd';
import { getPublicActorList, savePrivateActor, updatePrivateActor, checkActor, deleteActor, getPublicActorListV2 } from '@/api/RoleMangerApi';
import VocabularyModal from '../vocabulary';
import { formatPromptInput } from '@/utils/formatPromptInput';
import { useHookstate } from '@hookstate/core';
const { TextArea } = Input;

function RightStyle({ chosenRole = {}, onPrivateActorChange, storyId, refresh = isDelete => isDelete }) {

  const [optionArr, setOptionArr] = useState([])
  let [promptValue, setPromptValue] = useState("")
  let [actorName, setActorName] = useState("")
  const [modalVisible, setModalVisible] = useState(false);
  const targetRef = useRef(null);
  const actorList = useHookstate([]);
  const currentTag =  useHookstate(1);
  const currentSelectedTag = useHookstate(0);
  useEffect(() => {
    console.log('chosenRole', chosenRole);
   
    if (chosenRole.id === -1) { //新增角色
      setPromptValue("")
      setActorName("")
      const arr = [...optionArr]
      arr.forEach((obj, index) => {
        if (index === 0) {
          obj.isSelected = true
        } else {
          obj.isSelected = false
        }
      })
      setOptionArr(arr)
      return
    }

    setPromptValue(chosenRole.prompt)
    setActorName(chosenRole.actorName)

    currentSelectedTag.set(chosenRole.fromPubId);
    currentTag.set(actorList.get().find(image=>{
      return image.actorPubList.find(actor => actor.id == chosenRole.fromPubId)
    })?.id);
  }, [chosenRole])
  useEffect(() => {
    getPublicActorArr()
  }, [])
  function selectType(item: {}) {
    if (chosenRole == undefined || chosenRole.id == undefined) {
      setPromptValue(item.prompt)
      setActorName(item.actorName)
    }
    // let arr = [...optionArr]
    // arr.map(obj => {
    //   if (obj.id == item.id) {
    //     obj.isSelected = true
    //   } else {
    //     obj.isSelected = false
    //   }
    // })
    currentSelectedTag.set(item.id);
    actorList.get().forEach(model=>{
      model.actorPubList.map(actor=>{
        actor["isSelected"] = currentSelectedTag.get() == actor.id ? true : false;
      })
    })

    setOptionArr(arr)
  }

  const getPublicActorArr = async () => {
    let res = await getPublicActorList({ "storyId": storyId });
    const resv2 = await getPublicActorListV2({ "storyId": storyId });
    if (resv2 != undefined) {
      actorList.set(resv2);
      currentTag.set(resv2[0].id);
      currentSelectedTag.set(resv2[0].actorPubList[0].id)
      // console.log("currentSelectedTag", resv2[0].actorPubList[0].id);
    }

    if (res != undefined) {
      res.map((item, index) => {
        item.isSelected = (index == 0 ? true : false);
        item.name = item.actorName
        item.url = item.posterImage
      })
      setOptionArr(res)
    }
  }
  const updateOrAdd = () => {
    const pubActorId = currentSelectedTag.get();
    if (pubActorId == 0 || !promptValue || !actorName) {
      message.warning("请填写参数")
      return;
    }
    if (chosenRole.id == undefined || chosenRole.id == -1) {
      createPrivateActor(pubActorId)

    } else {
      editPrivateActor(pubActorId)
    }

  }
  const deleteActorFn = () => {
    deleteActor({
      storyId: (chosenRole as any).storyId,
      priActorId: (chosenRole as any).id
    }).then(() => {
      refresh(true);
    })
    // const pubActorId = getSelectPubActorId();
    // if (pubActorId == 0 || !promptValue || !actorName) {
    //   message.warning("请填写参数")
    //   return;
    // }
    // if (chosenRole.id == undefined || chosenRole.id == -1) {
    //   createPrivateActor(pubActorId)

    // } else {
    //   editPrivateActor(pubActorId)
    // }

  }
  const handlePromptInputChange = (event) => {
    setPromptValue(event.target.value)

  };
  const handleNameInputChange = (event) => {

    setActorName(event.target.value)
  };

  const getSelectPubActorId = () => {
    for (const item of optionArr) {
      if (item.isSelected) {
        return item.id;
      }
    }
    return 0;
  }
  //生成新角色
  const createPrivateActor = async (pubActorId: number) => {
    let res = await savePrivateActor({
      "storyId": storyId,
      "pubActorId": pubActorId,
      "prompt": promptValue,
      "actorName": actorName
    })
    console.log('savePrivateActor res', res);
    if (res) {
      setAddRoleObj(res);
      if (chosenRole.id==-1){
        chosenRole.id=res.id
      }
      onPrivateActorChange && onPrivateActorChange('begin', chosenRole.id);
    }

    // TODO loop check
    setStartPolling(true);
  };

  let interval = useRef(null)
  const [stopRequest, setStopRequest] = useState(false);
  const [startPolling, setStartPolling] = useState(false);
  const [addRoleObj, setAddRoleObj] = useState()
  // const [currentActorId, setCurrentActorId] = useState()

  // useEffect(() => {
  //   const priActorId = chosenRole.id > -1 ? chosenRole.id : addRoleObj?.id;
  //   setCurrentActorId(priActorId);
  // }, [chosenRole, addRoleObj])

  useEffect(() => {
    console.log('stopRequest', stopRequest, interval.current);

    if (stopRequest && interval.current) {
      clearInterval(interval.current)
      setStopRequest(false);
      setStartPolling(false);
      return
    }

    // TODO 如果有连续点击生成的，需要怎么处理？
    const fetchData = async () => {
      let priActorId = chosenRole.id;
      if (priActorId == -1) {
        priActorId = addRoleObj?.id;
      }

      try {
        const res = await checkActor({ priActorId: priActorId });
        console.log('checkActor res', res);
        if (res && res.status == 'finish') {
          setStopRequest(true);
          onPrivateActorChange && onPrivateActorChange('finish', priActorId);
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
    if (startPolling) {
      interval.current = setInterval(fetchData, 2000);
    }

    // console.log('interval', interval.current);
    // 清除定时器
    return () => clearInterval(interval.current);
  }, [stopRequest, startPolling]);

  const editPrivateActor = async (pubActorId: number) => {
    let res = await updatePrivateActor({
      "storyId": storyId,
      "pubActorId": pubActorId,
      "priActorId": chosenRole.id,
      "prompt": promptValue,
      "actorName": actorName
    })
    console.log('updatePrivateActor res', res);
    if (res) {
      onPrivateActorChange && onPrivateActorChange('begin', chosenRole.id);
    }

    setStartPolling(true);
  }
  const insertTag = (tag: string) => {
    const tagList = formatPromptInput(promptValue);
    if (tagList.includes(tag)) return;
    let comma = '';
    if (promptValue.length > 0) {
      comma = /[,;.，、。；][0-9a-z\s]+$/.test(promptValue) ? ', ' : '，';
    }
    setPromptValue(promptValue + `${comma}${tag}`);
  };

  const removeTag = (cn: string, en: string) => {
    const tagList = formatPromptInput(promptValue);
    let res = '';
    let commaLen = 0;
    tagList.forEach((tag) => {
      if (tag !== cn && tag !== en) {
        const comma = /^[0-9a-z\s]+$/.test(tag) ? ', ' : '，';
        commaLen = comma.length;
        res += `${tag}${comma}`;
      }
    });
    setPromptValue(res.slice(0, res.length - commaLen));
  };
  return (
    <>
      <div className='right-style-body' ref={targetRef}>
        <div className='right-style-type-label'>模型库</div>
        <div className='right-style-type-label-tag'>
          {
            actorList.get().map((item, index) =>
              <div key = {index} className={currentTag.get() == item.id ? "active" : ""} onClick={() => {
                currentTag.set(item.id)
                console.log(currentTag.get());}
              }>
                {item.name}
              </div>
            )
          }
        </div>
        <div className='right-style-types'>
          <ImageSelect tag='isSelected' options={actorList.get().find(item=>item.id == currentTag.get())?.actorPubList.map(item=>{
            return {
              ...item,
              isSelected : currentSelectedTag.get() == item.id ? true : false,
              name : item.actorName,
              url : item.posterImage
            }
          }) || []} onSelect={item => selectType(item)} />
        </div>
       {chosenRole.id ==-1&& <div>
        <div className='right-style-ratio-label'>角色名称</div>
        <div className='right-style-types'>
          <Input className='text-description' placeholder="请输入姓名" value={actorName} onChange={handleNameInputChange} />
        </div>
        </div>}
        <div className='right-style-ratio-label' >角色描述
          <img src='https://p1.ssl.qhimg.com/t11b673bcd6ae266fe78dc61186.png' onClick={(event) => setModalVisible(true)}></img>
        </div>
       
        <div className='right-style-types'>
          <TextArea className='text-description' rows={4} placeholder="请输入角色描述" maxLength={100} value={promptValue} onChange={handlePromptInputChange} />
        </div>
        <Flex gap="small" wrap="wrap" justify="center" style={{ padding: '16px 11px 16px 11px' }}>
          <Button className="basicsButton" disabled={chosenRole.status == 'begin'} loading={chosenRole.status == 'begin'} onClick={updateOrAdd}>{`${(chosenRole as any).id == -1 ? '添加角色' : '重新生成角色'}`}</Button>
          <Button className="basicsButton" style={{ display: `${(chosenRole as any).id == -1 ? "none" : "block"}` }} onClick={deleteActorFn}>删除角色</Button>
          <Button className="basicsButton" style={{ background: ' #FAFAFA', color: '#595959' }}>取消</Button>
        </Flex>
        {/* <div className='right-style-next' onClick={() => onClickNext()}>下一步</div> */}
      </div>
      <VocabularyModal
        targetRef={targetRef}
        title='角色词库'
        prompt={promptValue}
        isModalVisible={modalVisible}
        handleModalCancel={() => setModalVisible(false)}
        onCancelChoose={removeTag}
        onChoose={insertTag}
      />
    </>
  )
}
export default RightStyle