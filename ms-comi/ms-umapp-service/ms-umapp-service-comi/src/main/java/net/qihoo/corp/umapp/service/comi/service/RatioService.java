package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiRatio;

import java.util.List;


/**
 * @quthor cnn
 * @date 2024/4/1
 */
public interface RatioService extends IService<ComiRatio> {

    List<ComiRatio> getRadioList();
    ComiRatio getRadio(Integer id);

}
