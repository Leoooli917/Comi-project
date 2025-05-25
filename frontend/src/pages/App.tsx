import MyRoutes from '@/route/config'
// 这个是全局的页面 还可以做一些其他的操作
// import { useState } from 'react'
// import { message } from 'antd';
import './App.css'
// import LeftStep from './../components/leftstep/LeftStep.tsx';
// import RightStyle from './../components/rightstyle/RightStyle.tsx';
// import TopHead from './../components/tophead/TopHead.tsx';
// import Afflatuse from './afflatuse/Afflatuse.tsx';

export default function App() {

  // const [count, setCount] = useState(0)
  // const goLogin =()=>{
  //   message.success('登录')    
  // }
  // const userInit =()=>{
  //   console.log("------准备判断是否符合登录---------")

  //   if (typeof window !== "undefined") {
  //     const jquery: any = window.$;
  //     console.log("------准备判断是否符合登录xx---------")
  //     console.log(jquery)
      
  //     jquery
  //       .getScript("//s.ssl.qhimg.com/quc/quc7.js")
  //       .then(function () {
        
  //         const q: any = window.QHPass;
  //         q.init({ src: "pcw_comi" });
        
  //         q.signIn(function () {
           
  //           q.getUserInfo(
  //             function (u: any) {
  //               localStorage.setItem("loginInfo",u.username)
               
               
  //             },
  //             function () {
  //               localStorage.setItem("loginInfo","987")
            
  //           },
  //           function () {}
  //         );
  //       });
  //     });
   
  // }
  // }
  return (
    <>
      <div>
        <MyRoutes />
      </div>
    </>
  )
}
