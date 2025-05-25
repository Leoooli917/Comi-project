package net.qihoo.corp.ms.umapp.feign.evaluate.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.common.core.enums.MsUmappHttpStatus;

import java.io.Serializable;

/**
 * 基础返回类
 *
 * @author LBin
 * @date 2024-01-17 14:40:27
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 错误编码
     */
    private Integer errno;

    /**
     * 错误信息
     */
    private String errmsg;

    /**
     * 数据
     */
    T data;

    public static <T> BaseResult<T> error(Integer errCode, String errMsg) {
        BaseResult<T> result = new BaseResult<>();
        result.setErrno(errCode);
        result.setErrmsg(errMsg);
        log.info("error--->" + errMsg);
        return result;
    }

    /**
     * 服务调用错误
     *
     * @param serviceName 服务名
     * @param methodName  方法名
     * @return 结果视图
     */
    public static <T> BaseResult<T> hystrixError(String serviceName, String methodName) {
        String msg = MsUmappHttpStatus.HYSTRIX_ERROR.getMessage().replace("xxx", serviceName).replace("{}", methodName);
        return BaseResult.error(MsUmappHttpStatus.HYSTRIX_ERROR.getCode(), msg);
    }
}
