package net.qihoo.corp.ms.umapp.feign.evaluate.entity.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 工作项请求类
 *
 * @author LBin
 * @date 2024-01-19 14:19:47
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MatterReq extends SignModel implements Serializable {

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
     * 详情模式（0：默认，详情模式会返回所有的属性详情）还是精简模式（1，精简模式只返回基本的标题、状态等信息）
     */
    private Integer simple = 0;

    /**
     * 按状态筛选
     */
    private String type_id = "任务";

    /**
     * 按状态筛选
     */
    private String matter_ids = "";

    /**
     * 过滤标题
     */
    private int title;

    /**
     * 组合筛选字段，字典类型或者json格式字符串，推荐用json格式字符串。支持的搜索条件见下面说明。
     */
    private String customContent = "";

    /**
     * 排序字段//示例值["priority","desc/asc"] //支持排序的字段 优先级:priority,创建时间:create_time,更新时间:update_time, 实际开始时间:start_time,实际完成时间:finish_time,预计结束时间:mendtime,ID:id，默认id倒叙排序
     */
    private String order_by = "create_time,desc";

}
