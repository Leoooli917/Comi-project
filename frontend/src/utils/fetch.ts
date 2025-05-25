import { message as AntdMessage } from 'antd';
import { getUrl } from './BaseUrl';
import { GlobalModel } from '@/model/Global';
import PubSub from "pubsub-js";
 
 export interface IResponseStructure {
     data: any;
     context: any;
     errCode: any;
     errMsg: any;
 }
 
 //  转换为get方法需要的查询字符串
 export function transferQueryStr(data = {}) {
     let str = '?';
     for (let [key, value] of Object.entries(data)) {
         str += `${key}=${value}&`;
     }
     str = str.substring(0, str.length - 1);
     return str;
 }
 
 // 对返回数据的状态码进行处理
 function checkStatus(response: IResponseStructure, url: string) {
    const {
        errCode,
        errMsg,
        data,
    } = response;
     if (errCode === 0) {
            return data;
     } else if (errCode == 4001 || errCode == 51004 ){
        localStorage.setItem('qid', '')
        localStorage.setItem('authorization', '')
       
        // 登录失效则跳到首页
       // Router.push('/')
    } else {
         AntdMessage.error(errMsg);
         throw new CustomError(errCode, errMsg);
    }
 }
 
 // 扩展原生的Error对象，使其可以返回错误码
 class CustomError extends Error {
     constructor(code: number, message?: string) {
         super();
         this.message = code + (message || '请求失败');
     }
 }
 export const fetchData = async (
     url: string,
     data = {},
     method: string = 'GET',
     options?: Object
 ) => {
     console.log("url="+url )

     let customHeaders: RequestInit = {
         mode: 'cors',
         redirect: 'follow',
         referrer: 'no-referrer',
         ...options,
     };

    const p:any = data;
    
     if (!url.includes("/v1/user/login") && !url.includes("/v1/public")) {
         customHeaders.headers = {
             "Authorization": 'bearer '+ GlobalModel.token || '',
         }
     }
                

   

     method = method.toUpperCase();
     if (method === 'GET') {
         const queryStr = transferQueryStr(data);
         url = url + queryStr;
         customHeaders.method = 'GET';
     }
     if (data instanceof FormData && method === 'POST') {
         customHeaders.method = 'POST'
         customHeaders.body = data
     } else if (method === 'POST') {
         const reqData = JSON.stringify(data);
         customHeaders.method = 'POST';
         customHeaders.body = reqData;
         customHeaders.headers = {
             'content-Type': 'application/json',
             ...customHeaders.headers
         };
     }
     if (method === 'DELETE') {
        const queryStr = transferQueryStr(data);
        url = url + queryStr;
        const reqData = JSON.stringify(data);
        customHeaders.method = 'DELETE';
        customHeaders.body = reqData;
        customHeaders.headers = {
            'content-Type': 'application/json',
            ...customHeaders.headers
        };
     }
     if (method === 'PUT') {
         const reqData = JSON.stringify(data);
         customHeaders.method = 'PUT';
         customHeaders.body = reqData;
         customHeaders.headers = {
             'content-Type': 'application/json',
             ...customHeaders.headers
         };
     }
     
     console.log(url + "头部-=" + JSON.stringify(customHeaders.headers))

     return   fetch(getUrl(url), customHeaders)
         .then((response) => {
           
             if (response.ok) {
                
                 const isStream =
                     response.headers.get('content-type') === 'application/octet-stream';
                 if (isStream) {
                     // 如果是流的话直接返回
                     return response.blob();
                 } else {
                     return response.json().then((data) => {
                        const { error,errCode } = data;
                        if(error == "invalid_token" || errCode === -8){
                            PubSub.publish("login")
                        }
                        return checkStatus(data,response.url);
                     });
                 }
             } else {
                
                    response.json().then((data) => {
                        const { error,errCode } = data;
                        if(error == "invalid_token" || errCode === -8){
                            PubSub.publish("login")
                        } else {
                            AntdMessage.error('服务端响应异常');
                        }
                        // throw new CustomError(response.status, errors[0].msg);
                    });
               
             }
         })
         .catch((error) => {
             // throw new CustomError(error.errCode, error.errMsg);
         });
};