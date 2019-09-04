package top.xiaobucvg.apimock.pojo;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/***
 * Response 对象转换到 Json 时需要配置的过滤器
 * 过滤指定的属性
 *
 * by Mr.Zhang
 */
public class ResponseToJsonFilter extends SimplePropertyPreFilter {

    /***
     * 根据class的 set方法来设置 如：setName 对应的 property 是 name
     *
     * @param properties 需要过滤的属性
     *
     */
    public ResponseToJsonFilter(Class<?> clazz, String... properties) {
        super(clazz, properties);

        // 从 includes 中拿出来 放到 excludes 中
        // 性能会有点问题
        // 可以自己写 PropertyPreFilter 的实现类来解决
        // 这里是为了少些一点代码
        for (String item : properties) {
            if (item != null) {
                super.getIncludes().remove(item);
                super.getExcludes().add(item);
            }
        }
    }

}
