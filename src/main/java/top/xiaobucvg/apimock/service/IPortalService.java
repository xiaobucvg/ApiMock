package top.xiaobucvg.apimock.service;

import org.springframework.web.context.WebApplicationContext;
import top.xiaobucvg.apimock.pojo.Api;
import top.xiaobucvg.apimock.pojo.Response;

import java.util.List;

public interface IPortalService {
    Response registerApi(WebApplicationContext webApplicationContext, Api api);

    Response<List<Api>> getAllApi();

    Response unRegisterApi(WebApplicationContext webApplicationContext, Api api, int index);

    Response unRegisterApiById(WebApplicationContext webApplicationContext,String uuid);

    Response unRegisterAllApi(WebApplicationContext webApplicationContext);
}
