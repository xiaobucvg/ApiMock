package top.xiaobucvg.apimock.pojo;

/***
 * 响应的封装类
 *
 * by Mr.Zhang
 */
public class Response<T> {

    public static final int SUCCESS = 0; // 成功状态码
    public static final int FAIL = 1;    // 失败状态码
    public static final int ERROR = 100; // 错误状态码

    //-----------------------------------------------------//

    // 状态码
    private int status;

    // 提示信息
    private String msg;

    // 响应对象
    private T responseBody;

    // 是否成功的标志 这个不参与json的格式化
    private boolean isSuccess;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
