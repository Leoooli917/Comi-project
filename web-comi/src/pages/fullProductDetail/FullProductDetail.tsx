/* eslint-disable no-restricted-globals */
import { useEffect, useRef, useState } from "react";
// eslint-disable-next-line
import { A11y, Navigation, Pagination, Scrollbar, Mousewheel } from 'swiper/modules';
// eslint-disable-next-line
// import { Swiper, SwiperSlide } from 'swiper/swiper-react';
import { Swiper, SwiperSlide } from 'swiper/react';

import SwiperCore from "swiper";

import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import { useNavigate } from 'react-router-dom';




import styles from './FullProductDetail.module.css'

import { getStoryDetail, loveStory, unloveStory, collectStory, uncollectStory, getAuthorInfo, downLoadStoryImg, commitStoryPublish } from "@/api/StoryApi.ts";

import { getCurrentUrlParamValue } from "@/utils/url"

import { GlobalModel } from '@/model/Global';


// Install Swiper modules
SwiperCore.use([Navigation]);

let unlove_url = 'https://p4.ssl.qhimg.com/t110b9a930186b79de66c18e15b.png'
let lovel_url = 'https://p4.ssl.qhimg.com/t110b9a9301c220e09bc897c673.png'
let uncollectlogo_url = 'https://p4.ssl.qhimg.com/t110b9a930123bb6430e3653c7f.png'
let collectlogo_url = 'https://p2.ssl.qhimg.com/t110b9a93018c7dee4210a696fd.png'
let arrow_left = 'https://p2.ssl.qhimg.com/t110b9a9301eed08be1569c3a1c.png'
let arrow_right = 'https://p2.ssl.qhimg.com/t110b9a93019909b9eaf37b03e1.png'
//  const dataArr = [
//   { index:1,imageUrl: 'https://p2.ssl.qhimg.com/t01821d3947a7201d57.png', title:"孙悟空三打白骨精1", describe: "孙悟空三打白骨精的故事发生在唐僧师徒四人在取经路上的过程中。这一天，他们来到了一个叫做骷髅山的地方，山上有一座白骨精的洞府。白骨精是一个千年妖怪，她用自己的妖术把骷髅山上的骷髅变成了自己的手下，欺压当地的百姓。唐僧听说了这个情况后，决定让孙悟空去打败白骨精，为民除害"},
//   { index:2,imageUrl: 'https://p2.ssl.qhimg.com/t01821d3947a7201d57.png', title:"孙悟空三打白骨精2", describe: "孙悟空三打白骨精的故事发生在唐僧师徒四人在取经路上的过程中。这一天，他们来到了一个叫做骷髅山的地方，山上有一座白骨精的洞府。白骨精是一个千年妖怪，她用自己的妖术把骷髅山上的骷髅变成了自己的手下，欺压当地的百姓。唐僧听说了这个情况后，决定让孙悟空去打败白骨精，为民除害"},
//   { index:3,imageUrl: 'https://p2.ssl.qhimg.com/t01821d3947a7201d57.png', title:"孙悟空三打白骨精3", describe: "孙悟空三打白骨精的故事发生在唐僧师徒四人在取经路上的过程中。这一天，他们来到了一个叫做骷髅山的地方，山上有一座白骨精的洞府。白骨精是一个千年妖怪，她用自己的妖术把骷髅山上的骷髅变成了自己的手下，欺压当地的百姓。唐僧听说了这个情况后，决定让孙悟空去打败白骨精，为民除害"},
//   { index:4,imageUrl: 'https://p2.ssl.qhimg.com/t01821d3947a7201d57.png', title:"孙悟空三打白骨精4", describe: "孙悟空三打白骨精的故事发生在唐僧师徒四人在取经路上的过程中。这一天，他们来到了一个叫做骷髅山的地方，山上有一座白骨精的洞府。白骨精是一个千年妖怪，她用自己的妖术把骷髅山上的骷髅变成了自己的手下，欺压当地的百姓。唐僧听说了这个情况后，决定让孙悟空去打败白骨精，为民除害"},
//   { index:5,imageUrl: 'https://p2.ssl.qhimg.com/t01821d3947a7201d57.png', title:"孙悟空三打白骨精5", describe: "孙悟空三打白骨精的故事发生在唐僧师徒四人在取经路上的过程中。这一天，他们来到了一个叫做骷髅山的地方，山上有一座白骨精的洞府。白骨精是一个千年妖怪，她用自己的妖术把骷髅山上的骷髅变成了自己的手下，欺压当地的百姓。唐僧听说了这个情况后，决定让孙悟空去打败白骨精，为民除害"},
//   { index:6,imageUrl: 'https://p2.ssl.qhimg.com/t01821d3947a7201d57.png', title:"孙悟空三打白骨精6", describe: "孙悟空三打白骨精的故事发生在唐僧师徒四人在取经路上的过程中。这一天，他们来到了一个叫做骷髅山的地方，山上有一座白骨精的洞府。白骨精是一个千年妖怪，她用自己的妖术把骷髅山上的骷髅变成了自己的手下，欺压当地的百姓。唐僧听说了这个情况后，决定让孙悟空去打败白骨精，为民除害"},
//   { index:7,imageUrl: 'https://p2.ssl.qhimg.com/t01821d3947a7201d57.png', title:"孙悟空三打白骨精7", describe: "孙悟空三打白骨精的故事发生在唐僧师徒四人在取经路上的过程中。这一天，他们来到了一个叫做骷髅山的地方，山上有一座白骨精的洞府。白骨精是一个千年妖怪，她用自己的妖术把骷髅山上的骷髅变成了自己的手下，欺压当地的百姓。唐僧听说了这个情况后，决定让孙悟空去打败白骨精，为民除害"},
//   { index:8,imageUrl: 'https://p2.ssl.qhimg.com/t01821d3947a7201d57.png', title:"孙悟空三打白骨精8", describe: "孙悟空三打白骨精的故事发生在唐僧师徒四人在取经路上的过程中。这一天，他们来到了一个叫做骷髅山的地方，山上有一座白骨精的洞府。白骨精是一个千年妖怪，她用自己的妖术把骷髅山上的骷髅变成了自己的手下，欺压当地的百姓。唐僧听说了这个情况后，决定让孙悟空去打败白骨精，为民除害"},
//   { index:9,imageUrl: 'https://p2.ssl.qhimg.com/t01821d3947a7201d57.png', title:"孙悟空三打白骨精9", describe: "孙悟空三打白骨精的故事发生在唐僧师徒四人在取经路上的过程中。这一天，他们来到了一个叫做骷髅山的地方，山上有一座白骨精的洞府。白骨精是一个千年妖怪，她用自己的妖术把骷髅山上的骷髅变成了自己的手下，欺压当地的百姓。唐僧听说了这个情况后，决定让孙悟空去打败白骨精，为民除害"},
//   { index:10,imageUrl: 'https://p2.ssl.qhimg.com/t01821d3947a7201d57.png', title:"孙悟空三打白骨精10", describe: "孙悟空三打白骨精的故事发生在唐僧师徒四人在取经路上的过程中。这一天，他们来到了一个叫做骷髅山的地方，山上有一座白骨精的洞府。白骨精是一个千年妖怪，她用自己的妖术把骷髅山上的骷髅变成了自己的手下，欺压当地的百姓。唐僧听说了这个情况后，决定让孙悟空去打败白骨精，为民除害"},
//   { index:11,imageUrl: 'https://p2.ssl.qhimg.com/t01821d3947a7201d57.png', title:"孙悟空三打白骨精11", describe: "孙悟空三打白骨精的故事发生在唐僧师徒四人在取经路上的过程中。这一天，他们来到了一个叫做骷髅山的地方，山上有一座白骨精的洞府。白骨精是一个千年妖怪，她用自己的妖术把骷髅山上的骷髅变成了自己的手下，欺压当地的百姓。唐僧听说了这个情况后，决定让孙悟空去打败白骨精，为民除害"},
//   { index:12,imageUrl: 'https://p2.ssl.qhimg.com/t01821d3947a7201d57.png', title:"孙悟空三打白骨精12", describe: "孙悟空三打白骨精的故事发生在唐僧师徒四人在取经路上的过程中。这一天，他们来到了一个叫做骷髅山的地方，山上有一座白骨精的洞府。白骨精是一个千年妖怪，她用自己的妖术把骷髅山上的骷髅变成了自己的手下，欺压当地的百姓。唐僧听说了这个情况后，决定让孙悟空去打败白骨精，为民除害"},
// ];






function CustomButtom({ logoUrl, text, onClick }) {
  return <div className={styles.CustomButtom} onClick={onClick}>
    <img className={styles.CustomButtom_logo} src={logoUrl}></img>
    <div className={styles.bottomtext}>{text}</div>
  </div>
}

function CustomDownloadButtom({ isDark, logoUrl, text, onClick }) {
  return <div className={isDark ? styles.CustomDownloadDarkButtom : styles.CustomDownloadButtom} onClick={onClick}>
    <div className={styles.CustomDownloadButtomContainer}>
      <img className={styles.CustomDownloadButtomlogo} src={logoUrl}></img>
      <div className={styles.CustomDownloadButtomText}>{text}</div>
    </div>
  </div>
}

function PublishSuccessTips({ onClose, onAgaginPublish, onBackHome }) {

  return (<>
    <div className={styles.pubulish_container}>
      <div className={styles.publish_show}>
        <img className={styles.close_btn} src="https://p1.ssl.qhimg.com/t110b9a9301d741421c6ffef9df.png" onClick={onClose}></img>
        <div className={styles.publish_content}>
          <img className={styles.backImg} src="https://p1.ssl.qhimg.com/t110b9a9301bc2a915b0bfcc48b.png"></img>
          <img className={styles.again_publish} src="https://p3.ssl.qhimg.com/t110b9a9301f58dba73a76f6f89.png" onClick={onAgaginPublish}></img>
          <img className={styles.back_publish} src="https://p1.ssl.qhimg.com/t110b9a9301b0d56500d537bf77.png" onClick={onBackHome}></img>
        </div>
      </div>
    </div>
  </>)
}

function RightSlideImage({ selfStatus, editStatus, storyDetail, authorStory, collect = false, star = false, starNum, dataArr = [], swiperRef, onSlideChange, onStarChange, onCollectChange, onDownLoad, onPublish, cIndex }) {


  const slideNext = () => {
    if (swiperRef.current !== null && swiperRef.current.swiper !== null) {
      swiperRef.current.swiper.slideNext();
    }
  };

  const slidePrev = () => {
    if (swiperRef.current !== null && swiperRef.current.swiper !== null) {
      swiperRef.current.swiper.slidePrev();
    }
  };

  function clickProductItem(id) {
    window.open(`/productDetail?stroyId=${id}`)
  }

  function clickEdit() {
    window.history.back()
  }
  return (
    <>
      <div className={styles.rightContent}>

        <Swiper slidesPerView={1}
          ref={swiperRef}
          onSlideChangeTransitionEnd={
            onSlideChange
          }
          className={styles.swiper}
        >
          {dataArr.map((item, index) => (
            <SwiperSlide key={index}
              pagination={{
                type: 'fraction',
              }}
              navigation={`true`}
              modules={[Pagination, Navigation]}
            >

              <div className={styles.rigth_container}>
                <div className={styles.storyContainer}>
                  <div className={styles.topImageContainer}>
                    {editStatus && <div className={styles.editImage} onClick={clickEdit}>编辑图片</div>}
                    <img className={styles.bigImage} src={item.imgUrl}></img>
                  </div>
                  <div className={styles.arrowContainer}>
                    <img className={cIndex > 0 ? styles.left_arrow : styles.left_arrow_h} src={arrow_left} onClick={slidePrev}></img>
                    <img className={cIndex < dataArr.length - 1 ? styles.right_arrow : styles.right_arrow_h} src={arrow_right} onClick={slideNext}></img>
                  </div>
                  <div className={styles.storyDescribe}>{item.content}</div>
                </div>

              </div>
            </SwiperSlide>
          ))}
        </Swiper>
        {
          storyDetail && <div className={styles.storyInfoContainer}>
            <HeaderView title={storyDetail.title} collect={collect} star={star} starNum={starNum} onStarChange={onStarChange} onCollectChange={onCollectChange} />
            {
              !selfStatus && <div className={styles.author_container}>
                <AuthorInfo storyDetail={authorStory} onclickProductItem={clickProductItem} />
              </div>
            }
            {
              selfStatus && <div>

                <UserOperate editStatus={editStatus} onDownLoad={onDownLoad} onPublish={onPublish}></UserOperate>
              </div>
            }
          </div>
        }

      </div>
    </>
  );

}

function AuthorInfo({ storyDetail, onclickProductItem }) {
  return <>
    <div className={styles.author_content}>
      {storyDetail &&
        <>
          <div className={styles.infomiddleLine}></div>
          <div className={styles.authorInfo}>
            {/* <img className={styles.author_logo" src={storyDetail.authorLogo}></img> */}
            <div className={styles.author_logo}>{storyDetail.authorName[0]}</div>
            <div className={styles.author_name}>
              <div className={styles.name}>{storyDetail.authorName}</div>
              <div className={styles.articleNum}>
                {/* <div className={styles.article}>已发布文章</div> */}
                <div className={styles.article}> {storyDetail.pubulishDescribe} </div>
                {/* <div className={styles.article}>篇</div> */}
              </div>
            </div>
          </div>
          <div className={styles.infomiddleLine}></div>
          <ul className={styles.product_list}>
            {storyDetail.products.map((item, index) => (
              <li key={index} className={styles.product} onClick={() => onclickProductItem(item.id)}>{`${item.title}`}</li>
            ))}
          </ul>
        </>
      }
    </div>
  </>
}

function UserOperate({ editStatus, onDownLoad, onPublish }) {

  function onShare() {
    console.error("功能暂未提供")
  }

  return <div className={styles.userOperateContainer}>
    <div className={styles.middleLine}></div>
    <CustomDownloadButtom isDark={true} logoUrl="https://p1.ssl.qhimg.com/t110b9a9301fb21081da57653a9.png" text="下载" onClick={onDownLoad}></CustomDownloadButtom>
    {editStatus && <CustomDownloadButtom isDark={true} logoUrl="https://p3.ssl.qhimg.com/t110b9a930140af697e68d3fa38.png" text="发布" onClick={onPublish}></CustomDownloadButtom>
    }
    <div className={styles.toshared}>
      <div className={styles.sharemiddleLine}></div>
      <div className={styles.shareText}>分享至</div>
      <div className={styles.sharemiddleLine}></div>
    </div>
    <CustomDownloadButtom isDark={false} logoUrl="https://p5.ssl.qhimg.com/t110b9a93015c9dd504c538b798.png" text="转至快剪辑" onClick={onShare}></CustomDownloadButtom>
    <CustomDownloadButtom isDark={false} logoUrl="https://p2.ssl.qhimg.com/t110b9a9301f93c35a555ff18df.png" text="转至剪映" onClick={onShare}></CustomDownloadButtom>

    <CustomDownloadButtom isDark={false} logoUrl="https://p0.ssl.qhimg.com/t110b9a9301d2c3c826031d42da.png" text="分享小红书" onClick={onShare}></CustomDownloadButtom>
    <CustomDownloadButtom isDark={false} logoUrl="https://p1.ssl.qhimg.com/t110b9a9301548fe2aaa7f9a420.png" text="分享到抖音" onClick={onShare}></CustomDownloadButtom>
  </div>
}

function HeaderView({ title, collect = false, star = false, starNum, onStarChange, onCollectChange }) {
  return <div className={styles.headerView}>
    <div className={styles.title}>{title}</div>
    <div className={styles.collect_content}>
      <div className={styles.star_contain}>
        <CustomButtom logoUrl={collect ? collectlogo_url : uncollectlogo_url} text={collect ? "已收藏" : "加入收藏"} onClick={onCollectChange} />
      </div>
      <div className={styles.star_contain}>
        <CustomButtom logoUrl={star ? lovel_url : unlove_url} text={starNum} onClick={onStarChange} />
      </div>
    </div>
  </div>
}


function LeftSlideImage({ dataArr = [], selectIndex, onHandleClick }) {
  const listItems = dataArr.map((item, index) =>
    <li className={styles.image_container} key={index} onClick={() => onHandleClick(index)}>
      <div className={styles.image_index}>
        <div className={styles.imagetext}>{index >= 9 ? index + 1 : `0${index + 1}`}</div>
      </div>
      <img className={selectIndex == index ? styles.imagedd : styles.image} src={item.imgUrl}></img>
    </li>
  )

  return <ul className={styles.LeftSlideImage}>{listItems}</ul>
}




const FullProductDetail = () => {

  var stroyId = useRef("")
  var [arr, setArr] = useState([]);
  var [currentIndex, setCurrentIndex] = useState(0)
  var [star, setStar] = useState(false)
  var [collect, setCollect] = useState(false)
  var starCount = useRef(0)
  const swiperRef = useRef(null);
  var [storyDetail, setStoryDetail] = useState(null)
  var [authorStory, setAuthorStory] = useState(null)
  var [editStatus, setEditStatus] = useState(false)
  var [selfStatus, setSelfStatus] = useState(false)
  var [pubulishStatus, setPubulishStatus] = useState(false)

  const navigate = useNavigate();

  const getStoreDetail = async (storyId) => {
    try {
      const res = await getStoryDetail({
        storyId: storyId
      })
      setStoryDetail(res)
      console.log('getStoryDetail res', res);
      //isHistory
      let pictureListSub = res.pictureListSub
      let listSub = []
      for (let index = 0; index < pictureListSub.length; index++) {
        let sub = pictureListSub[index].filter(item => item.chooseImg === 1);
        if (sub.length > 0) {
          listSub.push(sub[0])
        }
      }

      listSub.forEach((item) => {
        item.title = res.title
      })

      starCount.current = res.starCount
      setStar(res.star)
      setCollect(res.collect)
      setArr(listSub)

      let userName = res.userName
      if (GlobalModel.isLogin) {
        let userInfo = localStorage.getItem("comi-user")
        userInfo = JSON.parse(userInfo)
        if (userInfo.userName == userName) {
          setSelfStatus(true)
          let isPreview = getCurrentUrlParamValue("preview")
          if (isPreview) {
            setEditStatus(true)
          }
        } else {
          setSelfStatus(false)
          setEditStatus(false)
        }
       
      }

      const resData3 = await getAuthorInfo({
        storyId: storyId
      })

      if (resData3) {
        var res3 = {
          authorLogo: "https://himg.bdimg.com/sys/portrait/item/pp.1.ff2999e7.GE9vpAWmGjHHP3CBahyIlg?_t=1718958388289",
          authorName: resData3.comiUser.displayName,
          products: resData3.storyList.slice(0, 5),
          pubulishDescribe: `已发布文章 ${resData3.storyList.length} 篇`
        }
        setAuthorStory(res3)
      }


    } catch (error) {
      console.error(error);
    }
  }

  const starSory = async (storyId) => {
    try {
      if (star) {
        const res = await unloveStory({
          storyId: storyId
        })
        if (res) {
          starCount.current = starCount.current - 1
          setStar(!star)
        }
      } else {
        const res = await loveStory({
          storyId: storyId
        })
        if (res) {
          starCount.current = starCount.current + 1
          setStar(!star)
        }
      }

    } catch (error) {
      console.error(error);
    }
  }

  const collectSory = async (storyId) => {
    try {
      if (collect) {
        const res = await uncollectStory({
          storyId: storyId
        })
        setCollect(!collect)
      } else {
        const res = await collectStory({
          storyId: storyId
        })
        setCollect(!collect)
      }


    } catch (error) {
      console.error(error);
    }
  }


  useEffect(() => {
    stroyId.current = getCurrentUrlParamValue("stroyId")
    getStoreDetail(stroyId.current)

    document.addEventListener("keyup", PopupKeyUp, false)
    return () => {
      document.removeEventListener("keyup", PopupKeyUp, false)
    }

  }, [])

  const PopupKeyUp = (e) => {
    switch (e.code) {
      case 'ArrowUp':
        console.log('Up arrow key pressed');
        if (swiperRef.current !== null && swiperRef.current.swiper !== null) {
          swiperRef.current.swiper.slidePrev();
        }
        break;
      case 'ArrowDown':
        console.log('Down arrow key pressed');
        if (swiperRef.current !== null && swiperRef.current.swiper !== null) {
          swiperRef.current.swiper.slideNext();
        }
        break;
      case 'ArrowLeft':
        console.log('Left arrow key pressed');
        if (swiperRef.current !== null && swiperRef.current.swiper !== null) {
          swiperRef.current.swiper.slidePrev();
        }
        break;
      case 'ArrowRight':
        console.log('Right arrow key pressed');
        if (swiperRef.current !== null && swiperRef.current.swiper !== null) {
          swiperRef.current.swiper.slideNext();
        }
        break;
      default:
        break;
    }
  }

  function handleClick(index) {
    setCurrentIndex(index)
    swiperRef.current.swiper.slideTo(index)
  }

  function onSlideChange() {
    let activeIndex = swiperRef.current.swiper.activeIndex
    setCurrentIndex(activeIndex)
    console.log(activeIndex)
  }

  function starChange() {
    starSory(stroyId.current)
  }

  function collectChange() {
    collectSory(stroyId.current)
  }

  function close() {
    setPubulishStatus(false)
  }

  function agaginPublish() {
    navigate('/afflatus', { replace: true })
  }

  function backHome() {
    handleToHome()
  }

  async function onDownLoad() {
    try {

      let blob = await downLoadStoryImg(storyDetail.id)
      const urlToDownload = URL.createObjectURL(blob);

      // 创建一个a标签并模拟点击  
      const link = document.createElement('a');
      link.href = urlToDownload;
      link.setAttribute('download', storyDetail.title + ".zip");
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      // 释放创建的URL对象  
      URL.revokeObjectURL(urlToDownload);
    } catch (error) {
      console.error(error);
    }
  }

  function onPublish() {
    handleClickPublish()
  }

  const handleClickPublish = async () => {
    try {
      let idd = stroyId.current
      const res = await commitStoryPublish({ storyId: idd })
      console.log('commitStoryPublish res', res);
      if (res) {
        setPubulishStatus(true)
      }
    } catch (error) {
      console.error(error);
    }
  }

  const handleToHome = () => {
    navigate('/', { replace: true })
  }

  return (
    <>
      <main className={styles.main_container}>
        <div className={styles.left_container}>
          <LeftSlideImage dataArr={arr} selectIndex={currentIndex} onHandleClick={handleClick} />
        </div>
        <div className={styles.right_container}>
          <RightSlideImage selfStatus={selfStatus} editStatus={editStatus} storyDetail={storyDetail} authorStory={authorStory} collect={collect} star={star} starNum={starCount.current} dataArr={arr} swiperRef={swiperRef} onSlideChange={onSlideChange} onStarChange={starChange} onCollectChange={collectChange} onPublish={onPublish} onDownLoad={onDownLoad} cIndex={currentIndex} />
        </div>

      </main>
      {pubulishStatus && <PublishSuccessTips onClose={close} onAgaginPublish={agaginPublish} onBackHome={backHome} ></PublishSuccessTips>}
    </>


  );
};

export default FullProductDetail;
