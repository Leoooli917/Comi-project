package net.qihoo.corp.umapp.service.sharebook.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * @author cnn
 */
@Data
@Accessors(chain = true)
public class SbUser {

    private Integer id;

    /**
     * account域账号
     */
    private String account;

    private Long userId;

    /**
     * 用户id ttid
     */
    private Long uid;

    /**
     * 姓名
     */
    private String name;

    /**
     * email
     */
    private String email;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 书币
     */
//    private Integer bookCoin;

    /**
     * 书的数量
     */
//    private Integer bookNum;

//    private Integer feedNum;

    private Integer gender;

    private String accessToken;

    private String introduction;

    private Date modifyTime;

    private Date createTime;


    //***数据库未存储

    @TableField(exist = false)
    private String refreshToken;

    @TableField(exist = false)
    private String tel;

    @TableField(exist = false)
    private String enName;

    @TableField(exist = false)
    private String status;

    @TableField(exist = false)
    private String roleId;

    @TableField(exist = false)
    private String staffId;
}
