import { lazy, Suspense } from 'react';
import { useRoutes } from 'react-router-dom';
import LayoutPage from '@/pages/layout';
import LoadingComponent from '@/pages/components/Loading';
import { Affix } from 'antd';
import Afflatuse from '@/pages/afflatuse/Afflatuse';
import ProductDetail from '@/pages/productDetail/ProductDetail';
import FullProductDetail from '@/pages/fullProductDetail/FullProductDetail';

import PicCreator from '@/pages/piccreator/PicCreator';
import Personal  from '@/pages/Personal';

import RoleSetting from '@/pages/rolesetting/RoleSetting';

const load = (children : any) => <Suspense fallback={<LoadingComponent />}>{children}</Suspense>;

const Demo = lazy(() => import('@/pages/stateDemo/localState'));
const Home = lazy(() => import('@/pages/Home'));
const Error404 = lazy(() => import('@/pages/error/404'));
const Login = lazy(() => import('@/login'));

const requirePublicLayout = (isShow = true) => (
    <LayoutPage isShow={isShow}/>
);

const requirePersonalLayout = () => (
  <LayoutPage isShow={true} isPersonal={true}/>
);

const routeList = [
  {
    path: '/',
    element: requirePublicLayout(),
    children: [
      {
        index: true,
        key: 'home',
        element: load(<Home />),
        meta: {
          title: 'Home'
        }
      }
    ]
  },
  {
    path: '/personal/:type',
    element: requirePersonalLayout(),
    children: [
      {
        index: true,
        key: 'home',
        element: load(<Personal />),
        meta: {
          title: '个人中心'
        }
      }
    ]
  },
  {
    path: '/login',
    element: requirePublicLayout(),
    children: [
      {
        index: true,
        key: 'login',
        element: load(<Login />),
        meta: {
          title: '系统登录'
        }
      }
    ]
  },
  {
    path: '/afflatus',
    element: requirePublicLayout(),
    children: [
      {
        index: true,
        key: 'afflatus',
        element: load(<Afflatuse />),
        meta: {
          title: '灵感生图'
        }
      }
    ]
  },
  {
    path: '/productDetail',
    element: requirePublicLayout(),
    children: [
      {
        index: true,
        key: 'productDetail',
        element: load(<FullProductDetail />),
        meta: {
          title: '图片列表'
        }
      }
    ]
  },
  {
    path: '/fullProductDetail',
    element: requirePublicLayout(),
    children: [
      {
        index: true,
        key: 'fullProductDetail',
        element: load(<ProductDetail />),
        meta: {
          title: '全屏图片列表'
        }
      }
    ]
  },
  {
    path: '/piccreator',
    element: requirePublicLayout(),
    children: [
      {
        index: true,
        key: 'piccreator',
        element: load(<PicCreator/>),
        meta: {
          title: '图片生成'
        }
      }
    ]
  },
  //生成角色路径
  {
    path: '/rolesetting',
    element: requirePublicLayout(),
    children: [
      {
        index: true,
        key: 'rolesetting',
        element: load(<RoleSetting />),
        meta: {
          title: '灵感生图'
        }
      }
    ]
  },
  {
    path: '/404',
    element: requirePublicLayout(),
    children: [
      {
        index: true,
        key: '404',
        element: load(<Error404 />),
        meta: {
          title: '404'
        }
      }
    ]
  },
  {
    path: '/demo',
    element: requirePublicLayout(),
    children: [
      {
        index: true,
        key: 'demo',
        element: load(<Demo />),
        meta: {
          title: 'DEMO'
        }
      }
    ]
  },
//   {
//     path: '*',
//     element: load(<Error404 />)
//   }
];

const RenderRouter = () => {
  const element = useRoutes(routeList);
  return element;
};

export const localRouters = routeList;
export default RenderRouter;