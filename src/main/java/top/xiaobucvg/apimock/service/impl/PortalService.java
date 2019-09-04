package top.xiaobucvg.apimock.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import top.xiaobucvg.apimock.controller.ApiController;
import top.xiaobucvg.apimock.pojo.Api;
import top.xiaobucvg.apimock.pojo.Response;
import top.xiaobucvg.apimock.service.IPortalService;
import top.xiaobucvg.apimock.util.ApiCache;
import top.xiaobucvg.apimock.util.ResponseCreater;

import java.lang.reflect.Method;
import java.util.List;

/***
 *
 * 注册已经有了的api的时候需要将原来的api移除，不然访问就会出现 Ambiguous 异常
 *
 * by Mr.Zhang
 */
@Service
public class PortalService implements IPortalService {


    /***
     * 注册api
     */
    public Response<Api> registerApi(WebApplicationContext webApplicationContext, Api api) {

        Method method = null;

        // 从handler中获取方法，获取不到就失败
        try {
            method = ApiController.class.getMethod("requestApi");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return ResponseCreater.createFailResponse();
        }

        int index = ApiCache.addApi(api);

        // 如果是替换了api则首先要注销原有的api，不然就会出错
        if (index != -1) {
            justUnRegisterApi(webApplicationContext, api);
        }

        // 设置需要响应的内容
        // ApiController.setResponseBody(api.getResponseBody());

        // 获取注解方式使用的 处理器映射器
        RequestMappingHandlerMapping handlerMapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);

        RequestMappingInfo mappingInfo = buildRequestMappingInfo(api);
        // 注册处理器
        handlerMapping.registerMapping(mappingInfo, "apiController", method);

        return ResponseCreater.createSuccessResponse(api);
    }


    /***
     * 获取所有的api
     */
    public Response<List<Api>> getAllApi() {
        return ResponseCreater.createSuccessResponse(ApiCache.getAllApi());
    }

    /***
     * 注销api的同时，将其从api缓存中删除
     */
    @Override
    public Response unRegisterApi(WebApplicationContext webApplicationContext, Api api, int index) {
        justUnRegisterApi(webApplicationContext, api);
        ApiCache.removeApi(index);
        return ResponseCreater.createSuccessResponse();
    }

    /***
    * 根据uuid删除api
    */
    @Override
    public Response unRegisterApiById(WebApplicationContext webApplicationContext, String uuid) {
        Api api = ApiCache.deleteApiByUUID(uuid);
        if(api == null){
            return ResponseCreater.createFailResponse();
        }
        justUnRegisterApi(webApplicationContext,api);
        return ResponseCreater.createSuccessResponse();
    }

    /***
    * 删除所有API
    */
    public Response unRegisterAllApi(WebApplicationContext webApplicationContext){
        List<Api> apis = ApiCache.getAllApi();
        for(Api a : apis){
            justUnRegisterApi(webApplicationContext, a);
        }
        ApiCache.removeAll();
        return ResponseCreater.createSuccessResponse();
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
     * 只注销api
     */
    private void justUnRegisterApi(WebApplicationContext webApplicationContext, Api api) {
        // 获取注解的方式使用的 处理器映射器
        RequestMappingHandlerMapping handlerMapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);

        RequestMappingInfo mappingInfo = buildRequestMappingInfo(api);

        handlerMapping.unregisterMapping(mappingInfo);
    }
}
