import { useLocation } from 'react-router-dom';
import { Outlet } from 'react-router-dom';
// import { getRouterData } from './index';
import { useEffect, useState } from 'react';
import { GlobalModel } from '@/model/Global';
import { Role } from '@/constants';
import PubSub from 'pubsub-js';

const whiteList = ['/login'];

const RequireAuth = () => {
  const location = useLocation();
  const [auth, setAuth] = useState(false);

  useEffect(() => {
    if (whiteList.includes(location.pathname)) {
      setAuth(true);
      return;
    }
    // FIXME: 优化
    // let obj = getRouterData(location.pathname);

    // if (!GlobalModel.isLogin) {
    //   setAuth(false);
    //   GlobalModel.toLogin();
    //   return;
    // }

    // 角色校验
    if (Role.upper(GlobalModel.user?.role, Role.RoleAdmin)) {
      setAuth(true);
      return;
    }

    // const role = obj?.role;

    // // 如果需要权限验证
    // if (typeof role === 'number') {
    //   if (Role.upper(GlobalModel.user?.role, role)) {
    //     setAuth(true);
    //   } else {
    //     setAuth(false);
    //   }
    // }
    setAuth(true);
  }, [location]);

  useEffect(()=>{
    PubSub.subscribe("login", () => {
      setAuth(false);
      GlobalModel.toLogin();
    })
    return ()=>{
      PubSub.unsubscribe("login");
    }
  },[]);

  return auth ? <Outlet /> : null;
};

export default RequireAuth;
