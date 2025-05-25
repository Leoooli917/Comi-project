import { fetchData } from '@/utils/fetch';
//删除私有角色
export const deletePrivateActor: (params: any) => Promise<any> = (params) => {
                return fetchData(
                                '/v1/actor/deletePrivateActor',
                                params,
                                'get'
                );
}


//将角色收藏
export const saveMyStarActors: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/actor/history/saveMyStarActors',
        params,
        'get'
    );
}
//获取私有角色库
export const getPrivateActorList: (params: any) => Promise<any> = (params) => {
                return fetchData(
                                '/v1/actor/getPrivateActorList',
                                params,
                                'get'
                );
}

//修改角色名称
export const changeMyPirActorName: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/actor/history/changeMyPirActorName',
        params,
        'get'
    );
}
//搜索我的历史角色
export const searchMyPriActors: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/actor/history/searchMyPriActors',
        params,
        'get'
    );
}
//获取我收藏的角色
export const getMyStarActors: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/actor/history/star/getMyStarActors',
        params,
        'get'
    );
}
//获取公共角色库
export const getPublicActorList: (params: any) => Promise<any> = (params) => {
                return fetchData(
                                '/v1/public/actor/getPublicActorList',
                                params,
                                'get'
                );
}

//new- 选择角色使用
export const chooseOnePriStarActor: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/actor/history/chooseOnePriStarActor',
        params,
        'get'
    );
}
//另存为私有角色
export const savePrivateActor: (params: any) => Promise<any> = (params) => {
                return fetchData(
                                '/v1/actor/savePrivateActor',
                                params,
                                'post'
                );
}
//更新私有角色
export const updatePrivateActor: (params: any) => Promise<any> = (params) => {
                return fetchData(
                                '/v1/actor/updatePrivateActor',
                                params,
                                'post'
                );
}

//查看私有角色
export const checkActor: (params: any) => Promise<any> = (params) => {
    return fetchData(
                    '/v1/actor/checkActor',
                    params,
                    'get'
    );
}

//删除角色
export const deleteActor: (params: any) => Promise<any> = (params) => {
    return fetchData(
                    '/v1/actor/deletePrivateActor',
                    params,
                    'get'
    );
}

//获取公共角色库
export const getPublicActorListV2: (params: any) => Promise<any> = (params) => {
    return fetchData(
                    '/v2/public/actor/getPublicActorList',
                    params,
                    'get'
    );
}
