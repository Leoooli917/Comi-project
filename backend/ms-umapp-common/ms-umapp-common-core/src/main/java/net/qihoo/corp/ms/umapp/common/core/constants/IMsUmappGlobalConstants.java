package net.qihoo.corp.ms.umapp.common.core.constants;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.core.constants
 * @ClassName: MsUmappGlobalConstants
 * @Description:
 * @date 2022-10-11 16:58:17
 */
public interface IMsUmappGlobalConstants {
    /**
     * jwt对称加密
     */
    String OAUTH_SIGNING_KEY = "umapp_oauth_key";

    /**
     * Redis Cache
     */
    String REDIS_USER_CACHE = "redis_user_cache";

    /**
     * Redis Cache
     */
    String REDIS_CLIENT_CACHE = "redis_client_cache";

    /**
     * 缓存中user的key
     */
    String USER_KEY_PREFIX = "UmappUser_";

    /**
     * oauth 客户端信息
     */
    String CLIENT_DETAILS_KEY = "UmappClient_";

    /**
     * Redis默认过期时长，单位：秒  5分钟
     */
    long DEFAULT_EXPIRE = 60 * 5;

    /**
     * Redis 不设置过期时长
     */
    long NOT_EXPIRE = -1;

    /**
     * Redis set 前缀
     */
    String KEY_SET_PREFIX = "_set:";

    /**
     * Redis list 前缀
     */
    String KEY_LIST_PREFIX = "_list:";

    /**
     * ip
     */
    String UNKNOWN = "unknown";

    /**
     * druid配置
     */
    String DB_PREFIX = "spring.datasource";

    /**
     * sophia security配置
     */
    String SOPHIA_OAUTH_PREFIX = "umapp.security";
    /**
     * oauth security配置
     */
    String OAUTH_SECURITY_PREFIX = "umapp.oauth2.client";

    /**
     * security  过滤url 配置
     */
    String FILTER_IGNORE = "ignore";

    /**
     * security  过滤url 配置
     */
    String SOPHIA_RESOURCE_IDS = "umapp.resource";

    /**
     * 成功标记
     */
    Integer SUCCESS = 0;
    /**
     * 失败标记
     */
    Integer FAIL = 1;

    /**
     * 前缀
     */
    String PROJECT_PREFIX = "umapp_";

    /**
     * oauth 相关前缀
     */
    String OAUTH_PREFIX = "oauth:";

    String CURRENT = "current";

    String SIZE = "size";


    /**
     * 内部
     */
    String FROM_IN = "Y";

    /**
     * 标志
     */
    String FROM = "from";

    String LOCAL_HOST_127 = "127.0.0.1";

    String LOCAL_HOST_1 = "0:0:0:0:0:0:0:1";

    String GET = "GET";

    String LOGIN = "/login";

    String SAVE_LOG = "/log/api/add";
}
