package top.xiaobucvg.apimock.filter;

import javax.servlet.*;
import java.io.IOException;

/***
* 编码过滤器
*/
public class CharsetFilter implements Filter {

    private String charset = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String charset = filterConfig.getInitParameter("charset");
        if(charset != null){
            this.charset = charset;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(this.charset);
        response.setCharacterEncoding(this.charset);
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
