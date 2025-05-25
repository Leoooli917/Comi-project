import * as React from 'react';
import RightStyle from "../../components/rightstyle/RightStyle.tsx";
import LeftStep from "../../components/leftstep/LeftStep.tsx";
import ImageComponent from '@/components/imagecomponent/ImageComponent.tsx';
import Right from "../../components/picCreatorRight";

import './PicCreator.less'
import SingleColumnImageView from '@/components/singlecolumnimageview/SingleColumnImageView.tsx';
import { useState, useEffect, useRef } from 'react';
import { checkGenerate } from '../../api/StoryApi.ts';
import { useWorkbenchState } from "@/stores/workbench.ts";
import { usePicCreatorState } from "@/stores/picCreatorState.ts";
import { getCurrentUrlParamValue } from "@/utils/url"
import PubSub from "pubsub-js";


const SelectRole = ({ }) => {
    const storyId = getCurrentUrlParamValue("storyId")

    const workbenchState = useWorkbenchState()
    const picCreatorState = usePicCreatorState()

    const [selectImageItem, setSelectImageItem] = useState();
    const [curHistoryList, setCurHistoryList] = useState([]);

    const initData = [[], [], [], [], [], [], [], []]
    const [images, setImages] = useState(initData)
    const [roleList, setRoleList] = useState([])


    const [stopRequest, setStopRequest] = useState(false);
    const [checkImageId, setCheckImageId] = useState(0);

    useEffect(() => {
        if (workbenchState.storyId !== parseInt(storyId)) {
            workbenchState.setStoryId(parseInt(storyId))
        }
    }, [storyId])

    const parseResData = (data) => {
        if (!data || !data.pictureListSub) {
            return;
        }
        // console.log('images', images);

        // TODO 设置 poster

        const list = []
        data.pictureListSub.forEach((historyList, index) => {
            let obj = {}
            historyList.forEach(element => {
                if (element.chooseImg === 1) {
                    if (obj['history']) {
                        obj = { history: [...obj['history']] } // 保留之前设置的历史记录
                    }
                    obj = { ...obj, ...element }
                    if (selectImageItem) { // 有选中的
                        if (selectImageItem.id === obj.id) {
                            obj['isSelected'] = true;
                        }
                    } else { // 没有选中的
                        if (index === 0) { // 默认选中 index 0
                            obj['isSelected'] = true;
                        } else {
                            obj['isSelected'] = false;
                        }
                    }
                }
                if (element?.chooseImg == 1) {
                    element['isSelected'] = true;
                } else {
                    element['isSelected'] = false;
                }
                if (!obj['history']) {
                    obj['history'] = []
                }
                obj['history'].push(element)
            });
            list.push(obj);
        })
        // console.log('list', list);
        setImages(list)

        // console.log('curHistoryList', curHistoryList, list[0]);
        // console.log('selectImageItem', selectImageItem, !selectImageItem);


        if (!selectImageItem) {
            // console.log('1111111', list[0]);
            if (list[0]) {
                setSelectImageItem(list[0])
                setCurHistoryList(list[0].history)
            }
        } else {
            list.forEach(item => {
                if (item.id == selectImageItem.id) {
                    setSelectImageItem(item)
                    setCurHistoryList(item.history)
                }
            })
        }

        if (!roleList || roleList.length == 0) {
            setRoleList(data.actorPris)
        }
    }
    const [generateRes, setGenerateRes] = useState()

    useEffect(() => {
        parseResData(generateRes);
    }, [generateRes])

    // const [intervalValue, setIntervalValue] = useState()
    let interval = useRef(null)
    useEffect(() => {
        // console.log('stopRequest', stopRequest, interval.current);

        if (stopRequest && interval.current) {
            PubSub.publish("storyGenerateFinish")
            workbenchState.setCurrentStep(4);
            clearInterval(interval.current)
            return
        }
        const fetchData = async () => {
            try {
                const res = await checkGenerate({ storyId: storyId });
                // console.log('checkGenerate res', res);
                if (res) {
                    setGenerateRes(res);
                }
                if (checkImageId > 0) {
                    console.log('checkImageId', checkImageId);

                    let isFinish = true;
                    for (const items of res.pictureListSub) {
                        if (items.length > 0) {
                            const item = items[items.length - 1];
                            if (item.id === checkImageId && !item.imgUrl) {
                                isFinish = false;// 继续轮训查询
                                break;
                            }
                        }
                    }
                    if (isFinish) {
                        setStopRequest(true);
                        setCheckImageId(0)
                    }
                } else if (res && res.status >= 4) {
                    setStopRequest(true);
                }
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };
        // 每隔3秒请求一次数据，直到满足停止请求的条件
        interval.current = setInterval(() => {
            if (!stopRequest) {
                fetchData();
            } else {
                PubSub.publish("storyGenerateFinish")
            }
        }, 3000);

        // 清除定时器
        return () => {
            clearInterval(interval.current)
        };
    }, [stopRequest]);

    useEffect(() => {
        if (checkImageId == 0) {
            return;
        }
        setStopRequest(false);
    }, [checkImageId])

    useEffect(() => {
        picCreatorState.setIsLoading(!stopRequest)
        return () => {
            picCreatorState.setIsLoading(false)
        }
    }, [stopRequest])

    useEffect(() => {
        //防止页面后退
        history.pushState(null, null, document.URL);
        window.addEventListener("popstate", function () {
            console.log(111)
            history.pushState(null, null, document.URL);
        });
    }, [])

    function handleLeftItemClick(obj, index) {
        let arr = [...images]
        arr.map((item) => {
            if (item.id == obj.id) {
                item.isSelected = true
            } else {
                item.isSelected = false
            }
        })
        // console.log('handleLeftItemClick arr',arr);

        setImages(arr)
        console.log('handleLeftItemClick obj', obj);

        setSelectImageItem(obj)
        obj.history.map((item, index) => {
            if (item.chooseImg === 1) {
                item.isSelected = true
            } else {
                item.isSelected = false
            }
        })
        setCurHistoryList(obj.history)
    }


    const handleHistoryItemClick = (obj, index) => {
        const arr = [...curHistoryList]
        arr.map((item) => {
            if (item.id == obj.id && item.historyId === obj.historyId) {
                item.isSelected = true
            } else {
                item.isSelected = false
            }
        })

        setCurHistoryList(arr)
        // console.log('handleHistoryItemClick obj', obj);
        setSelectImageItem(obj)
    }

    const handleReGenerateFinish = (imageId) => {
        // console.log('handleReGenerateFinish imageId', imageId);
        setTimeout(() => {
            setCheckImageId(imageId)
        }, 3000);
    }

    const handleItemStatusChange = async () => {
        try {
            const res = await checkGenerate({ storyId: storyId });
            console.log('checkGenerate res', res);
            if (res) {
                setGenerateRes(res);
            }
        } catch (error) {
            console.error(error);
        }

    }


    return (
        <div>
            <div className="body-layout">
                <div className="left">
                    <LeftStep progress={4}></LeftStep>
                </div>
                <div className="mid">
                    <div className='img-list'>
                        <SingleColumnImageView
                            images={images}
                            onImageClick={handleLeftItemClick}>
                        </SingleColumnImageView>
                    </div>
                    <ImageComponent
                        onItemStatusChange={handleItemStatusChange}
                        selectImageItem={selectImageItem}
                        historyList={curHistoryList}
                        onHistoryItemClick={handleHistoryItemClick}
                        showButtons={true}
                    >
                    </ImageComponent>
                </div>
                <div className='right'>
                    <Right
                        roleList={roleList}
                        characterDescription={selectImageItem ? selectImageItem.personDes : ''} 
                        sceneDescription={selectImageItem ? selectImageItem.content : ''}
                        imageId={selectImageItem?.id}
                        onReGenerateFinish={handleReGenerateFinish}
                        storyId={storyId}
                        actorId={selectImageItem?.actorId}
                    />
                </div>
            </div>
        </div>
    )
};

export default SelectRole;