package top.xiaobucvg.apimock.util;

import top.xiaobucvg.apimock.pojo.Response;

/***
 * 响应创建器，用来创建相关的响应对象
 *
 * by Mr.Zhang
 */
public class ResponseCreater {

    /***
     * 创建响应成功的对象
     */
    public static <T> Response<T> createSuccessResponse(T resBody) {
        Response<T> response = new Response<T>();
        response.setStatus(Response.SUCCESS);
        response.setMsg("请求成功");
        response.setResponseBody(resBody);
        return response;
    }

    /***
     * 创建响应成功的对象 - 没有响应体 - 只是提示响应成功
     */
    public static Response createSuccessResponse() {
        return createSuccessResponse(null);
    }

    /***
     * 创建响应失败的对象
     */
    public static Response createFailResponse() {
        Response response = new Response();
        response.setStatus(Response.FAIL);
        response.setMsg("请求失败");
        return response;
    }

    /***
     * 创建响应错误的对象
     */
    public static Response createErrorResponse() {
        Response response = new Response();
        response.setStatus(Response.ERROR);
        response.setMsg("请求出错");
        return response;
    }
}
