package lt.zuul.example.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author leitao.
 * @time: 2017/11/28  13:19
 * @version: 1.0
 * @description: 在请求来临时执行,在route Filter之前执行
 **/
@Component
public class AccessFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    public String filterType() {
        //前置过滤器
        return "pre";
    }

    @Override
    public int filterOrder() {
        //优先级，数字越大，优先级越低
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //是否执行该过滤器，true代表需要过滤
        return true;
    }

    @Override
    public Object run() {
        System.out.println("我是前置过滤器...我在鉴权哟...");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        System.out.println("=========="+request.getParameter("appKey"));
//        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
//
//        String[] methods = request.getParameterValues("method");
        //获取传来的参数accessToken
//        Object accessToken = request.getParameter("accessToken");
//        if(accessToken == null) {
//            log.warn("access token is empty");
            //过滤该请求，不往下级服务去转发请求，到此结束
            //注意，路由转发的停止和继续是由ctx.setSendZuulResponse来控制的，
            // 与下面的return null无关，这个方法的return值没有意义，并没有使用。
//            ctx.setSendZuulResponse(false);
//            ctx.setResponseStatusCode(401);
//            ctx.setResponseBody("{\"result\":\"accessToken is empty!\"}");
//            return null;
//        }
        //如果有token，则进行路由转发
//        log.info("access token ok");
        //这里return的值没有意义，zuul框架没有使用该返回值
        return null;
    }
}
