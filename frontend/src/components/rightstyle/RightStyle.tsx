import './RightStyle.less'
import { useState, useEffect } from 'react'
import ImageSelect from '../imageSelect'
import { useNavigate } from 'react-router-dom';
import { getRadioList, getStyleList } from '@/api/SettingApi';
import { Flex, Button } from 'antd';
import PubSub from "pubsub-js";
import { useHookstate } from '@hookstate/core';

function RightStyle({ onClickNext }) {

  const type = useHookstate({})
  const ratio = useHookstate({})
  const ratioImags = [
    {

      url: 'https://p4.ssl.qhimg.com/t014305aaec6298eddd.png',
      value: '1:1',

    },
    {

      url: 'https://p1.ssl.qhimg.com/t0198f384a7fffd25ad.png',
      value: '1:2',

    },
    {

      url: 'https://p3.ssl.qhimg.com/t01cb90fce2fe898656.png',
      value: '2:1',

    },
    {

      url: 'https://p4.ssl.qhimg.com/t01b7283d099ca6af94.png',
      value: '3:4',

    },
    {

      url: 'https://p1.ssl.qhimg.com/t01be9c7c71571f859e.png',
      value: '4:3',

    },
    {

      url: 'https://p3.ssl.qhimg.com/t01cb90fce2fe898656.png',
      value: '16:9',

    },
    {

      url: 'https://p1.ssl.qhimg.com/t0198f384a7fffd25ad.png',
      value: '9:16',
    }
  ]

  // setTimeout(() => {
  //   console.log("ratio", ratio);
  //   console.log("type", type);
  // }, 10000);

  useEffect(() => {
    PubSub.subscribe("confirmStory", () => {
      // setTimeout(() => {        
        onClickNext(ratio.get(), type.get());
        // console.log(ratio.get(),type.get())
      // }, 0)
    })
    return () => {
      PubSub.unsubscribe("confirmStory");
    }
  }, [ratio.get(), type.get()])

  let [ratios, setRatios] = useState([])
  const [types, setTypes] = useState([])
  useEffect(() => {
    getRadios()
    getTypes()
  }, [])
  const getTypes = async () => {
    let res = await getStyleList({})
    let arr = []
    if (res != undefined) {
      res.map((item, index) => {
        let obj = {
          'id': item.id,
          'name': item.displayName,
          'modelName': item.modelName,
          'url': item.posterImage,
          isSelected: index === 0
        }
        arr.push(obj)
      })
      console.log("arr", arr);
      setTypes(arr)
      type.set(arr[0])
    }
  }
  const getRadios = async () => {
    let res = await getRadioList({})
    if (res != undefined) {
      setRatios(res)
      res.map((item) => {
        ratioImags.map((obj, index) => {
          if (item.value == obj.value) {
            item.url = obj.url
            item.isSelected = index === 0
          }
        })
      })
      ratio.set(res[0]);
    }
  }
  const navigate = useNavigate();
  return (
    <>
      <div className='right-style-body'>
        <div className='right-style-type-label'>风格</div>
        <div className='right-style-types'>
          <ImageSelect tag='isSelected' options={types} onSelect={item => selectType(item)} />
        </div>
        <div className='right-style-ratio-label'>出图比例</div>
        <div className='right-style-types'>
          {
            ratios.map((item, index) => (
              <div className={item.isSelected ? 'right-style-size-card-selected' : 'right-style-size-card'} key={item.id} onClick={() => selectRadio(item)}>
                <img className='right-style-ratio-img' src={item.url}></img>
                <div className='right-style-ratio-text'>{item.value}</div>
              </div>
            ))
          }
        </div>
        {/* <Flex gap="small" wrap="wrap" justify="center" style={{ padding: '16px 11px 16px 11px' }}>
          <Button className="basicsButton" onClick={() => }>下一步</Button>
        </Flex> */}
        {/* <div className='right-style-next' onClick={()=>onClickNext(ratio,type)}>下一步</div> */}
      </div>
    </>
  )

  function selectRadio(item: any) {
    console.log("选择比例-------=" + JSON.stringify(item))

    ratio.set(item)
    let arr = [...ratios]
    arr.map(obj => {
      if (obj.id == item.id) {
        obj.isSelected = true
      } else {
        obj.isSelected = false
      }
    })
    setRatios(arr)
  }
  
  function selectType(item: any) {
    type.set(item)
    console.log("选择风格-------=" + JSON.stringify(item))
    let arr = [...types]
    arr.map(obj => {
      if (obj.id == item.id) {
        obj.isSelected = true
      } else {
        obj.isSelected = false
      }
    })
    setTypes(arr)
  }
}
export default RightStyle