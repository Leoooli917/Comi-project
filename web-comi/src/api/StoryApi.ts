import { fetchData } from '@/utils/fetch';


//故事场景提交
export const commitStoryWithScene: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/story/commitStoryWithScene',
        params,
        'post'
    );
}
//角色选择提交
export const commitStoryWithActor: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/story/commitStoryWithActor',
        params,
        'post'
    );
}

//检查生成结果
export const checkGenerate: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/story/checkGenerate',
        params,
        'get'
    );
}

//单张图重新生成
export const reGenerate: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/story/reGenerate',
        params,
        'post'
    );
}

// 故事分段 场景拆分
export const contentToScenes: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/story/commitStory',
        params,
        'post'
    );
}



//获取故事详情
export const getStoryDetail: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/public/story/getStoryDetail',
        params,
        'get'
    );
}

//获取全部故事
export const getStorys: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/public/story/getAllStory',
    )
}
//提交故事
export const commitStoryPublish: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/story/commitStoryPublish',
        params,
        'get'
    );
}

//获取故事排行榜
export const getMostPopularStory: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/public/story/getMostPopularStory',
        params,
        'get'
    )
}

//获取作者信息和作品列表
export const getAuthorInfo: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/public/story/getAtuhorInfo',
        params,
        'get'
    )
}

//获取B anner
export const getBanner: () => Promise<any> = () => {
    return fetchData(
        '/v1/public/getBanner'
    )
}

//获取故事名称
export const getStoryTitle: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/story/getStoryTitle',
        params,
        'get'
    )
}

//更新故事名称
export const updateStoryTitle: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/story/updateStoryTitle',
        params,
        'post'
    );
}

//设置为封面
export const setPosterImg: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/story/setPosterImg',
        params,
        'get'
    );
}

//个人中心-获取故事
export const getMyStory: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/story/getMyStory',
        params,        
        'get',
        );
}

//点赞
export const loveStory: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/action/star',
        params,
        'get'
    );
}

//取消点赞
export const unloveStory: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/action/starCancel',
        params,
        'get'
    );
}


//加入收藏
export const collectStory: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/action/collect',
        params,
        'get'
    );
}

//取消收藏
export const uncollectStory: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/action/collectCancel',
        params,
        'get'
    );
}


export const downLoadStoryImg: (storyId: any) => Promise<any> = (storyId) => {
    return fetchData(
        '/v1/pic/downLoadStoryImg/' + storyId,
        {},
        'get'
    );
}

export const offlineMyStory: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/story/offlineMyStory',
        params,
        'get'
    );
}

export const removeMyStory: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/story/removeMyStory',
        params,
        'get'
    );
}

// 获取全部私有角色
export const getMyStarActors: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/actor/history/star/getMyStarActors',
        params,
        'get'
    );
}

// 移除私有角色
export const deletePrivateActor: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/actor/history/star/getMyStarActors',
        params,
        'get'
    );
}
