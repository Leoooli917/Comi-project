import { extend } from 'umi-request';
import { message } from 'antd';
import { GlobalModel } from '@/model/Global';
import StorageUtil from '@/utils/StorageUtil';
import { CACHE_NAME } from '@/constants';
import { version } from '../../package.json';

const API_HOST = import.meta.env.VITE_API_HOST;
interface ResponseData {
  data: any;
  code: number;
  message: string;
  error: string;
}

const apiRequest = extend({
  credentials: 'include', // cookie 设置 omit
  method: 'POST',
  prefix: API_HOST + '/api/v1',
  timeout: 10 * 60 * 1000,
  timeoutMessage: '请求超时',
  requestType: 'json',
  responseType: 'json',
  errorHandler: (error) => {
    if (error.response && error.response.status !== 200) {
      message.error('请求异常: ' + error.message + ' ' + error.response.status);
      throw error;
    }
    if (error?.message) {
      if (!error?.message.startsWith(`【1000】`)) {
        message.error(error.message);
      }
      throw error;
    }

    // 可能是跨域问题
    message.error('服务异常，请联系管理员: ' + error);

    throw error;
  },
});
// request拦截器, 改变url 或 options.
apiRequest.interceptors.request.use((url, options) => {
    console.log('url== '+ url)
  return {
    url,
    options: {
      ...options,
      headers: {
        ...options.headers,
        'Authorization': 'bearer a0374ddb-e101-4416-8347-fc71193442b6',
        'x-finer-version': version,
      },
    },
  };
});

// 克隆响应对象做解析处理
apiRequest.interceptors.response.use(async (response) => {
  const res = await response.clone().json();
  const { code, error, data } = res as ResponseData;
  if (code === 403) {
    // 判断 request 是否传了 token
    if (GlobalModel.token) {
      if (!StorageUtil.getItem('token_flag')) {
        message.error('登录已失效');
        localStorage.setItem('token_flag', '1');
      }
    } else {
      if (!StorageUtil.getItem('token_flag')) {
        message.error('您未登录');
        localStorage.setItem('token_flag', '1');
      }
    }
    GlobalModel.clear();
    StorageUtil.removeItem(CACHE_NAME);
    // 延迟 1s 跳转
    // eslint-disable-next-line no-promise-executor-return
    await new Promise((resolve) => setTimeout(resolve, 1000));
    GlobalModel.toLogin();
    return;
  }
  if (code) {
    if (response.url.endsWith('/user/login')) {
      throw new Error(res.error || res.message || '未知错误');
    }
    // message.error(`【${code}】` + (res.message || error || '未知错误'));
    throw new Error(`【${code}】` + (res.message || error || '未知错误'));
  }

  return data;
});

export const apiReq = apiRequest;

export type OK = 'ok' | string;
