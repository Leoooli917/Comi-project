import { makeAutoObservable, runInAction } from 'mobx';
import Cookies from 'js-cookie';
import { login, logout } from '@/api/UserLogin';
import type { IUser } from '@/login/User';
import { CACHE_NAME, COOKIE_TOKEN } from '@/constants';
import { debounce } from 'lodash';
import StorageUtil from '@/utils/StorageUtil';

const hostname = window.location.hostname;
const domain = hostname.endsWith('.comi.qihoo.net')
  ? '.comi.qihoo.net'
  : hostname.endsWith('.qihoo.net')
  ? '.qihoo.net'
  : '';
class Global {
  user?: IUser | null;
  constructor() {
    makeAutoObservable(this);
    this.user = StorageUtil.getItem(CACHE_NAME);
  }

  get isLogin() {
    return !!this.token && this.user?.token;
  }

  get token() {
    return StorageUtil.getItem(COOKIE_TOKEN);
  }

  login = async (sid: string) => {
    const params = {sid:sid,from:'PC',ref:window.location.href}
    const data = await login(params);
    StorageUtil.setItem({key:COOKIE_TOKEN, value:data.token});
    StorageUtil.setItem({key:CACHE_NAME, value:data});
    runInAction(() => {
      this.user = data;
    });
  };
  logout = async () => {
    // await logout();
    this.clear();
    window.location.reload();
  };
  // 登录（跳转内网统一登录或者推推SSO登录）
  // eslint-disable-next-line @typescript-eslint/member-ordering
  toLogin = debounce(async () => {
    // const from = location.pathname + location.search + location.hash;
    const to = new URL('/login', window.location.origin).href;
    const ref = `${to}?from=` + encodeURIComponent('/personal/published');
    const loginUrl = `https://login.ops.qihoo.net:4436/sec/login?ref=${ref}`;
    window.location.href = loginUrl;
  }, 500);
  clear = () => {
    StorageUtil.removeItem(CACHE_NAME);
    this.user = undefined;
  };
}

export const GlobalModel = new Global();