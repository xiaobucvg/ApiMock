package top.xiaobucvg.apimock.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.Objects;

/***
 * Api 的封装
 *
 * by Mr.Zhang
 */
public class Api {

    // UUID
    private String id;

    // API的名字
    private String name;

    // API的描述信息
    private String description;

    // 请求的路径
    private String path;

    // 请求的方式
    @JSONField(name="method")
    private RequestMethod requestMethod;

    @JSONField(name="response")
    // 需要响应的内容
    private String responseBody;

    // 创建时间
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    // 只要请求的路径和方式一样就算同一个api

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
    Api api = (Api) o;
        return Objects.equals(path, api.path) &&
    requestMethod == api.requestMethod;
}

    @Override
    public int hashCode() {
        return Objects.hash(path, requestMethod);
    }

    @Override
    public String toString() {
        return "Api{" +
                "path='" + path + '\'' +
                ", requestMethod=" + requestMethod +
                ", responseBody='" + responseBody + '\'' +
                '}';
    }
}
