package net.qihoo.corp.ms.umapp.feign.todolist.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

/**
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @Description 待办清单文件表
 * @email 1472052711@qq.com
 * @date 2021-11-10 22:39:29
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappServiceTodoFile extends MsUmappBaseModel {
    // 关联业务数据id
    private Integer dataId;
    // 文件名称
    private String fileName;
    // 文件结尾地址
    private String filePath;
    // 文件服务器地址
    private String doMain;
    // 文件显示顺序
    private Integer fileOrder;
    // 文件访问地址：doMain+filePath
    private String fileUrl;
    // 文件base64码字符串
    private String dataBlog;


}
