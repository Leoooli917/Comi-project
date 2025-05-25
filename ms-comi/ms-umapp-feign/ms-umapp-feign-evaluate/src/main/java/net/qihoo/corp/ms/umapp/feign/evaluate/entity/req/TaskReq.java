package net.qihoo.corp.ms.umapp.feign.evaluate.entity.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 任务请求类
 *
 * @author LBin
 * @date 2024-01-18 14:50:19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TaskReq extends SignModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    private Long sub_id;

    /**
     * 页数
     */
    private Integer page = 1;

    /**
     * 每页条数
     */
    private Integer pageSize = 100;

    /**
     * 是否显示子任务1是0否
     */
    private Integer is_tree = 0;

    /**
     * 排序【格式和其他模块一样就中，具体参数同创建接口】
     */
    private String order_by = "";

    /**
     * 按状态筛选
     */
    private String status = "";

    /**
     * 按优先级筛选
     */
    private String priority = "";

    /**
     * 按迭代筛选
     */
    private String iteration_id = "";

    /**
     * 按预估工时筛选
     */
    private String labor_hour = "";

    /**
     * 按实际工时筛选
     */
    private String real_hour = "";

    /**
     * 按创建人筛选
     */
    private String creator = "";

    /**
     * 按负责人筛选
     */
    private String leader = "";

    /**
     * 按标签筛选
     */
    private String tag_id = "";

    /**
     * 按模块筛选
     */
    private String module_id = "";

    /**
     * 右上角搜索标题关键字
     */
    private String search = "";

    /**
     * 来源 1任务列表 2迭代详情下的任务列表
     */
    private String source = "";
}
