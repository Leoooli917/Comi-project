package net.qihoo.corp.ms.umapp.common.core.results;

import lombok.Data;
import net.qihoo.corp.ms.umapp.common.core.enums.MsUmappHttpStatus;

import java.io.Serializable;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.core.results
 * @ClassName: MsUmappFeignResult
 * @Description:
 * @date 2022-10-11 17:08:24
 */
@Data
public class MsUmappFeignResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private Object data;

    public MsUmappFeignResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public MsUmappFeignResult(MsUmappHttpStatus umappHttpStatus) {
        this.code = umappHttpStatus.getCode();
        this.message = umappHttpStatus.getMessage();
    }

    /**
     * 服务调用错误
     *
     * @param serviceName 服务名
     * @param methodName  方法名
     * @return 结果视图
     */
    public static MsUmappFeignResult hystrixError(String serviceName, String methodName) {
        String msg = MsUmappHttpStatus.HYSTRIX_ERROR.getMessage().replace("xxx", serviceName).replace("{}", methodName);
        return new MsUmappFeignResult(MsUmappHttpStatus.HYSTRIX_ERROR.getCode(), msg);
    }
}
