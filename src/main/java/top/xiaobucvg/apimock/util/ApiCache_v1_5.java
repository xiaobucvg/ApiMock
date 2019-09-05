package top.xiaobucvg.apimock.util;

import org.springframework.util.StringUtils;
import top.xiaobucvg.apimock.pojo.Api;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/***
 * 1.5版本后重写 api 缓存
 *
 * by Mr.Zhang
 */
public class ApiCache_v1_5 {
    // Api的ID为键，Api对象为值
    private static ConcurrentHashMap<String, Api> apiMap = new ConcurrentHashMap<>();

    /***
    * 检查Api是否合法
    */
    public static boolean checkApi(Api api){
        if(StringUtils.isEmpty(api.getPath())) return false;
        if(StringUtils.isEmpty(api.getId())) return false;
        if(StringUtils.isEmpty(api.getRequestMethod())) return false;
        return true;
    }

    public static void putApi(Api api){
        if(!checkApi(api)) return;
        apiMap.put(api.getId(),api);
    }

    /***
    * 是否包含某个Api
    */
    public static boolean containsApi(Api api){
        return apiMap.containsValue(api);
    }

    public static boolean containsApi(String uuid){
        return apiMap.containsKey(uuid);
    }

    /***
    * 获取所有的Api放在Set里面
    */
    public static Set<Api> getAllApi() {
       Set<Api> set = new HashSet<>();
       apiMap.forEach((k,v)->{
           set.add(v);
       });
       return set;
    }
    
    /***
    * 根据ID从缓存中删除Api
    */
    public static Api removeApi(String id){
        return apiMap.remove(id);
    }

    /***
    * 根据ID找到一个Api
    */
    public static Api getApiById(String id){
        return apiMap.get(id);
    }
}
