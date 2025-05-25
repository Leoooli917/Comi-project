package net.qihoo.corp.umapp.service.comi.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.IService;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiBanner;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiStat;

import java.util.List;


/**
 * @quthor cnn
 * @date 2024/3/29
 */
public interface StatService extends IService<ComiStat> {

    ComiStat statUser(String min_date, String max_date, String user, String model);


}
