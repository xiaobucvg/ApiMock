package top.xiaobucvg.apimock.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/***
 * 用来获取 webApplicationContext
 *
 * by Mr.Zhang
 */
@Component
public class WebApplicationAware implements ApplicationContextAware {

    private WebApplicationContext webApplicationContext ;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.webApplicationContext = (WebApplicationContext) applicationContext;
    }

    public WebApplicationContext getWebApplicationContext() {
        return webApplicationContext;
    }

}
