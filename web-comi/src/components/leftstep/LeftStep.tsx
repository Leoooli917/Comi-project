import { useEffect, useState, useLayoutEffect } from 'react';
import { getStoryDetail } from "@/api/StoryApi.ts";
import { getCurrentUrlParamValue } from "@/utils/url";
import { useWorkbenchState } from "@/stores/workbench.ts";
import { useNavigate } from 'react-router-dom';
import { getTagsByType } from '@/api/TagsApi';
import './LeftStep.css';

function LeftStep() {
        const navigate = useNavigate();
        const [storyStyle, setStoryStyle] = useState('story')
        const [storyImg, setStoryImg] = useState('https://s2.loli.net/2024/03/27/clYC5EbKyZFHpIu.png')
        const [roleStyle, setRoleStyle] = useState('role')
        const [roleImg, setRoleImg] = useState('')
        const [progress, setProgress] = useState(0);
        const [imgResultStyle, setImgResultStyle] = useState('result-img')
        const [imgResult, setImgResult] = useState('');
        const workbenchState = useWorkbenchState();

        useLayoutEffect(() => {
                // 检查登陆状态
                getTagsByType({ type: 1 }).then(() => { });
        });

        useEffect(() => {
          //正常状态的
          //故事
          setStoryImg('https://s2.loli.net/2024/03/27/PM3mKeFU4RpEGrI.png')
          setStoryStyle('story-selected')
          //角色
          setRoleImg("https://s2.loli.net/2024/03/27/lHtKhF3MLmYsVo4.png")
          setStoryStyle('role')
          //图片
          setImgResult("https://s2.loli.net/2024/03/27/ZoAbeEO7DPwvRfc.png")
          setImgResultStyle('result-img')
          // if(progress >0){//故事被选中
          //         //
          //         setStoryImg('https://s2.loli.net/2024/03/27/PM3mKeFU4RpEGrI.png')
          //         setStoryStyle('story-selected')
          // }
          if (progress > 1) {//角色被选中
                  //
                  setRoleImg('https://s2.loli.net/2024/03/27/uHDaqYBRJ8Ggxro.png')
                  setRoleStyle('role-selected')
          }
          if (progress > 2) {
                  //
                  setImgResult('https://s2.loli.net/2024/03/27/BR2eXTEx3lQMnip.png')
                  setImgResultStyle('story-selected')
          }
        }, [progress]);
      
        useEffect(() => {
          console.log("test refrash page");
        }, [])
      
        useEffect(() => {
                // /afflatus
                if (workbenchState.storyId) {
                        getStoryDetail({ storyId: workbenchState.storyId }).then(res => {
                                setProgress(res.status);
                        });

                }
        }, [location]);
      
        return (
          <>
            <div className='left-body'>
              <div className="pointer" onClick={() => {
                console.log("*****",workbenchState.currentStep)
                navigate(`/afflatus?storyId=${workbenchState.storyId}`, { replace: true });
                workbenchState.setCurrentStep(1);
              }}>
                <img className='img' src={storyImg} />
                <div className={`${storyStyle} text-overflow`}>故事</div>
              </div>
              <div className={`vertical-line`}></div>
              <div className={`${progress > 1 ? 'pointer' : 'no-drop'}`} onClick={() => {
                if (progress > 1) {
                  navigate(`/rolesetting?storyId=${workbenchState.storyId}`, { replace: true })
                  workbenchState.setCurrentStep(2);
                }
              }}>
                <img className='img' src={roleImg} />
                <div className={`${roleStyle} text-overflow`}>角色</div>
              </div>
              <div className={`vertical-line`}></div>
              <div className={`${progress > 2 ? 'pointer' : 'no-drop'}`} onClick={() => {
                if (progress > 2) {
                  navigate(`/piccreator?storyId=${workbenchState.storyId}`, { replace: true });
                  workbenchState.setCurrentStep(3);
                }
              }}>
                <img className='img' src={imgResult} />
                <div className='story text-overflow' style={{ marginTop: 0 }}>图片</div>
              </div>
            </div>
          </>
        )
}

export default LeftStep

