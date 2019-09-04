package top.xiaobucvg.apimock;

import org.junit.Test;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

public class MainTest {
    @Test
    public void addAnnotation() throws NoSuchMethodException {
    }

    @Test
    public void webApplicationTest(RequestMappingHandlerMapping requestMappingHandlerMapping){
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        for(RequestMappingInfo info : handlerMethods.keySet()){
//            info.get
        }
    }


    private class Demo{
        public void show(){

        }
    }
}
