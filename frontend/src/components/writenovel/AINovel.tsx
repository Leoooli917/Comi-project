
import { Button, Input, ConfigProvider, Space } from 'antd';
import TextArea from 'antd/es/input/TextArea';
import './WriteNovel.less'
import { useState } from 'react'
import { useHookstate } from '@hookstate/core';
import PubSub from "pubsub-js";

// const [aiStatus, setAiStatus] = useState(true);

function WriteNovel({ tapChangeClick, aiStatus }) {
   const content = useHookstate<string>('')
   const gradientColors = ['#FD8972', '#FD559B', ' #9065FB', '#546BFD'];
   const isLoading = useHookstate<boolean>(false);


   return (
      <div className='contaniner'>
         <div className='content'>
            <div className='write-novel-body'>
               <img src="https://p0.ssl.qhimg.com/t015c964f77ef687e66.png" />
               {/* <Input className="write-novel-compact" disabled={isLoading} placeholder='把你的故事灵感告诉我，我来帮你完成吧！'
                  value={content.get()} onChange={e => content.set(e?.target.value)} /> */}

               <div className={`text-area-container ${aiStatus ? '' : 'notAI-container'}`}>
                  <div className={`bgc ${isLoading.get() ? 'bgc-loading' : ''}`}></div>
                  <textarea
                     disabled={isLoading.get() ? true : false}
                     onChange={e => content.set(e?.target.value)}
                     className={`new-textarea ${aiStatus ? '' : 'notAI-textarea'}`}
                     placeholder='把你的故事灵感告诉我，我来帮你完成吧！'
                  />
               </div>
            </div>
            <div className='story'>
               {
                  aiStatus &&
                  <button className='describe' disabled={isLoading.get()} onClick={() => tapChangeClick(2)}>
                     <div className='describe2'>我的故事写好了，直接上传</div>
                  </button>
               }
               {
                  !aiStatus &&
                  <button className='describe' onClick={() => tapChangeClick(1)}>
                     <div className='describe2'>只有想法，文绘AI 帮我完成</div>
                  </button>
               }
               <Button className='aiBtn' loading={isLoading.get()} disabled={isLoading.get()} onClick={tapAIBtn}>
                  {isLoading.get() ? "生成中 " : "AI生成"}
               </Button>
            </div>
            {/* <div className='funcDesc'>
               <div className='describe'> 1. 输入灵感后，ai智能生成，需要生成动画交互；</div>
               <div className='describe'>2. 粘贴自己已有的故事后，是否需要ai按自己的逻辑进行分场景整理后展示</div>
            </div> */}
         </div>
      </div>
   )
   function tapAIBtn() {
      PubSub.publish("resetStep");
      isLoading.set(true);
      tapChangeClick(3, content.get(), () => isLoading.set(false))
   }
}


export default WriteNovel