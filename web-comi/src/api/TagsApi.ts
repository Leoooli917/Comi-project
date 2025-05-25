import { fetchData } from '@/utils/fetch';

// 获取词库
// type: 1表示任务，2表示角色，3环境
export const getTagsByType: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/tags/getTagsByType',
        params,
        'get'
    );
}


