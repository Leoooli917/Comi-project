
/* eslint-disable no-restricted-globals */
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



import './ProductDetail.css'

import { getStoryDetail,loveStory,unloveStory,collectStory,uncollectStory,getAuthorInfo,getMostPopularStory } from "@/api/StoryApi.ts";

import {getCurrentUrlParamValue} from "@/utils/url"
import { count } from "console";


// Install Swiper modules
SwiperCore.use([Navigation]);


let unlove_url = 'https://p0.ssl.qhimg.com/t11b673bcd6b835ec4e7eba076b.png'
let lovel_url = 'https://p2.ssl.qhimg.com/t11b673bcd623f2276e05342f9b.png'
let uncollectlogo_url = 'https://p0.ssl.qhimg.com/t11b673bcd6041eb3ca991fef63.png'
let collectlogo_url = 'https://p4.ssl.qhimg.com/t11b673bcd6235d916db13e8b1d.png'
let allsee_ulr = 'https://p0.ssl.qhimg.com/t11b673bcd6c7668e1236393950.png'

function CustomButtom({logoUrl,text,onClick}) {
  return <div className='CustomButtom' onClick={onClick}>
     <img className='CustomButtom_logo' src={logoUrl}></img>
     <div className='text'>{text}</div>
  </div>
}

function strToDate(time) {
  const date = new Date(time);
//   function dateFormat(fmt, date) {
//     let ret;
//     const opt = {
//         "Y+": date.getFullYear().toString(),        // 年
//         "m+": (date.getMonth() + 1).toString(),     // 月
//         "d+": date.getDate().toString(),            // 日
//         "H+": date.getHours().toString(),           // 时
//         "M+": date.getMinutes().toString(),         // 分
//         "S+": date.getSeconds().toString()          // 秒
//     };
//     for (let k in opt) {
//         ret = new RegExp("(" + k + ")").exec(fmt);
//         if (ret) {
//             fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
//         };
//     };
//     return fmt;
// }
  let fmt = "YYYY-mm-dd HH:MM:SS"
  const opt = {
      "Y+": date.getFullYear().toString(),        // 年
      "m+": (date.getMonth() + 1).toString(),     // 月
      "d+": date.getDate().toString(),            // 日
      "H+": date.getHours().toString(),           // 时
      "M+": date.getMinutes().toString(),         // 分
      "S+": date.getSeconds().toString()          // 秒
  };
  let ret;
  for (let k in opt) {
      ret = new RegExp("(" + k + ")").exec(fmt);
      if (ret) {
          fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
      };
  };

  return fmt;
}


function HeaderView ({storyDetail,collect=false,star=false,starNum,onStarChange,onCollectChange,onAllSeeChange}) {
  return <div className="headerView">
          <div className="title_content">
            <div className="title">{storyDetail ? storyDetail.title : '' }</div>
            <div className="date_content">
              <div className="date_text">{storyDetail ? strToDate(storyDetail.createTime) : ''}</div>
              {/* <div className="view_count">3562 次浏览</div> */}
            </div>
          </div>
          <div className="collect_content">
           <CustomButtom logoUrl={allsee_ulr} text='全屏浏览' onClick={onAllSeeChange}/>
            <div className="star_contain">
            <CustomButtom logoUrl={collect ? collectlogo_url : uncollectlogo_url} text={collect ? "已收藏" : "加入收藏"} onClick={onCollectChange}/>
            </div>
            <div className="star_contain">
              <CustomButtom logoUrl={star ? lovel_url : unlove_url} text={starNum} onClick={onStarChange}/>
            </div>
          </div>
        </div>
}

function ImageSlideView({dataArr=[]}) {
  return (
    <>
     <div className="left_content">
      {dataArr.map((item, index) => (
          <div key={index} className='image_content'>
              <img className='top_image' src={item.imgUrl}></img>
              <div className="image_describe">{item.content}</div>
          </div>
        ))}
     </div>
    </>
  );
}

function AuthorInfo({storyDetail,onclickProductItem}) {
  return <>
    <div className="author_content">
      {storyDetail &&
        <>
          <div className="top_decorate"></div>
          <div className="authorInfo">
            {/* <img className="author_logo" src={storyDetail.authorLogo}></img> */}
            <div className="author_logo">{storyDetail.authorName[0]}</div>
            <div className="author_name">
              <div className="name">{storyDetail.authorName}</div>
              <div className="articleNum">
                <div className="article">已发布文章</div>
                <div className="article1"> {storyDetail.products.length} </div>
                <div className="article">篇</div>
              </div>
            </div>
          </div>
          <div className="middleLine"></div>
          <ul className="product_list">
          {storyDetail.products.map((item, index) => (
              <li key={index} className="product" onClick={()=>onclickProductItem(item.id)}>{`${item.title}`}</li>
            ))}
          </ul>
        </>
      }
    </div>
  </>
}

function StarRankList({popularStorys,onclickRankItem}) {
  return <>
    <div className="rank_content">
      <div className="top_decorate"></div>
      <div className="title">点赞排行榜</div>
      {popularStorys && 
        <ul className="product_list">
        {popularStorys.map((item, index) => (
            <li key={index} className="product" onClick={()=>onclickRankItem(item.id)} >{item.title}</li>
          ))}
        </ul>
      }
    </div>
  </>
}

const ProductDetail = () =>  {
  var stroyId = useRef("")
  var [arr, setArr] = useState([]);
  var [storyDetail,setStoryDetail] = useState(null)
  var [star,setStar] = useState(false)
  var [collect,setCollect] = useState(false)
  var starCount = useRef(0)

  var [popularStorys,setPopularStorys] = useState([])
  var [authorStory,setAuthorStory] = useState(null)

  const swiperRef = useRef(null);
 
  const getStoreDetail = async (storyId)=> {
    try {
      const res = await getStoryDetail({
        storyId: storyId
      })
      //console.log('getStoryDetail res', res);
      setStoryDetail(res)
      //isHistory
      let pictureListSub = res.pictureListSub
      let listSub =[]
      for (let index = 0; index < pictureListSub.length; index++) {
        let sub = pictureListSub[index].filter(item => item.chooseImg === 1);
        if (sub.length > 0) {
          listSub.push(sub[0])
        } 
      }

      // listSub.forEach((item)=>{
      //   item.title = res.title
      // })
      starCount.current = res.starCount
      setStar(res.star)
      setCollect(res.collect)
      setArr(listSub)

      const res2 = await getMostPopularStory({
        count:10
      })
      setPopularStorys(res2)

      const resData3 = await getAuthorInfo({
        storyId: storyId
      })

      if (resData3) {
        var res3 = {
          authorLogo:"https://himg.bdimg.com/sys/portrait/item/pp.1.ff2999e7.GE9vpAWmGjHHP3CBahyIlg?_t=1718958388289",
          authorName:resData3.comiUser.displayName,
          products:resData3.storyList.slice(0, 5)
        }
        setAuthorStory(res3)
      }
      


  } catch (error) {
      console.error(error);
  }
  }

  const starSory= async (storyId)=> {
    try {
      if (star) {
        const res = await unloveStory({
          storyId: storyId
        })
        if (res) {
          starCount.current =  starCount.current - 1
          setStar(!star)
        }
      } else {
        const res = await loveStory({
          storyId: storyId
        })
        if (res) {
          starCount.current =  starCount.current + 1
          setStar(!star)
        }
      }
      
    } catch (error) {
      console.error(error);
    }
  }

  const collectSory= async (storyId)=> {
    try {
      if (collect) {
        const res = await uncollectStory({
          storyId: storyId
        })
        if (res) {
          setCollect(!collect)
        }
      } else {
        const res = await collectStory({
          storyId: storyId
        })
        if (res) {
          setCollect(!collect)
        }
      }
      
      
    } catch (error) {
      console.error(error);
    }
  }


  useEffect(()=>{
    stroyId.current = getCurrentUrlParamValue("stroyId")
    getStoreDetail(stroyId.current)
  },[])


  function starChange() {
    starSory(stroyId.current)
  }

  function collectChange() {
    collectSory(stroyId.current)
  }

  function allSeeChange() {
    window.open(`/fullproductDetail?stroyId=${stroyId.current}`)
  }

  function clickRankItem(id) {
    window.open(`/productDetail?stroyId=${id}`)
  }

  function clickProductItem(id) {
    window.open(`/productDetail?stroyId=${id}`)
  }
  

  return (
    <main className="main_container">
      <div className="main_content">
        <div className="left_container">
          <HeaderView storyDetail={storyDetail} collect={collect}  star={star} starNum={starCount.current} onStarChange={starChange} onCollectChange={collectChange} onAllSeeChange={allSeeChange}/>
          <div className="content_line"></div>
          <div className="leftcontent_contain">
            <ImageSlideView dataArr={arr}/>
          </div>
          </div>
          <div className="right_container">
            <div className="author_container">
              <AuthorInfo storyDetail={authorStory} onclickProductItem={clickProductItem}/>
            </div>
            <div className="popular_container">
              <StarRankList popularStorys={popularStorys} onclickRankItem={clickRankItem}/>
            </div>
          </div>
      </div>
    </main>
    );
  };
  
  export default ProductDetail;