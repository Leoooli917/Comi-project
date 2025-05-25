package net.qihoo.corp.ms.umapp.feign.todolist.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by xuxueli on 16/9/30.
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappServiceTodoJobGroup {

    private int id;
    private String appname;
    private String title;
    // 执行器地址类型：0=自动注册、1=手动录入
    private int addressType;
    // 执行器地址列表，多地址逗号分隔(手动录入)
    private String addressList;
    private Date updateTime;
    // registry list
    // 执行器地址列表(系统注册)
    private List<String> registryList;

    public List<String> getRegistryList() {
        if (addressList != null && addressList.trim().length() > 0) {
            registryList = new ArrayList<String>(Arrays.asList(addressList.split(",")));
        }
        return registryList;
    }

    public void setAddressList(String addressList) {
        this.addressList = addressList;
    }

}
