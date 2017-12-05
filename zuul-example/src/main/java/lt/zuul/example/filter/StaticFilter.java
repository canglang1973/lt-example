package lt.zuul.example.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.filters.StaticResponseFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @author leitao.
 * @time: 2017/11/29  14:44
 * @version: 1.0
 * @description:
 **/

//public class StaticFilter extends StaticResponseFilter {
//
//    @Override
//    public Object uri() {
//        return "www.baidu.com";
//    }
//
//    @Override
//    public String responseBody() {
//        System.out.println("================");
//        return null;
//    }
//}
