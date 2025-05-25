package net.qihoo.corp.umapp.service.sharebook.entity.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * @author cnn
 */
@Data

public class SbUserResBean {


    private Integer id;

    /**
     * account域账号
     */
    private String account;


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



    private Integer gender;



    private String introduction;



}
