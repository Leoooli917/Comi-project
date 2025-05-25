import './index.less'
import { Link, useNavigate } from 'react-router-dom';
import { useHookstate } from '@hookstate/core';
import { Masonry } from "masonic"
import { useEffect } from "react"
import { Carousel } from 'antd';
import { getStorys, getMostPopularStory, getBanner } from "../../api/StoryApi"


const FakeCard = ({ data: { title, posterImage, id } }) => {
  const navigate = useNavigate();
  return (
    <div className="home-story-item" onClick={() => window.open(`/productDetail?stroyId=${id.get()}`, "_blank")}>
      <div>
        <img style={{ width: "100%" }} src={posterImage.get()} alt="" />
      </div>
      <div className="home-story-item-title" style={{ height: "47px" }}>
        {title.get()}
      </div>
    </div>
  )
};

function Home() {
  const items = useHookstate([]);
  const popularStore = useHookstate([]);
  const bannerStory = useHookstate([]);
  const second = useHookstate(0);
  const navigate = useNavigate();

  useEffect(() => {
    const balls = document.getElementsByClassName('ball');

    const movement = (event = {
      clientX: 200,
      clientY: 200
    }) => {
      const x = (190 + (event.clientX * 100) / window.innerWidth) / 5.4 + '%';
      const y = (250 + ((event.clientY * 100) / window.innerHeight)) / 5.4 + '%';
      if (balls[0]) {
        (balls[0] as HTMLElement).style.left = x;
        (balls[0] as HTMLElement).style.top = y;
      }
    };

    document.onmousemove = movement;
    movement();

    const timer = setInterval(function () {
      second.set(p => p + 1);
    }, 300);

    return () => {
      clearInterval(timer)
    }
  }, []);

  useEffect(() => {
    getStorys({
      filterModelName: 'all',
      orderByWhat: 'date'
    }).then(res => {
      console.log("getStorys", res)
      items.merge(res);
    });
    getMostPopularStory({
      count: 7
    }).then(res => {
      popularStore.merge(res);
      console.log("getMostPopularStory", res)
    });
    getBanner().then(res => {
      bannerStory.merge(res.map(item => item.story))
    });
  }, [])

  const contentStyle: React.CSSProperties = {
    height: '320px',
    color: '#fff',
    lineHeight: '20px',
    textAlign: 'center',
    background: '#364d79',
    margin: 0,
    borderRadius: "16px"
  };

  useEffect(() => {
    //防止页面后退
    history.pushState(null, null, document.URL);
    window.addEventListener('popstate', function () {
      history.pushState(null, null, document.URL);
    });
  },[])

  return (
    <>
      <div className="home-container">
        <div className='home-container-bg'></div>
        <div style={{ width: "1360px", margin: "0 auto", marginTop: "-630px" }}>
          <div className="home-title">
            <img className="home-title-wenhui" src="https://p4.ssl.qhimg.com/t01ea788b60fbc7bb87.png" alt="" />
            <img className="home-title-slogan" src="https://p5.ssl.qhimg.com/t016a53d1c17ddcfa6e.png" alt="" />
            <Link to="/afflatus" replace style={{ marginLeft: '40px', height: '82px' }}>
              <div className="home-title-try">
                <img src="https://p4.ssl.qhimg.com/t01081f8037a9f35a45.png" style={{ width: "356px", height: "62px", marginRight: "30px" }} alt="" />
                <div style={{ position: "relative", left: "180px", top: "-110px" }}>
                  <img style={{ width: "95px", height: "96px" }} src="https://p0.ssl.qhimg.com/t016b1d1105f471d43c.png" />
                  <div className="home-title-eyes">
                    <div className="ball" style={{ backgroundImage: (second.get() % 19 == 0 || second.get() % 21 == 0) ? `url("https://p0.ssl.qhimg.com/t01dd26ed3077173c10.png")` : `url("https://p0.ssl.qhimg.com/t0117a63680d8a483bc.png")` }}></div>
                  </div>
                </div>

              </div>
            </Link>
          </div>
          <ul className='home-tools'>
            <li className='pic1'></li>
            <li className='pic2'>
              <Carousel autoplay dots={true}>
                {bannerStory.get().map((item, index) => {
                  return (
                    <div className="banner-container" key={index} onClick={() => window.open(`/productDetail?stroyId=${item.id}`, "_blank")}>
                      <h3>{item?.title}</h3>
                      <img src={item?.posterImage} />
                    </div>)
                })}
              </Carousel>
            </li>
            <li className='pic3'>
              {popularStore.get().map((item, index) => (
                <div className='home-popular' key={index} onClick={() => window.open(`/productDetail?stroyId=${item.id}`, "_blank")}>
                  <span>{item.title}</span>
                  <span>{item.starCount}</span>
                </div>
              ))}
            </li>
          </ul>
          <div className='home-story'>
            <img className="home-store-text" src="https://p2.ssl.qhimg.com/t017d4869a97d8bfc12.png" alt="" />
            {/* <div className="home-store-text"></div> */}
            <div className="home-store-tag-list">
              <div className="home-store-tag">全部</div>
            </div>
          </div>
          <Masonry
            // Provides the data for our grid items
            items={items}
            // Adds 8px of space between the grid cells
            columnGutter={16}
            // Sets the minimum column width to 172px
            columnWidth={256}
            // Pre-renders 5 windows worth of content
            overscanBy={5}
            // This is the grid item component
            render={FakeCard}
          />
        </div>
      </div>
    </>
  )
}

export default Home
