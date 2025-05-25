package net.qihoo.corp.ms.umapp.common.login.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.login.entity.resp
 * @ClassName: MsUmappCCWorkCheckSignResp
 * @Description:
 * @date 2022-10-13 11:38:39
 */
@Data
@Accessors(chain = true)
public class MsUmappCCWorkCheckSignResp {
    private String uid;
    private String account;
    private String name;
    private String enName;
    private String gender;
    private String email;
    private String avatar;
    private String pwd;
    private String tel;
    private String mobile;
    private String status;
    private List deptData;
    private String roleId;
    private String staffId;
    private String avatarUrl;
    private List posData;
}
