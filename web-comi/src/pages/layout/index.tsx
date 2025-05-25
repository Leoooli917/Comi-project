// layout/index.jsx
import { Outlet, Link, Location, useLocation } from 'react-router-dom';
import type { MenuProps } from 'antd';
import { getStoryDetail } from "@/api/StoryApi.ts";
import './style.less';
import { Input } from '@arco-design/web-react';
import { Layout, Avatar, message, Typography, Button, Dropdown } from "antd";
import { login } from '@/api/UserLogin';
import RequireAuth from '@/login/RequireAuth';
import { useEffect, useRef, useState } from 'react';
import { useHookstate } from '@hookstate/core';
import { IconEdit } from '@arco-design/web-react/icon';

import { storeState } from '@/stores/storeState';
import { getStoryTitle, updateStoryTitle } from "../../api/StoryApi"
import PubSub from "pubsub-js";
import { useWorkbenchState } from "@/stores/workbench.ts";
import { GlobalModel } from '@/model/Global';
import Personal from '../Personal';
import { useNavigate } from 'react-router-dom';
import StorageUtil from '@/utils/StorageUtil';
import { CACHE_NAME, COOKIE_TOKEN } from '@/constants';
import { getCurrentUrlParamValue } from "@/utils/url"
const { Header, Content } = Layout;
// const { Search } = Input;


function PublicLayout({ isShow = true, isPersonal = false }) {
  const pathname = useHookstate("/");
  const location = useLocation();
  const storeTitle = useHookstate("");
  const navigate = useNavigate();
  const store = useHookstate(storeState);
  const workbenchState = useWorkbenchState();
  var [generatorStatus, setGeneratorStatus] = useState(false)
  const stepMap = [
    ,
    "confirmStory",
    "confirmRole",
    "confirmRole",
  ]

  const items: MenuProps['items'] = [
    {
      key: 1,
      label: (
        <a target="_blank" rel="noopener noreferrer" onClick={() => navigate("/personal/published")}>
          个人中心
        </a>
      )
    },
    {
      key: 2,
      label: (
        <a target="_blank" rel="noopener noreferrer" onClick={() => {
          // 前端退出登录
          StorageUtil.removeItem(COOKIE_TOKEN);
          StorageUtil.removeItem(CACHE_NAME);
          StorageUtil.removeItem('storyId');
          navigate("/");
        }}>
          退出登录
        </a>
      )
    }
  ]


  const generatorRole = () => {
    PubSub.publish("confirm")
  }

  const updateStoreTitle = (title) => {
    if (title) {
      updateStoryTitle({
        storyId: workbenchState.storyId,
        title
      }).then(() => message.success('故事名称更新成功'))
    }
  }
  useEffect(() => {
    if (getCurrentUrlParamValue("storyId")) {
      setTimeout(() => {
        getStoryTitle({
          storyId: getCurrentUrlParamValue("storyId")
        }).then(res => storeTitle.set(res))
      }, 200);
    }
  }, [location])

  useEffect(() => {
    // /afflatus
    if (["/",
      "/personal/draft",
      "/personal/published",
      "/personal/private",
      "/personal/favorite",
      "/personal/like"].includes(location.pathname)) {
      // 临时方案，初始化去掉token
      storeState.isReady.set(false);
      workbenchState.setCurrentStep(0);
    } else {
      if (workbenchState.storyId) {
        if (location.pathname == '/afflatus') {
          workbenchState.setCurrentStep(1);
        } else if (location.pathname == '/rolesetting') {
          workbenchState.setCurrentStep(2);
        } else if (location.pathname == '/piccreator') {
          workbenchState.setCurrentStep(3);
        } else {
          workbenchState.setCurrentStep(0);
        }
      } else {
        workbenchState.setCurrentStep(0);
      }
    }
    pathname.set(location.pathname);
  }, [location]);

  const personalRoutes = [
    '/personal/like',
    '/personal/published',
    '/personal/favorite',
    '/personal/draft',
    '/personal/private',
    '/productDetail'
  ]

  useEffect(() => {
    PubSub.subscribe("storyGenerateFinish", () => {
      setGeneratorStatus(true)
    })

    PubSub.subscribe("resetStep", () => {
      workbenchState.setCurrentStep(0);
      setGeneratorStatus(false)
    })

    return () => {
      PubSub.unsubscribe("resetStep");
      PubSub.unsubscribe("storyGenerateFinish");
      setGeneratorStatus(false)
    }
  }, [])

  return (
    <Layout className="layout-container">
      {
        isShow &&
        <Header className="layout-header">
          {/* 仅在首页展示 */}
          {["/", ...personalRoutes].includes(pathname.get()) && <div className="layout-header-logo" onClick={() => {
            navigate('/', { replace: true })
          }}></div>}
          {!["/", ...personalRoutes].includes(pathname.get()) &&
            (
              <>
                <Link to="/" className="layout-header-back" replace title="返回首页"></Link>
                {
                  store.isReady.get() &&
                  <Input
                    allowClear
                    className="storeTitle"
                    style={{ minWidth: 100, backgroundColor: "#fff" }}
                    suffix={<IconEdit />}
                    autoWidth
                    value={storeTitle.get()}
                    placeholder='为故事增加一个名字吧'
                    onChange={(title: string) => storeTitle.set(title)}
                    onClear={() => { storeTitle.set("") }}
                    onBlur={(e) => updateStoreTitle(e.target.value)}
                  />
                }
              </>
            )}
          <div className="layout-header-nav">
            {/* <Link to="/" replace>首页</Link>
          <Link to="/afflatus" replace style={{ marginLeft: '40px' }}>个人工作台</Link> */}
          </div>
          <div className="layout-header-right">
            {/* {pathname.get() !== "/" && <Search placeholder='请输入作品名称' variant="borderless" size="large" className="layout-header-search" />} */}
            {/* Next step generator role */}
            {
              stepMap[workbenchState.currentStep] && workbenchState.currentStep != 3 &&
              <Button className="step-button" onClick={() => {
                PubSub.publish(stepMap[workbenchState.currentStep])
              }}>下一步</Button>
            }
            {
              workbenchState.currentStep == 3 && generatorStatus == false && !pathname.get().includes("/productDetail") &&
              (<>
                <Button className="step-button-pre" disable>下一步</Button>
                {/* <Button className="step-button" style={{ marginLeft: 16 }} onClick={() => PubSub.publish('publishStory')}>发布</Button> */}
              </>)
            }
            {
              workbenchState.currentStep == 4 && generatorStatus == true && !pathname.get().includes("/productDetail") &&
              (<>
                <Button className="step-button" onClick={() => {
                  PubSub.publish('previewStory')

                }}>下一步</Button>
                {/* <Button className="step-button" style={{ marginLeft: 16 }} onClick={() => PubSub.publish('publishStory')}>发布</Button> */}
              </>)
            }
            {
              GlobalModel.isLogin
                ?
                <Dropdown menu={{ items }} placement="bottom">
                  <Avatar
                    // onClick={() => navigate("/personal/published")} 
                    className='avatar'
                    size={34}
                  >{GlobalModel?.user?.displayName.slice(0, 1)}</Avatar>
                </Dropdown>

                : <Button className="step-button" onClick={() => PubSub.publish("login")}>登录</Button>
            }

          </div>
        </Header>
      }
      {
        isPersonal ? (<Personal />) : (
          <Layout className="layout-main-wrap">
            {/* <Sider
            width="240"
            collapsed={collapsed}
            collapsible
            onCollapse={handleCollapsed}
            trigger={collapsed ? <IconMenuUnfold /> : <IconMenuFold />}
            breakpoint="xl"
          >
            Sider
          </Sider> */}
            <Content className="layout-content">
              <div className="layout-content-breadcrumb">
                {/* Content */}
                <div className="layout-main-content">
                  <RequireAuth />
                </div>
              </div>
              {/* <Footer className="layout-footer">© comi-2024 奇舞团</Footer> */}
            </Content>
          </Layout>
        )
      }
    </Layout>
  );
}

export default PublicLayout;
