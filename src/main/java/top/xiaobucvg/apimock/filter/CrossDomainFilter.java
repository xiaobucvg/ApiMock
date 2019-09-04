package top.xiaobucvg.apimock.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * 解决跨域问题的过滤器
 *
 * by Mr.Zhang
 */
public class CrossDomainFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        HttpServletResponse resp = (HttpServletResponse) response;

        // 表示所有域都可以通过
        resp.setHeader("Access-Control-Allow-Origin", "*");
        // ajax可以携带cookie
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        // 跨域允许的方法
        resp.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        // 许跨域请求包含content-type头
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type,Token,Accept, Connection, User-Agent, Cookie");
        // 在3628800秒内，不需要再发送预检验请求，可以缓存该结果
        resp.setHeader("Access-Control-Max-Age", "3628800");

        chain.doFilter(request,resp);
    }

    public void destroy() {

    }
}
