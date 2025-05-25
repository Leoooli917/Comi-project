import RightStyle from "./../../components/rightstyle/RightStyle";
import LeftStep from "./../../components/leftstep/LeftStep";
import './Afflatuse.css'
import AfflatusContent from "./../../components/afflatuscontent/AfflatusContent";
import WriteNovel from "./../../components/writenovel/WriteNovel";
import { useEffect, useState } from 'react'
import ParagraphList from "@/components/paragraphlist/ParagraphList";
import { useNavigate, useLocation } from 'react-router-dom';
import { useHookstate } from '@hookstate/core';
import { useWorkbenchState } from "@/stores/workbench.ts";
import { contentToScenes, commitStoryWithScene, getStoryDetail } from "@/api/StoryApi.ts";
import { Divider, message, Spin } from 'antd';
import { storeState } from "../../stores/storeState";
/**
 * 灵感生成图片
 */
function Afflatuse() {
      const workbenchState = useWorkbenchState()
      const [aiStatus, setAiStatus] = useState(true);
      const [showParagraph, setShowParagraph] = useState(false)
      const [paragraphList, setParagraphList] = useState([])
      const navigate = useNavigate();
      const store = useHookstate(storeState);
      const [messageApi, contextHolder] = message.useMessage();
      const [isLoading, setIsLoading] = useState(false);
      const location = useLocation();
      const storyId = new URLSearchParams(location.search).get('storyId');
      workbenchState.setStoryId(Number(storyId));
      const tapNextStep = async (radio, type) => {
            console.log("tapNextStep-------radio=" + JSON.stringify(radio))
            console.log("tapNextStep-------type=" + JSON.stringify(type))
            console.log("tapNextStep-------paragraphList=" + JSON.stringify(paragraphList))
            const scenes = paragraphList.map(item => (item as any).value)

            try {
                  setIsLoading(true);
                  const res = await commitStoryWithScene({
                        scenes: scenes,
                        storyId: new URLSearchParams(location.search).get('storyId'),
                        modelId: type.id,
                        ratioId: radio.id,
                  })
                  console.log('commitStoryWithScene res', res);

                  if (res) {
                        workbenchState.setNextStep();
                        navigate(`/rolesetting?storyId=${workbenchState.storyId}`, { replace: true })
                  }
            } catch (error) {
                  console.error(error);
            } finally {
                  setIsLoading(false);
            }



      }
      function editParagraphList(list: []) {
            setParagraphList(list ? list : [])

      }
      const tapChangeClick = async (type: number, content: string, cb?: () => void) => {
            console.log("--------type=" + type)
            console.log("--------content=" + content)
            if (type == 3) {
                  try {
                        !cb && setIsLoading(true);
                        const result = await contentToScenes({ isFromGPT: aiStatus ? 1 : 0, content: content })
                        if (result) {
                              workbenchState.setNextStep();
                              workbenchState.setStoryId(result.storyId);
                              navigate(`/afflatus?storyId=${result.storyId}`, { replace: true })

                              // 以下逻辑保留1个版本，稳定后可删除，当前版本v1.4
                              // const scenes = result.scenes.map(item => { return { 'value': item } })
                              // console.log("The scenes is ", scenes);
                              // let currentUrl = new URL(window.location.href);
                              // currentUrl.searchParams.set('storyId', result.storyId);
                              // history.replaceState(null, null, currentUrl.toString());
                              // setParagraphList(scenes);
                              // workbenchState.setStoryId(result.storyId);
                              // setShowParagraph(true);
                              // workbenchState.setNextStep();
                              // store.isReady.set(true);
                        }
                  } catch (error) {
                        console.error('contentToScenes error', error);
                  } finally {
                        setIsLoading(false);
                        cb();
                  }
            }

            if (type < 3) {
                  setParagraphList([])
                  setShowParagraph(false)
                  setAiStatus(!aiStatus)
            }
      }

      useEffect(() => {
            //防止页面后退
            history.pushState(null, null, document.URL);
            window.addEventListener("popstate", function () {
                  history.pushState(null, null, document.URL);
            });
      }, [])

      useEffect(() => {
            const handleStorey = async () => {
                  if (storyId) {
                        // setIsLoading(true);
                        const result = await getStoryDetail({ storyId })
                        setParagraphList(result.pictureListSub.map(pic => {
                              return { 'value': pic[0].content }
                        }));
                        setShowParagraph(true);
                        store.isReady.set(true);
                        // const result = await contentToScenes({ isFromGPT: aiStatus ? 1 : 0, content: content })
                        // console.log('result', result);
                        // if (result) {
                        //       const scenes = result.scenes.map(item => { return { 'value': item } })
                        //       let currentUrl = new URL(window.location.href);
                        //       currentUrl.searchParams.set('storyId', result.storyId);
                        //       history.replaceState(null, null, currentUrl.toString());
                        //       setParagraphList(scenes);
                        //       workbenchState.setStoryId(result.storyId);
                        //       setShowParagraph(true);
                        //       workbenchState.setNextStep();
                        //       store.isReady.set(true);
                        // }
                  }
            }
            handleStorey();
      }, [location])

      return (
            <div>
                  <div className="body-layout">
                        <div className="left">
                              <LeftStep progress={1}></LeftStep>
                        </div>
                        <div className="mid">
                              {showParagraph ? <ParagraphList arr={paragraphList} paragraphChange={editParagraphList} ></ParagraphList> :
                                    <WriteNovel aiStatus={aiStatus} tapChangeClick={tapChangeClick} isLoading={isLoading} ></WriteNovel>
                              }
                        </div>
                        {showParagraph && (<div className="right">
                              <RightStyle onClickNext={tapNextStep} ></RightStyle>
                        </div>)}
                        <Spin indicator={
                              <div>
                                    <img className="g-loading" src="https://p0.ssl.qhimg.com/t01a57f52802217ccdc.gif" />
                              </div>
                        } spinning={isLoading} fullscreen />
                  </div>
            </div>
      )

}

export default Afflatuse;