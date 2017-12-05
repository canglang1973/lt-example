package lt.zuul.example.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author leitao.
 * @time: 2017/11/29  11:11
 * @version: 1.0
 * @description: 错误过滤器在POST Filter之前执行,只有发现异常才执行
 **/
//@Component
public class ErrorFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
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
        System.out.println("我是错误过滤器...");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String contextPath = request.getContextPath();
        String remoteAddr = request.getRemoteAddr();
        System.out.println("ERROR Filter return result:"+contextPath+"===="+remoteAddr);

        HttpServletResponse response = requestContext.getResponse();
        Throwable throwable = requestContext.getThrowable();
        ZuulException zuulException =null;
        if (throwable.getCause() instanceof ZuulRuntimeException) {
            // this was a failure initiated by one of the local filters
            zuulException= (ZuulException) throwable.getCause().getCause();
        }

        if (throwable.getCause() instanceof ZuulException) {
            // wrapped zuul exception
            zuulException = (ZuulException) throwable.getCause();
        }
        if (null!=zuulException){
            requestContext.setResponseBody("ERROR Filter return result:"+zuulException.getStackTrace());
        }

        return null;
    }
}
