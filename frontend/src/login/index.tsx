import { useEffect, useState } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { GlobalModel } from '@/model/Global';
import { useRequest } from 'ahooks';

// 登录验证中间页
const Login = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  const sid = searchParams.get('sid')!;
  const from = searchParams.get('from');

  useEffect(() => {
    console.log('进到了中间页=================')
    if (!sid || !from) {
      // 没有 sid 和 from，跳转到首页
      navigate('/');
      return;
    }
  }, [sid, from]);

  const login = async () => {
    if (!sid || !from) {
      return;
    }
    console.log('sid', sid);
    return GlobalModel.login(sid);
  };
  // 登录验证
  const { error, loading } = useRequest(login);
  if (error) {
    console.log(error);
    if (error.message === 'sid失效') {
      return navigate('/');
    }
    return <div>登录异常, 请刷新后重试。错误: {error.message}</div>;
  }

  if (loading) {
    return <div />;
  }

  window.location.href = window.location.origin + from;
  return <div />;
};

export default Login;
