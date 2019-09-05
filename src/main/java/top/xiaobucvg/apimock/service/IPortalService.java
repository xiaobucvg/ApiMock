package top.xiaobucvg.apimock.service;

import top.xiaobucvg.apimock.pojo.Api;
import top.xiaobucvg.apimock.pojo.Response;

import java.util.Map;
import java.util.Set;

public interface IPortalService {
    Response<Api> registerApi_v1_5(Api api);

    Map<String, Object> registerApiRe(Api api);

    Response<Set<Api>> getAllApi();

    Response unRegisterApiById(String uuid);

    Response unRegisterAllApi();
}
