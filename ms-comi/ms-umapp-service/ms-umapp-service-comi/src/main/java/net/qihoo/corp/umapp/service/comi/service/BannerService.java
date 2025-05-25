package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiBanner;

import java.util.List;


/**
 * @quthor cnn
 * @date 2024/3/29
 */
public interface BannerService extends IService<ComiBanner> {

    List<ComiBanner> getBannerList();


}
