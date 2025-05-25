
// import TextArea from 'antd/es/input/TextArea';
import './WriteNovel.less'

import { useState } from 'react'
import { useHookstate } from '@hookstate/core';
import { Button } from 'antd';


function WriteNovel2({ tapChangeClick, isLoading }) {

   const content = useHookstate<string>('')

   return (
      <div className='contaniner'>
         <div className='content'>
            <div className='write-novel-body'>
               <textarea className="write-novel" disabled={isLoading} value={content.get()} onChange={e => content.set(e.target.value)} placeholder='请输入小说内容，字数不多于500字哦，我正在努力生成中 ～' />
            </div>
            <div className='story'>
               <button className='describeContainer2' onClick={tapStoryBtn}>
                  <div className='describe2'>只有想法，文绘AI 帮我完成</div>
               </button>
               <Button className='aiBtn' loading={isLoading} disabled={isLoading} onClick={tapAIBtn}>提交</Button>
            </div>
         </div>
      </div>
   )
   function tapAIBtn() {
      tapChangeClick(3, content.get())
      //setAiStatus(!aiStatus)
   }
   function tapStoryBtn() {
      tapChangeClick(1)
   }
}




export default WriteNovel2


