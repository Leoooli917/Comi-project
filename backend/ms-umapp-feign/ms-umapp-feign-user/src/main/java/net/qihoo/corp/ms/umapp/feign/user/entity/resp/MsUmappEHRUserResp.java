package net.qihoo.corp.ms.umapp.feign.user.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.user.entity.resp
 * @ClassName: MsUmappEHRUserResp
 * @Description:
 * @date 2022-10-12 14:34:32
 */
@Data
@Accessors(chain = true)
public class MsUmappEHRUserResp {
    private String employeeId;//":"Q000001",
    private String name;//":"周鸿祎",
    private String deptId;//":"1000388",
    private String domainId;// ":"zhouhongyi",
    private String email;// ":"Q000001@360.cn",
    private String phone;// ":" ","sex":"男",
    private String hireDate;// ":"2006-03-01",
    private String departmentDescription;//":"集团办公室",
    private String supervisorId;//":"Q000001",
    private String supervisorName;//":"周鸿祎",
    private String supervisorEmail;//":"Q000001@360.cn",
    private String supervisorPhone;//":" ",
    private String supervisorDomainId;//":"zhouhongyi",
    private String position;//":"董事长兼CEO",
    private String hrStatus;// ":"A",
    private String isExistsManageLevel;//":"Y",
    private String jobFunction;//":"管理",
    private String jobSubFunction;//":"管理层",
    private String jobSecondSubFunction;//":"无",
    private String emplClass;//":"01",
    private String outerTitle;//":"董事长兼CEO",
    private String company;//":"176",
    private String location;//":"101",
    private String terminationDate;//":"",
    private String manageLevel;//":"副总裁级",
    private String businessUnit;//":"QH001",
    private String sensitiveFlag;//":"是"
}
