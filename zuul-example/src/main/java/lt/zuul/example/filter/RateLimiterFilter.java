package lt.zuul.example.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lt.zuul.example.dynamicrate.Rate;
import lt.zuul.example.dynamicrate.RateMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author leitao.
 * @time: 2017/12/4  17:20
 * @version: 1.0
 * @description:
 **/
//@Component
public class RateLimiterFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Autowired
    RateMapping rateMapping;

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String method = request.getParameter("method");
        method = "APP";
        //根据接口名控制流量
        Rate rate = rateMapping.get(method);
        if (null!=rate){
            RateLimiter rateLimiter = rate.getRateLimiter();
//            rateLimiter.acquire(5);
            boolean flag = rateLimiter.tryAcquire(rate.getAcquirePermits(),rate.getAcquireTimeout(),rate.getAcquireTimeoutTimeUnit());
            if (!flag){
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(110);
                requestContext.setResponseBody("{\"result\":\"你访问得太频繁了,受不鸟了!\"}");
            }
            System.out.println("==========="+method+"======="+rateLimiter.getRate());
        }
        return null;
    }
}