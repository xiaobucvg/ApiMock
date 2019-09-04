package top.xiaobucvg.apimock.filter;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.RequestMethod;
import top.xiaobucvg.apimock.controller.ApiController;
import top.xiaobucvg.apimock.pojo.Api;
import top.xiaobucvg.apimock.util.ApiCache;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * ApiFilter
 * 如果请求的是Api缓存中的Api那么就要吧处理器需要返回的数据改为要请求的api的响应数据
 *
 * by Mr.Zhang
 */
public class ApiFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest res = (HttpServletRequest) request;
        // 获取请求的方式
        String method = res.getMethod().toUpperCase();
        // 获取path地址，不带后面的参数，不带前面的主机地址和项目名
        String uri = res.getServletPath().substring(1);
        Api api = new Api();
        api.setPath(uri);
        api.setRequestMethod(mapRequestMethod(method));
        // 在缓存中查找有没有对应的api
        int index = ApiCache.indexOfApi(api);
        if (index != -1) {
            ApiController.setResponseBody(ApiCache.getApi(index).getResponseBody());
        }
        chain.doFilter(res, response);
    }

    @Override
    public void destroy() {
    }

    /***
     * 根据字符串返回请求方法的枚举类
     */
    private RequestMethod mapRequestMethod(String method) {
        switch (method.toUpperCase()) {
            case "GET":
                return RequestMethod.GET;
            case "POST":
                return RequestMethod.POST;
        }
        return RequestMethod.GET;
    }

    /***
     * 预处理
     */
    private void preHandle(HttpServletRequest req, HttpServletResponse resp) {
        String method = req.getMethod();
    }
}
