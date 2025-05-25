import { apiReq } from './ApiReq';
import { fetchData } from '@/utils/fetch';

export interface IUser {
  id: number;
  createdAt: string;
  updatedAt: string;

  userName: string;
  displayName: string;
  mail: string;
  avatar?: string;

  role?: number;
  deptId?: string;
  deptName?: string;
  isOpenTips?: boolean;

  token?: string;
}
interface ILoginReq {
  sid: string;
  from: string;
  ref: string;
}

export interface ILoginResp {
  token: string;
  uid: number;
  userName: string;
  mail: string;
  displayName: string;
  avatar: string;
  role: number;
}

export interface ILogoutResp extends IUser {
  token: string;
  uid: number;
  userName: string;
}


// 登录接口
// export const login = (data: ILoginReq) => apiReq<ILoginResp>('/user/login', { data });

// 根据 header.token 或 cookie 登出当前用户
export const logout = () => apiReq<ILoginResp>('/user/logout');

// 获取当前用户信息
export const getUser = () => apiReq<IUser>('/user');

export interface IUpdate {
  avatar?: string;
  description?: string;
}

export type FilterType = 'tester' | 'admin' | '';

export interface IGetUserListOptions {
  page: number;
  size: number;
  key?: string;
  filter?: FilterType;
}
export interface IGetUserList {
  page: number;
  size: number;
  count: number;
  data: IUser[];
}

export const getUserList = (data: IGetUserListOptions) => apiReq<IGetUserList>('/user/list', {
  method: 'GET',
  params: data,
  expire: 1000 * 3,
});