package net.qihoo.corp.ms.umapp.feign.evaluate.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息类
 *
 * @author LBin
 * @date 2024-01-19 16:14:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UseInfoSubListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数量
     */
    private Long count;

    /**
     * 用户信息
     */
    private List<UserInfo> list;

    @Data
    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo extends UserBase {

        /**
         * 用户域账号
         */
        private String name;

        /**
         * 部门id
         */
        private String dept_id;

        /**
         * 部门
         */
        private String department;

        /**
         * 组织
         */
        private String organization;

        /**
         * 状态
         */
        private String status;
    }
}
