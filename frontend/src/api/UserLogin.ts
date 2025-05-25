import { fetchData } from '@/utils/fetch';

// 登录
export const login: (params: any) => Promise<any> = (params) => {
    console.log('params==',params)
    return fetchData(
                    '/v1/user/login',
                    params,
                    'post'
    );
}

// 退出登录
export const logout: (params: any) => Promise<any> = () => {
    return fetchData(
                    '/v1/user/logout',
                    'post'
    );
}

