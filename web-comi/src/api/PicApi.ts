import { fetchData } from '@/utils/fetch';

//选择某张图为故事中的一张图片
export const setStoryImageForPart: (params: any) => Promise<any> = (params) => {
    return fetchData(
        '/v1/pic/setStoryImageForPart',
        params,
        'get'
    );
}


