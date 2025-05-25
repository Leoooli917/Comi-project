
import { Button, Input, Select, Space } from 'antd';
import TextArea from 'antd/es/input/TextArea';
import './WriteNovel.less'
import AINovel from "./AINovel.tsx";
import DirectNovel from "./DirectNovel.tsx";


// import { useState } from 'react'
// const [aiStatus, setAiStatus] = useState(true);

function WriteNovel({ aiStatus, tapChangeClick, isLoading }) {
   // let content = <DirectNovel tapChangeClick={tapChangeClick} isLoading={isLoading} />
   // if (aiStatus) {
   //    content = <AINovel aiStatus={aiStatus} tapChangeClick={tapChangeClick} isLoading={isLoading} />
   // }

   const content = <AINovel aiStatus={aiStatus} tapChangeClick={tapChangeClick} />

   return (
      <div className='contaniner'>
         {content}
      </div>
   )
}


export default WriteNovel