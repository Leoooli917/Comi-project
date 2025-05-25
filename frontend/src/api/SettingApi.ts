import { fetchData } from '@/utils/fetch';
// 获取风格列表
export const getStyleList: (params: any) => Promise<any> = (params) => {
                return fetchData(
                                '/v1/public/style/getList',
                                params,
                                'get'
                );
}
//获取图片比例列表
export const getRadioList: (params: any) => Promise<any> = (params) => {
                return fetchData(
                                '/v1/public/config/getRadioList',
                                params,
                                'get'
                );
}