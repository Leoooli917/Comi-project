package net.qihoo.corp.ms.umapp.common.core.results;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.common.core.enums.MsUmappHttpStatus;

import java.io.Serializable;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.core.results
 * @ClassName: MsUmappBaseResult
 * @Description:
 * @date 2022-10-11 17:06:41
 */
@Slf4j
@Data
public class MsUmappBaseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer errCode;
    private String errMsg;
    private T Data;

    @Override
    public String toString() {
        return "Result{" +
                " errCode='" + errCode + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", Data=" + Data +
                "}";

    }

    public static <T> MsUmappBaseResult<T> ok() {
        return ok(null);
    }

    public static <T> MsUmappBaseResult<T> ok(T Data) {
        MsUmappBaseResult<T> result = new MsUmappBaseResult<>();
        result.setErrCode(0);
        result.setErrMsg("ok");
        result.setData(Data);
        if (Data!=null){
            log.info("ok--->"+Data.toString().substring(Math.min(Data.toString().length(), 100)));
        }
        return result;
    }

    public static <T> MsUmappBaseResult<T> error(Integer errCode, String errMsg) {
        MsUmappBaseResult<T> result = new MsUmappBaseResult<>();
        result.setErrCode(errCode);
        result.setErrMsg(errMsg);
        log.info("error--->"+errMsg);
        return result;
    }

    public static <T> MsUmappBaseResult<T> error(String errMsg) {
        MsUmappBaseResult<T> result = new MsUmappBaseResult<>();
        result.setErrCode(-1);
        result.setErrMsg(errMsg);
        log.info("error--->"+errMsg);
        return result;
    }

    /**
     * 服务调用错误
     *
     * @param serviceName 服务名
     * @param methodName  方法名
     * @return 结果视图
     */
    public static MsUmappBaseResult hystrixError(String serviceName, String methodName) {
        String msg = MsUmappHttpStatus.HYSTRIX_ERROR.getMessage().replace("xxx", serviceName).replace("{}", methodName);
        return MsUmappBaseResult.error(MsUmappHttpStatus.HYSTRIX_ERROR.getCode(), msg);
    }
}
