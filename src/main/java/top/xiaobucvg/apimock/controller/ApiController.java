package top.xiaobucvg.apimock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * 这个 Controller 提供一个方法，每次请求方法的时候，从方法缓存中找到对应的方法对象，然后用处理器映射器重新映射到这个方法，就等于创建了一个Api
 *
 * by Mr.Zhang
 */
@Controller
public class ApiController {

    // 默认的响应内容
    private static String responseBody = "{}";

    /***
     * 接受响应的内容，原样响应给前台
     * 因为配置的消息转换器默认相应方式是 json 格式,所以返回字符串的时候会转义
     * 所以增加 produce 项,使其返回 plain
     *
     * 具体代码已经在 PortalService 实现
     */
    @ResponseBody
    public String requestApi() {
        return ApiController.responseBody;
    }

    public static String getResponseBody() {
        return responseBody;
    }

    public static void setResponseBody(String responseBody) {
        ApiController.responseBody = responseBody;
    }
}
