package net.qihoo.corp.ms.umapp.common.core.exceptions;

import net.qihoo.corp.ms.umapp.common.core.enums.MsUmappHttpStatus;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.core.exceptions
 * @ClassName: MsUmappCommonException
 * @Description:
 * @date 2022-10-11 17:03:09
 */
public class MsUmappCommonException extends RuntimeException {

    public Integer code;

    public String msg;

    public MsUmappCommonException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public MsUmappCommonException(String msg) {
        super(msg);
        this.code = MsUmappHttpStatus.COMMON_FAIL.getCode();
        this.msg = msg;
    }

    public MsUmappCommonException() {
        super(MsUmappHttpStatus.COMMON_FAIL.getMessage());
        this.code = MsUmappHttpStatus.COMMON_FAIL.getCode();
        this.msg = MsUmappHttpStatus.COMMON_FAIL.getMessage();
    }

    public MsUmappCommonException(MsUmappHttpStatus status) {
        super(status.getMessage());
        this.code = status.getCode();
        this.msg = status.getMessage();
    }

    public MsUmappCommonException(Throwable cause) {
        super(cause);
    }
}
