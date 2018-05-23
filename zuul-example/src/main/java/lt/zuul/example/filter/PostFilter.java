package lt.zuul.example.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author leitao.
 * @time: 2017/11/29  10:55
 * @version: 1.0
 * @description: 请求响应时过滤
 **/
@Component
public class PostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        String contextPath = request.getContextPath();
        String remoteAddr = request.getRemoteAddr();
        System.out.println("POST Filter return result:"+contextPath+"===="+remoteAddr);
        return null;
    }
}
