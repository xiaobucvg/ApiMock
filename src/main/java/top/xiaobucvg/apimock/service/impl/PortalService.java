package top.xiaobucvg.apimock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import top.xiaobucvg.apimock.controller.ApiController;
import top.xiaobucvg.apimock.pojo.Api;
import top.xiaobucvg.apimock.pojo.Response;
import top.xiaobucvg.apimock.service.IPortalService;
import top.xiaobucvg.apimock.util.ApiCache_v1_5;
import top.xiaobucvg.apimock.util.ResponseCreator;
import top.xiaobucvg.apimock.util.WebApplicationAware;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/***
 *
 * 注册已经有了的api的时候需要将原来的api移除，不然访问就会出现 Ambiguous 异常
 *
 * by Mr.Zhang
 */
@Service
public class PortalService implements IPortalService {
    @Autowired
    private WebApplicationAware webApplicationAware;

    /***
     * 1.5版本新增的注册Api的方法
     *
     * @since v1.5
     *
     * by Mr.Zhang
     */
    public Response<Api> registerApi_v1_5(Api api) {
        Method method = null;
        // 从handler中获取方法，获取不到就失败
        try {
            method = ApiController.class.getMethod("requestApi");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return ResponseCreator.createFailResponse();
        }

        if (ApiCache_v1_5.containsApi(api)) {
            return ResponseCreator.createFailResponse("该Api已经存在");
        }

        ApiCache_v1_5.putApi(api);

        // 获取注解方式使用的 处理器映射器
        RequestMappingHandlerMapping handlerMapping = webApplicationAware.getWebApplicationContext().getBean(RequestMappingHandlerMapping.class);

        RequestMappingInfo mappingInfo = buildRequestMappingInfo(api);
        // 注册处理器
        handlerMapping.registerMapping(mappingInfo, "apiController", method);

        return ResponseCreator.createSuccessResponse(api, "注册成功");

    }

    /***
     * 注册Api如果存在相同的api则覆盖
     */
    @Override
    public Map<String, Object> registerApiRe(Api api) {
        Map<String,Object> res = new HashMap<>();
        Method method = null;
        // 从handler中获取方法，获取不到就失败
        try {
            method = ApiController.class.getMethod("requestApi");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            res.put("status",Response.FAIL);
            res.put("msg","注册失败");
            return res;
        }

        // 如果原先存在相同的Api，把被覆盖标志改成true，反之，改成false
        // 如果原先存在相同的Api，把原先的处理器删除
        if (ApiCache_v1_5.containsApi(api)) {
            justUnRegisterApi(api);
            res.put("isCover","true");
        }
        else {
            res.put("isCover","false");
        }

        ApiCache_v1_5.putApi(api);

        // 获取注解方式使用的 处理器映射器
        RequestMappingHandlerMapping handlerMapping = webApplicationAware.getWebApplicationContext().getBean(RequestMappingHandlerMapping.class);

        RequestMappingInfo mappingInfo = buildRequestMappingInfo(api);
        // 注册处理器
        handlerMapping.registerMapping(mappingInfo, "apiController", method);

        res.put("msg","注册成功");
        res.put("responseBody",api);
        res.put("status",0);

        return res;
    }

    /***
     * 获取所有的api
     */
    public Response<Set<Api>> getAllApi() {
        Set<Api> apis = ApiCache_v1_5.getAllApi();
        return ResponseCreator.createSuccessResponse(apis);
    }

    /***
     * 根据uuid删除api
     */
    @Override
    public Response unRegisterApiById(String uuid) {
        if (!ApiCache_v1_5.containsApi(uuid)) {
            return ResponseCreator.createFailResponse("不存在这个Api");
        }
        Api api = ApiCache_v1_5.removeApi(uuid);
        if (api == null) {
            return ResponseCreator.createFailResponse("删除Api失败");
        }
        justUnRegisterApi(api);
        return ResponseCreator.createSuccessResponse("注销Api成功");
    }

    /***
     * 删除所有API
     */
    public Response unRegisterAllApi() {
        Set<Api> apis = ApiCache_v1_5.getAllApi();
        for (Api api : apis) {
            justUnRegisterApi(api);
            ApiCache_v1_5.removeApi(api.getId());
        }
        // 注意:返回的信息中会存在所有被删除的api信息
        return ResponseCreator.createSuccessResponse(apis,"删除成功");
    }

    /***
     * 根据api构造mappingInfo
     */
    private RequestMappingInfo buildRequestMappingInfo(Api api) {
        // api 路径
        PatternsRequestCondition patternsRequestCondition = new PatternsRequestCondition(api.getPath());

        // api 请求的方式
        RequestMethodsRequestCondition requestMethodsRequestCondition = new RequestMethodsRequestCondition(api.getRequestMethod());

        // * 接受响应的内容，原样响应给前台
        // * 因为配置的消息转换器默认相应方式是 json 格式,所以返回字符串的时候会转义
        // * 所以增加 produce 项,使其返回 纯文本
        // * 必须加 charset 不然返回中文乱码
        ProducesRequestCondition producesRequestCondition = new ProducesRequestCondition("text/plain;charset=utf-8");


        // 封装
        RequestMappingInfo mappingInfo = new RequestMappingInfo("", patternsRequestCondition, requestMethodsRequestCondition, null, null, null, producesRequestCondition, null);

        return mappingInfo;
    }

    /***
     * 注销处理器
     */
    private void justUnRegisterApi(Api api) {
        // 获取注解的方式使用的 处理器映射器
        RequestMappingHandlerMapping handlerMapping = webApplicationAware.getWebApplicationContext().getBean(RequestMappingHandlerMapping.class);

        RequestMappingInfo mappingInfo = buildRequestMappingInfo(api);

        handlerMapping.unregisterMapping(mappingInfo);
    }
}
