package top.xiaobucvg.apimock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import top.xiaobucvg.apimock.pojo.Api;
import top.xiaobucvg.apimock.pojo.Response;
import top.xiaobucvg.apimock.service.IPortalService;
import top.xiaobucvg.apimock.util.ApiCache;
import top.xiaobucvg.apimock.util.ResponseCreater;
import top.xiaobucvg.apimock.util.WebApplicationAware;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/***
 * 入口 controller
 *
 * by Mr.Zhang
 */
@Controller
@ResponseBody
public class PortalController {

    @Autowired
    private IPortalService portalService;

    @Autowired
    private WebApplicationAware webApplicationAware;

    /***
     * 注册 Api ,接受一个 Api 对象
     */
    @RequestMapping(value = "register_api.do", method = RequestMethod.POST)
    public Response<Api> registerApi(Api api) {
        try {
             api = checkApi(api);
        } catch (Exception e) {
            return ResponseCreater.createFailResponse();
        }
        WebApplicationContext webApplicationContext = webApplicationAware.getWebApplicationContext();
        return portalService.registerApi(webApplicationContext, api);
    }

    /***
     * 获取所有的api
     */
    @RequestMapping(value = "get_all_api.do", method = RequestMethod.GET)
    public Response<List<Api>> getAllApi() {
        return portalService.getAllApi();
    }

    /***
     * 注销（删除）api
     */
    @RequestMapping(value = "un_register_api.do", method = RequestMethod.POST)
    public Response unRegisterApi(Api api) {
        if (api.getPath() == null) {
            return ResponseCreater.createFailResponse();
        }
        if (api.getRequestMethod() == null) {
            api.setRequestMethod(RequestMethod.GET);
        }
        int index = ApiCache.indexOfApi(api);
        if (index != -1) {
            WebApplicationContext webApplicationContext = webApplicationAware.getWebApplicationContext();
            return portalService.unRegisterApi(webApplicationContext, api, index);
        }
        return ResponseCreater.createFailResponse();
    }

    /***
    * 根据UUID删除API
    */
    @RequestMapping(value = "un_register_api_by_id.do", method = RequestMethod.POST)
    public Response unRegisterApiById(String id){
        int index = ApiCache.getApiByUUID(id);
        if(index == -1){
            return ResponseCreater.createFailResponse();
        }
        WebApplicationContext webApplicationContext = webApplicationAware.getWebApplicationContext();
        return portalService.unRegisterApiById(webApplicationContext,id);
    }

    /***
     * 删除所有API
     */
    @RequestMapping(value = "un_register_all_api.do", method = RequestMethod.POST)
    public Response unRegisterAllApi(){
        WebApplicationContext webApplicationContext = webApplicationAware.getWebApplicationContext();
        return portalService.unRegisterAllApi(webApplicationContext);
    }

    /***
     *  检查API对象，并构造成一个合法的API对象
     *  如果无法构造成合法API对象，抛出异常
     */
    private Api checkApi(Api api) throws Exception{
        if (StringUtils.isEmpty(api.getPath())) {
            throw new Exception("API路径不能为空");
        }

        api.setId(UUID.randomUUID().toString());
        api.setDate(new Date());

        if(StringUtils.isEmpty(api.getName())){
            api.setName("匿名API");
        }
        if(StringUtils.isEmpty(api.getDescription())){
            api.setDescription("这个API没有任何描述~");
        }
        if (StringUtils.isEmpty(api.getRequestMethod())) {
            api.setRequestMethod(RequestMethod.GET);
        }
        if (StringUtils.isEmpty(api.getResponseBody())) {
            api.setResponseBody("{}");
        }
        return api;
    }

}
