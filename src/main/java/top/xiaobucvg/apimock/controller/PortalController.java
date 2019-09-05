package top.xiaobucvg.apimock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.xiaobucvg.apimock.interceptor.ApiInterceptor;
import top.xiaobucvg.apimock.pojo.Api;
import top.xiaobucvg.apimock.pojo.Response;
import top.xiaobucvg.apimock.service.IPortalService;
import top.xiaobucvg.apimock.util.ResponseCreator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    /***
     * 注册 Api ,接受一个 Api 对象
     */
    @RequestMapping(value = "register_api",method = RequestMethod.POST)
    public Response<Api> registerApi_v1_5(Api api){
        try {
            api = ApiInterceptor.checkAndBuildApi(api);
        } catch (Exception e) {
            return ResponseCreator.createErrorResponse("Api的路径不能为空且必须以.do结尾");
        }
        Response<Api> response = portalService.registerApi_v1_5(api);
        return response;
    }

    /***
     * 注册Api如果存在相同的api则覆盖
     */
    @RequestMapping(value = "register_api_re",method = RequestMethod.POST)
    public Map<String, Object> registerApiRe(Api api){
        try {
            ApiInterceptor.checkAndBuildApi(api);
        } catch (Exception e) {
            Map<String,Object> res = new HashMap<>();
            res.put("status",Response.ERROR);
            res.put("msg","Api的路径不能为空且必须以.do结尾");
            return res;
        }
        return portalService.registerApiRe(api);
    }

    /***
     * 获取所有的api
     */
    @RequestMapping(value = "get_all_api", method = RequestMethod.GET)
    public Response<Set<Api>> getAllApi() {
        return portalService.getAllApi();
    }

    /***
     * 根据UUID删除API
     */
    @RequestMapping(value = "un_register_api_by_id", method = RequestMethod.POST)
    public Response unRegisterApiById(String id){
        return portalService.unRegisterApiById(id);
    }

    /***
     * 删除所有API
     */
    @RequestMapping(value = "un_register_all_api", method = RequestMethod.POST)
    public Response unRegisterAllApi(){
        return portalService.unRegisterAllApi();
    }
}
