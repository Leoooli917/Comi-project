import { PhotoProvider, PhotoView } from 'react-photo-view';
import 'react-photo-view/dist/react-photo-view.css';
import './ImageComponent.less'; // 引入样式文件  
import { useEffect, useState, useRef } from 'react';
import { Flex, Progress } from 'antd';
import { useHookstate } from '@hookstate/core';
import { setPosterImg } from '@/api/StoryApi.ts';
import { setStoryImageForPart } from '@/api/PicApi';
import { download } from '@/utils/downloadUtil';





const ImageComponent = ({
  selectImageItem,
  showButtons = false,
  isSelected = false,
  historyList = [],
  onHistoryItemClick,
  onItemStatusChange }) => {

  console.log('selectImageItem',selectImageItem);
  // console.log('historyList', historyList);


  // const [currentItem, setCurrentItem] = useState(historyList[0]);

  const handleSeeSawPic = () => {

  }

  const handleDownload = () => {
    const history = getCurrentSelectHistory();
    if (history) {
      download(history.imgUrl);
    }
  }

  const handleSelectPic = async () => {
    const history = getCurrentSelectHistory();

    if (history) {
      try {
        await setStoryImageForPart({ picId: history.id, history_id: history.historyId })
        onItemStatusChange()
      } catch (error) {
        console.error(error);
      }
    }
  }

  const getCurrentSelectHistory = () => {
    return historyList.find(item => item.isSelected);
  }

  const handleSetCover = async () => {
    const history = getCurrentSelectHistory();

    if (history) {
      try {
        await setPosterImg({ picId: history.id, history_id: history.historyId })
        onItemStatusChange()
      } catch (error) {
        console.error(error);
      }
    }
  }


  const buttonUrls = [
    {
      url: 'https://p1.ssl.qhimg.com/t014126482b3e9c6003.png',
      alt_value: '查看原图',
      action: handleSeeSawPic,
    },
    {
      url: 'https://p3.ssl.qhimg.com/t01da871e48295ca584.png',
      alt_value: '下载图片',
      action: handleDownload,
    },
    {
      url: 'https://p0.ssl.qhimg.com/t01f01b6d4da4b38165.png',
      alt_value: '选定图片',
      action: handleSelectPic,
    },
    {
      url: 'https://p3.ssl.qhimg.com/t01263918dfddd71214.png',
      alt_value: '设为封面',
      action: handleSetCover,
    }
  ];

  const progress = useHookstate<number>(5)
  let interval = useRef(null)

  useEffect(() => {
    const updateProgress = () => {
      // console.log('updateProgress', progress.get());
      if (progress.get() >= 98) {
        progress.set(98);
      } else {
        progress.merge(p => p + 5)
      }
    }

    if (!selectImageItem?.imgUrl) {
      interval.current = setInterval(updateProgress, 2000);
    }
    return () => clearInterval(interval.current);
  }, [selectImageItem])


  return (
    <div className="image-component">
      <div className='top-imgs'>
        {
          historyList.map((item, index) => (
            <div key={index} className={`img-div ${item.isSelected ? 'is-active' : ''}`} onClick={() => onHistoryItemClick && onHistoryItemClick(item, index)}>
              {item.imgUrl && <img src={item.imgUrl} className="top-img-item" />}
              {!item.imgUrl && <div className='loading'>
                <img src="https://p0.ssl.qhimg.com/t0193e7a01b10889674.png" alt="" />
              </div>}
              {/* {item.isSelected && <img className='first-page' src='https://p2.ssl.qhimg.com/t0125ed63c507b78c36.png'></img>} */}
            </div>

          ))

        }

      </div>

      <div className="image-wrapper">

        {selectImageItem?.imgUrl &&
          <PhotoProvider>
            <PhotoView src={selectImageItem?.imgUrl}>
              <div className='img-div'>
                <img src={selectImageItem?.imgUrl} alt="图片" className="main-image" />
                {isSelected && <img className='first-page' src='https://p2.ssl.qhimg.com/t0125ed63c507b78c36.png'></img>}
                {selectImageItem?.isPoster === 1 && <img className='poster-tag' src='https://p2.ssl.qhimg.com/t0125ed63c507b78c36.png'></img>}
              </div>
            </PhotoView>
          </PhotoProvider>}
        {!selectImageItem?.imgUrl &&
          <div className='loading'>
            <div className='loading-tips'>
              <Progress percent={progress.get()} status="active" showInfo={false} strokeColor={{ from: '#9065fb', to: '#546bfd' }} />
              <span>正在努力生成中……</span>
            </div>
          </div>
        }


        <div className="button-container" style={{ visibility: (selectImageItem?.imgUrl && showButtons ? 'visible' : 'hidden') }}>
          {buttonUrls.map((item, index) => (
            item.alt_value === '查看原图' ? (
              <PhotoProvider key={index}>
                <PhotoView src={selectImageItem?.imgUrl} >
                  <div key={index} className='img-btn-shape-hover' onClick={item.action}>
                    <div className='img-btn-shape'>
                      <img src={item.url} alt={`Button ${item.alt_value}`} className="image-button" />
                    </div>
                    <p className="hover-text">{item.alt_value}</p>
                  </div>
                </PhotoView>
              </PhotoProvider>
            ) : (
              <div key={index} className='img-btn-shape-hover' onClick={item.action}>
                <div className='img-btn-shape'>
                  <img src={item.url} alt={`Button ${item.alt_value}`} className="image-button" />
                </div>
                <p className="hover-text">{item.alt_value}</p>
              </div>
            )
          ))}
        </div>
      </div>
      <p className="text-description">{selectImageItem?.content}</p>
    </div>
  );
};

export default ImageComponent;