package top.xiaobucvg.apimock.util;

import top.xiaobucvg.apimock.pojo.Api;

import java.util.ArrayList;
import java.util.List;

/***
 * Api缓存,将服务运行期间的动态mock的api存在这里
 *
 * by Mr.Zhang
 */
public class ApiCache {

    private static List<Api> apiCache = new ArrayList<>();

    /***
     * 增加一个新的api
     *
     * 如果缓存中已经有了这个api则替换为新的
     * @return int 如果是替换了api返回被替换的索引，如果是增加了api，返回 -1
     */
    public static int addApi(Api api) {
        if (api != null) {
            for (int i = 0; i < apiCache.size(); i++) {
                if (api.equals(apiCache.get(i))) {
                    apiCache.set(i, api);
                    return i;
                }
            }
            apiCache.add(api);
            return -1;
        }
        return -1;
    }

    /***
     * 删除一个指定的api
     *
     * 如果删除失败返回 false
     * 缓存中没有这个api或者要删除的api为null的情况会返回 false
     */
    public static boolean removeApi(Api api) {
        if (api != null) {
            return apiCache.remove(api);
        }
        return false;
    }

    /***
     * 根据索引删除api
     *
     * @param index 要删除的api的索引
     * @return Api 被删除的api
     */
    public static Api removeApi(int index) {
        Api api = apiCache.remove(index);
        return api;
    }

    /***
     * 更新一个指定的api
     *
     * 将api替换为新的
     * 如果没找到要替换的api则什么也不做
     */
    public static void updateApi(Api api) {
        if (api != null)
            for (int i = 0; i < apiCache.size(); i++) {
                if (api.equals(apiCache.get(i))) {
                    apiCache.set(i, api);
                }
            }
    }

    /***
     * 展示所有的api
     */
    public static void showAllApi() {
        for (Api api : apiCache) {
            System.out.println(api);
        }
    }

    /***
     * 获取所有的api
     */
    public static List<Api> getAllApi() {
        return apiCache;
    }

    /***
     * 获取缓存中的api数量
     */
    public static int getApiNumber() {
        return apiCache.size();
    }

    /***
     * 查找api缓存中是否有指定的api
     * 如果存在 返回该api的索引
     * 如果不存在返回 -1
     *
     * @return int 索引，没有返回 -1
     */
    public static int indexOfApi(Api api) {
        if (api != null)
            for (int i = 0; i < apiCache.size(); i++) {
                if (apiCache.get(i).equals(api)) {
                    return i;
                }
            }
        return -1;
    }

    /***
     * 根据索引返回Api对象
     */
    public static Api getApi(int index) {
        return apiCache.get(index);
    }

    /***
     * 根据UUID返回Api对象的索引
     */
    public static int getApiByUUID(String uuid) {
        if (uuid != null) {
            for (int i = 0; i < apiCache.size(); i++) {
                if (apiCache.get(i).getId().equals(uuid)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /***
     * 删除指定uuid的api
     * 删除失败返回 null
     *
     * @return API 删除成功的API
     *
     */
    public static Api deleteApiByUUID(String uuid) {
        int index = getApiByUUID(uuid);
        if (index != -1) {
            return apiCache.remove(index);
        }
        return null;
    }

    /***
    * 删除所有API
    */
    public static boolean removeAll(){
        apiCache = new ArrayList<>();
        return true;
    }
}

