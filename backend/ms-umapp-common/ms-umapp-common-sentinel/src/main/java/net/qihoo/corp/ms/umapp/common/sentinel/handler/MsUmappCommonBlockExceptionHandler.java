package net.qihoo.corp.ms.umapp.common.sentinel.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.sentinel.handler
 * @ClassName: MsUmappCommonBlockExceptionHandler
 * @Description:
 * @date 2022-10-24 16:19:42
 */
@Component
@Slf4j
public class MsUmappCommonBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        MsUmappBaseResult result = MsUmappBaseResult.error(305, "sentinel拦截");
        log.info("BlockException=======" + e.getRule());
        if (e instanceof FlowException) {
            result = MsUmappBaseResult.error(300, "接口被限流了");
        } else if (e instanceof DegradeException) {
            result = MsUmappBaseResult.error(301, "接口被降级了");
        } else if (e instanceof ParamFlowException) {
            result = MsUmappBaseResult.error(302, "接口热点参数限流了");
        } else if (e instanceof SystemBlockException) {
            result = MsUmappBaseResult.error(303, "接口触发系统保护规则了");
        } else if (e instanceof AuthorityException) {
            result = MsUmappBaseResult.error(304, "授权接口不通过");
        }
        //返回json格式
        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(httpServletResponse.getWriter(), result);
    }
}
