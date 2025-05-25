package net.qihoo.corp.ms.umapp.common.util.network;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.util.network
 * @ClassName: MsUmappHttpClientUtil
 * @Description:
 * @date 2022-10-11 17:18:42
 */
public class MsUmappHttpClientUtil {

    private static final int defaultTimeOut = 5000;
    private final static String TEXT_PLAIN = "text/plain";

    /**
     * 设置请求配置
     *
     * @return
     */
    public static RequestConfig getRequestConfig(int socketTimeOut, int connectTimeOut, int connectRequestTimeOut) {

        if (socketTimeOut <= 0) {
            socketTimeOut = defaultTimeOut;
        }

        if (connectTimeOut <= 0) {
            connectTimeOut = defaultTimeOut;
        }

        if (connectRequestTimeOut <= 0) {
            connectRequestTimeOut = defaultTimeOut;
        }

        RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut).setConnectTimeout(connectTimeOut).setConnectionRequestTimeout(connectRequestTimeOut).build();
        return defaultRequestConfig;
    }

    /**
     * http get方式获取远程地址内容
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String fetchContentByGetMethod(String url, Map<String, String> params) throws Exception {
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(getRequestConfig(defaultTimeOut, defaultTimeOut, defaultTimeOut)).build();
        return fetchContentByGetMethod(httpclient, url, params);
    }

    /**
     * 通过地址Get方式获取数据
     *
     * @param closeableHttpClient
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String fetchContentByGetMethod(CloseableHttpClient closeableHttpClient, String url, Map<String, String> params) throws Exception {
        return fetchContent(closeableHttpClient, url, params, "GET");
    }


    /**
     * POST 方式获取数据
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String fetchContentByPostMethod(String url, Map<String, String> params) throws Exception {
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(getRequestConfig(defaultTimeOut, defaultTimeOut, defaultTimeOut)).build();
        return fetchContentByPostMethod(httpclient, url, params);
    }

    /**
     * POST 方式获取数据
     *
     * @param httpclient
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String fetchContentByPostMethod(CloseableHttpClient httpclient, String url, Map<String, String> params) throws Exception {
        return fetchContent(httpclient, url, params, "POST");
    }

    /**
     * 内部获取数据方法
     *
     * @param closeableHttpClient
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    private static String fetchContent(CloseableHttpClient closeableHttpClient, String url, Map<String, String> params, String method) throws Exception {

        Assert.notNull(closeableHttpClient, "httpClient must be not null");

        if (StringUtils.isEmpty(method)) {
            method = "GET";
        }

        CloseableHttpResponse response = null;
        if ("GET".equals(method)) {
            URIBuilder uriBuilder = new URIBuilder(url);
            Iterator keyIt = params.keySet().iterator();
            while (keyIt.hasNext()) {
                String key = (String) keyIt.next();
                String val = (String) params.get(key);
                uriBuilder.setParameter(key, val);
            }

            URI uri = uriBuilder.build();

            HttpGet httpget = new HttpGet(uri);
            httpget.setHeader("Content-Type", "application/json");
            response = closeableHttpClient.execute(httpget);
        }

        if ("POST".equals(method)) {

            List nvps = new ArrayList();
            Iterator keyIt = params.keySet().iterator();
            while (keyIt.hasNext()) {
                String key = (String) keyIt.next();
                String val = (String) params.get(key);
                nvps.add(new BasicNameValuePair(key, val));
            }

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            response = closeableHttpClient.execute(httpPost);
        }


        return getRequestContent(response);
    }


    /**
     * 读取数据返回内容
     *
     * @param response
     * @return
     * @throws Exception
     */
    private static String getRequestContent(CloseableHttpResponse response) throws Exception {
        InputStream inputStream = null;
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                inputStream = entity.getContent();
                StringWriter writer = new StringWriter();
                IOUtils.copy(inputStream, writer, "UTF-8");
                return writer.toString();
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            response.close();
        }
        return null;
    }

    /**
     * 执行put 方法
     *
     * @param url
     * @param jsonData
     * @return
     * @throws Exception
     */
    public static String doPut(String url, String jsonData) throws Exception {

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(getRequestConfig(defaultTimeOut, defaultTimeOut, defaultTimeOut)).build();

        return doPut(httpClient, url, jsonData);

    }

    /**
     * GET方式获取图片二进制流
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static byte[] getByte(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(getRequestConfig(defaultTimeOut, defaultTimeOut, defaultTimeOut)).build();
        HttpResponse response = httpClient.execute(httpGet);
        InputStream inputStream = response.getEntity().getContent();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inputStream.read(outStream.toByteArray());
        inputStream.close();
        return outStream.toByteArray();
    }

    /**
     * PUT方式提交数据
     *
     * @param httpClient
     * @param url
     * @param jsonData
     * @return
     * @throws Exception
     */
    public static String doPut(CloseableHttpClient httpClient, String url, String jsonData) throws Exception {
        HttpPut httpPut = new HttpPut(url);
        httpPut.addHeader("Content-Type", "application/json");

        StringEntity entity = getRequestBody(jsonData);
        if (null != entity) {
            httpPut.setEntity(entity);
        }
        CloseableHttpResponse response = httpClient.execute(httpPut);

        return getRequestContent(response);
    }

    /**
     * 发送Post请求
     *
     * @param url
     * @param jsonData
     * @return
     * @throws Exception
     */
    public static String doPost(String url, String jsonData) throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(getRequestConfig(defaultTimeOut, defaultTimeOut, defaultTimeOut)).build();
        return doPost(httpClient, url, jsonData);
    }


    /**
     * 发送Post请求
     *
     * @param httpClient
     * @param url
     * @param jsonData
     * @return
     * @throws Exception
     */
    public static String doPost(CloseableHttpClient httpClient, String url, String jsonData) throws Exception {

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");

        StringEntity entity = getRequestBody(jsonData);
        if (null != entity) {
            httpPost.setEntity(entity);
        }

        CloseableHttpResponse response = httpClient.execute(httpPost);

        return getRequestContent(response);
    }

    /**
     * http post 方式获取远程内容
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String postFromUrl(String url, Map<String, String> params) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);

        List nvps = new ArrayList();
        Iterator keyIt = params.keySet().iterator();
        while (keyIt.hasNext()) {
            String key = (String) keyIt.next();
            String val = (String) params.get(key);
            nvps.add(new BasicNameValuePair(key, val));
        }

        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        return getRequestContent(response);
    }

    /**
     * 获取请求Body
     *
     * @param jsonData
     * @return
     * @throws Exception
     */
    private static StringEntity getRequestBody(String jsonData) throws Exception {
        if (!StringUtils.isEmpty(jsonData)) {
            StringEntity entity = new StringEntity(jsonData, "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            return entity;
        }
        return null;
    }

    /**
     * 执行Delete方法
     *
     * @param httpClient
     * @param url
     * @return
     * @throws Exception
     */
    public static String doDelete(CloseableHttpClient httpClient, String url) throws Exception {
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.addHeader("Content-Type", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpDelete);
        return getRequestContent(response);
    }


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * <p>
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * <p>
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     * <p>
     * 用户真实IP为： 192.168.1.110
     * X-Real-IP Nginx反向代理获取Ip
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("X-Cluster-Client-Ip");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * 通过post方式提交form表单
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String doPostByForm(String url, List<BasicNameValuePair> params) throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(getRequestConfig(defaultTimeOut, defaultTimeOut, defaultTimeOut)).build();
        return doPostByForm(httpClient, url, params);
    }

    /**
     * 通过post方式提交form表单
     *
     * @param httpClient
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String doPostByForm(CloseableHttpClient httpClient, String url, List<BasicNameValuePair> params) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        // form形式提交表单
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
        if (null != entity) {
            httpPost.setEntity(entity);
        }
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return getRequestContent(response);
    }

    /**
     * get方式请求
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        // get请求返回结果
        CloseableHttpClient client = HttpClients.createDefault();
        // 发送get请求
        HttpGet request = new HttpGet(url);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        request.setConfig(requestConfig);
        String strResult = null;
        try {
            CloseableHttpResponse response = client.execute(request);
            //请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //读取服务器返回过来的json字符串数据
                HttpEntity entity = response.getEntity();
                strResult = EntityUtils.toString(entity, "utf-8");
                //把json字符串转换成json对象
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            request.releaseConnection();
        }
        return strResult;
    }

    /**
     * 通过post方式提交form表单，包含文件
     *
     * @param url
     * @param inputStream
     * @param params
     * @return
     * @throws Exception
     */
    public static String doPostWithFile(String url, InputStream inputStream, List<BasicNameValuePair> params, int timeout) throws Exception {
        ContentType contentType = ContentType.create(TEXT_PLAIN, StandardCharsets.UTF_8);
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(getRequestConfig(timeout, timeout, timeout)).build();
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        //设置请求的编码格式UTF_8
        builder.setCharset(StandardCharsets.UTF_8);
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        String fileName = null;
        for (BasicNameValuePair pair : params) {
            if (pair.getName().equals("file_name")) {
                fileName = pair.getValue();
                continue;
            }
            builder.addPart(pair.getName(), new StringBody(pair.getValue(), contentType));
        }
        builder.addBinaryBody("file", inputStream, ContentType.DEFAULT_BINARY, fileName);
        HttpEntity entity = builder.build();
        post.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(post);
        return getRequestContent(response);
    }
}
