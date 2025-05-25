const baseURLFromBuild = import.meta.env.VITE_BASE_URL;

export const getUrl = (path: string) => {
     // 默认测试  通过env控制
     let baseURL = 'https://devapi.drawing.qihoo.net/comi'
     if (baseURLFromBuild) {
          baseURL = baseURLFromBuild
     } 
     return baseURL + path
};