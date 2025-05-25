package net.qihoo.corp.ms.umapp.feign.evaluate.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户基本信息
 *
 * @author LBin
 * @date 2024-01-17 15:53:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户邮箱
     */
    private String mail;

    /**
     * 用户名
     */
    private String real_name;
}
