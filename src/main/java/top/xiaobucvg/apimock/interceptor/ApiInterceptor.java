package top.xiaobucvg.apimock.interceptor;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.xiaobucvg.apimock.controller.ApiController;
import top.xiaobucvg.apimock.pojo.Api;
import top.xiaobucvg.apimock.util.ApiCache_v1_5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/***
 * Api拦截器
 *
 * by Mr.Zhang
 */
public class ApiInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果请求的路径不是以.do结尾的直接放过，因为根据正规方式注册的Api一定是以.do结尾的
        String servletPath = request.getServletPath();
        if (!servletPath.toLowerCase().endsWith(".do")) return true;

        // 请求的路径是以.do结尾的，这个时候需要从缓存中查找有没有这个Api
        String method = request.getMethod().toUpperCase();
        String id = buildId(servletPath, method);
        // 如果不存在，放过
        if (!ApiCache_v1_5.containsApi(id)) {
            return true;
        }
        // 如果存在修改ApiController的响应体
        Api api = ApiCache_v1_5.getApiById(id);
        if (api == null) {
            return true;
        }
        ApiController.setResponseBody(api.getResponseBody());
        return true;
    }

    /***
     * 构造Api的ID
     */
    private static String buildId(Api api) {
        return api.getPath() + api.getRequestMethod();
    }

    /***
     * 构造Api的ID
     */
    private static String buildId(String path, String method) {
        String id = path + method;
        if(!id.startsWith("/")){
            return "/" + id;
        }
        return id;
    }

    /***
     *  检查API对象，并构造成一个合法的API对象
     *  如果无法构造成合法API对象，抛出异常
     */
    public static Api checkAndBuildApi(Api api) throws Exception {
        if (StringUtils.isEmpty(api.getPath())) {
            throw new Exception("API路径不能为空");
        }

        if (!api.getPath().endsWith(".do")) {
            throw new Exception("要注册的Api必须以.do结尾");
        }

        api.setDate(new Date());

        if (StringUtils.isEmpty(api.getName())) {
            api.setName("匿名API");
        }
        if (StringUtils.isEmpty(api.getDescription())) {
            api.setDescription("这个API没有任何描述~");
        }
        if (StringUtils.isEmpty(api.getRequestMethod())) {
            api.setRequestMethod(RequestMethod.GET);
        }
        if (StringUtils.isEmpty(api.getResponseBody())) {
            api.setResponseBody("{}");
        }
        // 构造ID
        api.setId(buildId(api.getPath(), api.getRequestMethod().toString()));
        return api;
    }
}
