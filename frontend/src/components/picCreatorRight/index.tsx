import './index.less'
import { useState, useEffect, useRef } from 'react'
import ImageSelect from '../imageSelect'
import { Input, Flex, Button, Modal, message } from 'antd';
import { reGenerate, commitStoryPublish } from '@/api/StoryApi';
const { TextArea } = Input;
import { useWorkbenchState } from "@/stores/workbench.ts";
import { usePicCreatorState } from "@/stores/picCreatorState.ts";
import { useNavigate } from 'react-router-dom';
import PubSub from "pubsub-js";
import VocabularyModal from '../vocabulary';
import { formatPromptInput } from '@/utils/formatPromptInput';


function RightStyle({ roleList = [], characterDescription, sceneDescription, imageId, onReGenerateFinish, storyId, actorId }) {
  const picCreatorState = usePicCreatorState()
  const navigate = useNavigate();

  const [optionArr, setOptionArr] = useState([])
  const [characterValue, setCharacterValue] = useState(characterDescription)
  const [sceneValue, setSceneValue] = useState(sceneDescription)
  const [selectRole, setSelectRole] = useState()
  const [isModalOpen, setIsModalOpen] = useState(false);
  const workbenchState = useWorkbenchState();

  const [characterModalVisible, setCharacterModalVisible] = useState(false);
  const [sceneModalVisible, setSceneModalVisible] = useState(false);
  const targetRef = useRef(null);


  useEffect(() => {
    setCharacterValue(characterDescription ? characterDescription : '')
  }, [characterDescription, sceneDescription])

  useEffect(() => {
    setSceneValue(sceneDescription)
  }, [sceneDescription])

  // console.log('actorId', actorId);

  useEffect(() => {
    if (roleList && roleList.length > 0) {
      setSelectRole(roleList[0])
      const arr = []
      roleList.forEach((element, index) => {
        const obj = { url: element.posterImage, name: element.actorName, isSelected: false, id: element.id }
        if (!actorId && index == 0) {
          obj.isSelected = true;
        } else if (actorId == element.id) {
          obj.isSelected = true;
        }
        arr.push(obj)
      });
      setOptionArr(arr)
    } else {
      setOptionArr([])
    }

  }, [roleList, actorId])

  const handleCharacterInputChange = (event) => {
    setCharacterValue(event.target.value)
  };
  const handleSceneInputChange = (event) => {
    setSceneValue(event.target.value)
  };

  function selectType(item: {}) {
    console.log('selectType item', item);
    // setOption(item)
    setSelectRole(item)

    let arr = [...optionArr]
    arr.map(obj => {
      if (obj.id == item.id) {
        obj.isSelected = true
      } else {
        obj.isSelected = false
      }
    })
    setOptionArr(arr)
  }

  const generate = async () => {
    const res = await reGenerate({
      "actorId": selectRole?.id,
      "content": sceneValue,
      "personDes": characterValue ? characterValue : null,
      "picId": imageId
    })
    console.log('reGenerate res', res);
    picCreatorState.setIsLoading(true);
    onReGenerateFinish && onReGenerateFinish(imageId)

  }

  const handleClickPreview = () => {
    // window.open(`/productDetail?stroyId=${storyId}&preview=${true}`)
    navigate(`/productDetail?stroyId=${storyId}&preview=${true}`, { replace: true })
  }

  const handleClickPublish = async () => {
    try {
      const res = await commitStoryPublish({ storyId: storyId })
      console.log('commitStoryPublish res', res);
      if (res) {
        message.success('发布成功')
        workbenchState.setCurrentStep(0);
        // setIsModalOpen(true);
        handleToHome()
      }

    } catch (error) {
      console.error(error);
    }
  }

  useEffect(() => {
    PubSub.subscribe("previewStory", () => {
      handleClickPreview()
    })
    PubSub.subscribe("publishStory", () => {
      handleClickPublish()
    })
    return () => {
      PubSub.unsubscribe("previewStory");
      PubSub.unsubscribe("publishStory");
    }
  }, [])


  const handlePublishAgain = () => {
    navigate('/afflatus', { replace: true })
  }

  const handleToHome = () => {
    navigate('/', { replace: true })
  }
  // console.log('picCreatorState.isLoading',picCreatorState.isLoading);  
  const insertTag = (tag: string, prompt: string, setPromptValue: Function) => {
    const tagList = formatPromptInput(prompt);
    if (tagList.includes(tag)) return;
    let comma = '';
    if (prompt.length > 0) {
      comma = /[,;.，、。；][0-9a-z\s]+$/.test(prompt) ? ', ' : '，';
    }
    setPromptValue(prompt + `${comma}${tag}`);
  };

  const removeTag = (cn: string, en: string, prompt: string, setPromptValue: Function) => {
    const tagList = formatPromptInput(prompt);
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

  const insertCharacterTag = (tag: string) => {
    insertTag(tag, characterValue, setCharacterValue)
  };

  const removeCharacterTag = (cn: string, en: string) => {
    removeTag(cn, en, characterValue, setCharacterValue)
  };

  const insertSceneTag = (tag: string) => {
    insertTag(tag, sceneValue, setSceneValue)
  };

  const removeSceneTag = (cn: string, en: string) => {
    removeTag(cn, en, sceneValue, setSceneValue)
  };

  return (
    <>
      <div className='right-style-body' ref={targetRef}>
        <div className='right-style-type-label'>图中人物</div>
        <div className='right-style-types'>
          <ImageSelect tag='isSelected' options={optionArr} onSelect={item => selectType(item)} />
        </div>
        <div className='right-style-ratio-label' >人物描述
          <img style={{ cursor: `${picCreatorState.isLoading ? 'not-allowed' : 'pointer'}` }} src='https://p1.ssl.qhimg.com/t11b673bcd6ae266fe78dc61186.png'
            onClick={() => !picCreatorState.isLoading && setCharacterModalVisible(true)}></img>
        </div>
        <div className='right-style-types'>
          <TextArea className='text-description' rows={4} placeholder="人物角色在图片中的动作、表情、镜头等"
            disabled={picCreatorState.isLoading}
            maxLength={100} value={characterValue} onChange={handleCharacterInputChange} />
        </div>
        <div className='right-style-ratio-label' >场景描述
          <img style={{ cursor: `${picCreatorState.isLoading ? 'not-allowed' : 'pointer'}` }} src='https://p1.ssl.qhimg.com/t11b673bcd6ae266fe78dc61186.png'
            onClick={() => !picCreatorState.isLoading && setSceneModalVisible(true)}></img>
        </div>
        <div className='right-style-types'>
          <TextArea className='text-description' rows={4} placeholder="这里展示的该分镜的图片场景信息"
            disabled={picCreatorState.isLoading}
            maxLength={100} value={sceneValue} onChange={handleSceneInputChange} />
        </div>
        <Flex gap="small" wrap="wrap" justify="center" style={{ padding: '16px 11px 16px 11px' }}>
          <Button className="basicsButton" onClick={generate} disabled={picCreatorState.isLoading}>重新生成分镜图片</Button>
          {/* <Button className="basicsButton" >取消</Button> */}
        </Flex>
        {/* <Flex gap="small" wrap="wrap" justify="center" style={{ padding: '16px 11px 16px 11px' }}>
          <Button className="basicsButton" onClick={handleClickPreview} disabled={picCreatorState.isLoading}>预览</Button>
          <Button className="basicsButton" onClick={handleClickPublish} disabled={picCreatorState.isLoading}>发布</Button>
        </Flex> */}
      </div>
      <Modal title="发布成功" open={isModalOpen} footer={null}>
        <Flex gap="small" wrap="wrap" justify="center" vertical="vertical" style={{ padding: '16px 11px 16px 11px' }}>
          <Button className="basicsButton" onClick={handlePublishAgain}>再发一篇</Button>
          <Button className="basicsButton" onClick={handleToHome}>返回首页</Button>
        </Flex>
      </Modal>
      <VocabularyModal
        targetRef={targetRef}
        title='人物描述词库'
        prompt={characterValue}
        isModalVisible={characterModalVisible}
        handleModalCancel={() => setCharacterModalVisible(false)}
        onCancelChoose={removeCharacterTag}
        onChoose={insertCharacterTag}
      />
      <VocabularyModal
        targetRef={targetRef}
        title='环境描述词库'
        prompt={sceneValue}
        isModalVisible={sceneModalVisible}
        handleModalCancel={() => setSceneModalVisible(false)}
        onCancelChoose={removeSceneTag}
        onChoose={insertSceneTag}
      />
    </>
  )
}
export default RightStyle