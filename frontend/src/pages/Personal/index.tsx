// import React from 'react';
import './index.less';
import { useMemo, useEffect, useState } from 'react';
import { useHookstate } from '@hookstate/core';
import Published from './Published';
import Favorite from './Favorate';
import Draft from './Draft';
import Like from './Like';
import Private from './Private';
import RequireAuth from '@/login/RequireAuth';
import { Routes, Route, useNavigate, useParams } from 'react-router-dom';
import PubSub from 'pubsub-js';
import { GlobalModel } from '@/model/Global';

const componentsMap = {
    Published,
    Like,
    Draft,
    Favorite,
    Private
}

function DynamicComponent({ type }) {
    console.log("type", type)
    // 使用useMemo来避免在每次渲染时都重新创建组件
    const Component = useMemo(() => componentsMap[type] || null, [type]);
    return Component ? <Component /> : null;
}

// export default function Personal() {
//     const menuMap = [{
//         text: "已发布",
//         type: "published",
//         icon: "https://p3.ssl.qhimg.com/t01db831c2bf6ed8ed9.png",
//         iconWidth: 16,
//         iconHeight: 16
//     },
//     {
//         text: "草稿箱",
//         type: "draft",
//         icon: "https://p1.ssl.qhimg.com/t011ddbc870587e3096.png",
//         iconWidth: 14,
//         iconHeight: 12
//     },
//     {
//         text: "收藏夹",
//         type: "favorite",
//         icon: "https://p3.ssl.qhimg.com/t018357258d205d5ee2.png",
//         iconWidth: 16,
//         iconHeight: 16
//     },
//     {
//         text: "点赞 ",
//         type: "like",
//         icon: "https://p4.ssl.qhimg.com/t0111e4c6c7007a2ec5.png",
//         iconWidth: 14,
//         iconHeight: 12
//     }]
//     const currentType = useHookstate(GetRequest());
//     return (<>
//         <div className="personal-container">
//             <div className="personal-menu">
//                 <ul>
//                     {
//                         menuMap.map(item => {
//                             return (
//                                 <li key={item.type} onClick={() => {
//                                     currentType.set(item.type);
//                                     location.href = `/personal?type=${item.type || ""}`
//                                 }} className={item.type == currentType.get() ? "checked" : ""}>

//                                     <img src={item.icon} style={{ marginRight: `${16 - item.iconWidth / 2 + 9}px` }} alt="item.text" width={item.iconWidth} height={item.iconHeight} />
//                                     <span>
//                                         {item.text}
//                                     </span>

//                                 </li>
//                             )
//                         })
//                     }
//                 </ul>
//             </div>
//             <div className="personal-right-container">
//                 <DynamicComponent type={currentType.get()[0].toUpperCase() + currentType.get().substr(1)} />
//             </div>
//         </div>

//     </>)
// }

export default function Personal() {
    const { type } = useParams();
    const navigate = useNavigate();
    const menuMap = [{
        text: "已发布",
        type: "published",
        icon: "https://p3.ssl.qhimg.com/t01db831c2bf6ed8ed9.png",
        iconWidth: 16,
        iconHeight: 16
    },
    {
        text: "草稿箱",
        type: "draft",
        icon: "https://p1.ssl.qhimg.com/t011ddbc870587e3096.png",
        iconWidth: 14,
        iconHeight: 12
    },
    {
        text: "私有角色库",
        type: "private",
        icon: "https://p1.ssl.qhimg.com/t011ddbc870587e3096.png",
        iconWidth: 14,
        iconHeight: 12
    },
    {
        text: "收藏夹",
        type: "favorite",
        icon: "https://p3.ssl.qhimg.com/t018357258d205d5ee2.png",
        iconWidth: 16,
        iconHeight: 16
    },
    {
        text: "点赞 ",
        type: "like",
        icon: "https://p4.ssl.qhimg.com/t0111e4c6c7007a2ec5.png",
        iconWidth: 14,
        iconHeight: 12
    }];
    const [auth, setAuth] = useState(false);
    useEffect(() => {
        PubSub.subscribe("login", () => {

            GlobalModel.toLogin();
        })
        return () => {
            PubSub.unsubscribe("login");
        }
    }, []);

    useEffect(() => {
        //防止页面后退
        history.pushState(null, null, document.URL);
        window.addEventListener("popstate", function () {
            history.pushState(null, null, document.URL);
        });
    }, [])

    return (<>
        <div className="personal-container">
            <div className="personal-menu">
                <ul>
                    {
                        menuMap.map(item => {
                            return (
                                <li key={item.type} onClick={() => navigate(`/personal/${item.type}`)}
                                    className={item.type == type ? "checked" : ""}>
                                    <img src={item.icon} style={{ marginRight: `${16 - item.iconWidth / 2 + 9}px` }} alt="item.text" width={item.iconWidth} height={item.iconHeight} />
                                    <span>
                                        {item.text}
                                    </span>
                                </li>
                            )
                        })
                    }
                </ul>
                <img className="start" src="https://p4.ssl.qhimg.com/t01d92532b8593d322d.png" alt="" onClick={() => navigate("/afflatus")} />
            </div>
            <div className="personal-right-container">
                <DynamicComponent type={type[0].toUpperCase() + type.slice(1)} />
            </div>
            <div style={{ height: 0, width: 0, overflow: 'hidden' }}>
                <RequireAuth />
            </div>

        </div>
    </>)
}